package de.tu_darmstadt.gdi1.gorillas.main;

import org.newdawn.slick.SlickException;

public class MasterGame {

	private static Player playerOne;
	private static Player playerTwo;

	private static boolean applyPlayerNames;
	private static boolean running;

	private static boolean laeuftJukebox;

	private static double grav;

	private static double windScale = 0.2;
	private static double timeScale = 0.005;
	private static double wind;
	
	static {		
		applyPlayerNames = false;
		running = false;
		laeuftJukebox = false;
		grav = 9.81;
		
		windScale = 0.02;
		timeScale = 0.005;
		wind = 0;
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

	public static void setGravitation(double g) {
		if (Double.isNaN(g) || Double.isInfinite(g))
			return;

		grav = g;
	}

	public static double getGravitation() {
		return grav;
	}

	public static double getWindScale() {
		return windScale;
	}
	
	public static double getTimeScale() {
		return timeScale;
	}
	
	public static double getWind() {
		return wind;
	}
	
	public static void setWind(double d) {
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
}
