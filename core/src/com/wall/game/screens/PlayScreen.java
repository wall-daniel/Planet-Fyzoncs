package com.wall.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
	private Body rectBody;
	private Polygon rectangle;

	public PlayScreen(SpriteBatch sb, ShapeRenderer sr) {
		this.sb = sb;
		this.sr = sr;

		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();

		camera = new OrthographicCamera(Fyzoncs.WIDTH, Fyzoncs.HEIGHT);
		camera.position.set(Fyzoncs.WIDTH / 2f, Fyzoncs.HEIGHT / 2f, 0);

		// Create the planets
		planets = new ArrayList<Planet>();
		for (int i = 0; i < 8; i++) {
			int r = (int) (Math.random() * 128 + 64);
			float x = (float) (Math.random() * (Fyzoncs.WORLD_WIDTH) + r);
			float y = (float) (Math.random() * (Fyzoncs.WORLD_HEIGHT) + r);

			// Make sure planets don't intersect
			if (!Planet.isIntersecting(x, y, r, planets)) {
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

		// Create jump pads
		// for(int i = 0; i < planets.size(); i++) {
		// for(int j = 0; j < planets.size(); j++) {
		// if(j == i)
		// continue;
		// if(Math.random() > )
		// }
		// }

		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(camera.position.x, camera.position.y);
		bodyDef.type = BodyType.DynamicBody;
		rectBody = world.createBody(bodyDef);
		rectBody.setFixedRotation(true);
		PolygonShape rectShape = new PolygonShape();
		rectShape.set(new float[] { -16, -16, 16, -16, 16, 16, -16, 16 });
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = rectShape;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0.4f;
		Fixture fixture = rectBody.createFixture(fixtureDef);
		rectShape.dispose();

		rectangle = new Polygon(new float[] { -16, -16, 16, -16, 16, 16, -16, 16 });
		rectangle.setPosition(bodyDef.position.x, bodyDef.position.y);
	}

	public void handleInput(float delta) {
		// Move the camera on a plain
		if (camera.position.x > 0 && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			rectBody.applyForceToCenter((float) (Math.sin(rectBody.getAngle()) * delta * -Fyzoncs.MOVE_SPEED * 250),
					(float) (Math.cos(rectBody.getAngle()) * delta * Fyzoncs.MOVE_SPEED * 250), true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			rectBody.applyForceToCenter((float) (Math.sin(rectBody.getAngle()) * delta * Fyzoncs.MOVE_SPEED * 250),
					(float) (Math.cos(rectBody.getAngle()) * delta * -Fyzoncs.MOVE_SPEED * 250), true);
		}
		// Up and down aren't needed because 2d game
		// if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
		// rectBody.applyForceToCenter(0, delta * Fyzoncs.MOVE_SPEED * 250, true);
		// }
		// if (camera.position.y > 0 && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
		// rectBody.applyForceToCenter(0, delta * -Fyzoncs.MOVE_SPEED * 250, true);
		// }
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			float impulse = 100000000;
			rectBody.applyLinearImpulse(new Vector2((float) Math.cos(rectBody.getAngle()) * impulse,
					(float) Math.sin(rectBody.getAngle()) * impulse), rectBody.getWorldCenter(), true);
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

		// Check if outside boundary
		if (camera.position.x < 0)
			camera.position.x = 0;
		if (camera.position.y < 0)
			camera.position.y = 0;

		camera.position.set(rectBody.getPosition(), 0);

		// Calculate the force on rectangle due to gravity
		int index = 0;
		float lowestDistance = Float.MAX_VALUE;
		for (int i = 0; i < planets.size(); i++) {
			if (Planet.getDistance(rectBody.getPosition(), planets.get(i).getPosition()) < lowestDistance) {
				index = i;
				lowestDistance = Planet.getDistance(rectBody.getPosition(), planets.get(index).getPosition());
			}
		}

		// Set the position of the polygon and angle
		rectBody.applyForceToCenter(planets.get(index).getGravity(rectBody.getPosition(), rectBody.getMass()), true);
		rectBody.setTransform(rectBody.getPosition(),
				(float) Math.atan2(rectBody.getPosition().y - planets.get(index).getPosition().y,
						rectBody.getPosition().x - planets.get(index).getPosition().x));
		rectangle.setPosition(rectBody.getPosition().x, rectBody.getPosition().y);
		rectangle.setRotation((float) Math.toDegrees(rectBody.getAngle()));
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

		for (Planet p : planets) {
			sr.circle(p.getX(), p.getY(), p.getRadius());
		}

		sr.end();

		sr.begin(ShapeType.Line);
		sr.polygon(rectangle.getTransformedVertices());
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
