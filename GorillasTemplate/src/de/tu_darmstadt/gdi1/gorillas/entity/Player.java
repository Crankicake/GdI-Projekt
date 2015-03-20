package de.tu_darmstadt.gdi1.gorillas.entity;

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
 * 
 * Fuer jeden Spieler wird eine Instanz dieser Klasse erstellt. Repraesentiert
 * Spieler.
 *
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */

public class Player extends Entity {

	private String name;
	private int score;
	private int tries;
	
	private int counter;

	private Image links, normal, rechts;

	private PlayerImageState imageState;

	private int formerVelocity;
	private int formerAngle;

	/**
	 * Die Hitbox umzeichnet die Spieler mit einer unsichtbaren Box. Trifft die
	 * Banane einen vector2f innerhalb des Arrays (inklusive Toleranz), wird die
	 * Explosion ausgeloest, der Punkt geht an den Gegner und die Karte wird neue
	 * generiert (siehe Gameplaystate)
	 */
	
	protected Vector2f[] hitbox;

	/**
	 * Grundzustand der Spieler (grafisch) Erstellung der Instanz einer Hitbox
	 * 
	 * @param entityID: Die Entity, mit der der Spieler zu finden ist
	 * 
	 */
	
	public Player(String entityID) {
		super(entityID);

		imageState = PlayerImageState.NoHandsForYou;

		hitbox = new Vector2f[37 * 42];
	}

	/**
	 * Der Name wird auf den uebergebenen Wert festgelegt
	 * 
	 * @param value
	 */
	public void setName(String value) {
		name = value;
	}

	/**
	 * Entity wird erstellt und das jeweilige Bild geladen
	 * 
	 * @throws SlickException
	 */
	public void createEntity() throws SlickException {
		if (!MasterGame.getDebug()) {
			normal = new Image("/assets/gorillas/gorillas/gorilla.png");
			links = new Image("/assets/gorillas/gorillas/gorilla_left_up.png");
			rechts = new Image("/assets/gorillas/gorillas/gorilla_right_up.png");
		}
		setPosition(new Vector2f());
		setScale(Launcher.SCALE);
		setPassable(true);
		setRotation(0.0f);
	}

	/**
	 * Festlegen der position des Spielers. Arbeitet mit "initBuildings" aus
	 * Gameplaystate zusammen Zusaetzlich wird die umrahmende Hitbox berechnet
	 * und gesetzt
	 */
	public void setPosition(Vector2f pos) {
		super.setPosition(pos);

		pos.x -= 18;
		pos.y -= 21;

		int x = 0;
		int y = 0;

		for (int count = 0; count < hitbox.length; count++, x++, y++) {

			hitbox[count] = new Vector2f(pos.x + x, pos.y + y);

			if (x == 37)
				x = 0;

			if (y == 42)
				y = 0;
		}
	}

	/**
	 * Erhoehen des individuellen Punktestands
	 */
	public void increaseScore() {
		++score;
	}

	/**
	 * Erhoehen der Anzahl der Versuche
	 */
	public void increaseTries() {
		tries++;
	}

	/**
	 * Spezifische Position wird (grafisch) erstellt, Abhaengig davon, welcher
	 * Spieler am Zug ist. Siehe Namen der Enums
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {

		Vector2f position = super.getPosition();

		switch (imageState) {
		case NoHandsForYou:
			g.drawImage(normal, position.x, position.y);
			break;
		case LeftHandRised:
			g.drawImage(links, position.x, position.y);
			break;
		case RightHandRised:
			g.drawImage(rechts, position.x, position.y);
			break;
		case Dancing:
			switch (counter / 100) {
			case 0:
				g.drawImage(links, position.x, position.y);
				break;
			case 1:
				g.drawImage(normal, position.x, position.y);
				break;
			case 2:
				g.drawImage(rechts, position.x, position.y);
				break;
			case 3:
				g.drawImage(normal, position.x, position.y);
				break;
			}
		}

	}

	/**
	 * Findet derzeit keine Verwendung. Bewirkt das Tanzen der Affen
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) {

		if (imageState == PlayerImageState.Dancing) {
			counter++;
			if (counter == 401)
				counter = 0;
		}
	}

	/**
	 * Former bedeutet "zuletzt" (vom letzten Zug) In diesem Fall den zuletzt
	 * eingegebenen Winkel
	 * 
	 * @param angle
	 */
	public void setFormerAngle(int angle) {
		formerAngle = angle;
	}

	/**
	 * Setzt die zuletzt eingebene Staerke
	 * 
	 * @param velocity
	 */
	public void setFormerVelocity(int velocity) {
		formerVelocity = velocity;
	}

	/**
	 * Position des Affen wird gesetzt (bspw. wenn er die Bananae zugesprochen
	 * bekommt)
	 * 
	 * @param state
	 */
	public void setImageState(PlayerImageState state) {
		imageState = state;
	}

	/**
	 * 
	 * @return Aktueller Spielername
	 */
	public String getName() {
		if (name == null)
			return "";
		return name;
	}

	/**
	 * 
	 * @return Spielstand
	 */
	public int getScore() {
		return score;
	}

	/**
	 * 
	 * @return aktuelle Versuche
	 */
	public int getTries() {
		return tries;
	}

	/**
	 * 
	 * @return zuletzt eingegebener Winkel
	 */
	public int getFormerAngle() {
		return formerAngle;
	}

	/**
	 * 
	 * @return Zuletzt eingegebene Staerke
	 */
	public int getFormerVelocity() {
		return formerVelocity;
	}

	/**
	 * 
	 * @return alle Vektoren die den Spieler umrahmen
	 */
	public Vector2f[] getHitbox() {
		return hitbox;
	}

	/**
	 * 
	 * @return aktuelle Spielerposition
	 */
	public Vector2f getCoordinates() {
		Vector2f p = getPosition();

		Vector2f pos = new Vector2f(p.x, p.y);

		pos.x += 18;

		return pos;
	}
}