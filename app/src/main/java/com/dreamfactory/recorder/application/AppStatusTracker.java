package com.dreamfactory.recorder.application;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class AppStatusTracker implements Application.ActivityLifecycleCallbacks{

    private static AppStatusTracker sAppStatusTracker;
    private Application mApplication;
    private boolean mIsForground;
    private int mActiveCount;


    private AppStatusTracker(Application application) {
        this.mApplication = application;
        application.registerActivityLifecycleCallbacks(this);
    }

    public static void init(Application application) {
        sAppStatusTracker = new AppStatusTracker(application);
    }

    public static AppStatusTracker getInstance() {
        return sAppStatusTracker;
    }

    public boolean isForground() {
        return mIsForground;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        mActiveCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mIsForground = true;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        mActiveCount--;
        if (mActiveCount == 0) {
            mIsForground = false;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
