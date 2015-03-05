package de.tu_darmstadt.gdi1.gorillas.main;

public class MasterGame {

	protected static Player playerOne;
	protected static Player playerTwo;

	public MasterGame() {
		playerOne = new Player("One");
		playerTwo = new Player("Two");
	}

	public static Player getPlayerOne() {
		return playerOne;
	}

	public static Player getPlayerTwo() {
		return playerTwo;
	}
}
