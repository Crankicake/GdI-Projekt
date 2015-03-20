package de.tu_darmstadt.gdi1.gorillas.changed;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;

/**
 * In dieser Klasse sind die Klassen ChangeStateAction und ChangeStateInitAction
 * kombiniert, damit wir die Vorteile von beiden nutzen koennen
 * 
 * @author Alexander Jandousek, Timo B&auml;hr
 */

public class OwnChangeStateAction implements Action {
	private final int state;

	/**
	 * @param newState: Die ID des neuen States
	 */
	public OwnChangeStateAction(int newState) {
		state = newState;
	}
	
	/**
	 * Leert die Eingabe und wechselt den State
	 */
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
