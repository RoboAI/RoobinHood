package com.example.fuat.robinhood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by Fuat on 22/09/2017.
 */

public class GameObject {
    protected float x;
    protected float y;
    protected float rotation;
    protected Matrix matrix;
    boolean visible;
    boolean collisible;

    private Bitmap bitmap;
    private String name;

    public GameObject(){

    }

    public GameObject(Bitmap bmp){
        bitmap = bmp;

        name = "0";
        visible = true;
        collisible = true;
    }

    public void update(long _time){

    }

    public void draw(Canvas canvas){

    }

    public boolean checkCollision(GameObject objectA){
        return false;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isCollisible() {
        return collisible;
    }

    public void setCollisible(boolean bCollisible) {
        this.collisible = bCollisible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
