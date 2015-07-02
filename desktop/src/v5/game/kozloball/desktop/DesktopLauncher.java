package v5.game.kozloball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import v5.game.kozloball.KozloBallGame;
import v5.game.kozloball.KozloBallGame1;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new KozloBallGame1(), config);
	}
}
