package com.example.fuat.robinhood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Fuat on 08/10/2017.
 */

public class FingerLine extends GameObject {
    public static final int colour = Color.DKGRAY;
    public static final int thickness = 5;

    Paint pen;
    Vector2D vector;
    float xFirst, yFirst;
    float xLast, yLast;
    boolean bDraw;

    public FingerLine(Bitmap bmp){
        super(bmp);

        initialise();
    }

    public FingerLine (){
        initialise();
    }

    private void initialise(){
        pen = new Paint();
        pen.setColor(colour);
        pen.setStrokeWidth(thickness);
        pen.setStyle(Paint.Style.FILL_AND_STROKE);

        vector = new Vector2D();
        bDraw = false;
    }

    private void resetValues(){
        vector.set(0,0);
        xFirst = 0;
        yFirst = 0;
        xLast = 0;
        yLast = 0;
        bDraw = false;
    }

    @Override
    public void draw(Canvas canvas){
        if(bDraw){
            canvas.drawLine(xFirst, yFirst, xLast, yLast, pen);
        }
    }

    public void onTouchDown(float _x, float _y){
        bDraw = true;
        xFirst = xLast = _x;
        yFirst = yLast = _y;
    }

    public void onTouchUpdate(float _x, float _y){
        xLast = _x;
        yLast = _y;
    }

    public void onTouchUp(float _x, float _y){
        resetValues();
    }

}
