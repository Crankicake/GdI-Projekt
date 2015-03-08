package de.tu_darmstadt.gdi1.gorillas.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;

public class ApplyEvent extends Event {

	private boolean performActionBoolean;
	
	public ApplyEvent() {
		super("Test");
		
		performActionBoolean = false;
	}

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame sb,
			int delta) {
		
		return performActionBoolean;
	}

	public void SetPerformAction(boolean b) {
		performActionBoolean = b;
	}
}
