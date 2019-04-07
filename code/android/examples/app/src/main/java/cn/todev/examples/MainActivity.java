package cn.todev.examples;

import android.os.Bundle;
import android.os.Looper;

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
import cn.todev.examples.ui.EventFirstActivity;
import cn.todev.examples.ui.HandlerActivity;
import cn.todev.examples.ui.ServiceFirstActivity;

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
        res.add(level0Item);

        level0Item = new Level0Item("原理篇");
        level0Item.addSubItem(new Level1Item("事件分发机制", EventFirstActivity.class));
        level0Item.addSubItem(new Level1Item("Handler 机制", HandlerActivity.class));

        res.add(level0Item);

        return res;
    }
}
