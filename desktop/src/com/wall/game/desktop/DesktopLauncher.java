package com.wall.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.wall.game.Fyzoncs;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.foregroundFPS = 60;
		config.width = Fyzoncs.WIDTH;
		config.height = Fyzoncs.HEIGHT;
		
		new LwjglApplication(new Fyzoncs(), config);
	}
}
