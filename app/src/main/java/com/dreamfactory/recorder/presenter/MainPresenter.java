package com.dreamfactory.recorder.presenter;

import android.os.Handler;

import com.dreamfactory.recorder.common.Constants;
import com.dreamfactory.recorder.service.RecorderService;
import com.dreamfactory.recorder.ui.iview.IMainView;
import com.dreamfactory.recorder.util.LogUtil;


public class MainPresenter extends BasePresenter<IMainView> {

    private static final String TAG = MainPresenter.class.getName();

    private static final int TIME_COUNTING_DURATION = 1000;
    private int mRecordingSeconds = 0;
    private Handler mCountingHandler = new Handler();

    public MainPresenter(IMainView mainView) {
        super(mainView);
    }

    public void startRecording() {
        RecorderService.performAction(Constants.ACTION_START);
        mRecordingSeconds = 0;
        mView.updateRecordingState(mRecordingSeconds);
        mCountingHandler.postDelayed(mCountingRunnable, TIME_COUNTING_DURATION);
    }

    public void stopRecording() {
        RecorderService.performAction(Constants.ACTION_STOP);
        mRecordingSeconds = 0;
        mView.updateRecordingState(mRecordingSeconds);
        mView.stopRecord();
        mCountingHandler.removeCallbacks(mCountingRunnable);
    }

    public void pauseRecording() {
        RecorderService.performAction(Constants.ACTION_PAUSE);
        mView.stopRecord();
        mCountingHandler.removeCallbacks(mCountingRunnable);
    }

    public void resumeRecording() {
        RecorderService.performAction(Constants.ACTION_RESUME);
        mView.updateRecordingState(mRecordingSeconds);
        mCountingHandler.postDelayed(mCountingRunnable, TIME_COUNTING_DURATION);
    }

    private Runnable mCountingRunnable = new Runnable() {
        @Override
        public void run() {
            mRecordingSeconds++ ;
            LogUtil.i(TAG, "mRecordingSeconds = " + mRecordingSeconds );
            mView.updateRecordingState(mRecordingSeconds);
            mCountingHandler.postDelayed(mCountingRunnable, TIME_COUNTING_DURATION);
        }
    };

}
