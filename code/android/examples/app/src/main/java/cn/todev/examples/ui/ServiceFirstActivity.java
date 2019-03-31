package cn.todev.examples.ui;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
                break;
            case R.id.btn_unbind_service:
                break;
        }
    }


}
