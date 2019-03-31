package cn.todev.examples.ui;

import androidx.appcompat.app.AppCompatActivity;
import cn.todev.examples.R;

import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;

public class LiftcycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liftcycle);
    }

    @Override
    protected void onStart() {
        super.onStart();

        LogUtils.d("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LogUtils.d("onDestroy");
    }
}
