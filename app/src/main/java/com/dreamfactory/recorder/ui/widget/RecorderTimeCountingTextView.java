package com.dreamfactory.recorder.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.dreamfactory.recorder.util.StringUtil;


public class RecorderTimeCountingTextView extends android.support.v7.widget.AppCompatTextView {

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
        setText(StringUtil.formatCounting(0));
    }


    public void setRecorderDuration(int duration) {
        setText(StringUtil.formatCounting(duration));
    }
}
