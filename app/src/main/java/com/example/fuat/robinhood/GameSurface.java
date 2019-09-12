package com.example.fuat.robinhood;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Fuat on 20/10/2017.
 */

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{
    public interface ISurfaceCallbacks{
        void onSurfaceCreated(SurfaceHolder holder);
        void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height);
        void onSurfaceDestroyed(SurfaceHolder holder);
        void onWindowFocusChanged(boolean hasWindowFocus);
    }

    private ISurfaceCallbacks callbacks;
    SurfaceHolder holder;

    public int get_width() {
        return _width;
    }

    public int get_height() {
        return _height;
    }

    int _width;
    int _height;

    public GameSurface(Context context){
        super(context);

        init();
    }

    private void init(){
        holder = getHolder();
        holder.addCallback(this);

        setClickable(true);
        setFocusable(true);
    }

    public SurfaceHolder getSurfaceHolder(){
        return holder;
    }

    public void setCallbacks(ISurfaceCallbacks c){
        callbacks = c;
    }

    public void setSize(int width, int height){
        holder.setFixedSize(width, height);
    }

    public void setSurfaceTouchListener(OnTouchListener l){
        setOnTouchListener(l);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if(callbacks != null)
            callbacks.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;

        if(callbacks != null)
            callbacks.onSurfaceCreated(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        _width = width;
        _height = height;

        if(callbacks != null)
            callbacks.onSurfaceChanged(holder, format, width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(callbacks != null)
            callbacks.onSurfaceDestroyed(holder);
    }
}
