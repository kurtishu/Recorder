package com.dreamfactory.recorder.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.dreamfactory.recorder.common.Constants;
import com.dreamfactory.recorder.media.recorder.Mp3Recorder;
import com.dreamfactory.recorder.media.recorder.OnRecorderStatusChangeListener;
import com.dreamfactory.recorder.media.recorder.Recorder;
import com.dreamfactory.recorder.util.LogUtil;

public class RecorderService extends Service implements OnRecorderStatusChangeListener{

    private static final String TAG = RecorderService.class.getName();
    private Recorder mRecorder;

    public RecorderService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRecorder = new Mp3Recorder();
        mRecorder.setOnRecorderStatusChangeListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (null != intent && null != mRecorder) {
            String action = intent.getStringExtra(Constants.KEY_ACTION_RECORDING);
            if (Constants.ACTION_START.equals(action)) {
                Log.i(TAG, "ACTION_START");
                mRecorder.startRecording();
            } else if (Constants.ACTION_PAUSE.equals(action)) {
                mRecorder.pauseRecording();
            } else if (Constants.ACTION_STOP.equals(action)) {
                Log.i(TAG, "ACTION_STOP");
                mRecorder.stopRecording();
            }
        }

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onRecording() {
        LogUtil.i(TAG, "onRecording...");
    }

    @Override
    public void onStoped() {
        LogUtil.i(TAG, "onStoped...");
    }

    @Override
    public void onPaused() {
        LogUtil.i(TAG, "onPaused...");
    }

    @Override
    public void onError() {
        LogUtil.i(TAG, "onError...");
    }
}
