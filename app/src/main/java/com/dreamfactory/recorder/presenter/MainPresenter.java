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
        mView.onRecording();
        performAction(Constants.ACTION_START);
    }

    public void stopRecording() {
        mView.onStoped();
        performAction(Constants.ACTION_STOP);
    }

    public void pauseRecording() {
        mView.onStoped();
        performAction(Constants.ACTION_PAUSE);
    }

    private void performAction(String action) {
        Intent intent = new Intent(App.getContext(), RecorderService.class);
        intent.putExtra(Constants.KEY_ACTION_RECORDING, action);
        App.getContext().startService(intent);
    }
}
