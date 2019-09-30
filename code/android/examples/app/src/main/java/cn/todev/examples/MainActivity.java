package cn.todev.examples;

import android.os.Bundle;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.todev.examples.adapter.ExpandableItemAdapter;
import cn.todev.examples.entity.Level0Item;
import cn.todev.examples.entity.Level1Item;
import cn.todev.examples.ui.ActFirstActivity;
import cn.todev.examples.ui.AsyncTaskActivity;
import cn.todev.examples.ui.BasicViewActivity;
import cn.todev.examples.ui.CustomViewActivity;
import cn.todev.examples.ui.EventFirstActivity;
import cn.todev.examples.ui.HandlerActivity;
import cn.todev.examples.ui.LruCacheActivity;
import cn.todev.examples.ui.OSSActivity;
import cn.todev.examples.ui.ScreenSwitchActivity;
import cn.todev.examples.ui.ServiceFirstActivity;
import cn.todev.examples.ui.ThreadPoolActivity;
import cn.todev.examples.ui.ViewActivity;
import cn.todev.examples.ui.WindowActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private ExpandableItemAdapter adapter;

    private ArrayList<MultiItemEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        list = generateData();

        adapter = new ExpandableItemAdapter(list);
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<MultiItemEntity> generateData() {

        ArrayList<MultiItemEntity> res = new ArrayList<>();

        Level0Item level0Item = new Level0Item("基础篇");
        level0Item.addSubItem(new Level1Item("Activity", ActFirstActivity.class));
        level0Item.addSubItem(new Level1Item("Service", ServiceFirstActivity.class));
        level0Item.addSubItem(new Level1Item("BroadcastReceiver", null));
        level0Item.addSubItem(new Level1Item("Content Provider", null));
        level0Item.addSubItem(new Level1Item("屏幕切换", ScreenSwitchActivity.class));
        level0Item.addSubItem(new Level1Item("悬浮窗", WindowActivity.class));
        res.add(level0Item);

        level0Item = new Level0Item("View 篇");
        level0Item.addSubItem(new Level1Item("View 基础知识", BasicViewActivity.class));
        level0Item.addSubItem(new Level1Item("View 常见的(RecyclerView)", ViewActivity.class));
        level0Item.addSubItem(new Level1Item("View 自定义", CustomViewActivity.class));
        res.add(level0Item);

        level0Item = new Level0Item("原理篇");
        level0Item.addSubItem(new Level1Item("Event 机制", EventFirstActivity.class));
        level0Item.addSubItem(new Level1Item("Handler 机制", HandlerActivity.class));
        level0Item.addSubItem(new Level1Item("AsyncTask", AsyncTaskActivity.class));
        level0Item.addSubItem(new Level1Item("LruCache", LruCacheActivity.class));
        level0Item.addSubItem(new Level1Item("ThreadPool", ThreadPoolActivity.class));
        level0Item.addSubItem(new Level1Item("Binder (AIDL) 之 OSS 例子", OSSActivity.class));

        res.add(level0Item);

        return res;
    }
}
