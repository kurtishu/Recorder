package com.dreamfactory.recorder.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dreamfactory.recorder.presenter.BasePresenter;

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

}
