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
	private Image bild;

	private static boolean fliegt;
	private static ThrowAttempt throwAttempt;

	private long lastFrame;

	float r = 0;

	public Projectile(String entityID) {
		super(entityID);
		nextPositions = new PriorityQueue<Vector2f>();

		position = super.getPosition();
	}

	public void createEntity() throws SlickException {
		bild = new Image("/assets/gorillas/banana.png");

		setScale(Launcher.SCALE);
		setPassable(true);
		setRotation(0.0f);
	}

	public void setParamter(int angle, int velocity, double gravity, int playerID) {

		try {
			throwAttempt = new ThrowAttempt(angle, velocity, position, gravity, playerID);
			fliegt = true;
		} catch (GorillasException e) {
			fliegt = false;
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
		g.drawImage(bild, position.x, position.y);
	}

	public void updateOwn(GameContainer gc, StateBasedGame sbg, int i)
			throws GorillasException {

		int delta = getDelta();

		if (!fliegt)
			return;

		try {
			setPosition(throwAttempt.getNextPoint(delta));
		} catch (GorillasException ex) {
			fliegt = false;
			throw ex;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) {
		if (fliegt) 
		{			
			bild.setRotation(rotation);

			rotation += 5;
			rotation %= 360;	
		}
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
		return fliegt;
	}

	@Override
	public void setPosition(Vector2f newPosition) {
		position = newPosition;

		super.setPosition(newPosition);
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}

	@Override
	public void setRotation(float newRotation) {
		super.setRotation(newRotation);

		rotation = newRotation;
		bild.setRotation(newRotation);
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