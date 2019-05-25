package cn.todev.examples.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ServiceUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.todev.examples.R;
import cn.todev.examples.service.IOSSManager;
import cn.todev.examples.service.OSSService;

public class OSSActivity extends AppCompatActivity {

    @BindView(R.id.et_file)
    EditText etFile;
    @BindView(R.id.tv_oss_list)
    TextView tvOssList;

    private IOSSManager mIOSSManager;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mIOSSManager = IOSSManager.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oss);
        ButterKnife.bind(this);

        ServiceUtils.bindService(OSSService.class, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ServiceUtils.unbindService(mServiceConnection);
    }

    @OnClick({R.id.btn_add, R.id.btn_query})
    public void onViewClicked(View view) {
        if (mIOSSManager == null) {
            ToastUtils.showLong("iOSSManager not Connected");
            return;
        }

        switch (view.getId()) {
            case R.id.btn_add:

                addOss();

                break;
            case R.id.btn_query:

                queryOss();
                break;
        }
    }

    private void addOss() {
        if (TextUtils.isEmpty(etFile.getText())) {
            ToastUtils.showLong("ServiceConnection not Connected");
            return;
        }

        try {
            mIOSSManager.push(etFile.getText().toString());
            etFile.setText("");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void queryOss() {
        try {

            List<String> ossList = mIOSSManager.queryList();
            tvOssList.setText(ossList.toString());

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
