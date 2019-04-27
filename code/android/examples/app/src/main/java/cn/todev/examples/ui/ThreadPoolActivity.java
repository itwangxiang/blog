package cn.todev.examples.ui;

import androidx.appcompat.app.AppCompatActivity;
import cn.todev.examples.R;

import android.os.Bundle;

import com.orhanobut.logger.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolActivity extends AppCompatActivity {

    private ExecutorService mExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);

        mExecutor = Executors.newFixedThreadPool(3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < 10; i++) {
            final int finali = i;
            mExecutor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    Logger.i(finali + "-" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
