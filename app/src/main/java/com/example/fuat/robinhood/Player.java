package com.example.fuat.robinhood;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Fuat on 22/09/2017.
 */

public class Player extends GameObject {

    private PhysicsObject physics;
    private Vector2D direction;
    private Bitmap img;
    private Boolean isVisible = true;

    public Player(){
    }

    public Player(Bitmap bmp){
        super(null);

        init(bmp, null);
    }

    public Player(Bitmap bmp, String id){
      //  super(id);

        init(bmp, id);
    }

    private void init(Bitmap bmp, String id){
        direction = new Vector2D(0, -1);
        physics = new PhysicsObject();
        img = bmp;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public Bitmap getImg(){
        return img;
    }

    public void update(long timeNow){
        x += timeNow;
    }

    public void draw(Canvas canvas){
        if(getVisible())
            canvas.drawBitmap(getImg(), getX(), getY(), null);
    }
}
