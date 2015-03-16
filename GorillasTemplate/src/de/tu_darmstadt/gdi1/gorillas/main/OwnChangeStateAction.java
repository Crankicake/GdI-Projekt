package de.tu_darmstadt.gdi1.gorillas.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;

public class OwnChangeStateAction implements Action {
	  private final int state;

	  public OwnChangeStateAction(int newState) {
	    state = newState;
	  }

	  @Override
	  public void update(GameContainer gc, StateBasedGame sb, int delta,
	      Component event) {
	   
	    sb.enterState(state);
	    
	    gc.getInput().clearKeyPressedRecord();
	    gc.getInput().clearControlPressedRecord();
	    gc.getInput().clearMousePressedRecord();

	    
	    if (gc.isPaused())
	      gc.resume();
	  }

}
