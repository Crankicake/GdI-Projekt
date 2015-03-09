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
	private float rotation = 10f;
	private static Vector2f position;
	private Image bild;

	private boolean fliegt;
	private ThrowAttempt throwAttempt;

	private long lastFrame;

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

		bild.setRotation(rotation);

		rotation += 10f;

		if (rotation == 360f)
			rotation = 0;

		try {
			setPosition(throwAttempt.getNextPoint(delta));
		} catch (GorillasException ex) {
			fliegt = false;
			throw ex;
		}
	}

	public boolean isFlying() {
		return fliegt;
	}

	public void setPosition(Vector2f newPosition) {
		position = newPosition;

		super.setPosition(newPosition);
		
		System.out.println(newPosition);
	}

	public Vector2f getPosition() {
		return position;
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