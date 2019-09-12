package com.example.fuat.robinhood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Fuat on 13/10/2017.
 */

public class ArrowsThrower extends GameObject{
    public final int MAX_ARROWS = 30;

    ArrayList<SingleArrow> arrows;
    Rect area;
    int currentItem;

    public ArrowsThrower(Bitmap bmp){
        super(bmp);
        area = new Rect();
        arrows = new ArrayList<>();
    }

    public void setup(Rect r){
        area.set(r);
    }

    public void reset(){
        arrows.clear();
        currentItem = 0;
    }

    public int getArrowCount(){
        return arrows.size();
    }

    public void addNewArrow(float _x, float _y, Rect r, Vector2D direction){
        if(getArrowCount() >= MAX_ARROWS)
            arrows.remove(0);

        arrows.add(new SingleArrow(new Vector2D(0, 5f), direction, getBitmap(), r));
        currentItem = arrows.size() - 1;
        arrows.get(currentItem).setupArrow(_x, _y);
    }

    @Override
    public void update(long _time){
        for(int i = 0; i < arrows.size(); i++)
            arrows.get(i).update(_time);
    }

    @Override
    public void draw(Canvas canvas){
        for(int i = 0; i < arrows.size(); i++) {
            arrows.get(i).draw(canvas);
        //    canvas.drawCircle(temp.getTailX(), temp.getTailY(), 10, p);
         //   canvas.drawCircle(temp.getTipX(), temp.getTipY(), 10, p);
        }

    }
}
