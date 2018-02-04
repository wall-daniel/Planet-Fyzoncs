package com.wall.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Planet {
	public static final float PLANET_DENSITY = 0.75f;
	private static final float GRAVITY_CONSTANT = 9.8f;

	private int radius;
	private Vector2 position;
	
	private ArrayList<Vector2> jumpPads;

	public Vector2 getGravity(float x, float y, float mass) {
		// Get the distance between the planet and object
		float gravity = (float) (GRAVITY_CONSTANT * mass * (Math.pow(radius, 2) * PLANET_DENSITY)
				/ getDistance(x, y, position.x, position.y));

		double direction = Math.atan2(position.y - y, position.x - x);

		return new Vector2((float) (gravity * Math.cos(direction)), (float) (gravity * Math.sin(direction)));
	}
	
	public static float getDistance(Vector2 pos1, Vector2 pos2) {
		return getDistance(pos1.x, pos1.y, pos2.x, pos2.y);
	}
	
	public static float getDistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
	
	public Vector2 getGravity(Vector2 position, float mass) {
		return getGravity(position.x, position.y, mass);
	}

	// Returns whether the two circles intersect
	public static boolean isIntersecting(float x, float y, float r, ArrayList<Planet> planets) {
		for (Planet p : planets) {
			if (getDistance(x, y, p.getX(), p.getY()) <= r + p.getRadius() + 16)
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
	
	public void createJumpPad(Vector2 pos) {
		jumpPads.add(pos);
	}
	
	public ArrayList<Vector2> getJumpPads() {
		return jumpPads;
	}
}
