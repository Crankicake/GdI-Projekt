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
import de.tu_darmstadt.gdi1.gorillas.changed.OwnChangeStateAction;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

/**
 *  GameSetupState ist unser Einstellungsstate :D
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */

public class GameSetupState extends OwnState {

	private EditField playername1Textbox;
	private EditField playername2Textbox;
	private  EditField gravitationTextbox;
	private Label playername1Label;
	private Label playername2Label;
	private Label gravitationLabel;
	private InputOutput io;

	private String errormessage;

	private String errorMessageP1;
	private String errorMessageP2;

	/**
	 * Leert die errormessages und initialisiert das name-Array
	 * @param sid
	 */
	public GameSetupState(int sid) {
		super(sid);

		io = new InputOutput();
		errormessage = "";
		errorMessageP1 = "";
		errorMessageP2 = "";

		names = new String[] { "Zurueck", "Spiel starten" };
	}

	/**
	 * Überschreibt die Standartmethode und ruft die anderen init-Methoden auf
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		initBackground();
		initEntities();
	}

	/**
	 * Rendert die Strings (nicht die Labels)
	 */
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

	/**
	 * Updatet die Entities und den Imput
	 */
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

	/**
	 * @return: Der Frame mit den GUI Elementen
	 */
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

		gravitationTextbox = new EditField();
		gravitationTextbox.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {
				try {
					float d = Float.valueOf(gravitationTextbox.getText());

					fillGravitationTextbox(d);

				} catch (NumberFormatException nfe) {
					errormessage = "Gravitation muss eine Zahl sein";
				} catch (NullPointerException npe) {
					errormessage = "null";
				}
			}
		});

		playername1Label = new Label();
		playername1Label.setText("Name von Spieler 1:");

		playername2Label = new Label();
		playername2Label.setText("Name von Spieler 2:");

		gravitationLabel = new Label();
		gravitationLabel.setText("Gravitation:");

		/*
		 * Autocomplete 
		 */
		
		AutoCompletionDataSource acds = new AutoCompletionDataSource() {
			public AutoCompletionResult collectSuggestions(String text,
					int cursorPos, AutoCompletionResult prev) {
				ArrayList<String> ergebnis = new ArrayList<String>();
				ergebnis.add(io.findeNamen(text).toString());

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

		playername1Textbox.setAutoCompletion(acds);
		playername1Textbox.setAutoCompletionWindow(efacw1);
		playername2Textbox.setAutoCompletion(acds);
		playername2Textbox.setAutoCompletionWindow(efacw2);

		rp.add(playername1Textbox);
		rp.add(playername2Textbox);
		rp.add(gravitationTextbox);
		rp.add(playername1Label);
		rp.add(playername2Label);
		rp.add(gravitationLabel);

		if (!MasterGame.isAGameRunning()) {
			setPlayername1TextboxText("Player 1");
			setPlayername2TextboxText("Player 2");
		} else {
			setPlayername1TextboxText(MasterGame.getPlayerOne().getName());
			setPlayername2TextboxText(MasterGame.getPlayerTwo().getName());
		}

		setGravitationTextboxText(String.valueOf(MasterGame.getGravitation()));

		return rp;
	}

	/**
	 * 
	 * @param d die neue Gravitation
	 */
	public void fillGravitationTextbox(float d) {

		MasterGame.setGravitation(d);
	}

	/**
	 * Passt die Größe der GUI Elemente an
	 */
	@Override
	protected void layoutRootPane() {
		super.layoutRootPane();

		int width = windowWidth / 2;
		int height = windowHeight / 2;

		playername1Label.adjustSize();
		playername1Label.setPosition(width - 150, height - 100);

		playername1Textbox.adjustSize();
		playername1Textbox.setSize(130, 20);
		playername1Textbox.setPosition(width, height - 100);

		gravitationLabel.adjustSize();
		gravitationLabel.setPosition(width - 150, height - 20);

		gravitationTextbox.adjustSize();
		gravitationTextbox.setSize(130, 20);
		gravitationTextbox.setPosition(width, height - 20);

		playername2Label.adjustSize();
		playername2Label.setPosition(width - 150, height - 60);

		playername2Textbox.adjustSize();
		playername2Textbox.setSize(130, 20);
		playername2Textbox.setPosition(width, height - 60);

	}

	/**
	 * Initialisiert die beiden 'Knöpfe'
	 * @throws SlickException
	 */
	protected void initEntities() throws SlickException {
		Entity newGameEntity = createMenuEntity(names[1], new Vector2f(
				windowWidth / 2, windowHeight / 2 + 50));

		Entity zurueckEntity = createMenuEntity(names[0], new Vector2f(120, 80));

		Event zurueckEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		OwnChangeStateAction zurueckAction = new OwnChangeStateAction(
				Gorillas.MAINMENUSTATE);
		zurueckEvent.addAction(zurueckAction);
		zurueckEntity.addComponent(zurueckEvent);

		entityManager.addEntity(getID(), newGameEntity);
		entityManager.addEntity(getID(), zurueckEntity);
	}

	/**
	 * Die Methode, die ausgeführt wird, wenn der Name von Spieler 1 geändert wird
	 */	
	public void playername1Textbox_TextChanged() {

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

	/**
	 * Die Methode, die ausgeführt wird, wenn der Name von Spieler 2 geändert wird
	 */	
	public void playername2Textbox_TextChanged() {

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

	/**
	 * Simuliert einen Mouseclick an (x,y)
	 * @param x die x-koordinate
	 * @param y die y-koordinate
	 */
	public void mouseLeftButton_Click(GameContainer gc, StateBasedGame sbg,
			int x, int y) {
		Image i = getMenuEntryImage();

		double width = i.getWidth() * 0.35 * scale;
		double height = i.getHeight() * 0.35 * scale;

		double ix = windowWidth / 2 - width / 2;
		double iy = windowHeight / 2 + 50 - height / 2;

		if (x >= ix && x <= ix + width) {
			if (y >= iy && y <= iy + height) {

				StartButtonClick(gc, sbg);
			}
		}
	}

	/**
	 * Simuliert einen Klick auf den Startenbutton
	 */
	public void StartButtonClick(GameContainer gc, StateBasedGame sbg) {
		String name1 = MasterGame.getPlayerOne().getName();
		String name2 = MasterGame.getPlayerTwo().getName();

		if (name1 == null || name1.isEmpty()) {
			errorMessageP1 = "Die Spielernamen duerfen nicht leer sein";
			errormessage = errorMessageP1;
		}

		if (name2 == null || name2.isEmpty()) {
			errorMessageP2 = "Die Spielernamen duerfen nicht leer sein";
			errormessage = errorMessageP2;
		}

		if (name1.equals(name2)) {
			errormessage = "Bitte unterschiedliche Spielernamen eingeben";
		}

		if (errorMessageP1 == null || errorMessageP2 == null
				|| errormessage == null)
			return;

		if (!errorMessageP1.isEmpty() || !errorMessageP2.isEmpty()
				|| !errormessage.isEmpty())
			return;

		changeState(gc, sbg, Gorillas.GAMEPLAYSTATE);
		io.speichereName(name1);
		io.speichereName(name2);
	}

	/**
	 * Setzt den Playername1TextboxText
	 * @param text der neue Text
	 */
	public void setPlayername1TextboxText(String text) {
		playername1Textbox.setText(text);
	}

	/**
	 * Setzt den Playername2TextboxText
	 * @param text der neue Text
	 */
	public void setPlayername2TextboxText(String text) {
		playername2Textbox.setText(text);
	}

	/**
	 * Setzt den GravitationTextboxText
	 * @param text der neue Text
	 */
	private void setGravitationTextboxText(String text) {
		gravitationTextbox.setText(text);
	}

	/**
	 * Setzt den Namen von Spieler 1
	 * @param name: der Name
	 */
	public void setPlayerOneName(String name) {
		if (name == null || name.isEmpty()) {
			errorMessageP1 = "Die Spielernamen duerfen nicht leer sein";
			return;
		}

		if (name.equals(MasterGame.getPlayerTwo().getName())) {
			errorMessageP1 = "Bitte unterschiedliche Spielernamen eingeben";
			errorMessageP2 = "Bitte unterschiedliche Spielernamen eingeben";
			return;
		}

		errorMessageP1 = "";

		MasterGame.getPlayerOne().setName(name);
	}

	/**
	 * Setzt den Namen von Spieler 2
	 * @param name: der Name
	 */
	public void setPlayerTwoName(String name) {
		if (name == null || name.isEmpty()) {
			errorMessageP2 = "Die Spielernamen duerfen nicht leer sein";
			return;
		}

		if (name.equals(MasterGame.getPlayerOne().getName())) {
			errorMessageP1 = "Bitte unterschiedliche Spielernamen eingeben";
			errorMessageP2 = "Bitte unterschiedliche Spielernamen eingeben";
			return;
		}

		errorMessageP2 = "";

		MasterGame.getPlayerTwo().setName(name);
	}

	/**
	 * Gibt die ErrorMessage von Spieler 1 zurück
	 * @return die Message
	 */
	public String getErrorMessageP1() {
		return errorMessageP1;
	}
	
	/**
	 * Gibt die ErrorMessage von Spieler 2 zurück
	 * @return die Message
	 */
	public String getErrorMessageP2() {
		return errorMessageP2;
	}

	/**
	 * Gibt die ErrorMessage zurück
	 * @return die Message
	 */
	public String getErrorMessage() {
		return errormessage;
	}
}