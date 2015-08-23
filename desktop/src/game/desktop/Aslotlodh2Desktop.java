package game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.Main;

public class Aslotlodh2Desktop {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable=false;
		config.vSyncEnabled=true;
		config.foregroundFPS=60;
		config.width=Main.width;
		config.height=Main.height;
		System.out.println("asidufsiogdjf");
//		Settings settings = new Settings();
//		settings.combineSubdirectories = true;
//		TexturePacker.process(settings, "../images", "../core/assets", "atlas_image");
		config.title="aslotlodh2";
		config.addIcon("icon.png", FileType.Internal);

		
		new LwjglApplication(new Main(), config);
	}
}
