package com.dreamfactory.recorder.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dreamfactory.recorder.R;


public class RecorderButton extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener {

    private boolean mIsStarted;
    private boolean mIsPaused;

    private OnRecorderButtonStatusChangeListener mListener;

    public RecorderButton(Context context) {
        super(context);
        init();
    }

    public RecorderButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecorderButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setPlayDrawable();
        setOnClickListener(this);
    }

    private void setPlayDrawable() {
        setText(R.string.text_recording_start);
        Drawable dra = getResources().getDrawable(R.mipmap.ic_play);
        dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
        setCompoundDrawables(null, dra, null,null);
    }

    public void setPauseDrawable() {
        setText(R.string.text_recording_pause);
        Drawable dra = getResources().getDrawable(R.mipmap.ic_pause);
        dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
        setCompoundDrawables(null, dra, null,null);
    }

    public void stop() {
        setPlayDrawable();
        mIsStarted = false;
        mIsPaused = false;
        if (null != mListener) {
            mListener.onStop(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (!mIsStarted && !mIsPaused) {
            // start recording
            mIsStarted = true;
            setPauseDrawable();
            if (null != mListener) {
                mListener.onStart(this);
            }
        } else if (mIsStarted && !mIsPaused) {
            // pause recording
            mIsPaused = true;
            setPlayDrawable();
            if (null != mListener) {
                mListener.onPause(this);
            }
        } else if (mIsPaused && mIsPaused) {
            // resume recording
            mIsPaused = false;
            setPauseDrawable();
            if (null != mListener) {
                mListener.onResume(this);
            }
        }
    }

    public void setRecorderButtonStatusChangeListener(OnRecorderButtonStatusChangeListener listener) {
        mListener = listener;
    }

    public interface OnRecorderButtonStatusChangeListener {

        void onStart(View view);

        void onPause(View view);

        void onResume(View view);

        void onStop(View view);
    }
}
