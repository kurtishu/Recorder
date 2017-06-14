package com.dreamfactory.recorder.ui;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.dreamfactory.recorder.R;
import com.dreamfactory.recorder.presenter.MainPresenter;
import com.dreamfactory.recorder.ui.base.BaseActivity;
import com.dreamfactory.recorder.ui.iview.IMainView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {

    @BindView(R.id.recording_button)
    TextView mRecordingButton;

    @BindView(R.id.stop_button)
    TextView mStopRecordingButton;

    @Override
    public void initViews() {

    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter getPresenter() {
        return new MainPresenter(this);
    }

    @OnClick({R.id.recording_button, R.id.stop_button})
    public void OnClick(View view) {
        if (view.getId() == R.id.recording_button) {
           if (!mRecordingButton.getText().toString().equals("停止")) {
               mRecordingButton.setText("停止");
               Drawable dra = getResources().getDrawable(R.mipmap.ic_pause);
               dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
               mRecordingButton.setCompoundDrawables(null, dra, null,null);
           } else {
               mRecordingButton.setText("录音");
               Drawable dra = getResources().getDrawable(R.mipmap.ic_play);
               dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
               mRecordingButton.setCompoundDrawables(null, dra, null,null);
           }
        } else if (view.getId() == R.id.stop_button) {

        }
    }


}
