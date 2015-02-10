package de.tu_darmstadt.gdi1.gorillas.main;

public class Player {

	private final String name;
	private int score;

	public Player(String name) {
		this.name = name;
	}

	public void increaseScore() {
		 ++score;
	}
	
	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}
}
