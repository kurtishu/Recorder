package com.dreamfactory.recorder.media.recorder;

public interface Recorder {

    boolean initializeRecorder();

    void startRecording();

    void stopRecording();

    void pauseRecording();

    void resumeRecording();

    void setOnRecorderStatusChangeListener(OnRecorderStatusChangeListener listener);
}
