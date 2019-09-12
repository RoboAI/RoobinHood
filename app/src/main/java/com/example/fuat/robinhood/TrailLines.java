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

public class TrailLines extends TrailsBase {
    Path path;
    SingleTrail single;

    public TrailLines(){
        super();

        initialise();
    }

    private void initialise(){
        path = new Path();
        single = new SingleTrail();
    }

    @Override
    public void draw(Canvas canvas){
        if(isVisible()) {

            path.reset();
            path.moveTo(trail.get(0).x, trail.get(0).y);
            for (int i = 0; i < trail.size(); i++) {
                single = trail.get(i);
                path.lineTo(single.x, single.y);
            }
            canvas.drawPath(path, pen);
        }
    }

}
