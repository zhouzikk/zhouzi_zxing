package com.hicoo.android_zxing;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.hicoo.android_zxing.zxing.activity.BaseCaptureActivity;
import com.hicoo.android_zxing.zxing.activity.CodeUtils;

public class MainActivity extends BaseCaptureActivity<ViewfinderView> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public ViewfinderView getViewfinderView() {
        return findViewById(R.id.viewfinder_view);
    }

    @Override
    protected SurfaceView getSurfaceView() {
        return findViewById(R.id.surface_view);
    }

    @Override
    protected CodeUtils.AnalyzeCallback getAnalyzeCallback() {
        return analyzeCallback;
    }

    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAnalyzeFailed() {

        }
    };

}
