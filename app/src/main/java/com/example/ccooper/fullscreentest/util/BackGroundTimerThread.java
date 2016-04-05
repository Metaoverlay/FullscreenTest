package com.example.ccooper.fullscreentest.util;

import android.os.Handler;


/**
 * Created by CCooper on 2/17/2016.
 */
public class BackGroundTimerThread implements Runnable
{

    //http://developer.android.com/training/multiple-threads/define-runnable.html
    Handler handler = new Handler();
    int i = 0;

        @Override
        public void run()
        {

            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            try {
                while(i<5) {
                    Thread.sleep(5000);
                    i++;
                    //handler.post(this);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();            }
        }


}

