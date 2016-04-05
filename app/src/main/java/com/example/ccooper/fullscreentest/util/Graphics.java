package com.example.ccooper.fullscreentest.util;

import android.graphics.Color;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by CCooper on 1/4/2016.
 */
public class Graphics
{
    public int DrawTiles(int x)
    {
        for(int i=0;i<=50;i++)
        {

        }
        return x;
    }
    

    public void bounce(SurfaceView surfaceView)
    {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        surfaceView.setX(r*2);
        surfaceView.setY(b * 2);
    }



}
