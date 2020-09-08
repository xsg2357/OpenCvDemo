package com.yx.opencvdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xsg on 2020/9/4.
 */

public class WaveView extends View implements Runnable {


//    Paint.ANTI_ALIAS_FLAG ：抗锯齿标志
//    Paint.FILTER_BITMAP_FLAG : 使位图过滤的位掩码标志
//    Paint.DITHER_FLAG : 使位图进行有利的抖动的位掩码标志
//    Paint.UNDERLINE_TEXT_FLAG : 下划线
//    Paint.STRIKE_THRU_TEXT_FLAG : 中划线
//    Paint.FAKE_BOLD_TEXT_FLAG : 加粗
//    Paint.LINEAR_TEXT_FLAG : 使文本平滑线性扩展的油漆标志
//    Paint.SUBPIXEL_TEXT_FLAG : 使文本的亚像素定位的绘图标志
//    Paint.EMBEDDED_BITMAP_TEXT_FLAG : 绘制文本时允许使用位图字体的绘图标志
    private static final int DEFAULT_WAVE_1_COLOR = 0x999bcee7;
    private static final int DEFAULT_WAVE_2_COLOR = 0x9958bae7;
    private static final int DEFAULT_WAVE_3_COLOR = 0x999bcee7;
    private static final int DEFAULT_WAVE_4_COLOR = 0xffffffff;

    private float mAngle = 0;
    private boolean mIsRunning = false;

    private Paint mWavePaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mWavePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mWavePaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mWavePaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mWavePaintOvalStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mWavePaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mContainerPath = new Path();

    private float mWaveSpeed = 5f;
    private float mWaveRange = 15;
    private int mWave1Color = DEFAULT_WAVE_1_COLOR;
    private int mWave2Color = DEFAULT_WAVE_2_COLOR;
    private int mWave3Color = DEFAULT_WAVE_3_COLOR;
    private int mWaveTextColor = DEFAULT_WAVE_4_COLOR;
    private int mWaveArcColor = Color.BLACK;
    private float mStrokeWidth = 5;
    private float mWaveHeightPercent = 0.5f;
    private boolean mIsCircle = false;
    private float mPeriod;
    private String value = "0.00";
    private float mCurrentHeight = 0;
    private boolean isUserGradients = false;

