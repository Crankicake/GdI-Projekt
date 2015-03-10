package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditFieldAutoCompletionWindow;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.model.AutoCompletionDataSource;
import de.matthiasmann.twl.model.AutoCompletionResult;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class GameSetupState extends OwnState {

	private EditField playername1Textbox;
	private EditField playername2Textbox;
	private Label playername1Label;
	private Label playername2Label;

	private InputOutput io;

	private String errormessage;

	public GameSetupState(int sid) {
		super(sid);

		io = new InputOutput();
		errormessage = "";
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		initBackground();
		initEntities();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		entityManager.renderEntities(gc, sbg, g);

		g.drawString("Zurück", 85, 66);
		g.drawString("Spiel starten", windowWidth / 2 - 35,
				windowHeight / 2 + 36);
		g.drawString(errormessage, 220, 500);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		entityManager.updateEntities(gc, sbg, delta);

		Input input = gc.getInput();

		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			mouseLeftButton_Click(gc, sbg, input.getMouseX(), input.getMouseY());
		}
	}

	protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();

		playername1Textbox = new EditField();
		playername1Textbox.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {
				playername1Textbox_TextChanged();
			}

		});

		playername2Textbox = new EditField();
		playername2Textbox.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {
				playername2Textbox_TextChanged();
			}
		});

		playername1Label = new Label();
		playername1Label.setText("Name von Spieler 1:");

		playername2Label = new Label();
		playername2Label.setText("Name von Spieler 2:");

		rp.add(playername1Textbox);
		rp.add(playername2Textbox);
		rp.add(playername1Label);
		rp.add(playername2Label);

		setPlayername1TextboxText("Player 1");
		setPlayername2TextboxText("Player 2");

		return rp;
	}

	@Override
	protected void layoutRootPane() {

		int width = windowWidth / 2;
		int height = windowHeight / 2;

		playername1Label.adjustSize();
		playername1Label.setPosition(width - 150, height - 100);

		playername1Textbox.adjustSize();
		playername1Textbox.setSize(130, 20);
		playername1Textbox.setPosition(width, height - 100);

		playername2Label.adjustSize();
		playername2Label.setPosition(width - 150, height - 50);

		playername2Textbox.adjustSize();
		playername2Textbox.setSize(130, 20);
		playername2Textbox.setPosition(width, height - 50);
	}

	protected void initBackground() throws SlickException {

		Entity background = new Entity("Background");
		background.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/background/MenuBackground.jpg")));
		background.setPosition(new Vector2f(windowWidth / 2, windowHeight / 2));
		background.setScale(1.5f);
		background.setPassable(true);
		background.setRotation(0.0f);

		entityManager.addEntity(stateID, background);
	}

	protected void initEntities() throws SlickException {
		Entity newGameEntity = new Entity("Spiel wird gestartet");
		newGameEntity.setPosition(new Vector2f(windowWidth / 2,
				windowHeight / 2 + 50));
		newGameEntity.setScale(0.35f);
		newGameEntity.addComponent(new ImageRenderComponent(new Image(
				"assets/gorillas/background/entry.png")));

		Entity zurueckEntity = new Entity("Zurück");
		zurueckEntity.setPosition(new Vector2f(120, 80));
		zurueckEntity.setScale(0.35f);
		zurueckEntity.addComponent(new ImageRenderComponent(new Image(
				"assets/gorillas/background/entry.png")));

		Event zurueckEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		ChangeStateInitAction zurueckAction = new ChangeStateInitAction(
				Gorillas.MAINMENUSTATE);
		zurueckEvent.addAction(zurueckAction);
		zurueckEntity.addComponent(zurueckEvent);

		entityManager.addEntity(getID(), newGameEntity);
		entityManager.addEntity(getID(), zurueckEntity);
	}

	public void playername1Textbox_TextChanged() {
		/*
		 * AutoCompletionDataSource acds = new AutoCompletionDataSource() {
		 * 
		 * @Override public AutoCompletionResult collectSuggestions(String arg0,
		 * int arg1, AutoCompletionResult arg2) { // TODO Auto-generated method
		 * stub return null; } }; EditFieldAutoCompletionWindow ficken = new
		 * EditFieldAutoCompletionWindow(playername1Textbox);
		 * playername1Textbox.setAutoCompletionWindow(ficken);
		 * setPlayerOneName(io.FindeNamen(playername1Textbox.getText()));
		 */

		String name1, name2;

		name1 = playername1Textbox.getText();
		name2 = playername2Textbox.getText();

		if (name1 != null && name2 != null) {
			if (!name1.isEmpty() && !name2.isEmpty()) {

				if (!name1.equals(name2)) {
					setPlayerOneName(name1);
					setPlayerTwoName(name2);
				}
			}
		}
	}

	public void playername2Textbox_TextChanged() {

		// playername2Textbox.setText(io.FindeNamen(playername2Textbox.getText()));

		String name1, name2;

		name1 = playername1Textbox.getText();
		name2 = playername2Textbox.getText();

		if (name1 != null && name2 != null) {
			if (!name1.isEmpty() && !name2.isEmpty()) {

				if (!name1.equals(name2)) {
					setPlayerOneName(name1);
					setPlayerTwoName(name2);
				}
			}
		}
	}

	public void mouseLeftButton_Click(GameContainer gc, StateBasedGame sbg,
			int x, int y) throws SlickException {
		Image i = new Image("assets/gorillas/background/entry.png");

		double width = i.getWidth() * 0.35;
		double height = i.getHeight() * 0.35;

		double ix = windowWidth / 2 - width / 2;
		double iy = windowHeight / 2 + 50 - height / 2;

		if (x >= ix && x <= ix + width) {
			if (y >= iy && y <= iy + height) {

				String name1 = playername1Textbox.getText();
				String name2 = playername2Textbox.getText();

				if (name1 == null || name2 == null || name1.isEmpty()
						|| name2.isEmpty()) {
					errormessage = "Die Spielernamen dürfen nicht leer sein!";
					return;
				}

				if(name1.equals(name2)) {
					errormessage = "Bitte unterschiedliche Spielernamen eingeben!";
					return;
				}
				
				errormessage = "";
				
				changeState(gc, sbg, Gorillas.GAMEPLAYSTATE);
			}
		}
	}

	public void setPlayername1TextboxText(String text) {
		playername1Textbox.setText(text);
	}

	public void setPlayername2TextboxText(String text) {
		playername2Textbox.setText(text);
	}

	public void setPlayerOneName(String name) {
		if (name == null || name.isEmpty()
				|| name.equals(MasterGame.getPlayerTwo().getName()))
			return;

		MasterGame.getPlayerOne().setName(name);
	}

	public void setPlayerTwoName(String name) {
		if (name == null || name.isEmpty()
				|| name.equals(MasterGame.getPlayerOne().getName()))
			return;

		MasterGame.getPlayerTwo().setName(name);
	}

	public void letzteNamen() {

	}
}