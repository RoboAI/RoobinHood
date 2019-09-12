package com.example.fuat.robinhood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Fuat on 11/10/2017.
 */

public class BowString extends GameObject {
    public static final byte BOW_X_TOP = 0;
    public static final byte BOW_Y_TOP = 1;
    public static final byte ARROW_TAILX = 2;
    public static final byte ARROW_TAILY = 3;
    public static final byte BOW_X_BOTTOM = 4;
    public static final byte BOW_Y_BOTTOM = 5;
    public static final byte NUM_POINTS = 6;

    public static final byte string_thickness = 2;

    Paint stringPen;
    float [] points;

    public BowString () {
        stringPen = new Paint();
        stringPen.setColor(Color.WHITE);
        stringPen.setStrokeWidth(2);
        stringPen.setStyle(Paint.Style.STROKE);

        points = new float[NUM_POINTS];
    }

    void setupPointsArray(float x1, float y1, float x2, float y2, float x3, float y3) {
      /*  points = new float[]{
                _x1, _y1,
                _x1, _y1 + ((_y2 - _y1) / 2),
                _x2, _y2
        };*/

        points = new float[]{x1, y1, x2, y2, x3, y3};
    }

    //if arrow x is past bow.x, then stop moving bow string

    public void updateCoords(float x1, float y1, float x2, float y2, float x3, float y3){
        points[BOW_X_TOP] = x1;
        points[BOW_Y_TOP] = y1;
        points[ARROW_TAILX] = x2;
        points[ARROW_TAILY] = y2;
        points[BOW_X_BOTTOM] = x3;
        points[BOW_Y_BOTTOM] = y3;
    }

    boolean checkArrowTailLimit(){
    //    if( (points[ARROW_TAILX] > points[BOW_X_TOP]) && (points[ARROW_TAILX] > points[BOW_X_BOTTOM]){
    //        points[ARROW_TAILX] =
    //    }

        return false;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawLine(points[BOW_X_TOP], points[BOW_Y_TOP],
                        points[ARROW_TAILX], points[ARROW_TAILY], stringPen);
        canvas.drawLine(points[ARROW_TAILX], points[ARROW_TAILY],
                points[BOW_X_BOTTOM], points[BOW_Y_BOTTOM], stringPen);
    }

}
