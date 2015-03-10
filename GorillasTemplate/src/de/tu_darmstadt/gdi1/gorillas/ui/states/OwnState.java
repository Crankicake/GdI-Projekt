package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

public abstract class OwnState extends BasicTWLGameState {

	protected StateBasedEntityManager entityManager;
	protected int stateID;

	protected int windowWidth;
	protected int windowHeight;

	private static Image menuEntry;

	static {
		try {
			menuEntry = new Image("assets/gorillas/background/entry.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public OwnState(int sid) {
		stateID = sid;

		entityManager = StateBasedEntityManager.getInstance();

		windowWidth = Launcher.FRAME_WIDTH;
		windowHeight = Launcher.FRAME_HEIGHT;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		entityManager.updateEntities(container, game, delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		entityManager.renderEntities(container, game, g);
	}

	@Override
	protected RootPane createRootPane() {
		return super.createRootPane();
	}

	@Override
	protected void layoutRootPane() {
		super.layoutRootPane();
	}

	@Override
	public int getID() {
		return stateID;
	}

	public void changeState(GameContainer container, StateBasedGame game,
			int state) {
		game.enterState(state);

		entityManager.clearEntitiesFromState(state);

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

	protected Entity createMenuEntity(String name, Vector2f position)
			throws SlickException {
		Entity menuItem = new Entity(name);

		menuItem.setPosition(position);
		menuItem.setScale(0.35f);
		menuItem.addComponent(new ImageRenderComponent(getMenuEntryImage()));

		return menuItem;
	}

	protected Image getMenuEntryImage() {
		return menuEntry;
	}
}
