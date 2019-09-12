package com.example.fuat.robinhood;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MultiTouchController implements View.OnTouchListener{

	public interface TouchUpdateListener{
		/** @param touch the single touchSling element  **/
		void onTouchUpdate(SingleTouch touch);
		void onTouchDown(SingleTouch touch);
		void onTouchUp(SingleTouch touch);
	}

	public class SingleTouch {
		public int id;
		public int index;
		public float x;
		public float y;
		public boolean bIsDown;
		public int type;
	}
	
	private TouchUpdateListener touchUpdateListener;
	public ArrayList<SingleTouch> pointers = new ArrayList<SingleTouch>();
	

	public MultiTouchController() {
	}

	protected void InitTouch(SingleTouch touch) {
		touch.id = 0;
		touch.index = 0;
		touch.x = 0;
		touch.y = 0;
		touch.bIsDown = false;
		touch.type = 0;// not currently used
	}
	
	/** @param l the new listener **/
	public void setOnTouchUpdate(TouchUpdateListener l){
		touchUpdateListener = l;
	}
	
	private void notifyOnTouchUpdate(SingleTouch t){
		if(touchUpdateListener != null){
			touchUpdateListener.onTouchUpdate(t);
		}
	}

    private void notifyOnTouchDown(SingleTouch t){
        if(touchUpdateListener != null){
            touchUpdateListener.onTouchDown(t);
        }
    }

    private void notifyOnTouchUp(SingleTouch t){
        if(touchUpdateListener != null){
            touchUpdateListener.onTouchUp(t);
        }
    }

	protected SingleTouch findTouchById(int id) {
		SingleTouch touch = new SingleTouch();
		for (int i = 0; i < pointers.size(); i++) {
			if (pointers.get(i).id == id) {
				touch = pointers.get(i);
				break;
			}
		}
		return touch;
	}

	private void addPointer(MotionEvent event) {
		SingleTouch touch = new SingleTouch();
		int i_index = event.getActionIndex();

		Log.i("Motion",
				"AddNonPrimaryPointer -"
						+ String.valueOf(event.getPointerId(event
								.getActionIndex())));

		InitTouch(touch);
		touch.id = event.getPointerId(i_index);
		touch.index = i_index;
		touch.x = event.getX(touch.index);
		touch.y = event.getY(touch.index);
		touch.bIsDown = true;
		pointers.add(touch);

		notifyOnTouchDown(touch);

	}

	private SingleTouch removePointer(MotionEvent event) {
		int remove_id = event.getPointerId(event.getActionIndex());
        SingleTouch t;

		Log.i("Motion", "removePointer -" + String.valueOf(remove_id));

		for (int i = 0, size = pointers.size(); i < size; i++) {
			if (pointers.get(i).id == remove_id) {
                t = pointers.remove(i);
                t.bIsDown = false;
                return t;
			}
		}

		return  null;
	}

	private void pointerUpdate(MotionEvent event) {
		Log.i("Motion",
				"PointerUpdate -"
						+ event.getPointerId(event.getActionIndex()));

		for (int i = 0, count = event.getPointerCount(); i < count; i++) {
			SingleTouch touch = findTouchById(event.getPointerId(i));

			touch.x = event.getX(i);
			touch.y = event.getY(i);
			
			notifyOnTouchUpdate(touch);
		}
	}

	private void actionCancelled(MotionEvent event) {
		Log.i("Motion",
				"ActionCancelled -"
						+ event.getPointerId(event.getActionIndex()));

		pointers.clear();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                addPointer(event);
                break;
            case MotionEvent.ACTION_UP:
                notifyOnTouchUp( removePointer(event) );
                break;
            case MotionEvent.ACTION_MOVE:
                pointerUpdate(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                actionCancelled(event);
                break;
            case MotionEvent.ACTION_HOVER_ENTER:
                addPointer(event);
                break;
            case MotionEvent.ACTION_HOVER_EXIT:
                removePointer(event);
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                pointerUpdate(event);
                break;
            case MotionEvent.ACTION_MASK:
                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                addPointer(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                removePointer(event);
                break;
            case MotionEvent.ACTION_SCROLL:
                break;
            default:
                break;
        }
		return true;
	}

}
