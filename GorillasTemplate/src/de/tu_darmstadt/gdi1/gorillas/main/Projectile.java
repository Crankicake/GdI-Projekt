package de.tu_darmstadt.gdi1.gorillas.main;

import java.util.PriorityQueue;

import org.newdawn.slick.geom.Vector2f;

public class Projectile {

	private PriorityQueue<Vector2f> nextPositions;
	
	public Projectile() {
		nextPositions = new PriorityQueue<Vector2f>();
	}
	
	public Vector2f nextPosition() {
		return nextPositions.poll();
	}
}