import acm.program.*;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import acm.graphics.*;

public class GraphicsGame extends GraphicsProgram {
	/**
	 * Here are all of the constants
	 */
	public static final int PROGRAM_WIDTH = 1200;
	public static final int PROGRAM_HEIGHT = 800;

	private Screen currentScreen = null;
	private Screen[] screens;

	public GraphicsGame() {
		screens = new Screen[] {
			new StartMenu(),
			new QuitScreen(),
			new CreditsScreen(),
			new LevelScreen()
		};
	}

	public void displayScreen(String screenName, HashMap<String, Object> params) {
		// find the screen
		for (Screen screen : screens) {
			if (screen.getName() == screenName) {
				// deinit and init
				if (currentScreen != null) currentScreen.deinit();
				screen.init(this, params);
				currentScreen = screen;
				break;
			}
		}
	}

	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
	}

	public void run() {
		displayScreen("index", null);
	}

	public static void main(String[] args) {
		new GraphicsGame().start();
	}
}
