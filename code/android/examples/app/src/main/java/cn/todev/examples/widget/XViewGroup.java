package cn.todev.examples.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class XViewGroup extends LinearLayout {

    private int mScreenWidth;


    public XViewGroup(Context context) {
        this(context, null);
    }

    public XViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = getScreenWidth();
    }


    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int nextLeft = 0;
        int nextTop = 0;

        int childCount = getChildCount();

        boolean direction = true;

        for (int i = 0; i < childCount; i++) {

            View childView = getChildAt(i);

            int childViewWidth = childView.getMeasuredWidth();
            int childViewHeight = childView.getMeasuredHeight();

            if (nextLeft + childViewWidth > mScreenWidth) nextLeft = 0;

            int childViewL = nextLeft;
            int childViewT = nextTop;
            int childViewR = nextLeft + childViewWidth;
            int childViewB = nextTop + childViewHeight;


            childView.layout(childViewL, childViewT, childViewR, childViewB);

            nextLeft += childViewWidth;
            nextTop += childViewHeight;
        }

    }
}
