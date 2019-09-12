package com.example.fuat.robinhood;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;

/**
 * Created by Fuat on 28/10/2017.
 */

public class SceneBackground extends GameObject{
    Paint pen;

    int colours[] = {  0xFFeeeeee, 0xFFeeeeee, 0xFFffffff };
    float positions[] = {0, 0.8f, 1.0f};

    //other good colours
    //0xFF0a0a15 start
    //0xFF00c1ee finish
    //0xFF15487b middle

    public SceneBackground(Rect area){
        pen = new Paint();
        pen.setShader(new LinearGradient(0, 0, area.width(), area.height(),
                colours, positions,
                Shader.TileMode.CLAMP));
        pen.setDither(true);
        pen.setAntiAlias(true);
        pen.setColor(Color.WHITE);
    }

    public void draw(Canvas canvas) {
        canvas.drawPaint(pen);
    }

}
