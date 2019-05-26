package cn.todev.examples.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.todev.examples.R;
import cn.todev.examples.widget.CircleView;

public class BasicViewActivity extends AppCompatActivity {


    @BindView(R.id.cv_hello)
    CircleView cvHello;
    @BindView(R.id.sb_x)
    SeekBar sbX;
    @BindView(R.id.sb_y)
    SeekBar sbY;
    @BindView(R.id.tv_info)
    TextView tvInfo;

    int screenWidth, screenHeight, screenDpi;
    @BindView(R.id.et_scroll_x)
    EditText etScrollX;
    @BindView(R.id.et_scroll_y)
    EditText etScrollY;
    @BindView(R.id.btn_refresh)
    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_view);
        ButterKnife.bind(this);

        screenDpi = ScreenUtils.getScreenDensityDpi();
        screenWidth = ScreenUtils.getScreenWidth();
        screenHeight = ScreenUtils.getScreenHeight();

        sbX.setMax(screenWidth - cvHello.getWidth());
        sbY.setMax(screenHeight - cvHello.getHeight());

        sbX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cvHello.setX(progress);
                refreshLocaltion();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cvHello.setY(progress);
                refreshLocaltion();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cvHello.setOnTouchListener(new View.OnTouchListener() {

            private int lastX = 0;
            private int lastY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int x = (int) event.getX();
                int y = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:

                        int offsetX = x - lastX;
                        int offsetY = y - lastY;
                        v.offsetLeftAndRight(offsetX);
                        v.offsetTopAndBottom(offsetY);

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

        refreshLocaltion();
    }

    private void refreshLocaltion() {
        String info = ""
                + " ScreenWidth: " + screenWidth + "\n"
                + " ScreenHeight: " + screenHeight + "\n"
                + " ScreenDpi: " + screenDpi + "\n\n"
                + " Top: " + cvHello.getTop() + "\n"
                + " Left: " + cvHello.getLeft() + "\n"
                + " Right: " + cvHello.getRight() + "\n"
                + " Bottom: " + cvHello.getBottom() + "\n"
                + " Width: " + cvHello.getWidth() + "\n"
                + " Height: " + cvHello.getHeight() + "\n"
                + " X: " + cvHello.getX() + "\n"
                + " Y: " + cvHello.getY() + "\n"
                + " TranslationX: " + cvHello.getTranslationX() + "\n"
                + " TranslationY: " + cvHello.getY() + "\n"
                + " ScrollX: " + cvHello.getScrollX() + "\n"
                + " ScrollY: " + cvHello.getScrollY() + "\n"
                + "  ";
        tvInfo.setText(info);
    }

    @OnClick({R.id.cv_hello, R.id.btn_scroll_to, R.id.btn_scroll_by, R.id.btn_refresh, R.id.btn_animator, R.id.btn_animate_change_width})
    public void onViewClicked(View view) {
        int scrollX = 0;
        int scrollY = 0;
        int x = (int) cvHello.getX();
        int y = (int) cvHello.getY();

        if (!TextUtils.isEmpty(etScrollX.getText()))
            scrollX = Integer.parseInt(etScrollX.getText().toString());
        if (!TextUtils.isEmpty(etScrollY.getText()))
            scrollY = Integer.parseInt(etScrollY.getText().toString());

        switch (view.getId()) {
            case R.id.cv_hello:
                ToastUtils.showLong("ImageView onClick");
                break;
            case R.id.btn_scroll_to:
                cvHello.scrollTo(scrollX, scrollY);
                refreshLocaltion();
                break;
            case R.id.btn_scroll_by:
                cvHello.scrollBy(scrollX, scrollY);
                refreshLocaltion();
                break;
            case R.id.btn_animator:
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(cvHello, "translationX", x, scrollX);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(cvHello, "translationY", y, scrollY);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(animator1, animator2);
                set.setDuration(1000);
                set.start();

                break;

            case R.id.btn_animate_change_width:
                ObjectAnimator.ofInt(btnRefresh, "width", btnRefresh.getWidth() + 200).setDuration(5000).start();
                break;
            case R.id.btn_refresh:
                refreshLocaltion();
                break;
        }
    }
}
