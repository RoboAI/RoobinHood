package com.example.fuat.robinhood;

/**
 * Created by Fuat on 21/09/15.
 */

import android.os.Handler;
import android.os.SystemClock;

public class MyTimer extends Handler implements Runnable {
    public static final int ONE_SECOND = 1000;

    public interface ITimerElapsed{
        void onTimerElapsed(long timeNow);
    }

    private ITimerElapsed timerElapsed;
    private long millis = 0;
    private boolean bLoop = false;
    private long startTime = 0;
    private long lastEndTime = 0;
    private long newTime = 0;

    public MyTimer(ITimerElapsed callback, long milli, boolean loop){
        timerElapsed = callback;
        setCallbackTime(milli);
        setLoop(loop);
    }

    public void setCallback(ITimerElapsed callback){
        timerElapsed = callback;
    }

    public void setLoop(boolean loop){
        bLoop = loop;
    }

    public void setCallbackTime(long milli){
        millis = milli;
    }

    public void start(){
        if(timerElapsed != null)
            this.postDelayed(this, millis);
    }

    public void start(long milli){
        setCallbackTime(milli);
        start();
    }

    public void stop(){
        this.removeCallbacks(this);
    }

    private void callback(long timeNow){
        timerElapsed.onTimerElapsed(timeNow);
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        if(bLoop == false)
            callback(SystemClock.elapsedRealtime());
        else{
            startTime = SystemClock.elapsedRealtime();
            callback(startTime);
            lastEndTime = SystemClock.elapsedRealtime();
            newTime = millis - (lastEndTime - startTime);
            this.postDelayed(this, (newTime > 0 ? newTime : 0));
        }
    }
}
