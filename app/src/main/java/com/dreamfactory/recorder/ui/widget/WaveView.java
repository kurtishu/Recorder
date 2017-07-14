package com.dreamfactory.recorder.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dreamfactory.recorder.util.LogUtil;

import static com.dreamfactory.recorder.ui.widget.WaveView.Style.Reverse;

/**
 * Created by kurtishu on 7/12/17.
 */

public class WaveView extends View{

    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;
    private Paint mPaint4;

    private Path mPath;
    private int mWidth;
    private int mHeight;
    private float mOffset;
    private ValueAnimator mValueAnimator;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setDither(true);
        mPaint1.setStrokeWidth(3);
        mPaint1.setColor(Color.BLUE);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setDither(true);
        mPaint2.setStrokeWidth(3);
        mPaint2.setColor(Color.GREEN);

        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setDither(true);
        mPaint3.setStrokeWidth(3);
        mPaint3.setColor(Color.WHITE);

        mPath = new Path();

        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);

        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = animation.getAnimatedFraction();
                invalidate();
            }
        });
        //mValueAnimator.start();
        setVisibility(GONE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawPath(getWavePath(mOffset, Style.Normal), mPaint1);
        canvas.drawPath(getWavePath(mOffset, Style.Reverse) , mPaint2);
        canvas.drawPath(getWavePath(mOffset, Style.Double), mPaint3);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }

        LogUtil.i("TAG", "Width=" + mWidth  + " Height=" + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    private Path getWavePath(float offset, Style style) {

        Path path = new Path();

        int x = -mWidth;
        //当前x点坐标（根据动画进度水平推移，一个动画周期推移的距离为一个mWidth）
        x += offset * mWidth;
        //波形的起点
        path.moveTo(x, mHeight / 2);
        //控制点的相对宽度
        int quadWidth = mWidth / 4;
        //控制点的相对高度
        int quadHeight = mHeight / 2;

        if (Style.Normal == style) {
            //第一个周期
            path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
            path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
            //第二个周期
            path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
            path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
        } else if (Reverse == style){
            //第一个周期
            path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
            path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
            //第二个周期
            path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
            path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
        } else if (Style.Double == style) {
            path.rQuadTo(quadWidth / 2, quadHeight, quadWidth, 0);
            path.rQuadTo(quadWidth / 2, -quadHeight, quadWidth, 0);

            path.rQuadTo(quadWidth / 2, quadHeight, quadWidth, 0);
            path.rQuadTo(quadWidth / 2, -quadHeight, quadWidth, 0);

            path.rQuadTo(quadWidth / 2, quadHeight, quadWidth, 0);
            path.rQuadTo(quadWidth / 2, -quadHeight, quadWidth, 0);

            path.rQuadTo(quadWidth / 2, quadHeight, quadWidth, 0);
            path.rQuadTo(quadWidth / 2, -quadHeight, quadWidth, 0);
        }

        //右侧的直线
        path.lineTo(x + mWidth * 2, mHeight);
        //下边的直线
        path.lineTo(x, mHeight);
        //自动闭合补出左边的直线
        path.close();
        return path;
    }

    public void start() {
        setVisibility(VISIBLE);
        if (null != mValueAnimator) {
            if (!mValueAnimator.isRunning()) {
                mValueAnimator.start();
            }
        }
    }

    public void stop() {
        setVisibility(GONE);
        if (null != mValueAnimator) {
            if (mValueAnimator.isRunning()) {
                mValueAnimator.end();
            }
        }
    }

    enum Style {
        Normal,
        Reverse,
        Double
    }
}
