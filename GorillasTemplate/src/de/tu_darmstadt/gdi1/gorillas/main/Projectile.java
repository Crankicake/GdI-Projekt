package de.tu_darmstadt.gdi1.gorillas.main;

import java.util.LinkedList;
import java.util.PriorityQueue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;

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

	public void createEntity() throws SlickException {
		picture = new Image("/assets/gorillas/banana.png");

		setScale(Launcher.SCALE);
		setPassable(true);
		setRotation(0.0f);
	}

	public void setParameter(int angle, int velocity, double gravity,
			int playerID) {

		try {
			throwAttempt = new ThrowAttempt(angle, velocity, position, gravity,
					playerID);
			flying = true;
		} catch (GorillasException e) {
			flying = false;
			e.printStackTrace();
		}
	}

	public Vector2f nextPosition() {
		return nextPositions.poll();
	}

	public LinkedList<Vector2f> getAllPositions() {
		return throwAttempt.getAllPoints();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.drawImage(picture, position.x, position.y);
	}

	public void updateOwn(GameContainer gc, StateBasedGame sbg, int i)
			throws GorillasException {

		if(exploded)
			throw new GorillasException(null, "Boooom", 0);
		
		if (!flying)
			return;

		try {
			setPosition(throwAttempt.getNextPoint(i));
		} catch (GorillasException ex) {
			flying = false;
			throw ex;
		}
	}

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

	public double getGravity() {
		return throwAttempt.getGravity();
	}

	public int getVelocity() {
		return throwAttempt.getVelocity();
	}

	public int getAngle() {
		return throwAttempt.getAngle();
	}

	public boolean isFlying() {
		return flying;
	}

	@Override
	public void setPosition(Vector2f newPosition) {
		Vector2f pos = new Vector2f(newPosition.x, newPosition.y);

		pos.x -= 5;
		pos.y -= 5;

		position = pos;

		super.setPosition(pos);
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}

	@Override
	public void setRotation(float newRotation) {
		super.setRotation(newRotation);

		rotation = newRotation;
		try{
			picture.setRotation(newRotation);
		}
		catch (NullPointerException ex) {
			
		}
	}

	public float getRotation() {
		return rotation;
	}

	public void explode() {
		exploded = true;
	}
}