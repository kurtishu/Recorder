package com.dreamfactory.recorder.media.recorder;


import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.dreamfactory.recorder.common.Constants;
import com.dreamfactory.recorder.util.DateTimeUtils;
import com.dreamfactory.recorder.util.FileUtil;
import com.dreamfactory.recorder.util.LogUtil;
import com.dreamfactory.recorder.util.ThreadUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Mp3Recorder implements Recorder{

    private static final String TAG = Mp3Recorder.class.getName();

    private static final int AUDIO_RECORDER_SAMPLE_RATE = 8000;
    private static final short AUDIO_RECORDER_BPS = 8; // bit per sample
    private static final short AUDIO_RECORDER_CHANNELS = 1; // mono
    private static final int AUDIO_RECORDER_CHANNEL_CFG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_RECORDER_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    private volatile AudioRecord mAudioRecord;
    private volatile int mBufferSize;

    private volatile boolean mIsRecording = false;
    private Thread mInitialRecorderThread;
    private volatile int mRecorderSize;
    private volatile File mRecorderFile = null;
    private volatile RandomAccessFile mRecorderWriter;
    private String mRecorderFilePath;

    private OnRecorderStatusChangeListener mListener;

    @Override
    public boolean initializeRecorder() {
        mBufferSize = AudioRecord.getMinBufferSize(AUDIO_RECORDER_SAMPLE_RATE, AUDIO_RECORDER_CHANNEL_CFG, AUDIO_RECORDER_FORMAT);
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, AUDIO_RECORDER_SAMPLE_RATE, AUDIO_RECORDER_CHANNEL_CFG, AUDIO_RECORDER_FORMAT, mBufferSize);

        return (mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED);
    }

    @Override
    public void startRecording() {
        mIsRecording = true;
        mRecorderFilePath = Constants.FOLDER_URL + File.separator + DateTimeUtils.getDefaultDate() + Constants.RECORDING_NAME;
        record(false);
    }

    @Override
    public void stopRecording() {
        mIsRecording = false;

        if(mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
        }
        if (null != mListener) {
            mListener.onStoped();
        }
    }

    @Override
    public void pauseRecording() {
        mIsRecording = false;
        if (null != mListener) {
            mListener.onPaused();
        }
    }

    @Override
    public void resumeRecording() {
        mIsRecording = true;
        record(true);
    }

    @Override
    public void setOnRecorderStatusChangeListener(OnRecorderStatusChangeListener listener) {
        mListener = listener;
    }

    private void record(final boolean isAppending) {

        if (!ThreadUtil.isTaskAlive(mInitialRecorderThread)) {
            mInitialRecorderThread = createInitialRecorderThread(isAppending);
            mInitialRecorderThread.start();
        }
    }

    private Thread createInitialRecorderThread(final boolean isAppending) {
        return new Thread() {
            public synchronized void run() {
                if(!isAppending) {
                    // When creating a brand new recording, prepare the file
                    FileUtil.createFolder(Constants.FOLDER_URL);
                    mRecorderFile = new File(mRecorderFilePath);
                    mRecorderSize = 0;

                    // If file already exists then delete it
                    if(mRecorderFile.exists()) {
                        FileUtil.deleteFile(mRecorderFile);
                    }
                }

                if (initializeRecorder()) {
                    performRecording(isAppending);
                }
            }
        };
    }

    private void performRecording(final boolean isAppending) {
        try {
            mAudioRecord.startRecording();
            if (mAudioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                return;
            }
            saveRecording(isAppending);
        } catch (Exception e) {
            if (null != mListener) {
                mListener.onError();
            }
            LogUtil.e(TAG, e.getMessage());
        }

        mIsRecording = false;
    }

    private void saveRecording(final boolean isAppending) {
        try {
            mRecorderWriter = new RandomAccessFile(mRecorderFile, "rw");

            if(!isAppending) {
                // For a brand new recording, write the file header
                writeWaveHeader();
            }
            else {
                // For a continued recording, prepare for appending new data to the existing file
                mRecorderWriter.seek(mRecorderFile.length());
            }

            byte[] data = new byte[mBufferSize];
            byte[] data8bit = new byte[mBufferSize/2];

            while (mIsRecording) {
                int read = mAudioRecord.read(data, 0, mBufferSize);
                if (read != AudioRecord.ERROR_INVALID_OPERATION) {
                    convertTo8bit(data, data8bit, read);
                    mRecorderSize += read / 2;
                    mRecorderWriter.write(data8bit, 0, read / 2);
                }
                if (null != mListener) {
                    mListener.onRecording();
                }
            }
            updateWaveHeader();
        } catch (Exception e) {
            if (null != mListener) {
                mListener.onError();
            }
            LogUtil.e(TAG, e.getMessage());
        } finally {
            stopRecording();
            FileUtil.closeQuietly(mRecorderWriter);
            mRecorderWriter = null;
        }
    }

    private void convertTo8bit(byte[] src, byte[] dest, int srcLen) {
        for (int srcIndex = 0, destIndex = 0; srcIndex < srcLen; srcIndex += 2, destIndex++) {
            // Using only the high byte of the 16-bit frame
            // and shifting it from -128..127 range to 0..255 range
            dest[destIndex] = (byte)(128 + src[srcIndex+1]);
        }
    }

    private void writeWaveHeader() throws IOException {
        mRecorderWriter.setLength(0); // Set file length to 0, to prevent unexpected behavior in case the file already existed
        mRecorderWriter.writeBytes("RIFF");
        mRecorderWriter.writeInt(0); // Final file size not known yet, write 0
        mRecorderWriter.writeBytes("WAVE");
        mRecorderWriter.writeBytes("fmt ");
        mRecorderWriter.writeInt(Integer.reverseBytes(16)); // Sub-chunk size, 16 for PCM
        mRecorderWriter.writeShort(Short.reverseBytes((short) 1)); // AudioFormat, 1 for PCM
        mRecorderWriter.writeShort(Short.reverseBytes(AUDIO_RECORDER_CHANNELS)); // Number of channels, 1 for mono, 2 for stereo
        mRecorderWriter.writeInt(Integer.reverseBytes(AUDIO_RECORDER_SAMPLE_RATE)); // Sample rate
        mRecorderWriter.writeInt(Integer.reverseBytes(AUDIO_RECORDER_SAMPLE_RATE * AUDIO_RECORDER_CHANNELS * AUDIO_RECORDER_BPS / 8)); // Byte rate: SampleRate * NumberOfChannels * BitsPerSample/8
        mRecorderWriter.writeShort(Short.reverseBytes((short) (AUDIO_RECORDER_CHANNELS * AUDIO_RECORDER_BPS / 8))); // Block align: NumberOfChannels*BitsPerSample/8
        mRecorderWriter.writeShort(Short.reverseBytes(AUDIO_RECORDER_BPS)); // Bits per sample
        mRecorderWriter.writeBytes("data");
        mRecorderWriter.writeInt(0); // Data chunk size not known yet, write 0
    }

    private void updateWaveHeader() throws IOException {
        mRecorderWriter.seek(4); // Write size to RIFF header (36 bytes)
        mRecorderWriter.writeInt(Integer.reverseBytes(36 + mRecorderSize));

        mRecorderWriter.seek(40); // Write size to Subchunk2Size field
        mRecorderWriter.writeInt(Integer.reverseBytes(mRecorderSize));
    }

}
