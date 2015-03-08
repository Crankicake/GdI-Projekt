package de.tu_darmstadt.gdi1.gorillas.main;

public class MasterGame {

	private static Player playerOne;
	private static Player playerTwo;

	private static boolean applyPlayerNames;
	
	static  {
		playerOne = new Player("One");
		playerTwo = new Player("Two");
		applyPlayerNames = false;
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

}
