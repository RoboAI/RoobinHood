package com.example.fuat.robinhood;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Fuat on 22/09/2017.
 */

public class Level1 extends GameScene implements MultiTouchController.TouchUpdateListener{
    //touch controller
    MultiTouchController touchController;

    //this returns the vector based on the user's drag-line
    TouchVector touchVector;

    //stores the current touch-event
    MultiTouchController.SingleTouch touchDrag;

    //array for all objects in the scene
    ArrayList<GameObject> scene_objects;

    //the 'game objects'
    private SceneBackground background;
    private Ground ground;
    private TheSun sun;
    private Bow bow;
    private StaticArrow staticArrow;
    private BowString bowString;
    private ArrowsThrower arrowsThrower;
    private FingerLine fingerLine;
    private Stars stars;
//    private TrailsBase trails;

    //we always need this
    Context ctx;

    //area
    Rect area;
    Rect actualArea;

    //temporary vector for touch-input
    Vector2D new_vec;

    //debug / info purposes
    InfoTexts info = new InfoTexts();
    int counter;
    long __time;
    private Paint paint;

    public Level1(Context context, SurfaceView sv, Rect r) {
        super(context, sv);

        ctx = context;
        surfaceView = sv;

        initialise(r);
        setupScene();
    }

    private void setupScene(){

        //sun
        sun.setup(area.right - sun.getBitmap().getWidth(), 0);

        //ground
        ground.setupArea(area);

        //actual area (screen minus the ground)
        actualArea.set(area.left, area.top, area.right, ground.getArea().top);

        //setup bow's coords
        bow.setupBow(200, 600);

        //place the static arrow inside the bow
        staticArrow.setupArrow(bow.getX(),
                (bow.getY() + bow.half_height),
                bow.xCenter - bow.getX(),
                bow.yCenter - bow.getY());

        //place the bow-string on the bow
        bowString.setupPointsArray(bow.getTopX(), bow.getTopY(),
                bow.getTailX(), bow.getTailY(),
                bow.getBottomX(), bow.getBottomY());

        //setup arrowsThrower's scene-area
        arrowsThrower.setup(actualArea);
    }

    private void initialise(Rect r){
        Resources res = ctx.getResources();

        touchController = new MultiTouchController();
        touchController.setOnTouchUpdate(this);
        surfaceView.setOnTouchListener(touchController);

        area = new Rect(r);

        actualArea = new Rect();

        touchDrag = touchController.new SingleTouch();
        touchVector = new TouchVector();

        new_vec = new Vector2D();

        background = new SceneBackground(area);
 //       stars = new Stars(area);
        ground = new Ground();
        fingerLine = new FingerLine();
        bowString = new BowString();
        sun = new TheSun(BitmapFactory.decodeResource(res, R.drawable.sunny));
        bow = new Bow(BitmapFactory.decodeResource(res, R.drawable.med_small_bow));
        staticArrow = new StaticArrow(BitmapFactory.decodeResource(res, R.drawable.med_small_arrow));
        arrowsThrower = new ArrowsThrower(BitmapFactory.decodeResource(res, R.drawable.med_small_arrow));

        scene_objects = new ArrayList<>(100);
        scene_objects.add(background);
  //      scene_objects.add(stars);
        scene_objects.add(sun);
        scene_objects.add(ground);
        scene_objects.add(bow);
        scene_objects.add(bowString);
        scene_objects.add(staticArrow);
        scene_objects.add(fingerLine);
        scene_objects.add(arrowsThrower);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(4);
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.onSurfaceChanged(holder, format, width, height);

        area.set(0, 0, surfaceView.getWidth(), surfaceView.getHeight());
        setupScene();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        area.set(0, 0, surfaceView.getWidth(), surfaceView.getHeight());
        setupScene();
    }

    @Override
    public void onUpdate(long _time) {
        for(int i = 0; i < scene_objects.size(); i++){
            scene_objects.get(i).update(1);
            //TODO passing _time does not work
        }

        counter++;
        __time = _time;
    }

    @Override
    public void onDrawGraphics(Canvas canvas) {
        for(int i = 0; i < scene_objects.size(); i++){
            scene_objects.get(i).draw(canvas);
        }

      //  drawInfoTexts(canvas);
        info.clearAll();
    }

    public void drawInfoTexts(Canvas canvas){
        addText();
        info.drawAll(canvas);

    }

