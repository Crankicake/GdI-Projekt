package de.tu_darmstadt.gdi1.gorillas.entity;

import java.util.LinkedList;
import java.util.PriorityQueue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.entity.Entity;

/**
 * Repraesetation der Banane, erweitert dazu Entity
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */
public class Projectile extends Entity {

	private PriorityQueue<Vector2f> nextPositions;
	private Vector2f position;
	private float rotation;
	private Image picture;

	private boolean flying;
	private ThrowAttempt throwAttempt;
	private boolean exploded;

	public Projectile(String entityID) {
		super(entityID);
		nextPositions = new PriorityQueue<Vector2f>();

		position = super.getPosition();
	}

	/**
	 * Erstellen einer Entity und Laden des Images
	 * 
	 * @throws SlickException
	 */
	public void createEntity() throws SlickException {
		if (!MasterGame.getDebug()) {
			picture = new Image("/assets/gorillas/banana.png");
		}

		setScale(0.5f);
		setPassable(true);
		setRotation(0.0f);
	}

	/**
	 * 
	 * Parameter werden an die Klasse ThrowAttempt uebergeben und die Banane wird
	 * auf "fliegend" festgelegt
	 * 
	 * @param angle
	 * @param velocity
	 * @param playerID
	 */
	public void setParameter(int angle, int velocity, int playerID) {
		throwAttempt = new ThrowAttempt(angle, velocity, position, playerID);
		flying = true;
	}

	public Vector2f nextPosition() {
		return nextPositions.poll();
	}

	/**
	 * 
	 * @return Hier koennen alle fuer die Banane relevanten Parameter abgerufen
	 *         werden
	 */
	public LinkedList<Vector2f> getAllPositions() {
		return throwAttempt.getAllPoints();
	}

	/**
	 * Banane wird an aktueller Position gezeichnet
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.drawImage(picture, position.x, position.y);
	}

	/**
	 * Excecption Handling und ueberpruefung, ob die Banane aus dem Bild rand
	 * herausgeflogen ist
	 * 
	 * @param gc
	 * @param sbg
	 * @param i
	 * @throws Exception
	 */
	public void updateOwn(GameContainer gc, StateBasedGame sbg, int i)
			throws Exception {

		if (exploded)
			throw new Exception("Already boooom");

		if (!flying)
			return;

		Vector2f pos = throwAttempt.getNextPoint(i);

		if (pos.x > 0 && pos.y < Launcher.FRAME_HEIGHT
				&& pos.x < Launcher.FRAME_WIDTH) {
			setPosition(pos);

		} else {
			flying = false;
			throw new Exception("Banane ist ausserhalb vom Bild, " + pos.x
					+ " " + pos.y);
		}
	}

	/**
	 * Rotation der Banane wird berechnet und ausgefuehrt
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) {
		if (flying) {
			picture.setRotation(rotation);

			rotation += 5;
			rotation %= 360;
		} else {
			picture.setRotation(0);
			rotation = 0;
		}

		super.update(gc, sbg, i);
	}

	/**
	 * 
	 * @return aktuelle Staerke
	 */
	public int getVelocity() {
		return throwAttempt.getVelocity();
	}

	/**
	 * 
	 * @return aktuellen Winkel
	 */
	public int getAngle() {
		return throwAttempt.getAngle();
	}

	/**
	 * 
	 * @return ist die Banane am Fliegen?
	 */
	public boolean isFlying() {
		return flying;
	}

	/**
	 * Setzte die Position
	 */
	@Override
	public void setPosition(Vector2f newPosition) {
		Vector2f pos = new Vector2f(newPosition.x, newPosition.y);

		pos.x -= 5;
		pos.y -= 5;

		position = pos;

		super.setPosition(pos);
	}

	/**
	 * @return aktuelle Position
	 */
	@Override
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * Setzen der Rotation
	 */
	@Override
	public void setRotation(float newRotation) {
		super.setRotation(newRotation);

		rotation = newRotation;
		try {
			picture.setRotation(newRotation);
		} catch (NullPointerException ex) {

		}
	}

	/**
	 * @return rotation der Banane
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * ist die Banane explodiert?
	 */
	public void explode() {
		exploded = true;
	}
}