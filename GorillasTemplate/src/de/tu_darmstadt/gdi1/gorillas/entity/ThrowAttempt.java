package de.tu_darmstadt.gdi1.gorillas.entity;

import java.util.LinkedList;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;

/**
 * Soll die Sonne erstaunt gucken oder nicht?
 *
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */

public class ThrowAttempt {

	private float velocityX, velocityY;
	private float x0, y0;
	private float timePassed;

	private int velocity;
	private int angle;

	private int playerID;

	/*
	 * Einen neuen Wurf starten mit: Dem Winkel Der Kraft Der Position, von der
	 * aus geworfen wird Der Gravitation
	 * 
	 * Hier wird viel geprueft und viele Exceptions geschmissen. Gemacht habe
	 * ich das, weil das robuster ist, als wenn ich annehme, dass die Werte
	 * immer richtig reinkommen.
	 */
	public ThrowAttempt(int angle, int velocity, Vector2f position, int playerID) {

		if (angle < 0) {
			angle = 0;
		}

		if (angle > 360) {
			angle = 360;
		}

		// Der Winkel soll ja zwischen 0 und 360° sein und eine natuerliche
		// Zahl.

		if (velocity < 0) {
			velocity = 0;
		}
		if (velocity > 200) {
			velocity = 200;
		}

		// Die Kraft ist zwischen 0 und 200 und aus N.

		if (playerID != 1 && playerID != 2) {
			playerID = 1;
		}

		// Falls die Gravitation gar keine Zahl ist oder unendlich, dann auf den
		// Standartwert.

		this.x0 = position.x;
		this.y0 = position.y;

		velocityX = (float) Math.cos(Math.toRadians(angle)) * velocity; // Nach
																		// der
		// gegebenen
		// Formel
		// berechnet.
		velocityY = (float) Math.sin(Math.toRadians(angle)) * velocity;

		this.velocity = velocity;
		this.angle = angle;

		this.playerID = playerID;
	}

	// Hier drunter passiert 2x das gleiche. Nach der Formel fuer x und y wird
	// deren Wert errechnet.
	// Danach geprueft, ob der noch im Fenster liegt...

	public int getVelocity() {
		return velocity;
	}

	public int getAngle() {
		return angle;
	}

	public LinkedList<Vector2f> getAllPoints() {

		LinkedList<Vector2f> liste = new LinkedList<Vector2f>();

		int x, y, timePassed = 1; // Hier muss eine Zeiteinheit rein.
									// Prinzipiell alles moeglich, ich hab aber
									// mal 1 als kleinsten Wert eingetragen.

		while (true) {

			x = (int) (x0 + (velocityX * timePassed) + (0.5
					* MasterGame.getWindScale() * MasterGame.getWind()
					* timePassed * timePassed));

			y = (int) (y0 - (velocityY * timePassed) + (0.5
					* MasterGame.getGravitation() * timePassed * timePassed));

			if (x > 0 && y > 0 && x < Launcher.FRAME_WIDTH)
				liste.add(new Vector2f(x, y));
			else
				return liste; // ... wenn nicht, einfach die Liste zurueckgeben.
		}
	}

	public Vector2f getNextPoint(double time) {

		time *= MasterGame.getTimeScale();

		timePassed += time;

		float x = (x0 + (playerID == 1 ? (velocityX * timePassed)
				: -(velocityX * timePassed)));
		x = (x + (0.5f * MasterGame.getWindScale() * MasterGame.getWind()
				* timePassed * timePassed));

		float y = (y0 - (velocityY * timePassed) + (0.5f
				* MasterGame.getGravitation() * timePassed * timePassed));

		return new Vector2f(x, y);
	}
}
