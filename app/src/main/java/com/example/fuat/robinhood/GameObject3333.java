package com.example.fuat.robinhood;

/**
 * Created by Fuat on 31/03/2017.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Belal on 6/24/2016.
 */
public class GameObject3333 {
    //Bitmap to get character from image
    protected Bitmap bitmap;

    //coordinates
    protected float x;
    protected float y;

    public GameObject3333(){
    }

    //constructor
    public GameObject3333(Bitmap bmp) {

        //Getting bitmap from drawable resource
        bitmap = bmp;
    }

    //Method to update coordinate of character
    public void update(long _time){
    }

    //draw
    public void draw(Canvas canvas){
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
