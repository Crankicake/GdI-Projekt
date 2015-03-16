package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditFieldAutoCompletionWindow;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.model.AutoCompletionDataSource;
import de.matthiasmann.twl.model.AutoCompletionResult;
import de.matthiasmann.twl.model.SimpleAutoCompletionResult;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.action.basicactions.ChangeStateAction;
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
	private Label AutoCompleteTest;

	private InputOutput io;

	private String errormessage;

	public GameSetupState(int sid) {
		super(sid);

		io = new InputOutput();
		errormessage = "";
		names = new String[] { "Zurueck", "Spiel starten" };
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		initBackground();
		initEntities();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);

		g.setColor(org.newdawn.slick.Color.white);
		g.drawString(names[0], 85, 66);
		g.drawString("Spiel starten", windowWidth / 2 - 35,
				windowHeight / 2 + 36);
		g.drawString(errormessage, 220, 500);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

		Input input = container.getInput();

		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			mouseLeftButton_Click(container, game, input.getMouseX(),
					input.getMouseY());
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

		AutoCompletionDataSource acds = new AutoCompletionDataSource() {
			public AutoCompletionResult collectSuggestions(String text,
					int cursorPos, AutoCompletionResult prev) {
				ArrayList<String> ergebnis = new ArrayList<String>();
				ergebnis.add(io.FindeNamen(text).toString());

				if (ergebnis.isEmpty()) {
					return null;
				}
				return new SimpleAutoCompletionResult(text, 0, ergebnis);
			}
		};

		EditFieldAutoCompletionWindow efacw1 = new EditFieldAutoCompletionWindow(
				playername1Textbox, acds);
		
		EditFieldAutoCompletionWindow efacw2 = new EditFieldAutoCompletionWindow(
				playername2Textbox, acds);

		efacw1.setSize(130, 80);
		//efacw1.adjustSize();
		efacw2.setSize(130, 80);
		// efacw2.adjustSize();
		playername1Textbox.setAutoCompletion(acds);
		playername1Textbox.setAutoCompletionWindow(efacw1);
		playername2Textbox.setAutoCompletion(acds);
		playername2Textbox.setAutoCompletionWindow(efacw2);

		/*
		 * AutoCompleteTest = new Label(); AutoCompleteTest.addCallback(new
		 * CallbackWithReason<Label.CallbackReason>() {
		 * 
		 * @Override public void callback(CallbackReason arg0) {
		 * AutoCompleteTest_Clicked();
		 * 
		 * } });
		 */

		rp.add(playername1Textbox);
		rp.add(playername2Textbox);
		rp.add(playername1Label);
		rp.add(playername2Label);

		if (!MasterGame.isAGameRunning()) {
			setPlayername1TextboxText("Player 1");
			setPlayername2TextboxText("Player 2");
		} else {
			setPlayername1TextboxText(MasterGame.getPlayerOne().getName());
			setPlayername2TextboxText(MasterGame.getPlayerTwo().getName());
		}

		return rp;
	}

	protected void AutoCompleteTest_Clicked() {
		if (playername1Textbox.hasKeyboardFocus())
			playername1Textbox.setText(AutoCompleteTest.getText());
		if (playername2Textbox.hasKeyboardFocus())
			playername2Textbox.setText(AutoCompleteTest.getText());

	}

	@Override
	protected void layoutRootPane() {
		super.layoutRootPane();

		int width = windowWidth / 2;
		int height = windowHeight / 2;

		/*
		 * AutoCompleteTest.adjustSize(); AutoCompleteTest.setSize(130, 20);
		 * AutoCompleteTest.setPosition(width, height-10);
		 */

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

	protected void initEntities() throws SlickException {
		Entity newGameEntity = createMenuEntity(names[1], new Vector2f(
				windowWidth / 2, windowHeight / 2 + 50));

		Entity zurueckEntity = createMenuEntity(names[0], new Vector2f(120, 80));

		Event zurueckEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		ChangeStateAction zurueckAction = new ChangeStateAction(
				Gorillas.MAINMENUSTATE);
		zurueckEvent.addAction(zurueckAction);
		zurueckEntity.addComponent(zurueckEvent);

		entityManager.addEntity(getID(), newGameEntity);
		entityManager.addEntity(getID(), zurueckEntity);
	}

	public void playername1Textbox_TextChanged() {

		String name1, name2;

		name1 = playername1Textbox.getText();
		name2 = playername2Textbox.getText();

		// AutoCompleteTest.setText(io.FindeNamen(name1));
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

		String name1, name2;

		name1 = playername1Textbox.getText();
		name2 = playername2Textbox.getText();

		// AutoCompleteTest.setText(io.FindeNamen(name2));
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
		Image i = getMenuEntryImage();

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
					errormessage = "Die Spielernamen duerfen nicht leer sein!";
					return;
				}

				if (name1.equals(name2)) {
					errormessage = "Bitte unterschiedliche Spielernamen eingeben!";
					return;
				}

				errormessage = "";

				changeState(gc, sbg, Gorillas.GAMEPLAYSTATE);
				io.speichereName(name1);
				io.speichereName(name2);

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

}