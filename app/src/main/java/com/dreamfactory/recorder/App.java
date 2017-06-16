package com.dreamfactory.recorder;

import android.app.Application;
import android.content.Context;

import com.dreamfactory.recorder.application.AppStatusTracker;

public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        AppStatusTracker.init(this);
        mInstance = this;
    }

    public static Context getContext() {
        return mInstance;
    }
}
