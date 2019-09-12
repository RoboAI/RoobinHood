package com.example.fuat.robinhood;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements GiffyView.IGifComplete{
    GameSurface surface;
    GameController controller;
    GiffyView giffyView;

    boolean first = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        giffyView = new GiffyView(this);
        giffyView.loadGif(this, +R.drawable.splash, GiffyView.SCALE_FULLSCREEN);
        giffyView.setCallback(this);
        giffyView.setbEnableTouchPause(false);

        surface = new GameSurface(this);
        controller = new GameController(this, surface, new Rect(0, 0, metrics.widthPixels, metrics.heightPixels));

        setContentView(giffyView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(first) {
            if(giffyView != null) {
                giffyView.startGIF(false);
            }
            first = false;
        }else
          controller.startGame();
    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();

        if(!first)
            controller.suspendGame();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(!first)
            controller.endGame();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();

        if(!first)
            controller.unsuspendGame();
    }

    @Override
    public void onGifComplete() {
        first = false;
        setContentView(surface);
        giffyView = null;
        controller.startGame();
    }
}
