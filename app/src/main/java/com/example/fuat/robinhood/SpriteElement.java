package com.example.fuat.robinhood;

/**
 * Created by Fuat on 01/04/2017.
 */

public class SpriteElement {
    int locationx;
    int locationy;
    int width;
    int height;

    public SpriteElement(int x, int y){
        locationx = x;
        locationy = y;
    }

    public int getLocationx() {
        return locationx;
    }

    public void setLocationx(int locationx) {
        this.locationx = locationx;
    }

    public int getLocationy() {
        return locationy;
    }

    public void setLocationy(int locationy) {
        this.locationy = locationy;
    }
}
