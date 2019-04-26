package cn.todev.examples.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.LruCache;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.todev.examples.R;

public class LruCacheActivity extends AppCompatActivity {

    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.et_value)
    EditText etValue;
    @BindView(R.id.tv_info)
    TextView tvInfo;

    private LruCache<String, String> mLruCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);
        ButterKnife.bind(this);

        mLruCache = new LruCache<>(5);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mLruCache.evictAll();
        mLruCache = null;
    }

    @OnClick({R.id.btn_put, R.id.btn_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_put:
                lruPut();
                break;
            case R.id.btn_get:
                lruGet();
                break;
        }

        refresh();
    }

    private void lruPut() {
        String value = etValue.getText().toString();

        if (TextUtils.isEmpty(value)) return;

        mLruCache.put(value, value);
    }

    private void lruGet() {
        String key = etKey.getText().toString();
        if (TextUtils.isEmpty(key)) return;

        ToastUtils.showShort(mLruCache.get(key));
    }

    private void refresh() {
        if (mLruCache == null || mLruCache.snapshot() == null) return;

        StringBuilder info = new StringBuilder();

        for (Map.Entry<String, String> entry : mLruCache.snapshot().entrySet())
            info.append(entry.getValue()).append("\n");

        tvInfo.setText(info.toString());
    }
}
