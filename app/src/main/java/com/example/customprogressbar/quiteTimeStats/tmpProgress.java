package com.example.customprogressbar.quiteTimeStats;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

//trenutno nije koristeno, zaobli rubove na progress baru, ali ne radi onda gradient

/**
 * Simple single android view component that can be used to showing a round progress bar.
 * It can be customized with size, stroke size, colors and text etc.
 * Progress change will be animated.
 * Created by Kristoffer, http://kmdev.se
 */
public class tmpProgress extends View {

    private int mViewWidth;
    private int mViewHeight;

    private final float mStartAngle = -90;      // Always start from top (default is: "3 o'clock on a watch.")
    private float mSweepAngle = 0;              // How long to sweep from mStartAngle
    private float mMaxSweepAngle = 360;         // Max degrees to sweep = full circle
    private int mStrokeWidth = 50;              // Width of outline
    private int mMaxProgress = 120;             // Max progress to use
    private boolean mRoundedCorners = true;     // Set to true if rounded corners should be applied to outline ends
    private int mProgressColor = Color.RED;   // Outline color
    private LinearGradient gradient = new LinearGradient(
            0f, 0, 100, 100,
            Color.BLACK,
            Color.WHITE,
            Shader.TileMode.CLAMP);

    private final Paint mPaint;                 // Allocate paint outside onDraw to avoid unnecessary object creation

    public tmpProgress(Context context) {
        this(context, null);
    }

    public tmpProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public tmpProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initMeasurements();
        drawOutlineArc(canvas);
    }

    private void initMeasurements() {
        mViewWidth = getWidth();
        mViewHeight = getHeight();
    }

    private void drawOutlineArc(Canvas canvas) {

        final int diameter = Math.min(mViewWidth, mViewHeight);
        final float pad = mStrokeWidth / 2.0f;
        final RectF outerOval = new RectF(pad, pad, diameter - pad, diameter - pad);

        mPaint.setColor(mProgressColor);
//        mPaint.setShader(gradient);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(mRoundedCorners ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(outerOval, mStartAngle, mSweepAngle, false, mPaint);
    }

    private float calcSweepAngleFromProgress(int progress) {
        return (mMaxSweepAngle / mMaxProgress) * progress;
    }

    /**
     * Set progress of the circular progress bar.
     * @param progress progress between 0 and max.
     */
    public void setProgress(int progress) {
        mSweepAngle = calcSweepAngleFromProgress(progress);
        invalidate();
    }

    public void setProgressColor(int color) {
        mProgressColor = color;
        invalidate();
    }

    public void setProgressWidth(int width) {
        mStrokeWidth = width;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }


    /**
     * Toggle this if you don't want rounded corners on progress bar.
     * Default is true.
     * @param roundedCorners true if you want rounded corners of false otherwise.
     */
    public void useRoundedCorners(boolean roundedCorners) {
        mRoundedCorners = roundedCorners;
        invalidate();
    }
}
