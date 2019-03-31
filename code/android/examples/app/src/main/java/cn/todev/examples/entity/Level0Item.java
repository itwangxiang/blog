package cn.todev.examples.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.todev.examples.adapter.ExpandableItemAdapter;

public class MainLv0Item extends AbstractExpandableItem<MainLv1Item> implements MultiItemEntity {
    public String title;
    public String subTitle;

    public MainLv0Item( String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
