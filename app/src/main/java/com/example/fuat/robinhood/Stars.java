package com.example.fuat.robinhood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Fuat on 28/10/2017.
 */

public class Stars extends GameObject{
    public static final int SPARKLEX = 5;
    public static final int SPARKLEY = 7;

    class Star{
        float x;
        float y;
        boolean active = true;
    }

    int x;
    int y;

    Rect src;
    Rect dst;
    Paint dot1;
    Paint pen;

    Star[] star1;
    Star[] star2;
    Star[] star3;
    Star[] star4;

    Random random = new Random();

    public Stars(Rect area){
        src = new Rect();
        dst = new Rect(area);

        dot1 = new Paint();
        dot1.setDither(true);
        dot1.setAntiAlias(true);
        dot1.setStyle(Paint.Style.FILL);
        dot1.setColor(Color.WHITE);
        dot1.setStrokeWidth(1);

        star1 = new Star[200];
        star2 = new Star[200];
        star3 = new Star[200];
        star4 = new Star[200];

        initStar(star1, area);
        initStar(star2, area);
        initStar(star3, area);
        initStar(star4, area);

        pen = new Paint(dot1);
        pen.setStrokeWidth(5);
        pen.setColor(Color.RED);
    }

    private void initStar(Star[] stars, Rect area){
        for(int i=0; i < stars.length; i++){
            stars[i] = new Star();
            stars[i].x = random.nextInt(area.width() * 2);
            stars[i].y = random.nextInt(area.height());
            stars[i].active = random.nextBoolean();
        }
    }

    public void updateScroll(float dx, float dy){
        x += dx;
        y += dy;
/*
        if(x < 0)
            x = 0;
        else if(x >= dst.width())
            x = dst.width();
            */

        scroll(star1, dx*0.05f, dy*0.05f);
        scroll(star2, dx*0.1f, dy*0.1f);
        scroll(star3, dx*0.2f, dy*0.2f);
        scroll(star4, dx*0.3f, dy*0.3f);

    }

    private void scroll(Star[] stars, float dx, float dy){
        for(int i=0; i < stars.length; i++){
            if(stars[i].active) {
                stars[i].x += dx;
                stars[i].y += dy;
            }
        }
    }

    private void draw(Star[] stars, boolean sparkle, float parallax, int size, Canvas canvas){
        for(int i=0; i < stars.length; i++){
            canvas.drawCircle(stars[i].x, stars[i].y, size, dot1);
            if(sparkle){
                if(random.nextBoolean())
                {
                    canvas.drawLine(stars[i].x - SPARKLEX, stars[i].y,
                            stars[i].x + SPARKLEX, stars[i].y, dot1);
                    canvas.drawLine(stars[i].x, stars[i].y - SPARKLEY,
                            stars[i].x, stars[i].y + SPARKLEY, dot1);
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        draw(star1, false, 1, 0, canvas);
        draw(star2, false, 1, 0, canvas);
        draw(star3, true, 2, 0, canvas);
        draw(star4, false, 3, 0, canvas);
    }
}

/*
        dot.setStrokeWidth(1);
        canvas.drawCircle(300, 300, 1, dot);
        dot.setStrokeWidth(2);
        canvas.drawCircle(310, 300, 2, dot);
        dot.setStrokeWidth(3);
        canvas.drawCircle(320, 300, 3, dot);
        dot.setStrokeWidth(4);
        canvas.drawCircle(330, 300, 4, dot);
        dot.setStrokeWidth(1);
        canvas.drawCircle(350, 300, 5, dot);
        dot.setStrokeWidth(1);
        canvas.drawCircle(370, 300, 6, dot);
        dot.setStrokeWidth(1);
        canvas.drawCircle(390, 300, 7, dot);
        dot.setStrokeWidth(1);
        canvas.drawCircle(410, 300, 8, dot);
        */
