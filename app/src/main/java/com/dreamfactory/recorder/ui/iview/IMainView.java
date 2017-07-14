package com.dreamfactory.recorder.ui.iview;


public interface IMainView extends IBaseView {

    void updateRecordingState(int recordDuration);

    void stopRecord();
}
