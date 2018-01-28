package com.wall.game;

public abstract class Body {

	// Whether or not this body is affected by forces
	// Only immovable objects exhibit forces
	protected final boolean movable;

	protected float x, y;
	protected float mass;

	public Body(boolean mov) {
		this.movable = mov;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getMass() {
		return mass;
	}
	public void setMass(float mass) {
		this.mass = mass;
	}
	
	// Returns the force of gravity that this object would have on a point in space
	public Force getGravity(float x, float y) {
		// Check to see if it exhibits gravity
		if (!movable) {
			// First get the distance that the point is away
			// This doesn't use sqrt -->
			// https://math.stackexchange.com/questions/1351706/pythagorean-theorem-expressed-without-roots-in-an-old-tamilian-indian-statemen
			float distance = (float) Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));

			// Use gravity equation, check to see if going correct direction
			if ((this.y - y) < 0)
				return new Force((float) (mass / Math.pow(distance, 2)),
						Math.atan((this.x - x) / (this.y - y)) + Math.PI);
			else
				return new Force((float) (mass / Math.pow(distance, 2)), Math.atan((this.x - x) / (this.y - y)));
		}
		
		return new Force();
	}
}