    public void addText(){
        info.addText("-----");
        info.addText("-----");
        info.addText("-----");

        info.addText("counter:                   " + (String.valueOf(counter)));

        info.addText("area.width:                " + String.valueOf(area.width()));
        info.addText("area.height:               " + String.valueOf(area.height()));

        info.addText("touchVector.x1:    " + String.valueOf(touchVector.x1));
        info.addText("touchVector.y1:    " + String.valueOf(touchVector.y1));
        info.addText("touchVector.vec.width:     " + String.valueOf(touchVector.getVector().width));
        info.addText("touchVector.vec.height:    " + String.valueOf(touchVector.getVector().height));
        info.addText("touchVector.vec.length:    " + String.valueOf(touchVector.getVector().length()));

        info.addText("angle Rad:                 " + String.valueOf(bow.angle));
        info.addText("angle Deg:                 " + String.valueOf(Math.toDegrees(bow.angle)));

        info.addText("touchX:                    " + String.valueOf((int) touchDrag.x));
        info.addText("touchY:                    " + String.valueOf((int) touchDrag.y));

        info.addText("staticArrow.X:                   " + (String.valueOf(staticArrow.getX())));
        info.addText("staticArrow.Y:                   " + (String.valueOf(staticArrow.getY())));
        info.addText("staticArrow.vec.length:          " + (String.valueOf(staticArrow.vec.length())));
        info.addText("staticArrow.vec.width:           " + (String.valueOf(staticArrow.vec.width)));
        info.addText("staticArrow.vec.height:          " + (String.valueOf(staticArrow.vec.height)));
        info.addText("staticArrow.offset:              " + (String.valueOf(staticArrow.offset)));
        info.addText("staticArrow.bitmap.height:       " + (String.valueOf(staticArrow.getBitmap().getHeight())));

        info.addText("thrower.direction.width:   " + (String.valueOf(staticArrow.vec.width)));
        info.addText("thrower.direction.height:  " + (String.valueOf(staticArrow.vec.height)));

        info.addText("object count:  " + (String.valueOf(scene_objects.size())));


        info.addText("time:  " + (String.valueOf(__time)));

        //    for(int i = 0; i < 8; i++)
        //        info.addText("points[" + i + "]:" + String.valueOf(staticArrow.points[i]));

        //    info.addText("arrowAngle:             " + (String.valueOf(arrowsThrower.arrows.get(0).angle)));

    }

    @Override
    public void onTouchDown(MultiTouchController.SingleTouch touch) {
        if(touch.index == 0) {
            this.touchDrag = touch;

            touchVector.onTouchDown((int) touchDrag.x, (int) touchDrag.y);

            //changed
            fingerLine.onTouchDown(touchVector.getX1(), touchVector.getY1());
            staticArrow.onTouchDown(touchVector.getVector());
       //     arrowsThrower.bGo = false;
            staticArrow.showArrow(true);

            bowString.setupPointsArray(bow.getTopX(), bow.getTopY(),
                    staticArrow.getTailX(), staticArrow.getTailY(),
                    bow.getBottomX(), bow.getBottomY());

        }
    }

    @Override
    public void onTouchUpdate(MultiTouchController.SingleTouch touch) {
        if(touch.index == 0) {
            this.touchDrag = touch;

            touchVector.onTouchUpdate((int) touchDrag.x, (int) touchDrag.y);

            if (touchVector.getVector().length() > 50) {

                fingerLine.onTouchUpdate(touchVector.getX2(), touchVector.getY2());
                bow.updateValues(touchDrag, touchVector.getVector());
                staticArrow.updateValues(touchDrag, touchVector.getVector());
                bowString.updateCoords(bow.getTopX(), bow.getTopY(),
                        staticArrow.getTailX(), staticArrow.getTailY(),
                        bow.getBottomX(), bow.getBottomY());
            }
        }
    }

    @Override
    public void onTouchUp(MultiTouchController.SingleTouch touch) {
        if(touch.index == 0) {
            this.touchDrag = touch;
            this.touchDrag.bIsDown = false;

            fingerLine.onTouchUp(touchVector.getX2(), touchVector.getY2());
            bow.onTouchUp(touchDrag);
            staticArrow.onTouchUp(touchDrag);
            staticArrow.showArrow(false);

            new_vec.set(touchVector.getVector().getWidth(), -touchVector.getVector().getHeight());
            new_vec.multiplyByScalar(0.333f);
            arrowsThrower.addNewArrow(staticArrow.getX(), staticArrow.getY(), actualArea, new_vec);

            bowString.setupPointsArray(bow.getTopX(), bow.getTopY(),
                    bow.getTailX(), bow.getTailY(),
                    bow.getBottomX(), bow.getBottomY());
        }
    }
}
