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
	private int tries;
	
	private int counter;

	private Image links, normal, rechts;

	private PlayerImageState imageState;

	private int formerVelocity;
	private int formerAngle;

	protected Vector2f[] hitbox;

	public Player(String entityID) {
		super(entityID);

		imageState = PlayerImageState.NoHandsForYou;

		hitbox = new Vector2f[37 * 42];
	}

	public void setName(String value) {
		name = value;
	}

	public void createEntity() throws SlickException {
		if(!MasterGame.getDebug()) {
		normal = new Image("/assets/gorillas/gorillas/gorilla.png");
		links = new Image("/assets/gorillas/gorillas/gorilla_left_up.png");
		rechts = new Image("/assets/gorillas/gorillas/gorilla_right_up.png");
		}
		setPosition(new Vector2f());
		setScale(Launcher.SCALE);
		setPassable(true);
		setRotation(0.0f);
	}

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

	public void increaseScore() {
		++score;
	}

	public void increaseTries() {
		tries ++;
	}
	
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

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) {

		if (imageState == PlayerImageState.Dancing) {
			counter++;
			if (counter == 401)
				counter = 0;
		}
	}

	public void setFormerAngle(int angle) {
		formerAngle = angle;
	}

	public void setFormerVelocity(int velocity) {
		formerVelocity = velocity;
	}

	public void setImageState(PlayerImageState state) {
		imageState = state;
	}
	
	public String getName() {
		if(name == null)
			return "";
		return name;
	}

	public int getScore() {
		return score;
	}

	public int getTries() {
		return tries;
	}
	
	public int getFormerAngle() {
		return formerAngle;
	}

	public int getFormerVelocity() {
		return formerVelocity;
	}

	public Vector2f[] getHitbox() {
		return hitbox;
	}
}