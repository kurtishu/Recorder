package com.dreamfactory.recorder.presenter;

import com.dreamfactory.recorder.ui.iview.IBaseView;

public class BasePresenter<T extends IBaseView> {

    T mBaseView;

    public BasePresenter(T baseView) {
        mBaseView = baseView;
        if (null != baseView) {
            baseView.initViews();
        }
    }

    public void onDetach() {
        mBaseView = null;
    }
}
