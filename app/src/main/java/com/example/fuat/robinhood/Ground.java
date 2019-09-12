package com.example.fuat.robinhood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Fuat on 10/10/2017.
 */

public class Ground extends GameObject {
    public static final int GROUND_HEIGHT = 20;

    Rect area;
    Paint p;

    public Ground() {
        super();

        p = new Paint();
        p.setColor(0xffababab);
        p.setStyle(Paint.Style.FILL_AND_STROKE);

        area = new Rect();
    }

    public void setupArea(Rect screenArea){
        area.set(screenArea);
        float groundHeight = (area.height() / (float)100) * GROUND_HEIGHT;
        area.top = area.bottom - (int)groundHeight;
    }

    public Rect getArea(){
        return area;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(area, p);
    }
}
