package com.wall.game;

public class Force {

	public float x, y;
	public double direction;
	
	public Force(float m, double d) {
		this.direction = d;
		
		this.x = (float) (m * Math.sin(d));
		this.y = (float) (m * Math.cos(d));
	}
	
	public Force() {
		x = 0;
		y = 0;
		direction = 0;
	}
	
	public void addForce(Force f) {
		this.x += f.x;
		this.y += f.y;
		
		// Calculate the new direction
		direction = Math.atan(x/y);
	}
	
	public String toString() {
		return x + " - " + y + ", " + direction + "°";
	}
}
