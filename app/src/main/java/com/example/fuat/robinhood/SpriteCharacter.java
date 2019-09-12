package com.example.fuat.robinhood;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Fuat on 01/04/2017.
 */

public class SpriteCharacter extends GameObject {
    SpriteElement[] elements;
    Rect src;
    Rect dest;

    int index = 0;
    int counter = 0;

    int frames_horizontal = 8;
    int frames_vertical = 1;
    int number_of_frames = frames_horizontal * frames_vertical;

    int sprite_width = 108;
    int sprite_height = 140;

    public SpriteCharacter(Context c, Rect r, Bitmap bmp){
        super(bmp);

        initialise();
    }

    private void initialise(){
        elements = new SpriteElement[number_of_frames];
        src = new Rect(0,0,sprite_width,sprite_height);
        dest = new Rect(0,0,sprite_width,sprite_height);
 //       dest.offset(0, area.height() - (sprite_height + 10));//TODO

        x = x;
        y = dest.top;

        int count = 0;
        for(int i=0; i < frames_vertical; i++){
            for(int j=0; j < frames_horizontal; j++) {
                elements[count] = new SpriteElement(sprite_width * j, sprite_height * i);
                elements[count].width = sprite_width;
                elements[count].height = sprite_height;

                count++;
            }
        }
    }


    public void update(MultiTouchController.SingleTouch touch){
        counter++;
        updateX();
        if(counter >= 2){
            counter = 0;

            updateIndex();



            src.set(elements[index].getLocationx(),
                    elements[index].getLocationy(),
                    elements[index].getLocationx() + elements[index].width,
                    elements[index].getLocationy() + elements[index].height);

            dest.set((int)x, (int)y, (int)x + sprite_width, (int)y + sprite_height);

       }

    }

    public void updateIndex(){
        index++;

        if(index >= number_of_frames)
            index = 0;
    }

    public void updateX(){
        x += 4;

//        if(x >= area.width())//TODO
  ///          x = 0;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(getBitmap(), src, dest, null );
    }
}
