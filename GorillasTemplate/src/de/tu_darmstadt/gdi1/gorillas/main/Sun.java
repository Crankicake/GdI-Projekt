package de.tu_darmstadt.gdi1.gorillas.main;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.component.render.DestructionRenderComponent;
import eea.engine.entity.DestructibleImageEntity;

public class Sun extends DestructibleImageEntity {

	protected LinkedList<Image> images;
	protected SunMode sm;

	public Sun(String entityID, DestructionRenderComponent destructionRenderComponent) {
		super(entityID, destructionRenderComponent);

		images = new LinkedList<Image>();
		sm = SunMode.normal;
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
}
