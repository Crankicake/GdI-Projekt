package de.tu_darmstadt.gdi1.gorillas.main;

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
	private Vector2f position;
	private Vector2f startPosition;
	private Image bild;

	private boolean fliegt;
	private ThrowAttempt throwAttempt;

	private long lastFrame;

	public Projectile(String entityID) {
		super(entityID);
		nextPositions = new PriorityQueue<Vector2f>();
		
		startPosition = new Vector2f();
		position = new Vector2f();
	}

	public void setMyPosition(Vector2f pos) {
		startPosition = pos;
		position = pos;
	}

	public void createEntity() throws SlickException {
		bild = new Image("/assets/gorillas/banana.png");

		setScale(Launcher.SCALE);
		setPassable(true);
		setRotation(0.0f);
	}

	public void setParamter(int angle, int velocity, double gravity) {

		try {
			throwAttempt = new ThrowAttempt(angle, velocity, startPosition,
					gravity);
			fliegt = true;
		} catch (GorillasException e) {
			fliegt = false;
			e.printStackTrace();
		}
	}

	public Vector2f nextPosition() {
		return nextPositions.poll();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		if (fliegt)
			g.drawImage(bild, position.x, position.y);
		else
			g.drawImage(bild, startPosition.x, startPosition.y);
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
			position = throwAttempt.getNexPoint(delta);
		} catch (GorillasException ex) {
			fliegt = false;
			throw ex;
		}
	}

	public boolean isFlying() {
		return fliegt;
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