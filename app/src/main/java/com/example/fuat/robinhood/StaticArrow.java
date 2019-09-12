package com.example.fuat.robinhood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Fuat on 19/10/2017.
 */

public class StaticArrow extends SingleArrow {
    public static final float PULL_LENGTH_DIVISOR = 8f;
    public static final float MAX_PULL_MULTIPLIER = 3f;

    protected Vector2D vec;

    float xPivot;
    float yPivot;

    float max_pull;
    float min_pull;

    float offset;
    boolean bShow = true;

    Paint p;

    public StaticArrow(Bitmap bmp){
        super(bmp);

        vec = new Vector2D(1,0);

        bStopPhysics = true;

        max_pull = getBitmap().getWidth() * MAX_PULL_MULTIPLIER;
        min_pull = 0;

        p = new Paint();
        p.setStrokeWidth(2);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.RED);
    }

    private void setPivots(float _x, float _y){
        xPivot = _x;
        yPivot = half_height;
    }

    public void setupArrow(float _x, float _y, float _xLocalPivot, float _yLocalPivot){
        setX(_x);
        setY(_y - half_height);// minus half_height will center it
        setPivots(_xLocalPivot, _yLocalPivot);

        updateMatrix();
    }

    public void onTouchDown(Vector2D vec){
        //     updateValues(touch);
    }

    public void onTouchUp(MultiTouchController.SingleTouch touch){
        offset = 0;
    }

    @Override
    public void draw(Canvas canvas){

        if(bShow) {
            canvas.drawBitmap(
                    getBitmap(),
                    matrix,
                    null);
        }
      //  canvas.drawCircle(getTailX(), getTailY(), 10, p);
    }

    public void updateValues(MultiTouchController.SingleTouch touch, Vector2D vecTouch){

        if(touch.bIsDown == true){
            vec.set(vecTouch);

            Vector2D.limitVectorLength(vec, max_pull, min_pull);
            offset = vec.length() / PULL_LENGTH_DIVISOR;
        }

        updateMatrix();

    }

    @Override
    void updateMatrix(){
        //calculate arrow angle
        angle = getArrowAngleDegrees(vec.getWidth(), vec.getHeight());

        matrix.setRotate((float) angle, xPivot + offset, yPivot);
        matrix.postTranslate(x - offset, y);

        setupPoints();
        matrix.mapPoints(points);
    }

    public void showArrow(boolean show){
        bShow = show;
    }

    public float getOffset(){
        return offset;
    }

    public double getArrowAngleDegrees(float width, float height){
        return Math.toDegrees(-Math.atan2(height, width));
    }
}
