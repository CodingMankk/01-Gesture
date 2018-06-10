package com.oztaking.www.gesture;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * @function:
 */

public class zoomImage extends AppCompatImageView implements ScaleGestureDetector
        .OnScaleGestureListener,
        View.OnTouchListener, OnGlobalLayoutListener {

    private static final float SCALE_MAX = 4.0f; //??
    //    初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
    private float initScale = 1.0f;
    //    存放矩阵的9个值
    private final float[] matrixValues = new float[9];
    //缩放的手势检测
    private ScaleGestureDetector mScaleGestureDetetor = null;
    private final Matrix mScaleMatrix = new Matrix();


    private boolean once = true;


    public zoomImage(Context context) {
        this(context, null);
    }

    public zoomImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetetor = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }

    /**
     * 在onScale的回调中对图片进行缩放的控制，首先进行缩放范围的判断，然后设置mScaleMatrix的scale值
     *
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() == null) {
            return true;
        }

        //控制缩放的范围
        if ((scale < SCALE_MAX && scaleFactor > 1.0f)
                || (scale > initScale && scaleFactor < 1.0f)) {
            //最大值最小值的判断
            if (scaleFactor * scale < initScale) {
                scaleFactor = initScale / scale;
            }

            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }

            //            设置缩放比例

            mScaleMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mScaleMatrix);

        }

        return true;
    }

    private float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * OnTouchListener的MotionEvent交给ScaleGestureDetector进行处理
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mScaleGestureDetetor.onTouchEvent(event);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 我们在onGlobalLayout的回调中，根据图片的宽和高以及屏幕的宽和高，
     * 对图片进行缩放以及移动至屏幕的中心。如果图片很小，那就正常显示，不放大了
     */
    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable d = getDrawable();
            if (d == null) {
                return;
            }
            int width = getWidth();
            int height = getHeight();

            //取图片的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            float scale = 1.0f;
            //如果屏幕的宽高大于或者高于屏幕，则缩放至屏幕的宽或者高
            if (dw > width && dh <= height) {
                scale = width * 1.0f / dw;
            }

            if (dh > height && dh <= width) {
                scale = height * 1.0f / dh;
            }

            if (dw > width && dh > height) {
                scale = Math.min(dw * 1.0f / width, dh * 1.0f / height);
            }

            initScale = scale;
            //图片移到屏幕中心
            mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
            mScaleMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            once = false;

        }
    }

}
