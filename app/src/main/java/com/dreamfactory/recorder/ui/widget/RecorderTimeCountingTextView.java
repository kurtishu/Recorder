package com.dreamfactory.recorder.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.dreamfactory.recorder.util.StringUtil;

public class RecorderTimeCountingTextView extends android.support.v7.widget.AppCompatTextView {

    public RecorderTimeCountingTextView(Context context) {
        super(context);
        init();
    }

    public RecorderTimeCountingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecorderTimeCountingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setText(StringUtil.formatCounting(0));
    }

}
