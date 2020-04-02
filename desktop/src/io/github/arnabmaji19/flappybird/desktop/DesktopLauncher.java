package io.github.arnabmaji19.flappybird.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.arnabmaji19.flappybird.FlappyBird;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.height = 600;
		config.width = 1000;
		new LwjglApplication(new FlappyBird(), config);
	}
}
