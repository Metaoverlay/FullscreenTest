package com.example.ccooper.fullscreentest.util;


import android.view.SurfaceView;
import java.util.Random;

/**
 * Created by CCooper on 3/6/2016.
 */
public class Platform {


    //Constructor
    public Platform(SurfaceView surface, int width, int height)
    {

        surfaceView = surface;

        Random i = new Random();
        int r = i.nextInt(255);
        int g = i.nextInt(255);
        int b = i.nextInt(255);

        screenWidth = width;
        screenHeight = height;

        length = 200;
        yPosition = i.nextInt(50);
        xPosition = width + length*2;
        surfaceView.setBackgroundColor(android.graphics.Color.argb(255,r,g,b));
        surfaceView.setY(yPosition);
    }

    //Initial Variables
    boolean expired = false;
    public SurfaceView surfaceView;
    int xPosition=0;
    int yPosition=0;
    int length =0;
    int screenWidth;
    int screenHeight;

    //Methods
    public boolean shiftLeft() //returns true if successful, returns false if off screen
    {
        xPosition = xPosition - 10;


        surfaceView.setX(xPosition);

        if ((xPosition + length*2) < 0) //if platform has gone past screen, expire it
        {
            expired = true;
            return false;
        }
        return true;
    }

    public SurfaceView freeSurfaceView()
    {
        return surfaceView;
    }


}
