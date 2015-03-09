package de.tu_darmstadt.gdi1.gorillas.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;

public class ApplyEvent extends Event {

	private static boolean performActionBoolean;
	
	@SuppressWarnings("unused")
	private double id;
	
	private long threadID = -1;
	
	public ApplyEvent() {
		super("ButtonTest123");
		
		id = Math.random();
		
		performActionBoolean = false;
	}

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame sb,
			int delta) {
		if(Thread.currentThread().getId() != threadID && threadID != -1)
		{
			System.out.println("Fehler in den Threads");
		}
		return performActionBoolean;
	}

	public void SetPerformAction(boolean b) {
		performActionBoolean = b;
		threadID = (Thread.currentThread().getId());
	}
}
