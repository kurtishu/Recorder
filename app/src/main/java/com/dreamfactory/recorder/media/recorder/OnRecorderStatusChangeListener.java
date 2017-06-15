package com.dreamfactory.recorder.media.recorder;


public interface OnRecorderStatusChangeListener {

    void onRecording();

    void onStoped();

    void onPaused();

    void onError();
}
