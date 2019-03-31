package cn.todev.examples.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.todev.examples.adapter.ExpandableItemAdapter;

public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {
    public String title;

    public Level0Item(String title) {
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
