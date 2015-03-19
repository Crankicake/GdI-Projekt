package de.tu_darmstadt.gdi1.gorillas.main;

import java.util.LinkedList;
import java.util.Random;

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
	protected String[] remarks;

	public Sun(String entityID,
			DestructionRenderComponent destructionRenderComponent) {
		super(entityID);

		images = new LinkedList<Image>();
		sm = SunMode.normal;
		hitbox = new Vector2f[100 * 101];
		
		remarks = MasterGame.getRemarks();	
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
			graphicsContext.drawImage(images.get(0).getScaledCopy(getScale()), pos.x, pos.y);
			break;
		case astonished:
			graphicsContext.drawImage(images.get(1).getScaledCopy(getScale()), pos.x, pos.y);
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

	public Vector2f[] getHitbox() {
		return hitbox;
	}

	public String getComment(int mode) {
		int r = new Random().nextInt(2);

		int index = mode * 2 + r;
		
		return remarks[index];
	}
}
