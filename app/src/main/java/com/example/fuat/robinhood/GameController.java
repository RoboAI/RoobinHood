package com.example.fuat.robinhood;

import android.content.Context;
import android.graphics.Rect;
import android.view.SurfaceView;

/**
 * Created by Fuat on 21/10/2017.
 */

public class GameController {
    GameScene currentScene;
    GMainMenu mainMenu;
    Level1 level1;

    Context context;
    SurfaceView surfaceView;
    Rect area;

    public GameController(Context ctx, SurfaceView sv, Rect r){
        context = ctx;
        surfaceView = sv;
        area = new Rect(r);

        initialise();
    }

    private void initialise(){
        mainMenu = new GMainMenu(context, surfaceView);
        level1 = new Level1(context, surfaceView, area);

        currentScene = level1;
    }

    public void pause(){
        if(currentScene != null)
            currentScene.pause();
    }

    public void resume(){
        if(currentScene != null)
            currentScene.unpause();
    }

    public void startGame(){
        if(currentScene != null)
            if(!currentScene.isRunning()) {
                currentScene.start();
                currentScene.unpause();
            }
    }

    public void unsuspendGame(){
        startGame();
    }

    public void suspendGame(){
        if(currentScene != null)
            currentScene.stop();
    }

    public void endGame(){
        if(currentScene != null)
            currentScene.stop();
    }
}
