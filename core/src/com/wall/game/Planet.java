package com.wall.game;

import java.util.ArrayList;

public class Planet {

	private float x, y;
	private int radius;

	// Returns whether the two circles intersect
	public static boolean isIntersecting(float x, float y, float r, ArrayList<Planet> planets) {
		for (Planet p : planets) {
			if (Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2) <= Math.pow(r + p.getRadius(), 2))
				return true;
		}

		return false;
	}

	// Returns the force of gravity that this object would have on a point in space
	public Force getGravity(float x, float y, int mass) {
		// First get the distance that the point is away
		// This doesn't use sqrt -->
		// https://math.stackexchange.com/questions/1351706/pythagorean-theorem-expressed-without-roots-in-an-old-tamilian-indian-statemen
		float distance = (float) Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));

		// Use gravity equation, check to see if going correct direction
		double d = Math.atan((this.x - x) / (this.y - y));
		if ((this.y - y) < 0)
			return new Force((float) (mass * radius / Math.pow(distance, 2)), d + Math.PI);
		else
			return new Force((float) (mass * radius / Math.pow(distance, 2)), d);
	}

	public Planet(float x, float y, int r) {
		this.x = x;
		this.y = y;
		this.radius = r;
	}

	// Generates a planet with random coords and size
	public Planet(int world_size) {
		this.x = (float) (Math.random() * world_size);
		this.y = (float) (Math.random() * world_size);
		this.radius = (int) (Math.random() * 32 + 32);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getRadius() {
		return radius;
	}
}
