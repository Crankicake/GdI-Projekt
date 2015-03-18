package de.tu_darmstadt.gdi1.gorillas.main;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.component.render.DestructionRenderComponent;
import eea.engine.entity.Entity;

public class Sun extends Entity {

	protected LinkedList<Image> images;
	protected SunMode sm;
	protected Vector2f[] hitbox;

	public Sun(String entityID,
			DestructionRenderComponent destructionRenderComponent) {
		super(entityID);

		images = new LinkedList<Image>();
		sm = SunMode.normal;
		hitbox = new Vector2f[100 * 100];
	}

	public void addImage(Image image) {
		if (!images.contains(image))
			images.add(image);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb,
			Graphics graphicsContext) {
		Vector2f pos = getPosition();

		switch (sm) {
		case normal:
			graphicsContext.drawImage(images.get(0), pos.x, pos.y);
			break;
		case astonished:
			graphicsContext.drawImage(images.get(1), pos.x, pos.y);
			break;
		}

		// super.render(gc, sb, graphicsContext);
	}

	public SunMode getSunMode() {
		return sm;
	}

	public void setSunMode(SunMode sm) {
		this.sm = sm;
	}

	public void setPosition(Vector2f pos) {
		super.setPosition(pos);

		int x = 0;
		int y = 0;

		for (int count = 0; count < hitbox.length; count++, x++, y++) {
			
			hitbox[count] = new Vector2f(pos.x + x, pos.y + y);
			
			if (x == 57)
				x = 0;

			if (y == 56)
				y = 0;
		}
	}

	public Vector2f[] getHitbox() {
		return hitbox;
	}
}
