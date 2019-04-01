package cn.todev.examples.ui;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.todev.examples.R;

public class EventFirstActivity extends AppCompatActivity {

    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.btn_event)
    Button btnEvent;
    @BindView(R.id.btn_event_two)
    Button btnEventTwo;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.iv_event)
    ImageView ivEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_first);
        ButterKnife.bind(this);

        llContainer.setOnClickListener(v -> Logger.i("LinearLayout onClick"));

        llContainer.setOnTouchListener((v, event) -> {
            updateMsg("LinearLayout onTouch: " + event.getAction());
            return false;
        });

        btnEvent.setOnTouchListener((View v, MotionEvent event) -> {
            updateMsg("Button 1 onTouch: " + event.getAction());
            return false;
        });

        btnEvent.setOnClickListener(v -> updateMsg("Button 1 onClick"));

        btnEventTwo.setOnTouchListener((v, event) -> {
            updateMsg("Button 2 onTouch: " + event.getAction());
            return false;
        });

        btnEventTwo.setOnClickListener(v -> updateMsg("Button 2 onClick"));

        ivEvent.setOnTouchListener((v, event) -> {
            updateMsg("ImageView onTouch: " + event.getAction());
            return false;
        });

        tvMsg.setOnTouchListener((v, event) -> {
            updateMsg("TextView onTouch: " + event.getAction());
            return false;
        });

        tvMsg.setOnClickListener(v -> updateMsg("TextView onClick"));
    }

    private void updateMsg(String msg) {
        String message = (String) tvMsg.getText();
        message += msg + "\n";
        tvMsg.setText(message);
    }
}
