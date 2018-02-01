package com.wall.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Planet {
	public static final int PLANET_DENSITY = 8;
	private static final float GRAVITY_CONSTANT = 25f;

	private int radius;
	private Vector2 position;

	public Vector2 getGravity(float x, float y, float mass) {
		// Get the distance between the planet and object
		float gravity = (float) (GRAVITY_CONSTANT * mass * (Math.pow(radius, 2) * PLANET_DENSITY)
				/ Math.pow(Math.sqrt(Math.pow(position.x - x, 2) + Math.pow(position.y - y, 2)), 2));

		double direction = Math.atan2(position.y - y, position.x - x);

		return new Vector2((float) (gravity * Math.cos(direction)), (float) (gravity * Math.sin(direction)));
	}
	
	public Vector2 getGravity(Vector2 position, float mass) {
		return getGravity(position.x, position.y, mass);
	}

	// Returns whether the two circles intersect
	public static boolean isIntersecting(float x, float y, float r, ArrayList<Planet> planets) {
		for (Planet p : planets) {
			if (Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2) <= Math.pow(r + p.getRadius(), 2) + 16)
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
