package de.tu_darmstadt.gdi1.gorillas.main;

import org.newdawn.slick.SlickException;

public class MasterGame {

	private static Player playerOne;
	private static Player playerTwo;

	private static boolean applyPlayerNames;
	private static boolean running;
	private static boolean debug;
	private static boolean laeuftJukebox;

	private static float grav;

	private static float windScale;
	private static float timeScale;
	private static int wind;

	private static String[] remarks;

	static {
		applyPlayerNames = false;
		running = false;
		laeuftJukebox = false;
		debug = false;
		grav = 9.81f;

		windScale = 0.02f;
		timeScale = 0.005f;
		wind = 0;

		remarks = new String[] {
				"Geh doch lieber AngryBirds spielen!"
		};
	}

	public static Player getPlayerOne() {
		return playerOne;
	}

	public static Player getPlayerTwo() {
		return playerTwo;
	}

	public static boolean getApplyPlayerNames() {
		return applyPlayerNames;
	}

	public static void setIsAGameRunning(boolean b) {
		if (b != running)
			running = b;
	}

	public static boolean isAGameRunning() {
		return running;
	}

	public static boolean isJukeboxRunning() {
		return laeuftJukebox;
	}

	public static void setIsJukeboxRunning(boolean b) {
		laeuftJukebox = b;
	}

	public static void setGravitation(float g) {
		if (Double.isNaN(g) || Double.isInfinite(g))
			return;

		grav = g;
	}

	public static float getGravitation() {
		return grav;
	}

	public static float getWindScale() {
		return windScale;
	}

	public static float getTimeScale() {
		return timeScale;
	}

	public static int getWind() {
		return wind;
	}

	public static void setWind(int d) {
		wind = d;
	}

	public static void createPlayer() {
		playerOne = new Player("One");
		playerTwo = new Player("Two");

		try {
			playerOne.createEntity();
			playerTwo.createEntity();
		} catch (SlickException s) {

		}

		playerOne.setName("Payer 1");
		playerTwo.setName("Player 2");
	}

	public static String[] getRemarks() {
		return remarks;
	}

	public static boolean getDebug() {
		return debug;
	}
	
	public static void setDebug(boolean b) {
		debug = b;
	}
}
