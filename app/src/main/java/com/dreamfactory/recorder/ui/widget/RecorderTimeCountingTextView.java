package com.dreamfactory.recorder.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import com.dreamfactory.recorder.util.LogUtil;
import com.dreamfactory.recorder.util.StringUtil;

public class RecorderTimeCountingTextView extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = RecorderTimeCountingTextView.class.getName();
    private static final int TIME_COUNTING_DURATION = 1000;
    private int mRecordingSeconds = 0;
    private Handler mCountingHandler = new Handler();

    public RecorderTimeCountingTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public RecorderTimeCountingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RecorderTimeCountingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        setText(StringUtil.formatCounting(mRecordingSeconds));
    }

    public void start() {
        mRecordingSeconds = 0;
        setText(StringUtil.formatCounting(mRecordingSeconds));
        mCountingHandler.postDelayed(mCountingRunnable, TIME_COUNTING_DURATION);
    }

    public void stop() {
        mCountingHandler.removeCallbacks(mCountingRunnable);
    }

    public void pause() {
        mCountingHandler.removeCallbacks(mCountingRunnable);
    }

    public void resume() {
        mCountingHandler.postDelayed(mCountingRunnable, TIME_COUNTING_DURATION);
    }

    public int getRecordingSeconds() {
        return mRecordingSeconds;
    }

    private Runnable mCountingRunnable = new Runnable() {
        @Override
        public void run() {
            setText(StringUtil.formatCounting(++mRecordingSeconds));
            LogUtil.i(TAG, "mRecordingSeconds = " + mRecordingSeconds );
            mCountingHandler.postDelayed(mCountingRunnable, TIME_COUNTING_DURATION);
        }
    };
}
