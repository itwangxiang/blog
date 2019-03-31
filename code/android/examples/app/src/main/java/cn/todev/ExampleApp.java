package cn.todev;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class ExampleApp extends Application {

    private static ExampleApp sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        init();
    }

    public static ExampleApp getInstance() {
        return sInstance;
    }

    private void init() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .tag("Examples")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.addLogAdapter(new DiskLogAdapter());
    }
}
