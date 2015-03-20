package de.tu_darmstadt.gdi1.gorillas.entity;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.entity.Entity;

/**
 * Diese Klasse repraesentiert die Sonne. Deswegen erbt sie von Entity.
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */
public class Sun extends Entity {

	// Die verschiedenen Attribute, wie die Bilder und die Bemerkungen
	protected LinkedList<Image> images;
	protected SunMode sm;
	protected Vector2f[] hitbox;
	protected String[] remarks;

	/**
	 * 
	 * @param entityID
	 *            : Der Name, unter dem die Entitaet spaeter gefunden werden kann.
	 */
	public Sun(String entityID) {
		super(entityID);

		images = new LinkedList<Image>();
		sm = SunMode.normal;
		hitbox = new Vector2f[100 * 101];

		remarks = MasterGame.getRemarks();
	}

	/**
	 * Fuegt ein neues Bild zu
	 * 
	 * @param image
	 *            : das neue Bild
	 */
	public void addImage(Image image) {
		if (!images.contains(image))
			images.add(image);
	}

	/**
	 * Rendert das aktuelle Bild
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sb,
			Graphics graphicsContext) {
		Vector2f pos = getPosition();

		switch (sm) {
		case normal:
			graphicsContext.drawImage(images.get(0).getScaledCopy(getScale()),
					pos.x, pos.y);
			break;
		case astonished:
			graphicsContext.drawImage(images.get(1).getScaledCopy(getScale()),
					pos.x, pos.y);
			break;
		}
	}

	/**
	 * @return: Der aktuelle Status
	 */
	public SunMode getSunMode() {
		return sm;
	}

	/**
	 * @param sm
	 *            : Der neue Gefuehlsstatus der Sonne
	 */
	public void setSunMode(SunMode sm) {
		this.sm = sm;
	}

	/**
	 * @param pos
	 *            : Setzt die Sonne an die angegebene Position und fuellt die
	 *            Hitbox neu
	 */
	public void setPosition(Vector2f pos) {
		super.setPosition(pos);

		int x = -1;
		int y = -1;

		for (int count = 0; count < hitbox.length; count++, x++, y++) {

			hitbox[count] = new Vector2f(pos.x + x, pos.y + y);

			if (x == 100)
				x = -1;

			if (y == 99)
				y = -1;
		}
	}

	/**
	 * 
	 * @return Gibt die Hitbox zurueck
	 */
	public Vector2f[] getHitbox() {
		return hitbox;
	}

	/**
	 * @param mode
	 *            : Der Modus, von dem der Kommentar erwartet wird
	 * @return: Ein zufaelliger, passender Kommentar
	 */
	public String getComment(int mode) {
		int r = new Random().nextInt(2);

		int index = mode * 2 + r;

		return remarks[index];
	}
}
