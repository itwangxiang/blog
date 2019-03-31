package cn.todev.examples;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.todev.examples.entity.MainLv0Item;
import cn.todev.examples.entity.MainLv1Item;

public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);

        addItemType(TYPE_LEVEL_0, R.layout.item_main_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_main_expandable_lv1);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                MainLv0Item lv0Item = (MainLv0Item) item;
                helper.setText(R.id.lv0_title, lv0Item.title);
                break;
            case TYPE_LEVEL_1:
                MainLv1Item lv1Item = (MainLv1Item) item;
                helper.setText(R.id.lv1_title, lv1Item.title);
                break;
        }
    }
}
