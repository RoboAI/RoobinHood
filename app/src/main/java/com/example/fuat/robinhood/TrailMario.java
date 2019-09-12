package com.example.fuat.robinhood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Fuat on 02/11/2017.
 */

public class TrailMario extends TrailsBase {
    public static final int[] colours = {Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.CYAN,
            Color.GRAY,
            Color.MAGENTA,
            Color.WHITE};

    Random random;
    private SingleTrail single;
    private int colour_iterator;

    public TrailMario(){
        super();

        initialise();
    }

    private void initialise(){
        random = new Random();
    }

    private int updateColourIterator(){
        colour_iterator++;
        if(colour_iterator >= colours.length)
            colour_iterator = 0;
        return colour_iterator;
    }

    @Override
    public void draw(Canvas canvas){
        if(isVisible()) {

            pen.setColor(colours[updateColourIterator()]);
            for (int i = 0; i < trail.size(); i++) {
                single = trail.get(i);
                canvas.drawCircle(single.x, single.y, single.size, pen);
            }
        }
    }

}
