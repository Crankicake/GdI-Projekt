package de.tu_darmstadt.gdi1.gorillas.main;

public class MasterGame {

	private static Player playerOne;
	private static Player playerTwo;

	private static boolean applyPlayerNames;
	private static boolean running;

	private static boolean laeuftJukebox;

	private static double grav;

	static {
		playerOne = new Player("One");
		playerTwo = new Player("Two");
		applyPlayerNames = false;
		running = false;
		laeuftJukebox = false;
		grav = 9.81;
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
}
