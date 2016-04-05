package com.example.ccooper.fullscreentest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.example.ccooper.fullscreentest.util.AnimationView;
import com.example.ccooper.fullscreentest.util.BackGroundTimerThread;
import com.example.ccooper.fullscreentest.util.Disco;
import com.example.ccooper.fullscreentest.util.Graphics;
import com.example.ccooper.fullscreentest.util.Platform;
import com.example.ccooper.fullscreentest.util.SystemUiHider;

import java.util.ArrayList;
import java.util.List;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */

    private Graphics graphics;

    public Disco disco;
    public SurfaceView surfaceView;
    public SurfaceView surfaceViewPlatform;
    int width;
    int height;

    //StartTimer() variables
    public int platformCounter = 0;
    public List<Platform> platformList = new ArrayList<Platform>();
    public List<SurfaceView> freeSurfaceList = new ArrayList<SurfaceView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //My First Code////
        graphics = new Graphics();
        //////////////////
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);


        //Disco Instantiation
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        //surfaceViewPlatform = (SurfaceView) findViewById(R.id.surfaceViewPlatform1);

        freeSurfaceList.add((SurfaceView) findViewById(R.id.surfaceViewPlatform1));
        freeSurfaceList.add((SurfaceView) findViewById(R.id.surfaceViewPlatform2));
        freeSurfaceList.add((SurfaceView) findViewById(R.id.surfaceViewPlatform3));
        freeSurfaceList.add((SurfaceView) findViewById(R.id.surfaceViewPlatform4));
        freeSurfaceList.add((SurfaceView) findViewById(R.id.surfaceViewPlatform5));

        DisplayMetrics metrics = surfaceView.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        disco = new Disco(surfaceView, width, height);
        disco.SetPosition(0,0);
        StartTimer();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */


    public void myFancyMethod(View view)
    {
        //SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        // Graphics testGraphics = new Graphics();
        //Disco.disco(surfaceView);
        disco.ChangeColor();
        disco.Bounce();

    }

    public void StartTimer()  //This is a method that contains a Handler that posts a recursive Runnable. The handler is the object that gets executed on the main UI thread(I think???).
    {
        ////////TIMER CODE  -> http://stackoverflow.com/questions/4597690/android-timer-how //////////////////
        final int period = 20;

        final long startTime = System.currentTimeMillis();
        final TextView timerTextView = (TextView)findViewById(R.id.timerTextView);
        final SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        //runs without a timer by reposting this handler at the end of the runnable
        final Handler timerHandler = new Handler();
        final Runnable timerRunnable = new Runnable() {

            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);

                //int minutes = seconds / 60;
                //seconds = seconds % 60;
                timerTextView.setText(String.format("%d:%d", seconds,millis%1000));

                disco.ApplyGravity(period);

                platformCounter= platformCounter + period;

                    if(!platformList.isEmpty()) {
                        for (int i = 0; i < platformList.size(); i++) //Shift all platforms left, if a platform is off screen, remove it
                        {
                            if (platformList.get(i).shiftLeft() == false) {
                                freeSurfaceList.add(platformList.get(i).freeSurfaceView()); //free up the surfaceView and reuse it
                                platformList.remove(i);                                     //remove the platform
                            }

                        }
                    }


                if(platformCounter >= 1000) //Generate a platform every second
                {
                    //SurfaceView platformSurface = new SurfaceView(getApplicationContext()); //change this to a programmatically added surface

                    Platform platform = new Platform(freeSurfaceList.get(1),height,width); //give the platform a surfaceView to work with
                    freeSurfaceList.remove(1);                                             //remove the surface from list of unused surfaces
                    platformList.add(platform);
                    platformCounter= 0;
                }


                timerHandler.postDelayed(this, period);
            }
        };

        timerHandler.postDelayed(timerRunnable, period);
        //////////////////////////////////////////////////////////////////////////////////////////////////////
    }



}
