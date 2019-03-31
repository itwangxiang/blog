package cn.todev.examples.entity;

import android.app.Activity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.todev.examples.adapter.ExpandableItemAdapter;

public class Level1Item implements MultiItemEntity {

    public String title;
    public Class<? extends Activity> actClass;

    public Level1Item(String title, Class<? extends Activity> actClass) {
        this.actClass = actClass;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_1;
    }

}
