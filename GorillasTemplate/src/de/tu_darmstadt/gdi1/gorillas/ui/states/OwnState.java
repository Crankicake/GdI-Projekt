package de.tu_darmstadt.gdi1.gorillas.ui.states;

import de.matthiasmann.twl.slick.BasicTWLGameState;
import eea.engine.entity.StateBasedEntityManager;

public abstract class OwnState extends BasicTWLGameState {

	protected StateBasedEntityManager entityManager;
	protected int stateID;
	
	public OwnState(int sid) {
		stateID = sid;

		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public int getID() {
		return stateID;
	}
}
