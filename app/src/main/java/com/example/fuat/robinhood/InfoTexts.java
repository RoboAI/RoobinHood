package com.example.fuat.robinhood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Fuat on 31/03/2017.
 */

public class InfoTexts {
    ArrayList<String> array;
    Paint pen;
    int text_ypos;
    int text_xpos;

    public int x;
    public int y;
    public int text_spacing;

    public InfoTexts(){
        initialise(null);
    }

    public InfoTexts(Paint p){
        initialise(p);
    }

    void initialise(Paint p){
        array = new ArrayList<>();

        x = 10;
        y = 30;
        text_spacing = 30;

        if(p != null)
            pen = p;
        else {
            pen = new Paint();
            pen.setColor(Color.BLACK);
            pen.setStyle(Paint.Style.FILL);
            pen.setTextSize(20);
            pen.setStrokeWidth(1);
        }

        Paint.FontMetrics fm = pen.getFontMetrics();
        text_spacing = (int) (fm.descent - fm.ascent) + 3;
    }

    public void addText(String s){
        array.add(s);
    }

    public void clearAll(){
        array.clear();
    }

    public void drawAll(Canvas c){
        text_xpos = x;
        text_ypos = y;

        for (String str : array){
            c.drawText(str, text_xpos, text_ypos, pen);
            text_ypos += text_spacing;
        }
    }
}
