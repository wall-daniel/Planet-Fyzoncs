package com.wall.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wall.game.screens.PlayScreen;

public class Fyzoncs extends Game {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int WORLD_WIDTH = 1000;
	public static final int WORLD_HEIGHT = 1000;
	public static final float MOVE_SPEED = 4096;

	SpriteBatch sb;
	ShapeRenderer sr;

	@Override
	public void create() {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		this.setScreen(new PlayScreen(sb, sr));
	}

	public void render() {
		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Call the render function of current screen
		super.render();
	}
	
	@Override
	public void dispose() {
		
	}
	
}
