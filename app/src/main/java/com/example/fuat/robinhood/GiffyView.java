package com.example.fuat.robinhood;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.FileInputStream;
import java.io.InputStream;

public class GiffyView extends View implements View.OnTouchListener, View.OnFocusChangeListener{
    private final static int TIMER_DELAY_MS = 50;

    public final static int SCALE_NONE = 0;
    public final static int SCALE_RATIO = 1;
    public final static int SCALE_FULLSCREEN = 2;

	public interface IGifComplete{
		void onGifComplete();
	}

	Handler handler;
	Runnable runnable;
	Boolean bStop;
	boolean bLoop;
	long pauseTime;

	private Movie gifMovie;
	private int movieWidth, movieHeight;
	private long movieDuration;
	private long movieStart;

	IGifComplete callback;
	long time_now;
	int relTime;

    int bScale;
	boolean bEnableTouchPause;

    float scale_x;
    float scale_y;
	float canvasWidth;
	float canvasHeight;
	Rect rcGif;
	int	backgroundColour;

	public GiffyView(Context context) {
		super(context);
		init();
	}

	public GiffyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GiffyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void resetValues() {
		bStop = false;
		bLoop = false;
        bScale = SCALE_RATIO;
        pauseTime = 0;
		time_now = 0;
		relTime = 0;
		scale_x = 1.0f;
		scale_y = 1.0f;
		rcGif.set(0,0,0,0);
	}

