package com.yx.opencvdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * *******************************************
 * 标题 :                                     *
 * 编辑 : 向绍谷                               *
 * 日期 : 2020/8/14                             *
 * 描述 :                                     *
 * *******************************************
 */
public  class HomeActivity extends AppCompatActivity {

    private WaveView mWaveView2;
    private WaveView mWaveView3;
    private WaveView mWaveView4;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.demo_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });
        findViewById(R.id.demo_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, com.demo.xclcharts.MainActivity.class));
            }
        });
        findViewById(R.id.demo_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, com.huawei.codelabpush.MainActivity.class));
            }
        });

        mWaveView2 =  findViewById(R.id.waveView2);
        mWaveView3 =  findViewById(R.id.waveView3);
        mWaveView4 =  findViewById(R.id.waveView4);


//        mWaveView2.start();
//        mWaveView3.start();
//        mWaveView4.start();

    }

}
