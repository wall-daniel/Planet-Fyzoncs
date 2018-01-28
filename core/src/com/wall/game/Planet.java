package com.wall.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Planet {
	public static final int PLANET_DENSITY = 1;

	private int radius;
	private Vector2 position;

	// Returns whether the two circles intersect
	public static boolean isIntersecting(float x, float y, float r, ArrayList<Planet> planets) {
		for (Planet p : planets) {
			if (Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2) <= Math.pow(r + p.getRadius(), 2))
				return true;
		}

		return false;
	}

	public Planet(float x, float y, int r) {
		position = new Vector2(x, y);

		this.radius = r;
	}

	// Generates a planet with random coords and size
	public Planet(int world_size) {
		radius = (int) (Math.random() * 32 + 32);

		position = new Vector2((float) (Math.random() * (world_size - radius) + radius),
				(float) (Math.random() * (world_size - radius) + radius));
	}

	public int getRadius() {
		return radius;
	}

	public Vector2 getPosition() {
		return position;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}
}
