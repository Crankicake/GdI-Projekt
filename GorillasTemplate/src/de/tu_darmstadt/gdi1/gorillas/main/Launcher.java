package de.tu_darmstadt.gdi1.gorillas.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

@SuppressWarnings("unused")
public class Launcher {

	public static final int FRAME_WIDTH = 1000;
	public static final int FRAME_HEIGHT = 600;

	public static final int TARGET_FRAME_RATE = 600;

	public static final float SCALE = ((float) FRAME_WIDTH / 800f) < ((float) FRAME_HEIGHT / 600f) ? ((float) FRAME_HEIGHT / 600f)
			: ((float) FRAME_WIDTH / 800f);

	public static void main(String[] args) throws SlickException {

		String osname = System.getProperty("os.name").toLowerCase();
		// Set the native library path (depending on the operating system)
		// @formatter:off
		if (osname.contains("windows")) {
			System.setProperty("org.lwjgl.librarypath",
					System.getProperty("user.dir")
							+ "/lib/lwjgl-2.9.1/native/windows");
		} else if (osname.contains("mac")) {
			System.setProperty("org.lwjgl.librarypath",
					System.getProperty("user.dir")
							+ "/lib/lwjgl-2.9.1/native/macosx");
		} else {
			System.setProperty("org.lwjgl.librarypath",
					System.getProperty("user.dir") + "/lib/lwjgl-2.9.1/native/"
							+ System.getProperty("os.name").toLowerCase());
		}

		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL",
				"false");
		System.err.println(System.getProperty("os.name") + ": "
				+ System.getProperty("org.lwjgl.librarypath"));
		// @formatter:on

		// Insert this StateBasedGame into an AppContainer (a window)
		AppGameContainer app = new AppGameContainer(new Gorillas(false));
		MasterGame.setDebug(false);
		
		// Set window properties and start it
		app.setAlwaysRender(true);
		app.setShowFPS(true);
		app.setVSync(false);
		app.setDisplayMode(FRAME_WIDTH, FRAME_HEIGHT, false);
		app.setTargetFrameRate(TARGET_FRAME_RATE);
		app.start();
	}
}