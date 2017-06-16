package com.dreamfactory.recorder.presenter;

import android.content.Intent;

import com.dreamfactory.recorder.App;
import com.dreamfactory.recorder.common.Constants;
import com.dreamfactory.recorder.service.RecorderService;
import com.dreamfactory.recorder.ui.iview.IMainView;


public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(IMainView mainView) {
        super(mainView);
    }

    public void startRecording() {
        RecorderService.performAction(Constants.ACTION_START);
    }

    public void stopRecording() {
        RecorderService.performAction(Constants.ACTION_STOP);
    }

    public void pauseRecording() {
        RecorderService.performAction(Constants.ACTION_PAUSE);
    }

    public void resumeRecording() {
        RecorderService.performAction(Constants.ACTION_RESUME);
    }


}
