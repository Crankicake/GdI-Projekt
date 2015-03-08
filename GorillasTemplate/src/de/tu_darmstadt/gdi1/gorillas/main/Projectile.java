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
	private Image bild;

	private boolean fliegt;
	private ThrowAttempt throwAttempt;

	private long lastFrame;

	public Projectile(String entityID, Vector2f pos) {
		super(entityID);
		position = pos;
		nextPositions = new PriorityQueue<Vector2f>();
	}

	public void createEntity() throws SlickException {
		bild = new Image("/assets/gorillas/banana.png");

		setScale(Launcher.SCALE);
		setPassable(true);
		setRotation(0.0f);
	}

	public void setParamter(int angle, int velocity, Vector2f pos,
			double gravity) {
		position.x = pos.x;
		position.y = pos.y;

		try {
			throwAttempt = new ThrowAttempt(angle, velocity, pos, gravity);
			fliegt = true;
		} catch (GorillasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Vector2f nextPosition() {
		return nextPositions.poll();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.drawImage(bild, position.x, position.y);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) {
		int delta = getDelta();

		if (!fliegt)
			return;

		bild.setRotation(rotation);

		rotation += 10f;

		if (rotation == 360f)
			rotation = 0;
try{
		position = throwAttempt.getNexPoint(delta);
}
catch (GorillasException ex){
	
}
	}

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