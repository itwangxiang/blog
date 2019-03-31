package cn.todev.examples.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.todev.examples.R;

public class ActFirstActivity extends AppCompatActivity {

    @BindView(R.id.tv_act_first_info)
    TextView tvActFirstInfo;

    private String mInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_first);
        ButterKnife.bind(this);

        updateInfo("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateInfo("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateInfo("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateInfo("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateInfo("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateInfo("onDestroy");
    }

    private void updateInfo(String info) {
        mInfo += info + "\n";
        if (tvActFirstInfo != null) tvActFirstInfo.setText(mInfo);
    }


    @OnClick({R.id.btn_dialog, R.id.btn_to_act_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("dialog");
                builder.setMessage("dialog");
                builder.create().show();

                break;
            case R.id.btn_to_act_two:

                ActivityUtils.startActivity(ActTwoActivity.class);

                break;
        }
    }
}
