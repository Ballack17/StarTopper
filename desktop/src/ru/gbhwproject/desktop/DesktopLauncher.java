package ru.gbhwproject.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.gbhwproject.StarTopperHarley;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 600;
		config.resizable = true;
		new LwjglApplication(new StarTopperHarley(), config);
	}
}
