package com.example.fuat.robinhood;

public class Vector2D {
	protected float width;
	protected float height;

	public Vector2D(){
	}
	
	public Vector2D(float w, float h) {
		width = w;
		height = h;
	}
	
	public Vector2D(Vector2D v){
		width = v.width;
		height = v.height;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public void set(float w, float h){
		width = w;
		height = h;
	}

	public void set(Vector2D new_vec){
		width = new_vec.width;
		height = new_vec.height;
	}

	public Vector2D invert() {
		width = -width;
		height = -height;
		return this;
	}

	public float length(){
		return (float) Math.sqrt((width * width) + (height * height));
	}

	public float dotProduct(Vector2D v) {
		return (width * v.width) + (height * v.height);
	}

	public float angleBetween2DVectors(Vector2D v) {
		return (float) Math.acos(dotProduct(v) / (v.length() * length()));
	}

	public Vector2D multiplyByScalar(float scalar){
		width *= scalar;
		height *= scalar;
		return this;
	}

	public Vector2D add(Vector2D v) {
		width += v.width;
		height += v.height;
		return this;
	}

	public Vector2D subtract(Vector2D v) {
		return add(new Vector2D(-v.width, -v.height));//TODO
	}

	public Vector2D normalise(){
		float vmag = length();
		if(vmag > 0){
			width = width / vmag;
			height = height / vmag;
		}
		return this;
	}

    public Vector2D calculateReboundAngle(Vector2D wall){
		Vector2D v = new Vector2D(this);
		Vector2D n = new Vector2D(wall);

		Vector2D u = n.multiplyByScalar(v.dotProduct(n) / n.dotProduct(n));
		Vector2D w = v.subtract(u);
		w.subtract(u);
		return w.invert();
    }

	public Vector2D calcCWPerpendicular(){
		return new Vector2D(-height, width);
	}

	public Vector2D extendByValue(float s){
		float length = length();
		return multiplyByScalar((length + s) / length);
	}

	static public Vector2D multiplyByScalar(Vector2D v, float s){
		v.width *= s;
		v.height *= s;
		return v;
	}

	static public float length(Vector2D v){
		return (float) Math.sqrt((v.width * v.width) + (v.height * v.height));
	}

	static public float dotProduct(Vector2D v1, Vector2D v2) {
		return (v1.width * v2.width) + (v1.height * v2.height);
	}

	static public float angleBetween2DVectors(Vector2D v1, Vector2D v2) {
		return (float) Math.acos( dotProduct(v1, v2) / (length(v1) * length(v2)) );
	}

	static public Vector2D add(Vector2D v1, Vector2D v2) {
		return new Vector2D(v1.width + v2.width, v1.height + v2.height);
	}

	static public Vector2D subtract(Vector2D v1, Vector2D v2) {
		return add(v1, new Vector2D(-v2.width, -v2.height));
	}

	static public Vector2D normalise(Vector2D v){
		Vector2D a = new Vector2D(v);
		float vmag = length(a);
		if(vmag > 0){
			a.width = a.width / vmag;
			a.height = a.height / vmag;
		}
		return a;
	}

	static Vector2D limitVectorLength(Vector2D vec, float max, float min){
		float length = Math.abs(vec.length());

		if (length > max) {
			vec.multiplyByScalar(max / length);
		} else if (length < min) {
			vec.multiplyByScalar(min / length);
		}

		return vec;
	}
}
