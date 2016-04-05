package com.example.ccooper.fullscreentest.util;


import android.view.SurfaceView;
import java.util.Random;

/**
 * Created by CCooper on 2/23/2016.
 */
public class Disco{

    //Constructor
    public Disco(SurfaceView surface, int width, int height)

    {
        screenWidth = width;
        screenHeight = height;
        surfaceView = surface;

    }

    //Initial Variables
    SurfaceView surfaceView;
    int screenWidth = 0;
    int screenHeight = 0;
    float xPosition = 0;
    float yPosition = 0;
    float xVelocity = 0;
    float yVelocity = 0;

    //Methods
    public void ChangeColor()
    {
        Random i = new Random();
        int r = i.nextInt(255);
        int g = i.nextInt(255);
        int b = i.nextInt(255);

        surfaceView.setBackgroundColor(android.graphics.Color.argb(255,r,g,b));

    }

    public void Bounce()
    {
        yVelocity = 30;
    }

    public void ApplyGravity(int amountOfMillisecondsGoneBy) //Gravity pulls 100 pixels per sec
    {

        //yVelocity = yVelocity - ((amountOfMillisecondsGoneBy/1000)*100);
        yVelocity = yVelocity - 1;
        //yPosition = yPosition + (yVelocity * (amountOfMillisecondsGoneBy/1000));
        yPosition = yPosition + yVelocity;

        //yPosition = yPosition + 10;

        CalculateBoundaries();
    }

    public void CalculateBoundaries()
    {
        if (yPosition<0)
        {
            yPosition = 0;
            yVelocity =0;
        }

        surfaceView.setX(0);
        surfaceView.setY(yPosition);
    }

    public void SetPosition(int x, int y)
    {
        surfaceView.setX(x);
        surfaceView.setY(y);
    }
}
