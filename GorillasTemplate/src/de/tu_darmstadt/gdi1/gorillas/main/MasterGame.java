package de.tu_darmstadt.gdi1.gorillas.main;

public class MasterGame {

	private static Player playerOne;
	private static Player playerTwo;

	private static boolean applyPlayerNames;
	private static boolean running;
	
	static  {
		playerOne = new Player("One");
		playerTwo = new Player("Two");
		applyPlayerNames = false;
		running = false;
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
		if(b != running)
			running = b;
	}
	
	public static boolean isAGameRunning() {
		return running;
	}
}
