package com.yx.opencvdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        initOpenCv();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.mipmap.ic_hello);
                Mat src = new Mat();
                Mat dst = new Mat();
                Utils.bitmapToMat(bitmap,src);
                Imgproc.cvtColor(src,dst,Imgproc.COLOR_BGR2GRAY); //灰度
                Utils.matToBitmap(dst,bitmap);
                ImageView ivsrc =  findViewById(R.id.iv_bg);
                ivsrc.setImageBitmap(bitmap);
                src.release();
                dst.release();
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    private void  initOpenCv(){
        boolean b = OpenCVLoader.initDebug();
        if (b){
            Log.i("opcv", "OpenCV Libraries loaded...");
        }else {
            Toast.makeText(this.getApplicationContext(), "WARNING: Could not load OpenCV Libraries!", Toast.LENGTH_LONG).show();
        }
    }


}
