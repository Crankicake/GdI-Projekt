package de.tu_darmstadt.gdi1.gorillas.main;

import java.util.LinkedList;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.ui.states.GamePlayState;

public class ThrowAttempt {

	private double velocityX, velocityY;
	private double x0, y0;
	private double gravity;
	private double timePassed;

	private int velocity;
	private int angle;

	private int playerID;
	/*
	 * Einen neuen Wurf starten mit: Dem Winkel Der Kraft Der Position, von der
	 * aus geworfen wird Der Gravitation
	 * 
	 * Hier wird viel geprueft und viele Exceptions geschmissen. Gemacht habe ich
	 * das, weil das robuster ist, als wenn ich annehme, dass die Werte immer
	 * richtig reinkommen.
	 */
	public ThrowAttempt(int angle, int velocity, Vector2f position,
			double gravity, int playerID) throws GorillasException {

		if (angle < 0 || angle > 360) {
			throw new GorillasException(new ArithmeticException(), "angle: "
					+ angle, ExceptionReason.ThrowAttemptAngelWrong);
		}
		// Der Winkel soll ja zwischen 0 und 360° sein und eine natuerliche Zahl.

		if (velocity < 0 || velocity > 200) {
			throw new GorillasException(new ArithmeticException(), "velocity: "
					+ velocity, ExceptionReason.ThrowAttemptVelocityWrong);
		}
		// Die Kraft ist zwischen 0 und 200 und aus N.

		if (gravity <= 0) {
			throw new GorillasException(new ArithmeticException(), "gravity: "
					+ gravity, ExceptionReason.ThrowAttemptGravityNegative);
		}
		// Negative Gravitation ist schlecht.

		if (position.x < 0 || position.y < 0
				|| position.x > Launcher.FRAME_WIDTH
				|| position.y > Launcher.FRAME_HEIGHT) {
			throw new GorillasException(new ArithmeticException(), "positon: "
					+ position.x + ", " + position.y,
					ExceptionReason.ThrowAttemptPositionOutsideWindow);
		}
		// Pruefen, ob die Position ausserhalb des Fensters ist.

		if (playerID != 1 && playerID != 2) {
			throw new GorillasException(new IllegalArgumentException(),
					"Die SpielerID ist falsch: " + playerID,
					ExceptionReason.UnknownPlayerID);
		}

		if (Double.isNaN(gravity) || Double.isInfinite(gravity)) {
			this.gravity = 9.81;
		} else {
			this.gravity = gravity;
		}
		// Falls die Gravitation gar keine Zahl ist oder unendlich, dann auf den
		// Standartwert.

		this.x0 = position.x;
		this.y0 = position.y;

		velocityX = Math.cos(Math.toRadians(angle)) * velocity; // Nach der
																// gegebenen
																// Formel
		// berechnet.
		velocityY = Math.sin(Math.toRadians(angle)) * velocity;

		this.velocity = velocity;
		this.angle = angle;
		
		this.playerID = playerID;
	}

	// Hier drunter passiert 2x das gleiche. Nach der Formel fuer x und y wird
	// deren Wert errechnet.
	// Danach geprueft, ob der noch im Fenster liegt...

	public double getGravity() {
		return gravity;
	}

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
					* GamePlayState.windScale * GamePlayState.wind * timePassed * timePassed));

			y = (int) (y0 - (velocityY * timePassed) + (0.5 * gravity
					* timePassed * timePassed));

			if (x > 0 && y > 0 && x < Launcher.FRAME_WIDTH)
				liste.add(new Vector2f(x, y));
			else
				return liste; // ... wenn nicht, einfach die Liste zurueckgeben.
		}
	}

	public Vector2f getNextPoint(double time) throws GorillasException {

		timePassed += 0.02;

		int x = (int) (x0 + (playerID == 1? (velocityX * timePassed): -(velocityX * timePassed)));
		x = (int) (x + (0.5 * GamePlayState.windScale * GamePlayState.wind * timePassed * timePassed));

		int y = (int) (y0 - (velocityY * timePassed) + (0.5 * gravity
				* timePassed * timePassed));

		if (x > 0 && y < Launcher.FRAME_HEIGHT && x < Launcher.FRAME_WIDTH)
			return new Vector2f(x, y);

		throw new GorillasException(new Exception(),
				"Banane ist ausserhalb vom Bild",
				ExceptionReason.ThrowAttemptNoNextPosition);
	}
}
