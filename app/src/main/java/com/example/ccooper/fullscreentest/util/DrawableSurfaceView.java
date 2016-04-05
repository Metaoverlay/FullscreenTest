package com.example.ccooper.fullscreentest.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by CCooper on 3/27/2016.
 */
public class DrawableSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private boolean touched = false;
    private float touched_x, touched_y = 0;
    private Paint paint;
    private Canvas c;
    private Random random;
    private AnimationThread thread;

    public DrawableSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new AnimationThread(holder);
    }

    class AnimationThread extends Thread {
        private boolean mRun;
        private SurfaceHolder mSurfaceHolder;

        public AnimationThread(SurfaceHolder surfaceHolder) {
            mSurfaceHolder = surfaceHolder;
            paint = new Paint();
            paint.setARGB(255,255,255,255);
            paint.setTextSize(32);
        }

        @Override
        public void run() {
            while (mRun) {
                c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        doDraw(c);
                        sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        private void doDraw(Canvas canvas) {
            //clear the canvas
            //canvas.drawColor(Color.BLACK);

            random = new Random();
            int w = canvas.getWidth();
            int h = canvas.getHeight();
            int x = random.nextInt(w-50);
            int y = random.nextInt(h-50);
            int r = random.nextInt(255);
            int g = random.nextInt(255);
            int b = random.nextInt(255);
            int size = 20;
            canvas.drawCircle(x,y,size,paint);
            canvas.restore();
        }
        public void setRunning(boolean b) {
            mRun = b;
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touched_x = event.getX();
        touched_y = event.getY();

        int action = event.getAction();

        switch(action){
            case MotionEvent.ACTION_DOWN:
                touched = true;
                break;
            case MotionEvent.ACTION_MOVE:
                touched = true;
                break;
            default:
                touched = false;
                break;
        }

        return true;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}