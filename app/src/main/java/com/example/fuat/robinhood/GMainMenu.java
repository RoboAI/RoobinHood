package com.example.fuat.robinhood;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Fuat on 21/10/2017.
 */

//TODO
    /*
    add STATES, so scene can know if is loaded for the first time or resuming from 'Home Button' etc..
     */

public class GMainMenu extends GameScene implements MultiTouchController.TouchUpdateListener{

    MultiTouchController touchController;
    TouchVector touchVector;
    Paint pen;


    public GMainMenu(Context context, SurfaceView sv) {
        super(context, sv);

        touchVector = new TouchVector();

        touchController = new MultiTouchController();
        touchController.setOnTouchUpdate(this);

        sv.setOnTouchListener(touchController);

        pen = new Paint();
        pen.setStrokeWidth(3);
        pen.setColor(Color.GREEN);
        pen.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void onUpdate(long _time) {
    }

    @Override
    public void onDrawGraphics(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawLine(touchVector.getX1(), touchVector.getY1(), touchVector.getX2(), touchVector.getY2(), pen);
    }

    @Override
    public void onSurfaceCreated(SurfaceHolder holder) {
        super.onSurfaceCreated(holder);
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.onSurfaceChanged(holder, format, width, height);
    }

    @Override
    public void onSurfaceDestroyed(SurfaceHolder holder) {
        super.onSurfaceDestroyed(holder);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }


    @Override
    public void onTouchUpdate(MultiTouchController.SingleTouch touch) {
        touchVector.onTouchUpdate(touch.x, touch.y);
    }

    @Override
    public void onTouchDown(MultiTouchController.SingleTouch touch) {
        touchVector.onTouchDown(touch.x, touch.y);
    }

    @Override
    public void onTouchUp(MultiTouchController.SingleTouch touch) {

    }
}
