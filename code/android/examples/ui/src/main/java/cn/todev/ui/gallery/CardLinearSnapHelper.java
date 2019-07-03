package cn.todev.ui.gallery;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CardLinearSnapHelper extends LinearSnapHelper {
    public boolean mNoNeedToScroll = false;

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        if (mNoNeedToScroll) {
            return new int[]{0, 0};
        } else {
            return super.calculateDistanceToFinalSnap(layoutManager, targetView);
        }
    }

}
