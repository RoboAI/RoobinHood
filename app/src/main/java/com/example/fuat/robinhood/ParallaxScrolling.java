package com.example.fuat.robinhood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Fuat on 28/02/2016.
 */
public class ParallaxScrolling {
    public final static int SCROLL_HORIZONTAL = 1;
    public final static int SCROLL_VERTICAL = 2;

    int scrollDirection = 0;
    Rect rcCanvas;
    Rect rcSrc;
    Rect rcDst;
    Bitmap bitmap;

    public ParallaxScrolling(Bitmap bmp, Rect rectCanvas){
        initialise(bmp, rectCanvas);
    }

    private void initialise(Bitmap bmp, Rect rectCanvas) {
        scrollDirection = SCROLL_HORIZONTAL;

        bitmap = bmp;

        rcCanvas = new Rect(rectCanvas);
        rcSrc = new Rect(0, 0, 1, 1);
        rcDst = new Rect(0, 0, 1, 1);

        rcSrc.left = 0;
        rcSrc.top = 0;
        rcSrc.right = (rcCanvas.width() <= bitmap.getWidth()) ? rcCanvas.width() : bitmap.getWidth();
        rcSrc.bottom = (rcCanvas.height() <= bitmap.getHeight()) ? rcCanvas.height() : bitmap.getHeight();
        rcSrc.sort();

        rcDst.left = rcCanvas.left;
        rcDst.top = rcCanvas.bottom - rcSrc.height();//bottom align
        rcDst.right = rcCanvas.right;
        rcDst.bottom = rcCanvas.bottom;
        rcDst.sort();
    }

    public int getScrollDirection() {
        return scrollDirection;
    }

    public void setScrollDirection(int scrollDirection) {
        this.scrollDirection = scrollDirection;
    }

    public void scroll(float dx, float dy){
        if((scrollDirection & SCROLL_HORIZONTAL) == SCROLL_HORIZONTAL) {
            rcSrc.offset((int) dx, 0);

            if (rcSrc.left <= 0)
                rcSrc.offsetTo(0, rcSrc.top);
            else if (rcSrc.right > bitmap.getWidth())
                rcSrc.offsetTo(bitmap.getWidth() - rcSrc.width(), rcSrc.top);
        }

        if((scrollDirection & SCROLL_VERTICAL) == SCROLL_VERTICAL) {
            rcSrc.offset(0, (int) dy);

            if (rcSrc.top <= 0)
                rcSrc.offsetTo(rcSrc.left, 0);
            else if (rcSrc.bottom > bitmap.getHeight())
                rcSrc.offsetTo(rcSrc.left, bitmap.getHeight() - rcSrc.height());
        }

    }

    public /*final*/ Rect getRcSrc(){
        return rcSrc;
    }

    public /*final*/ Rect getRcDst(){
        return rcDst;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, rcSrc, rcDst, null);
    }
}
