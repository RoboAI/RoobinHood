package com.example.fuat.robinhood;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Fuat on 21/10/2017.
 */

public class GameScene implements GameThread.IGameCallbacks, GameSurface.ISurfaceCallbacks{
    GameThread thread;
    SurfaceView surfaceView;

    public GameScene(Context context, SurfaceView sv) {
        surfaceView = sv;
        initialiseThread();
    }

    private void initialiseThread(){
        thread = new GameThread(surfaceView.getHolder());
        thread.setCallbacks(this);
    }

    public boolean isRunning(){
        if(thread != null)
            return thread.isAlive();

        return false;
    }

    public boolean start() {
        if (thread != null) {
            if (!thread.isAlive())
                thread.start();
        }else {
            initialiseThread();
            thread.start();
        }

        return isRunning();
    }

    public void pause() {
        if (thread != null)
            thread.pause();
    }

    public void unpause() {
        if (thread != null)
            thread.unpause();
    }

    public boolean stop() {
        if (thread != null) {
            thread.endThread();
            try{
                if(thread.isAlive())
                    thread.join();
            }catch (Exception e){
                Log.e("GAME ERROR", "stop: thread.join()" + e.getMessage());
                return false;
            }

            thread = null;
        }

        return true;
    }

    @Override
    public void onUpdate(long _time) {

    }

    @Override
    public void onDrawGraphics(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
    }

    @Override
    public void onSurfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void onSurfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {

    }
}
