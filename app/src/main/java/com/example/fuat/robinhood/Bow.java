package com.example.fuat.robinhood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Fuat on 07/04/2017.
 */

public class Bow extends GameObject {
    public static final byte P1_X = 0;
    public static final byte P1_Y = 1;
    public static final byte P2_X = 2;
    public static final byte P2_Y = 3;
    public static final byte TIP_X = 4;
    public static final byte TIP_Y = 5;
    public static final byte P3_X = 6;
    public static final byte P3_Y = 7;
    public static final byte P4_X = 8;
    public static final byte P4_Y = 9;
    public static final byte TAIL_X = 10;
    public static final byte TAIL_Y = 11;
    public static final short num_points = 12;

    protected Matrix matrix;
    protected double angle;
    protected float half_width;
    protected float half_height;

    protected Vector2D vec;

    protected float xCenter;
    protected float yCenter;

    Rect bmpRect;
    public float[] points;

    Paint p;

    public Bow(Bitmap bmp) {
        super(bmp);

        vec = new Vector2D();
        matrix = new Matrix();
        bmpRect = new Rect();
        points = new float[num_points];

        half_width = getBitmap().getWidth() / 2;
        half_height = getBitmap().getHeight() / 2;

        fillRectWithBitmapDims(bmpRect, bmp);

        updateMatrix();


        p = new Paint();
        p.setStrokeWidth(2);
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);

    }

    private Rect fillRectWithBitmapDims(Rect r, Bitmap bmp){
        r.left = 0;
        r.top = 0;
        r.right = bmp.getWidth();
        r.bottom = bmp.getHeight();
        return r;
    }

    protected void setupPoints(){
        points[P1_X] = bmpRect.left;
        points[P1_Y] = bmpRect.top;
        points[P2_X] = bmpRect.right;
        points[P2_Y] = bmpRect.top;
        points[P3_X] = bmpRect.right;
        points[P3_Y] = bmpRect.bottom;
        points[P4_X] = bmpRect.left;
        points[P4_Y] = bmpRect.bottom;
        points[TIP_X] = bmpRect.right;
        points[TIP_Y] = bmpRect.top + half_height;
        points[TAIL_X] = bmpRect.left;
        points[TAIL_Y] = bmpRect.top + half_height;
    }

    public void setupBow(float _x, float _y){
        setX(_x);
        setY(_y);

        xCenter = getX() + half_width;
        yCenter = getY() + half_height;

        updateMatrix();
    }

    public void onTouchDown(Vector2D vec){
    }

    public void onTouchUp(MultiTouchController.SingleTouch touch){
    }

    @Override
    public void draw(Canvas canvas){

        canvas.drawBitmap(
                getBitmap(),
                matrix,
                null);
        canvas.drawCircle(getTopX(), getTopY(), 5, p);
        canvas.drawCircle(getBottomX(), getBottomY(), 5, p);
    }

    public void updateValues(MultiTouchController.SingleTouch touch, Vector2D vecTouch){
        if(touch.bIsDown == true){
            vec.set(vecTouch);
        }

        updateMatrix();
    }

    void updateMatrix(){
        //calculate Bow angle
        angle = getBowAngleDegrees(vec.getWidth(), vec.getHeight());

        matrix.setRotate((float) angle, half_width, half_height);
        matrix.postTranslate(getX(), getY());

        setupPoints();
        matrix.mapPoints(points);
    }

    public double getBowAngleDegrees(float width, float height){
        return Math.toDegrees(-Math.atan2(height, width));
    }

    public float getTopX(){
       return points[P1_X];
    }

    public float getTopY(){
        return points[P1_Y];
    }

    public float getBottomX(){
        return points[P4_X];
    }

    public float getBottomY(){
        return points[P4_Y];
    }

    public float getTailX(){
        return points[TAIL_X];
    }

    public float getTailY(){
        return points[TAIL_Y];
    }
}
