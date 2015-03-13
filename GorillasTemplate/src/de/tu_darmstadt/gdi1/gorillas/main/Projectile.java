package de.tu_darmstadt.gdi1.gorillas.main;

import java.util.LinkedList;
import java.util.PriorityQueue;

import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;

public class Projectile extends Entity {

	private PriorityQueue<Vector2f> nextPositions;
	private static Vector2f position;
	private float rotation = 5f;
	private Image picture;

	private static boolean flying;
	private static ThrowAttempt throwAttempt;

	private long lastFrame;

	float r = 0;

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

	public void setParamter(int angle, int velocity, double gravity,
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

		int delta = getDelta();

		if (!flying)
			return;

		try {
			setPosition(throwAttempt.getNextPoint(delta));
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
		picture.setRotation(newRotation);
	}

	public float getRotation() {
		return rotation;
	}

	// http://wiki.lwjgl.org/index.php?title=LWJGL_Basics_4_%28Timing%29
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	// http://wiki.lwjgl.org/index.php?title=LWJGL_Basics_4_%28Timing%29
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}
}