    private int[] waveGradient1 = null;
    private int[] waveGradient2 = null;
    private int[] waveGradientLine = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public WaveView(Context context) {
        super(context);
        initView(null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.WaveView));
    }

    private void initAttrs(TypedArray typedArray) {
        TypedArray array = typedArray;
        initView(array);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.WaveView,
                defStyleAttr, 0));
    }

    @RequiresApi(21)
    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.WaveView,
                defStyleAttr, defStyleRes));
    }

    private void initView(TypedArray array) {
        if (array != null) {
            mWaveSpeed = array.getFloat(R.styleable.WaveView_waveSpeed, 5f);
            mWaveRange = array.getDimension(R.styleable.WaveView_waveRange, 15);
            mWave1Color = array.getColor(R.styleable.WaveView_wave1Color,
                    DEFAULT_WAVE_1_COLOR);
            mWave2Color = array.getColor(R.styleable.WaveView_wave2Color,
                    DEFAULT_WAVE_2_COLOR);
            mStrokeWidth = array.getDimension(R.styleable.WaveView_waveStrokeWidth, 5);
            mWaveHeightPercent = array.getFloat(R.styleable.WaveView_waveHeightPercent, 0.5f);
            mIsCircle = array.getBoolean(R.styleable.WaveView_isCircle, false);
            mPeriod = array.getFloat(R.styleable.WaveView_period, 1.0f);
        } else {
            mWaveSpeed = 5f;
            mWaveRange = 15;
            mWave1Color = DEFAULT_WAVE_1_COLOR;
            mWave2Color = DEFAULT_WAVE_2_COLOR;
            mStrokeWidth = 5;
            mWaveHeightPercent = 0.5f;
            mIsCircle = false;
            mPeriod = 1.0f;
        }

        initPaint();
    }

    private void initPaint() {
        mWavePaint1.setStyle(Paint.Style.FILL_AND_STROKE);
        mWavePaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        setPaint();
    }

    private void setPaint() {
        mWavePaint1.setStrokeWidth(mStrokeWidth);
        mWavePaint1.setColor(mWave1Color);

        mWavePaint2.setStrokeWidth(mStrokeWidth);
        mWavePaint2.setColor(mWave2Color);
        mWavePaint3.setStrokeWidth(mStrokeWidth);
        mWavePaint3.setColor(mWave3Color);
        mWavePaintOvalStroke.setStrokeWidth(mStrokeWidth);
        mWavePaintOvalStroke.setStyle(Paint.Style.STROKE);
        mWavePaintOvalStroke.setColor(mWaveArcColor);
        mWavePaintOvalStroke.setAntiAlias(true);
        mWavePaint4.setStrokeWidth(mStrokeWidth);
        mWavePaint4.setStyle(Paint.Style.STROKE);

        mWavePaint4.setColor(mWaveArcColor);
        mWavePaint4.setAntiAlias(true);
        mWavePaint3.setColor(mWave3Color);
        mWavePaintText.setColor(mWaveTextColor);
        mWavePaintText.setTextAlign(Paint.Align.CENTER);
        mWavePaintText.setTextSize(20f);
        mWavePaintText.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsRunning) {
            int height = getHeight();
            int width = getWidth();
            clipContainer(canvas, width, height);
            if (Build.VERSION.SDK_INT >= 26) {
                drawOval(canvas, width, height);
            }
            drawWave(canvas, width, height);
            drawText(canvas, width, height);

        }
    }

    private void drawText(Canvas canvas, int width, int height) {
        canvas.drawText(value + "℃", width / 2, 40, mWavePaintText);
    }

    private void drawOval(Canvas canvas, int width, int height) {
        RectF rectF = new RectF();
        rectF.set(0, height, width, 0);
        canvas.drawOval(rectF, mWavePaint3);

        if (isUserGradients) {//使用渐变色
            if (waveGradientLine != null && waveGradientLine.length > 0) {
//                positions可能为空。相对位置 颜色数组中的每个对应颜色，开始 单调，绘图可能会产生意想不到的结果。
//                如果positions为空，则颜色将自动 均匀分布。
                SweepGradient sweepGradient = new SweepGradient(width / 2, height / 2,
                        waveGradientLine, null);
                mWavePaint4.setShader(sweepGradient);
            } else {
                throw new RuntimeException("your waveGradientLine is null");
            }
        }
        canvas.drawArc(rectF, 0, 360, false, mWavePaint4);
    }


    private void clipContainer(Canvas canvas, int width, int height) {
        if (mIsCircle) {
            mContainerPath.reset();
            RectF rectF = new RectF();
            rectF.set(0, height, width, 0);
            if (Build.VERSION.SDK_INT >= 26) {
                mContainerPath.addRoundRect(rectF
                        , height
                        , height
                        , Path.Direction.CW);
                canvas.clipPath(mContainerPath);
            } else {
                canvas.clipPath(mContainerPath);
                mContainerPath.addOval(rectF, Path.Direction.CCW);
                canvas.clipPath(mContainerPath, Region.Op.REPLACE);
                canvas.drawPath(mContainerPath, mWavePaint3);
                mWavePaint3.setStyle(Paint.Style.STROKE);
                canvas.drawPath(mContainerPath, mWavePaint3);
                if (isUserGradients) {//使用渐变色
                    if (waveGradientLine != null && waveGradientLine.length > 0) {
//                        float[] positions = new float[]{
//                        };
                        SweepGradient sweepGradient = new SweepGradient(width / 2, height / 2,
                                waveGradientLine, null);
                        mWavePaintOvalStroke.setShader(sweepGradient);
                    } else {
                        throw new RuntimeException("your waveGradientLine is null");
                    }
                }
                canvas.drawPath(mContainerPath, mWavePaintOvalStroke);
            }
        }
    }

    private void drawWave(Canvas canvas, int width, int height) {
        setPaint();
        double lineX = 0;
        double lineY1 = 0;
        double lineY2 = 0;
        for (int i = 0; i < width; i += mStrokeWidth) {
            lineX = i;
            if (mIsRunning) {
                lineY1 = mWaveRange * Math.sin((mAngle + i) * Math.PI / 180 / mPeriod) +
                        height * (1 - mWaveHeightPercent);
                lineY2 = mWaveRange * Math.cos((mAngle + i) * Math.PI / 180 / mPeriod) +
                        height * (1 - mWaveHeightPercent);
            } else {
                lineY1 = 0;
                lineY2 = 0;
            }
            if (isUserGradients) {
                if (waveGradient1 != null && waveGradient1.length > 0) {
                    if (!TextUtils.isEmpty(value) && isPointNumber(value)) {
                        float valueF = Float.parseFloat(value);
//                        CLAMP 如果着色器绘制超出其原始边界，则复制边颜色。
//                        REPEAT 水平和垂直重复着色器的图像
//                        MIRROR 水平和垂直重复着色器的图像，交替镜像图像，以便相邻图像始终接合
                        LinearGradient linearGradient = new LinearGradient(
                            width/2,height,width/2,height-height*valueF,waveGradient1,
                                null, Shader.TileMode.CLAMP
                        );
                        mWavePaint1.setShader(linearGradient);
                    } else {
                        throw new RuntimeException("your value is error");
                    }
                } else {
                    throw new RuntimeException("your waveGradient1 is null");
                }
            }
            canvas.drawLine((int) lineX, (int) lineY1,
                    (int) lineX + 1, height, mWavePaint1);
            if (isUserGradients) {//使用渐变色
                if (waveGradient2 != null && waveGradient2.length > 0) {
                    if (!TextUtils.isEmpty(value) && isPointNumber(value)) {
                        float valueF = Float.parseFloat(value);
                        LinearGradient linearGradient = new LinearGradient(
                                width/2,height,width/2,height-height*valueF,waveGradient2,
                                null, Shader.TileMode.CLAMP
                        );
                        mWavePaint2.setShader(linearGradient);
                    } else {
                        throw new RuntimeException("your value is error");
                    }
                } else {
                    throw new RuntimeException("your waveGradient2 is null");
                }
            }
            canvas.drawLine((int) lineX, (int) lineY2,
                    (int) lineX + 1, height, mWavePaint2);
        }
    }


    @Override
    public void run() {
        while (mIsRunning) {
            mAngle += mWaveSpeed;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            });
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void start() {
        if (mIsRunning) {
            return;
        }
        new Thread(this).start();
        mIsRunning = true;
    }

    public void stop() {
        mIsRunning = false;
        mAngle = 0;
    }

    public Path getContainerPath() {
        return mContainerPath;
    }

    public void setContainerPath(Path mContainerPath) {
        this.mContainerPath = mContainerPath;
    }

    public float getWaveSpeed() {
        return mWaveSpeed;
    }

    public void setWaveSpeed(float mWaveSpeed) {
        this.mWaveSpeed = mWaveSpeed;
    }

    public float getWaveRange() {
        return mWaveRange;
    }

    public void setWaveRange(float mWaveRange) {
        this.mWaveRange = mWaveRange;
    }

    public int getWave1Color() {
        return mWave1Color;
    }

    public void setWave1Color(int mWave1Color) {
        this.mWave1Color = mWave1Color;
    }

    public int getWave2Color() {
        return mWave2Color;
    }

    public void setWave2Color(int mWave2Color) {
        this.mWave2Color = mWave2Color;
    }

    public int getWave3Color() {
        return mWave3Color;
    }

    public void setWave3Color(int mWave3Color) {
        this.mWave3Color = mWave2Color;
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    public float getWaveHeightPercent() {
        return mWaveHeightPercent;
    }

    public void setWaveHeightPercent(float mWaveHeightPercent) {
        this.mWaveHeightPercent = mWaveHeightPercent;
    }

    public boolean isCircle() {
        return mIsCircle;
    }

    public void setIsCircle(boolean mIsCircle) {
        this.mIsCircle = mIsCircle;
    }

    public int getWaveTextColor() {
        return mWaveTextColor;
    }

    public void setWaveTextColor(int mWaveTextColor) {
        this.mWaveTextColor = mWaveTextColor;
    }

    public int getWaveArcColor() {
        return mWaveArcColor;
    }

    public void setWaveArcColor(int mWaveArcColor) {
        this.mWaveArcColor = mWaveArcColor;
    }


    public void setUserGradients(boolean userGradients) {
        isUserGradients = userGradients;
    }

    public void setWaveGradient1(int[] waveGradient1) {
        this.waveGradient1 = waveGradient1;
    }

    public void setWaveGradient2(int[] waveGradient2) {
        this.waveGradient2 = waveGradient2;
    }

    public void setWaveGradientLine(int[] waveGradientLine) {
        this.waveGradientLine = waveGradientLine;
    }

    private boolean isPointNumber(String temp) {
        //判断小数
        //说明一下的是该正则只能识别4位小数；如果不限制小数位数的话，
        // 写成[+-]?[0-9]+(\\.[0-9]+)?就可以了
        Pattern pattern = Pattern.compile("[+-]?[0-9]+(\\.[0-9]+)?");
        Matcher isNum = pattern.matcher(temp);
        return isNum.matches();
    }

}
