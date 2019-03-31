package cn.todev.examples.adapter;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.todev.examples.R;
import cn.todev.examples.entity.Level0Item;
import cn.todev.examples.entity.Level1Item;

public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);

        addItemType(TYPE_LEVEL_0, R.layout.item_main_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_main_expandable_lv1);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                Level0Item lv0 = (Level0Item) item;
                helper.setText(R.id.lv0_title, lv0.title);

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });

                break;
            case TYPE_LEVEL_1:
                Level1Item lv1 = (Level1Item) item;
                helper.setText(R.id.lv1_title, lv1.title);

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", lv1.title);
                        ActivityUtils.startActivity(lv1.actClass, bundle);
                    }
                });

                break;
        }
    }
}
