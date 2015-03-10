package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import eea.engine.entity.StateBasedEntityManager;

public abstract class OwnState extends BasicTWLGameState {

	protected StateBasedEntityManager entityManager;
	protected int stateID;

	protected int windowWidth;
	protected int windowHeight;

	public OwnState(int sid) {
		stateID = sid;

		entityManager = StateBasedEntityManager.getInstance();

		windowWidth = Launcher.FRAME_WIDTH;
		windowHeight = Launcher.FRAME_HEIGHT;
	}

	@Override
	public int getID() {
		return stateID;
	}

	public void changeState(GameContainer container, StateBasedGame game, int state) {
		game.enterState(state);

		StateBasedEntityManager.getInstance().clearEntitiesFromState(state);

		try {
			container.getInput().clearKeyPressedRecord();
			container.getInput().clearControlPressedRecord();
			container.getInput().clearMousePressedRecord();
			game.init(container);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		if (container.isPaused())
			container.resume();
	}
}
