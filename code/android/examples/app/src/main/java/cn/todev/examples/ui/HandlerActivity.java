package cn.todev.examples.ui;

import androidx.appcompat.app.AppCompatActivity;
import cn.todev.examples.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.Logger;

public class HandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        Handler mHandler = new HelloHandler();

        Message msg = Message.obtain();
        msg.obj = "hello";
        mHandler.sendMessage(msg);
    }

    private static class HelloHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Logger.i((String) msg.obj);
        }
    }
}
