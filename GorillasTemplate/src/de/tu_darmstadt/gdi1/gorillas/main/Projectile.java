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
	private static float rotation;
	private Image bild;

	private boolean fliegt;
	private ThrowAttempt throwAttempt;

	private long lastFrame;

	public Projectile(String entityID) {
		super(entityID);
		nextPositions = new PriorityQueue<Vector2f>();

		position = super.getPosition();
		rotation = super.getRotation();
	}

	public void createEntity() throws SlickException {
		bild = new Image("/assets/gorillas/banana.png");

		setScale(Launcher.SCALE);
		setPassable(true);
		setRotation(0.0f);
	}

	public void setParamter(int angle, int velocity, double gravity) {

		try {
			throwAttempt = new ThrowAttempt(angle, velocity, position, gravity);
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

		rotate();

		try {
			setPosition(throwAttempt.getNextPoint(delta));
		} catch (GorillasException ex) {
			fliegt = false;
			throw ex;
		}
	}

	public double getGravity () {
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

	public void rotate() {
		float rotation = getRotation();
		
		rotation += 10f;
		
		if (rotation == 360f)
			rotation = 0f;
		
		bild.rotate(rotation);
		
		System.out.println(rotation);
		
		setRotation(rotation);
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
		rotation = newRotation;
		
		super.setRotation(newRotation);
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