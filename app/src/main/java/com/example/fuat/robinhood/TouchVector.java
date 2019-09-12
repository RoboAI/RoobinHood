package com.example.fuat.robinhood;

/**
 * Created by Fuat on 07/10/2017.
 */

public class TouchVector {
    public static final int max_length = 850;

    Vector2D fingerLine;
    float x1;
    float y1;
    float x2;
    float y2;

    Vector2D vecClone;

    public TouchVector(){
        fingerLine = new Vector2D();
        vecClone = new Vector2D();
    }

    public float getX1() {
        return x1;
    }

    public float getY1() {
        return y1;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public Vector2D getVector(){
        return vecClone;
    }

    public void onTouchDown(float x, float y){
        x1 = x2 = x;
        y1 = y2 = y;
        fingerLine.set(0,0);
        vecClone.set(0,0);
    }

    public Vector2D onTouchUpdate(float x, float y){
        fingerLine.set(x1 - x, y - y1);
        Vector2D.multiplyByScalar(fingerLine, 0.8f);
        Vector2D.limitVectorLength(fingerLine, max_length, 0);
        x2 = x1 - fingerLine.width;
        y2 = y1 + fingerLine.height;

        vecClone.set(fingerLine);
        return vecClone;
    }
}
