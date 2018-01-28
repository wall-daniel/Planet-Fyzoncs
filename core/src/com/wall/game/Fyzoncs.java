package com.wall.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Fyzoncs extends ApplicationAdapter {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int WORLD_SIZE = 480;

	SpriteBatch sb;
	ShapeRenderer sr;

	float x, y;
	Force f;

	ArrayList<Planet> planets;

	@Override
	public void create() {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();

		x = y = 420;

		planets = new ArrayList<Planet>();
		for (int i = 0; i < 4; i++) {
			float x = (float) (Math.random() * WORLD_SIZE);
			float y = (float) (Math.random() * WORLD_SIZE);
			int r = (int) (Math.random() * 96 + 32);

			// Check if it is in a planet
			if (!Planet.isIntersecting(x, y, r, planets))
				planets.add(new Planet(x, y, r));
		}
		
		f = new Force();
	}

	public void update(float dt) {
		// Calculate the force of all the planets in middle of screen
		for (Planet p : planets) {
			f.addForce(p.getGravity(x, y));
		}
		// System.out.println(f.toString());

		x += f.x * dt;
		y += f.y * dt;
	}

	@Override
	public void render() {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sr.begin(ShapeType.Filled);
		sr.setColor(1, 1, 1, 1);

		for (Planet p : planets) {
			sr.circle(p.getX(), p.getY(), p.getRadius());
		}

		sr.rect(x, y, 32, 32);

		sr.end();
	}

	@Override
	public void dispose() {
		sr.dispose();
		sb.dispose();
	}
}
