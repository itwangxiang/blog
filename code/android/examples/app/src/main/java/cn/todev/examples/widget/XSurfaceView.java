package cn.todev.examples.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class XSurfaceView extends SurfaceView implements SurfaceHolder.Callback {


    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Paint paint;

    private boolean isDraw = false;


    public XSurfaceView(Context context) {
        this(context, null);
    }

    public XSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
    }


    private void draw(int i) {
        if (!isDraw) return;

        try {
            Log.i("XSurfaceView", "=========draw========");
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawLine(0, 50, i, 50, paint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null)
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("XSurfaceView", "=========surfaceCreated========");

        isDraw = true;

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 1000 && isDraw; i++) {
                    try {
                        Thread.sleep(100);

                        draw(i);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("XSurfaceView", "=========surfaceChanged========");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("XSurfaceView", "=========surfaceDestroyed========");
        isDraw = false;
    }
}
