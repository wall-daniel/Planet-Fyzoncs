package com.wall.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Fyzoncs extends ApplicationAdapter {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int WORLD_SIZE = 480;

	World world;
	Box2DDebugRenderer debugRenderer;
	OrthographicCamera camera;
	Body orbiter;
	
	SpriteBatch sb;
	ShapeRenderer sr;

	float x, y;
	Force f;

	ArrayList<Planet> planets;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();
		
		sb = new SpriteBatch();
		sr = new ShapeRenderer();

		x = y = 420;

		planets = new ArrayList<Planet>();
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		for (int i = 0; i < 6; i++) {
			float x = (float) (Math.random() * WORLD_SIZE);
			float y = (float) (Math.random() * WORLD_SIZE);
			int r = (int) (Math.random() * 64 + 32);

			// Check if it is in a planet
			if (!Planet.isIntersecting(x, y, r, planets)) {
				planets.add(new Planet(x, y, r));
				
				// Add planet to box2d TODO
				bodyDef.position.set(x, y);
				Body planetBody = world.createBody(bodyDef);
				
				CircleShape planet = new CircleShape();
				planet.setRadius(r);
				
				FixtureDef fixDef = new FixtureDef();
				fixDef.shape = planet;
				fixDef.friction = 0.4f;
				
				Fixture fixture = planetBody.createFixture(fixDef);
				
				planet.dispose();
			}
		}
		
		f = new Force();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x + 16, y + 16);
		orbiter = world.createBody(bodyDef);
		
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(16, 16);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = poly;
		fixtureDef.friction = 0.01f;
		fixtureDef.restitution = 0.6f;
		orbiter.createFixture(fixtureDef);
		poly.dispose();
	}

	public void update(float dt) {
		// Calculate the force of all the planets in middle of screen
		for (Planet p : planets) {
			f.addForce(p.getGravity(orbiter.getPosition()));
		}
		// System.out.println(f.toString());
		
		orbiter.applyForce(f.getVector(), orbiter.getPosition(), false);
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

		sr.rect(orbiter.getPosition().x - 16, orbiter.getPosition().y - 16, 32, 32);

		sr.end();
		
		// Update box2d
		debugRenderer.render(world, camera.combined);
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
	}

	@Override
	public void dispose() {
		sr.dispose();
		sb.dispose();
	}
}
