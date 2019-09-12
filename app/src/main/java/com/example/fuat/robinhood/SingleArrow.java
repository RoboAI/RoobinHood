package com.example.fuat.robinhood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

/**
 * Created by Fuat on 15/10/2017.
 */

public class SingleArrow extends PhysicsObject{

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
    Rect bmpRect;
    public float[] points;

    Rect area;
     boolean bStopPhysics = false;

    TrailsBase trails;

    public SingleArrow(Bitmap bmp){
        super(new Vector2D(), new Vector2D(), false, bmp);

        init(null);
    }

    public SingleArrow(Vector2D _force, Vector2D _direction, Bitmap bmp, Rect r){
        super(_force, _direction, false, bmp);

        init(r);
    }

    private void init(Rect r){

        slow_motion = 0.75f;

        matrix = new Matrix();
        bmpRect = new Rect();
        area = new Rect(r);
        points = new float[num_points];

        trails = new TrailsBase();

        half_width = getBitmap().getWidth() / 2;
        half_height = getBitmap().getHeight() / 2;

        angle = (getAngleDegrees(direction.getWidth(), direction.getHeight() / 1.25f) % 360);

        fillRectWithBitmapDims(bmpRect, getBitmap());
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

    void updateMatrix(){
        matrix.setRotate((float) angle, half_width, half_height);
        matrix.postTranslate(x - half_width, y);

        setupPoints();
        matrix.mapPoints(points);
    }

    private double getAngleDegrees(float width, float height){
        return Math.toDegrees(Math.atan2(height, width));
    }

    public void setupArrow(float _x, float _y){
        setX(_x);
        setY(_y - half_height);// minus half_height will center it

        updateMatrix();

        trails.setupTrail(getX(), getY());
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(getBitmap(), matrix, null);
        trails.draw(canvas);
    }

    @Override
    public void update(long _time){
        if(bStopPhysics == false) {
            bStopPhysics = update_vectors(_time, force, area);

            angle = (getAngleDegrees(direction.getWidth(), direction.getHeight() / 1.25f) % 360);

            updateMatrix();

            if(bStopPhysics)
                trails.setVisible(false);
            else
                trails.updateTrailXY(getTailX(), getTailY());
        }
    }

    /*
    public boolean update(float _time){
        if(bStopPhysics == false) {
            bStopPhysics = update_vectors(_time, force, area);

            angle = (getAngleDegrees(direction.getWidth(), direction.getHeight() / 1.25f) % 360);

            updateMatrix();
        }
        return bStopPhysics;
    }*/

    public float getX(){
        return points[P1_X];
    }

    public float getY(){
        return points[P1_Y];
    }

    public float getTipX(){
        return points[TIP_X];
    }

    public float getTipY(){
        return points[TIP_Y];
    }

    public float getTailX(){
        return points[TAIL_X];
    }

    public float getTailY(){
        return points[TAIL_Y];
    }

}
