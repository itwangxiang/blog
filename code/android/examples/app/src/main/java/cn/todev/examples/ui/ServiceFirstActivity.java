package cn.todev.examples.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ServiceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.todev.examples.R;
import cn.todev.examples.event.LifeCycleEvent;
import cn.todev.examples.service.MusicService;

public class ServiceFirstActivity extends AppCompatActivity {

    @BindView(R.id.tv_msg)
    TextView tvMsg;

    private MusicService mMusicService;
    private boolean mBound;

    private String mp3Url = "https://our-cloud.oss-cn-shanghai.aliyuncs.com/hello.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_first);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLifeCycleEvent(LifeCycleEvent event) {
        String msg = (String) tvMsg.getText();

        msg += event.mClass.getSimpleName() + "：" + event.mMethod + "\n";
        tvMsg.setText(msg);

    }

    @OnClick({R.id.btn_start_service, R.id.btn_stop_service, R.id.btn_bind_service, R.id.btn_unbind_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start_service:
                ServiceUtils.startService(MusicService.class);
                break;
            case R.id.btn_stop_service:
                ServiceUtils.stopService(MusicService.class);
                break;
            case R.id.btn_bind_service:
                bindService(new Intent(this, MusicService.class), mMusicServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                if (mBound) {
                    unbindService(mMusicServiceConnection);
                    mBound = false;
                }
                break;
        }
    }

    private ServiceConnection mMusicServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getService();
            mMusicService.play(mp3Url);

            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


}
