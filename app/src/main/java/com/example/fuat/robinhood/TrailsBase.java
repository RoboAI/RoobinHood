package com.example.fuat.robinhood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Fuat on 02/11/2017.
 */

public class TrailsBase extends GameObject {
    public static final int TRAIL_COUNT = 10;
    public static final int MAX_SIZE = 10;
    public static final int MINIMUM_COUNT = 2;

    private boolean visible;
    protected Paint pen;
    protected int colour;
    protected ArrayList<SingleTrail> trail;
    private SingleTrail single;

    class SingleTrail {
        float x;
        float y;
        int size;
    }

    public TrailsBase(){
        super();

        initialise();
        initialiseTrail();
    }

    private void initialise(){

        pen = new Paint();
        pen.setColor(Color.RED);
        pen.setStrokeWidth(5);
        pen.setStyle(Paint.Style.FILL);

        trail = new ArrayList<>();

        visible = true;
    }

    private void initialiseTrail(){

        for(int i=0; i < TRAIL_COUNT; i++){
            SingleTrail single = new SingleTrail();
            single.size = (TRAIL_COUNT - i > MAX_SIZE) ? MAX_SIZE : TRAIL_COUNT - i;
            trail.add(single);
        }
    }

    public void setupTrail(float x, float y){
        setX(x);
        setY(y);

        for(int i=0; i < trail.size(); i++) {
            trail.get(i).x = getX();
            trail.get(i).y = getY();
        }
    }

    @Override
    public void draw(Canvas canvas){
        if(isVisible()) {

            for (int i = 0; i < trail.size(); i++) {
                single = trail.get(i);
                canvas.drawCircle(single.x, single.y, single.size, pen);
            }
        }
    }

    public void updateTrailXY(float x, float y) {

        SingleTrail single;

        for (int i = trail.size() - 1; i > 0; i--) {
            single = trail.get(i - 1);
            trail.get(i).x = single.x;
            trail.get(i).y = single.y;
        }

        single = trail.get(0);
        single.x = x;
        single.y = y;

    }

    public ArrayList<SingleTrail> getTrail() {
        return trail;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public Paint getPen(){
        return pen;
    }
}
