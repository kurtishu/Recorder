package com.dreamfactory.recorder.ui;

import android.view.View;
import android.widget.TextView;

import com.dreamfactory.recorder.R;
import com.dreamfactory.recorder.presenter.MainPresenter;
import com.dreamfactory.recorder.ui.base.BaseActivity;
import com.dreamfactory.recorder.ui.iview.IMainView;
import com.dreamfactory.recorder.ui.widget.RecorderButton;
import com.dreamfactory.recorder.ui.widget.RecorderTimeCountingTextView;
import com.dreamfactory.recorder.ui.widget.WaveView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {

    @BindView(R.id.recording_button)
    RecorderButton mRecordingButton;

    @BindView(R.id.stop_button)
    TextView mStopRecordingButton;

    @BindView(R.id.recording_time_text_view)
    RecorderTimeCountingTextView mTimeView;

    @BindView(R.id.recording_waveView)
    WaveView mWaveView;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        mRecordingButton.setRecorderButtonStatusChangeListener(mButtonStatusChangeListener);
    }

    @Override
    public MainPresenter getPresenter() {
        return new MainPresenter(this);
    }

    @OnClick(R.id.stop_button)
    public void OnClick(View view) {
        mRecordingButton.stop();
    }

    private RecorderButton.OnRecorderButtonStatusChangeListener mButtonStatusChangeListener = new RecorderButton.OnRecorderButtonStatusChangeListener() {
        @Override
        public void onStart(View view) {
            mPresenter.startRecording();
            mTimeView.start();
            mWaveView.start();
        }

        @Override
        public void onPause(View view) {
            mPresenter.pauseRecording();
            mTimeView.pause();
            mWaveView.stop();
        }

        @Override
        public void onResume(View view) {
            mPresenter.resumeRecording();
            mTimeView.resume();
            mWaveView.start();
        }

        @Override
        public void onStop(View view) {
            mPresenter.stopRecording();
            mTimeView.stop();
            mWaveView.stop();
        }
    };
}
