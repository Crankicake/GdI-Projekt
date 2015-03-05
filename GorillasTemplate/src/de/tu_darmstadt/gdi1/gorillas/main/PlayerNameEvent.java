package de.tu_darmstadt.gdi1.gorillas.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;

public class PlayerNameEvent extends Event {

	public PlayerNameEvent() {
		super("Hallo");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame sb,
			int delta) {
		Player one = MasterGame.getPlayerOne();
		
		if(one == null)
			return false;
		
		Player two = MasterGame.getPlayerTwo();
		
		if(two == null)
			return false;
		
		String oneName = one.getName();
		
		if(oneName == null)
			return false;
		
		String twoName = two.getName();
		
		if(twoName == null)
			return false;
		
		return oneName.equals(twoName);
	}

}
