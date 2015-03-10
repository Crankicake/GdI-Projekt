package de.tu_darmstadt.gdi1.gorillas.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;

public class EscapePressedAction implements Action {

	public EscapePressedAction() {
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		System.out.println("Hallo action");
		MasterGame.setIsAGameRunning(true);
	}

}
