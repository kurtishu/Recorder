package com.dreamfactory.recorder.presenter;

import com.dreamfactory.recorder.ui.iview.IBaseView;

public class BasePresenter<T extends IBaseView> {

    T mView;

    public BasePresenter(T baseView) {
        mView = baseView;
        if (null != baseView) {
            baseView.initViews();
        }
    }

    public void onDetach() {
        mView = null;
    }
}
