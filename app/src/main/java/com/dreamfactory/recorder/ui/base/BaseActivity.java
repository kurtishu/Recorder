package com.dreamfactory.recorder.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dreamfactory.recorder.common.Constants;
import com.dreamfactory.recorder.presenter.BasePresenter;
import com.dreamfactory.recorder.service.RecorderService;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewRes());
        ButterKnife.bind(this);
        mPresenter = getPresenter();
    }

    public abstract int getContentViewRes();

    public T getPresenter(){
        return null;
    };

    @Override
    protected void onStart() {
        super.onStart();
        RecorderService.performAction(Constants.ACTIVITY_LIFECYCLE_CHANGE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RecorderService.performAction(Constants.ACTIVITY_LIFECYCLE_CHANGE);
    }
}
