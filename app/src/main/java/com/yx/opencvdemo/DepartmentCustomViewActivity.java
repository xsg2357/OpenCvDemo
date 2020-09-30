package com.yx.opencvdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yx.opencvdemo.widget.WaveView;

/**
 * 标题 :
 * 编辑 : 向绍谷
 * 日期 : 2020/9/30
 * 描述 :
 */
public  class DepartmentCustomViewActivity extends AppCompatActivity {

    private WaveView mWaveView2;
    private WaveView mWaveView3;
    private WaveView mWaveView4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_department);

        mWaveView2 =  findViewById(R.id.waveView2);
        mWaveView3 =  findViewById(R.id.waveView3);
        mWaveView4 =  findViewById(R.id.waveView4);


        mWaveView2.start();
        mWaveView3.start();
        mWaveView4.start();

    }
}
