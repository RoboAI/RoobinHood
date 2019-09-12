package com.example.fuat.robinhood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by Fuat on 01/04/2017.
 */

public class TheSun extends GameObject {
    Matrix matrix;
    double angle;

    float half_width;
    float half_height;

    float rotation_speed;

    public TheSun(Bitmap bmp){
        super(bmp);

        half_width = getBitmap().getWidth() / 2;
        half_height = getBitmap().getHeight() / 2;

        matrix = new Matrix();

        rotation_speed = 0.15f;
    }

    public void setup(float _x, float _y){
        setX(_x);
        setY(_y);
    }

    public float getRotation_speed() {
        return rotation_speed;
    }

    public void setRotation_speed(float rotation_speed) {
        this.rotation_speed = rotation_speed;
    }

    @Override
    public void update(long _time){
        angle += rotation_speed;
        if(angle >= 360)
            angle = 0;

        matrix.setRotate((float) angle , half_width, half_height);
        matrix.postTranslate( getX(), getY());
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(
                getBitmap(),
                matrix,
                null);
    }
}
