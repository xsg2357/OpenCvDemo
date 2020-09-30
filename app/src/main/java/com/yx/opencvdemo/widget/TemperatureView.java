package com.yx.opencvdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 标题 :
 * 编辑 : 向绍谷
 * 日期 : 2020/9/8
 * 描述 :
 */
public class TemperatureView extends View {

    private Paint mOutArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mOutCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTopArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mWaterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int arcWidth = 8; // dp
    private Path path = new Path();
    private int verticalRangeNum = 8;
    private float outerScalePx = 0.3f;
    private float outerScaleBottomPx = 2f;
    private float waterScaleWidth = 5f;

    public TemperatureView(Context context) {
        super(context);
        initView();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setPaint();
    }


    private void setPaint() {

        mOutArcPaint.setColor(Color.parseColor("#1e80c3"));
        mOutArcPaint.setStrokeWidth(dp2px(arcWidth));
        mOutArcPaint.setStyle(Paint.Style.STROKE); // STROKE 实心  FILL 空心

        mOutCirclePaint.setStyle(Paint.Style.FILL);
        mOutCirclePaint.setColor(Color.WHITE);

        mTopArcPaint.setStyle(Paint.Style.FILL);
        mTopArcPaint.setColor(Color.parseColor("#8abde2"));

        mWaterPaint.setStyle(Paint.Style.FILL);
        mWaterPaint.setColor(Color.parseColor("#24cff9"));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        setLayerType(View.LAYER_TYPE_SOFTWARE);
        int width = getWidth();
        int height = getHeight();
        // 外层圆环
        float centerHorLeft = width / 2;
        float centerHorTop = height * 0.78f;
        float centerHorBottom = height - 30;
        float rectHeight = centerHorBottom - centerHorTop; //边长
        float centerHorRight = centerHorLeft + rectHeight;
        // 里面圆
        float innerRadius = dp2px(8);
        float centerTempX = centerHorLeft + rectHeight / 2;
        float centerTempY = centerHorTop + rectHeight / 2;


        // 缺缺
//        path.reset();
//        RectF rectPointF = new RectF();
//        rectPointF.set(centerTempX - dp2px(arcWidth),
//                centerHorTop - dp2px(arcWidth), centerTempX + dp2px(arcWidth),
//                centerHorTop + dp2px(arcWidth)
//        );
//        path.addRect(rectPointF, Path.Direction.CW);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            canvas.clipOutPath(path);
//        } else {
//            canvas.clipPath(path, Region.Op.DIFFERENCE);
//        }
        // 外层圆环
        RectF rectF = new RectF();
        rectF.set(centerHorLeft, centerHorTop, centerHorRight, centerHorBottom);
        canvas.drawArc(rectF, 0, 360, false, mOutArcPaint);
        // 里面圆
        canvas.drawCircle(centerTempX, centerTempY, innerRadius, mOutCirclePaint);

        // 帽帽 -半圆 5-7
        RectF rectFTop = new RectF();
        float rfLeft = centerTempX - dp2px(arcWidth) + dp2px(waterScaleWidth);
        float rfRight = centerTempX + dp2px(arcWidth) - dp2px(waterScaleWidth);

        rectFTop.set(centerTempX - dp2px(arcWidth), dp2px(10), centerTempX + dp2px(arcWidth), dp2px(20));
        canvas.drawArc(rectFTop, 0, -180, true, mTopArcPaint);

        // 水银
        path.reset();
        float rTop = dp2px(15);
        float rBottom = centerHorTop + dp2px(arcWidth) - 5;
        RectF rectFWater = new RectF();
        rectFWater.set(rfLeft, rTop, rfRight, rBottom);
        canvas.drawRect(rectFWater, mWaterPaint);

        // 左刻度
        float oHeight = rBottom - rTop;
        float oRectLeft = centerTempX - dp2px(arcWidth);
//        float oRectRight = rfLeft - dp2px(0.5f);
        float oRectRight = rfLeft ;
        float avgHeight = oHeight / verticalRangeNum;
        float temp = 0f; //临时存储上一个端点
        for (int i = 0; i < verticalRangeNum; i++) {
            RectF rectFRange = new RectF();
            float rangeHeight = rTop + avgHeight * (i + 1);
            if (i == 0) {
                rectFRange.set(oRectLeft, rTop, oRectRight, rangeHeight);
            } else {
                rectFRange.set(oRectLeft, temp + dp2px(outerScalePx), oRectRight, rangeHeight);
            }
            float degreeScale = avgHeight / 5;
            if (i != verticalRangeNum - 1) {
                for (int j = 0; j < 5; j++) {
                    if (j != 4) {
                        Path dePath = new Path();
                        dePath.reset();
                        RectF rectFScale = new RectF();
                        float vTop = rTop + avgHeight * i + degreeScale * (j + 1) - 2f;
                        float vBottom = vTop + outerScaleBottomPx;
                        rectFScale.set(oRectLeft, vTop, oRectRight - 8, vBottom);
                        dePath.addRoundRect(rectFScale, 3, 3, Path.Direction.CCW);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            canvas.clipOutPath(dePath);
                        } else {
                            canvas.clipPath(dePath, Region.Op.DIFFERENCE);
                        }
                    }
                }
            }
            temp = rangeHeight;
            canvas.drawRect(rectFRange, mTopArcPaint);
        }

        // 右刻度
        float oRectRightLeft =  rfRight;
        float oRectRightRight = centerTempX + dp2px(arcWidth) ;
//        float oRectRightRight = rfRight + dp2px(0.5f);
        float tempRight = 0f; //临时存储上一个端点
        for (int i = 0; i < verticalRangeNum; i++) {
            RectF rectFRange = new RectF();
            float rangeHeight = rTop + avgHeight * (i + 1);
            if (i == 0) {
                rectFRange.set(oRectRightLeft, rTop, oRectRightRight, rangeHeight);
            } else {
                rectFRange.set(oRectRightLeft, tempRight + dp2px(outerScalePx), oRectRightRight, rangeHeight);
            }
            float degreeScale = avgHeight / 5;
            if (i != verticalRangeNum - 1) {
                for (int j = 0; j < 5; j++) {
                    if (j != 4) {
                        Path dePath = new Path();
                        dePath.reset();
                        RectF rectFScale = new RectF();
                        float vTop = rTop + avgHeight * i + degreeScale * (j + 1) - 1f;
                        float vBottom = vTop + outerScaleBottomPx;
                        rectFScale.set(oRectRightLeft+8, vTop, oRectRightRight , vBottom);
                        dePath.addRoundRect(rectFScale, 3, 3, Path.Direction.CW);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            canvas.clipOutPath(dePath);
                        } else {
                            canvas.clipPath(dePath, Region.Op.DIFFERENCE);
                        }
                    }
                }
            }
            tempRight = rangeHeight;
            canvas.drawRect(rectFRange, mTopArcPaint);
        }

    }


    private float dp2px(float dpi) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return dpi * density + 0.5f;
    }

}
