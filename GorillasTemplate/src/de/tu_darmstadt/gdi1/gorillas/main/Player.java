package de.tu_darmstadt.gdi1.gorillas.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;

public class Player extends Entity {

	private String name;
	private int score;
	private int counter = 0;

	private Vector2f position;

	private Image links, normal, rechts;

	public Player(String entityID) {
		super(entityID);
	}

	public void setName(String value) {
		name = value;
	}

	public void createEntity(Vector2f pos) throws SlickException {

		pos.x -= 18;
		pos.y -= 21;

		this.position = pos;

		normal = new Image("/assets/gorillas/gorillas/gorilla.png");
		links = new Image("/assets/gorillas/gorillas/gorilla_left_up.png");
		rechts = new Image("/assets/gorillas/gorillas/gorilla_right_up.png");

		setPosition(pos);
		setScale(Launcher.SCALE);
		setPassable(true);
		setRotation(0.0f);
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

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {

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

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) {
		counter++;
		if (counter == 401)
			counter = 0;
	}
}
