package com.dreamfactory.recorder.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.dreamfactory.recorder.App;
import com.dreamfactory.recorder.R;
import com.dreamfactory.recorder.application.AppStatusTracker;
import com.dreamfactory.recorder.common.Constants;
import com.dreamfactory.recorder.media.recorder.Mp3Recorder;
import com.dreamfactory.recorder.media.recorder.OnRecorderStatusChangeListener;
import com.dreamfactory.recorder.media.recorder.Recorder;
import com.dreamfactory.recorder.ui.MainActivity;
import com.dreamfactory.recorder.util.LogUtil;
import com.dreamfactory.recorder.util.NotificationUtil;

public class RecorderService extends Service implements OnRecorderStatusChangeListener{

    private static final String TAG = RecorderService.class.getName();
    private Recorder mRecorder;
    private boolean mIsRecording;

    public RecorderService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRecorder = new Mp3Recorder();
        mRecorder.setOnRecorderStatusChangeListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (null != intent && null != mRecorder) {
            String action = intent.getStringExtra(Constants.KEY_ACTION_RECORDING);
            if (Constants.ACTION_START.equals(action)) {
                Log.i(TAG, "ACTION_START");
                mRecorder.startRecording();
            } else if (Constants.ACTION_PAUSE.equals(action)) {
                mRecorder.pauseRecording();
            } else if (Constants.ACTION_RESUME.equals(action)) {
                Log.i(TAG, "ACTION_RESUME");
                mRecorder.resumeRecording();
            } else if (Constants.ACTION_STOP.equals(action)) {
                Log.i(TAG, "ACTION_STOP");
                mRecorder.stopRecording();
            } else if (Constants.ACTIVITY_LIFECYCLE_CHANGE.equals(action)) {
                // Check whether activity enter backend
                onActivityEnterBackend();
            }
        }

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onRecording() {
        //LogUtil.i(TAG, "onRecording...");
        mIsRecording = true;
    }

    @Override
    public void onStoped() {
        LogUtil.i(TAG, "onStoped...");
        mIsRecording = false;
    }

    @Override
    public void onPaused() {
        LogUtil.i(TAG, "onPaused...");
    }

    @Override
    public void onError() {
        LogUtil.i(TAG, "onError...");
    }


    public static void performAction(String action) {
        Intent intent = new Intent(App.getContext(), RecorderService.class);
        intent.putExtra(Constants.KEY_ACTION_RECORDING, action);
        App.getContext().startService(intent);
    }

    public void onActivityEnterBackend() {
        if (!AppStatusTracker.getInstance().isForground() && mIsRecording) {
            Intent intent = new Intent(this, MainActivity.class);
            // App enter backend, should show notification
            NotificationUtil.showNotification(RecorderService.this,
                    1, getString(R.string.app_name),
                    getString(R.string.text_recording_ing), intent);
        } else {
            // Clear notification
            NotificationUtil.cancelAllNotifications();
        }
    }
}