	private void initBackgroundThread(){
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (bStop) {
                    invalidate();

                    if (!bStop) {
                        handler.postDelayed(this, TIMER_DELAY_MS);
                    }
                }
            }
        };
    }

	private void init() {
		setFocusable(true);
		setClickable(true);
		setOnTouchListener(this);

		rcGif = new Rect();

		initBackgroundThread();

        canvasWidth = 0;
        canvasHeight = 0;
        bEnableTouchPause = true;
        backgroundColour = Color.BLACK;

		resetValues();
	}

	private void animComplete(){
		if(bLoop) {
			synchronized (bStop) {
				if (!bStop) {
					startGIF(bLoop);
				}
			}
		}else{
			stopGIF();
			notifyListener();
		}
	}

	private void notifyListener(){
		if (callback != null)
			callback.onGifComplete();
	}

	private void _startGIF(){
		bStop = false;
		handler.postDelayed(runnable, 1);
	}

	public boolean loadGif(Context context, Movie movie, int iScale){
		stopGIF();
		resetValues();

		try {
			gifMovie = movie;
			movieWidth = gifMovie.width();
			movieHeight = gifMovie.height();
			movieDuration = gifMovie.duration();
		}catch (Exception e){
			resetValues();
			return false;
		}

		//this also calls setupDimensions()
		setbScale(iScale);

		return true;
	}

	public boolean loadGif(Context context, InputStream stream, int iScale){
		boolean b = false;

		try{
			b = loadGif(context, Movie.decodeStream(stream), iScale);
		}catch (Exception e){
			resetValues();
			b = false;
		}

		return b;
	}

	public boolean loadGif(Context context, String path, int iScale) {
        boolean b = false;

        try {
            b = loadGif(context, Movie.decodeFile(path), iScale);
        } catch (Exception e) {
            resetValues();
            b = false;
        }

        return b;
    }

	public boolean loadGif(Context context, int rawID, int iScale) {
        InputStream stream = context.getResources().openRawResource(rawID);
        return loadGif(context, stream, iScale);
    }

	public void pause(){
		if(!bStop) {
			pauseTime = SystemClock.uptimeMillis();
			stopGIF();
		}
	}

	public void resume(){
		if(bStop) {
			movieStart += SystemClock.uptimeMillis() - pauseTime;
			_startGIF();
		}
	}

	public void setCallback(IGifComplete g){
		callback = g;
	}

	public void stopGIF(){
		bStop = true;
	}

	public void startGIF(boolean loop){
		bLoop = loop;
		movieStart = time_now = SystemClock.uptimeMillis();
		_startGIF();
	}

	public int getMovieWidth() {
		return movieWidth;
	}

	public int getMovieHeight() {
		return movieHeight;
	}

	public int getCanvasWidth(){
	    return (int)canvasWidth;
    }

    public int getCanvasHeight(){
        return (int)canvasHeight;
    }

	public long getMovieDuration() {
		return movieDuration;
	}

    public void setbScale(int iScale) {
        this.bScale = iScale;
        setupDimensions(getCanvasWidth(), getCanvasHeight(), iScale);
    }

    public void setBackgroundColour(int colour){
		backgroundColour = colour;
	}

	public int getBackgroundColour(){
		return backgroundColour;
	}

	public void setbEnableTouchPause(boolean bEnableTouchPause) {
		this.bEnableTouchPause = bEnableTouchPause;
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (bEnableTouchPause) {
                if (!bStop)
                    pause();
                else
                    resume();
            }
		}

		return false;
	}

	@Override
	public void onFocusChange(View view, boolean b) {
		if (!bStop) {
			pause();
		} else
			resume();
	}

	//This function center's rcGif (rect) to the given canvasWidth and canvasHeight.
    //
    //How this works:
    //if scaling, first see which side (right or bottom) is closer to the edge. Calculate the ratio (scale) between
    //THAT side and the edge of the view. We will use this 'scale' to scale the entire image.
    //
    //
	private void setupDimensions(int canvasWidth, int canvasHeight, int iScale) {
		rcGif.setEmpty();
		scale_x = 0;
		scale_y = 0;

		rcGif.right = rcGif.left + getMovieWidth();
		rcGif.bottom = rcGif.top + getMovieHeight();

		if (iScale == SCALE_NONE) {
			rcGif.left = (canvasWidth / 2) - (getMovieWidth() / 2);
			rcGif.top = (canvasHeight / 2) - (getMovieHeight() / 2);
		}
        else if (iScale == SCALE_FULLSCREEN) {
			scale_x = canvasWidth / (float) getMovieWidth();
			scale_y = canvasHeight / (float) getMovieHeight();
        }

        else if (iScale == SCALE_RATIO) {
            //whichever side is closer to the edge of the view, calculate scale accordingly
            //e.g. if horizontal-sides were closer, then 'right' of movie would touch the sides first if scaled
			float sx = (canvasWidth / (float) getMovieWidth());
			float sy = (canvasHeight / (float) getMovieHeight());
			if (sx <= sy) {
                scale_x = scale_y = sx;
            } else {
                scale_x = scale_y = sy;
            }

            rcGif.left = (int) ((canvasWidth - (getMovieWidth() * scale_x)) / (2 * scale_x));
            rcGif.top = (int) ((canvasHeight - (getMovieHeight() * scale_y)) / (2 * scale_y));
        }
    }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

		canvasWidth = MeasureSpec.getSize(widthMeasureSpec);
		canvasHeight = MeasureSpec.getSize(heightMeasureSpec);

		setupDimensions(getCanvasWidth(), getCanvasHeight(), bScale);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(backgroundColour);

		if (gifMovie != null) {

            if (!bStop) {
                time_now = SystemClock.uptimeMillis();

                relTime = (int) (time_now - movieStart);
                gifMovie.setTime(relTime);

                //if completed
                if (relTime >= getMovieDuration()) {

                    //set to end of gif
                    gifMovie.setTime((int) movieDuration);

                    //do whatever is needed
                    animComplete();
                }
            }

            canvas.save();

            if (bScale != SCALE_NONE)
                canvas.scale(scale_x, scale_y);

            gifMovie.draw(canvas, rcGif.left, rcGif.top);
            canvas.restore();
        }
	}

	/*
	public void addText(){
		infoTexts.addText(" ");
		infoTexts.addText(" ");
		infoTexts.addText(" ");
		infoTexts.addText(String.valueOf(gifMovie.width()));
		infoTexts.addText(String.valueOf(gifMovie.height()));
		infoTexts.addText(String.valueOf(getMovieDuration()));
		infoTexts.addText(String.valueOf(relTime));
		infoTexts.addText(String.valueOf(canvas_ratio));
		infoTexts.addText(String.valueOf(canvasWidth));
		infoTexts.addText(String.valueOf(canvasHeight));
		infoTexts.addText(String.valueOf(gif_ratio));
		infoTexts.addText(String.valueOf(fsWidth));
		infoTexts.addText(String.valueOf(fsHeight));
	}
*/
}
