package com.example.fuat.robinhood;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class PhysicsObject extends GameObject {

	public class Size{
		float width = 0;
		float height = 0;

		public Size(){
		}

		public Size(float w, float h){
			width = w;
			height = h;
		}

		public void set(float w, float h){
			width = w;
			height = h;
		}
	}

	//	public float lapsed_time;
	public Size size = new Size();

	public boolean bComputeY = false;
	public boolean bComputeX = false;

	public float delta;
	public Vector2D direction = new Vector2D(0,0);
	public Vector2D force = new Vector2D(0,0);
	public float elasticity;
	public Vector2D friction = new Vector2D(0.9f, 0.9f);
	public float spin;
	boolean bRebound;

	public float slow_motion;

	public PhysicsObject() {
		reset();
	}

	public PhysicsObject(Vector2D _force, Vector2D _direction, boolean rebound, Bitmap bmp){
		super(bmp);

		reset();

		force.set(_force);
		direction.set(_direction);
		bRebound = rebound;
	}

	public void reset() {
		x = 0;
		y = 0;
//		zpos = 0;
		delta = 0;
//		lapsed_time = 0;
		slow_motion = 1.0f;
		direction.set(0,0);
		elasticity = 0.75f;
		friction.set(0.99f, 0.90f);
		force.set(0,0);
		spin = 0.15f;
		bComputeX = true;
		bComputeY = true;
		bRebound = true;
	}

	public float getTimeDelta(float _lapsed_time){
		return (_lapsed_time * 0.3f);
	}

	public float abs(float value) {
		return (value < 0) ? value * -1 : value;
	}

	private void updateTime(float _lapsed_time){
		delta = getTimeDelta(_lapsed_time);
	}

	public void update_y(){
		direction.height += force.height;
		y += direction.height * delta * slow_motion;
	}

	public void update_x(){
		direction.width += force.width;
		x += direction.width * delta * slow_motion;
	}

	public boolean update_vectors(float _lapsed_time, Vector2D _force, Rect bounds){
//		lapsed_time = _lapsed_time;

		updateTime(_lapsed_time);
		if (bComputeY == true || bComputeX == true)
		{
			force.set(_force);

			if(bComputeY == true) {
				update_y();

				if ((y + size.height) >= bounds.bottom /*|| (y - size.height) <= bounds.top  */) {
					if ((y + size.height) >= bounds.bottom)
						y = bounds.bottom - size.height;
					else
						y = bounds.top + size.height;

					if(bRebound == true) {
						direction.height *= -1;//change direction
						direction.height *= elasticity;//reduce momentum

						if (Math.abs(direction.height) <= 2f)
							bComputeY = false;
					}else {
						bComputeY = false;
						bComputeX = false;
					}

					return true;
				}
			}else if (bComputeX == true)
				direction.width *= friction.width;


			if(bComputeX == true) {
				update_x();

				//if hit vertical wall
				if ((x + size.width) >= bounds.right || (x - size.width) <= bounds.left) {
					if ((x + size.width) >= bounds.right)
						x = bounds.right - size.width;
					else
						x = bounds.left + size.width;

					if(bRebound == true) {
						direction.width *= -1;
						direction.width *= elasticity;
						direction.height *= ((1 / spin) / 10);//spin effect
						direction.width *= spin;
					}else {
						bComputeY = false;
						bComputeX = false;
					}

					return true;
				}
				if (Math.abs(direction.width) < 2.0f)
					bComputeX = false;
			}
		}else
			return true;

		return false;
	}

}