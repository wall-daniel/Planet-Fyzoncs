package com.wall.game;

import java.util.ArrayList;

public class Planet extends Body {
	public static final int PLANET_DENSITY = 1;
	
	private int radius;

	// Returns whether the two circles intersect
	public static boolean isIntersecting(float x, float y, float r, ArrayList<Planet> planets) {
		for (Planet p : planets) {
			if (Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2) <= Math.pow(r + p.getRadius(), 2))
				return true;
		}

		return false;
	}

	

	public Planet(float x, float y, int r) {
		super(false);
		
		this.x = x;
		this.y = y;
		this.radius = r;
		
		setMass((float) (PLANET_DENSITY * (Math.PI * Math.pow(radius, 2))));
	}

	// Generates a planet with random coords and size
	public Planet(int world_size) {
		super(false);
		
		this.x = (float) (Math.random() * world_size);
		this.y = (float) (Math.random() * world_size);
		this.radius = (int) (Math.random() * 32 + 32);

		setMass((float) (PLANET_DENSITY * (Math.PI * Math.pow(radius, 2))));
	}

	public int getRadius() {
		return radius;
	}
}
