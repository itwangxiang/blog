package cn.todev.examples.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.todev.examples.adapter.ExpandableItemAdapter;

public class MainLv1Item implements MultiItemEntity {

    public String title;
    public String subTitle;

    public MainLv1Item(String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_1;
    }

}
