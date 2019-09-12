package com.example.fuat.robinhood;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.view.SurfaceHolder;

/**
 * Created by Fuat on 20/10/2017.
 */

public class GameThread extends Thread {
    public interface IGameCallbacks{
        void onUpdate(long _time);
        void onDrawGraphics(Canvas canvas);
    }

    volatile boolean playing;
    volatile boolean bPause;
    IGameCallbacks callbacks;

    SurfaceHolder holder;
    Canvas canvas;

    private long millis = 20;
    private long startTime = 0;
    private long lastEndTime = 0;
    private long newTime = 0;

    public GameThread(SurfaceHolder h){
        holder = h;
        bPause = true;
        playing = true;
    }

    public void setCallbacks(IGameCallbacks c){
        callbacks = c;
    }

    public void pause(){
        bPause = true;
    }

    public void unpause(){
        bPause = false;
    }

    public boolean endThread(){
        playing = false;

        //short delay
        for(int i = 0; i < 500; i++){
            if(!this.isAlive())
                return true;
        }

        return false;
    }

    @Override
    public void run() {
        while (playing) {
            startTime = SystemClock.uptimeMillis();

            if (callbacks != null) {
                update();
                draw();
            }

            lastEndTime = SystemClock.uptimeMillis();
            newTime = /*millis - */(lastEndTime - startTime);

            /*
            //-need another variable where it accumulates, then if greater than SOMETHING, then draw().
            //this way update is called as many times as possible, but draw() is
            //called only a set amount of FPS
            //-pause should only pause update(newTime);

            if(accumulator > SOMETHING){
                accumulator = 0;
                draw();
            }
             */
        }
    }

    private void update(){
        if (!bPause)
            callbacks.onUpdate(newTime);
    }

    private void draw() {
        if(holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            callbacks.onDrawGraphics(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }
}
