package cn.todev.examples.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OSSService extends Service {
    public OSSService() {
    }

    private List<String> mOssList;

    private ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i("OSSService - onCreate");

        mOssList = new CopyOnWriteArrayList<>();

        executorService = Executors.newFixedThreadPool(5);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("OSSService - onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IOSSManager.Stub() {
            @Override
            public List<String> queryList() {
                return mOssList;
            }

            @Override
            public void push(String oss) {
                if (mOssList != null) {
                    ToastUtils.showLong(":remote 进程 OSSService 正在添加 Oss 文件，大约需要 5 s");

                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            mOssList.add(oss);
                        }
                    });
                }
            }
        };
    }


}
