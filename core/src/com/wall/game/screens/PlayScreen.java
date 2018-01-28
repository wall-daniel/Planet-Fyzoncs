package com.wall.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.wall.game.Fyzoncs;
import com.wall.game.Planet;

public class PlayScreen implements Screen {

	private SpriteBatch sb;
	private ShapeRenderer sr;

	private World world;
	private Box2DDebugRenderer debugRenderer;

	private OrthographicCamera camera;
	
	private ArrayList<Planet> planets;
	private Rectangle rectangle;

	public PlayScreen(SpriteBatch sb, ShapeRenderer sr) {
		this.sb = sb;
		this.sr = sr;

		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();

		camera = new OrthographicCamera(Fyzoncs.WIDTH, Fyzoncs.HEIGHT);
		camera.update();
		
		// Create the planets
		planets = new ArrayList<Planet>();
		for(int i = 0; i < 5;i++) {
			int r = (int) (Math.random() * 32 + 32);
			float x = (float) (Math.random() * (Fyzoncs.WIDTH - r) + r);
			float y = (float) (Math.random() * (Fyzoncs.HEIGHT - r) + r);
			
			// Make sure planets don't intersect
			if(!Planet.isIntersecting(x, y, r, planets)) {
				planets.add(new Planet(x, y, r));
				
				BodyDef bodyDef = new BodyDef();
				bodyDef.position.set(x, y);
				bodyDef.type = BodyType.StaticBody;
				Body planetBody = world.createBody(bodyDef);
				CircleShape planetShape = new CircleShape();
				planetShape.setRadius(r);
				FixtureDef fixtureDef = new FixtureDef();
				fixtureDef.shape = planetShape;
				fixtureDef.friction = 0.4f;
				fixtureDef.restitution = 0.2f;
				Fixture fixture = planetBody.createFixture(fixtureDef);
				planetShape.dispose();
			}
		}
	}

	public void handleInput(float delta) {
		// Move the camera on a plain
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(delta * -Fyzoncs.MOVE_SPEED, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(delta * Fyzoncs.MOVE_SPEED, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, delta * Fyzoncs.MOVE_SPEED);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, delta * -Fyzoncs.MOVE_SPEED);
		}

		// Rotate the camera
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.rotate(delta * -90);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			camera.rotate(delta * 90);
		}

		// Zoom the camera
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			camera.zoom *= 0.99f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			camera.zoom *= 1.01f;
		}
	}

	@Override
	public void render(float delta) {
		handleInput(delta);

		camera.update();

		// Sprite stuff
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.end();

		// Shape/line stuff
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Filled);
		sr.setColor(1, 1, 1, 1);
		
		for(Planet p : planets) {
			sr.circle(p.getX(), p.getY(), p.getRadius());
		}
		
		sr.end();

		debugRenderer.render(world, camera.combined);
		world.step(delta, 6, 2);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
