package cn.todev.examples.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import androidx.core.app.NotificationCompat;
import cn.todev.ExampleApp;
import cn.todev.examples.R;
import cn.todev.examples.event.LifeCycleEvent;

import static androidx.core.app.NotificationCompat.PRIORITY_MAX;

public class MusicService extends Service {

    private NotificationManager manager;

    private MediaPlayer mediaPlayer;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().post(new LifeCycleEvent(getClass(), "onCreate"));

        manager = (NotificationManager) ExampleApp.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);

        String mp3Url = "https://our-cloud.oss-cn-shanghai.aliyuncs.com/hello.mp3";
        play(mp3Url);

        showMusicNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().post(new LifeCycleEvent(getClass(), "onStartCommand"));

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        EventBus.getDefault().post(new LifeCycleEvent(getClass(), "onBind"));

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        EventBus.getDefault().post(new LifeCycleEvent(getClass(), "onUnbind"));
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new LifeCycleEvent(getClass(), "onDestroy"));

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (manager != null) manager.cancel(0);
    }

    private void showMusicNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ExampleApp.getInstance(), "music")
                .setContentTitle(getString(R.string.app_name))
                .setContentText("MusicService")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(PRIORITY_MAX)
                .setOngoing(true)
                .setAutoCancel(false);

        Notification n = builder.build();

        manager.notify(0, n);
    }

    private void play(String url) {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
