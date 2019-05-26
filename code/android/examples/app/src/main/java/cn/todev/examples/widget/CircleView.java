package cn.todev.examples.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import cn.todev.examples.R;

public class CircleView extends View {

    private int mColor;

    private int mWidth = 200;

    private int mHeight = 200;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView, 0, 0);

        try {
            mColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.RED);
        } finally {
            typedArray.recycle();
        }

        init();
    }

    private void init() {
        mPaint.setColor(mColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        boolean widthSpecModeIsATMOST = widthSpecMode == MeasureSpec.AT_MOST;
        boolean heightSpecModeIsATMOAT = heightSpecMode == MeasureSpec.AT_MOST;

        if (widthSpecModeIsATMOST || heightSpecModeIsATMOAT)
            setMeasuredDimension(widthSpecModeIsATMOST ? mWidth : widthSpecSize, heightSpecModeIsATMOAT ? mHeight : heightSpecSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        Log.d("CircleView", "l:" + paddingLeft + "r:" + paddingRight + "t:" + paddingTop + "b:" + paddingBottom);

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, radius, mPaint);
    }
}
