package de.tu_darmstadt.gdi1.gorillas.main;

import java.net.URL;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.matthiasmann.twl.slick.TWLStateBasedGame;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GamePlayState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GameSetupState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.HighScoreState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.InstructionState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.MainMenuState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.OptionState;
import eea.engine.entity.StateBasedEntityManager;

/**
 * Main class of the gorilla game
 * 
 * @author Peter Kloeckner, Sebastian Fach
 * @version 1.0
 */
public class Gorillas extends TWLStateBasedGame {

	// Each state is represented by an integer value
	public static final int MAINMENUSTATE = 0;
	public static final int GAMESETUPSTATE = 1;
	public static final int GAMEPLAYSTATE = 2;
	public static final int HIGHSCORESTATE = 3;
	public static final int OPTIONSTATE = 4;
	public static final int INSTRUCTIONSSTATE = 5;

	public static boolean debug = false;

	public Gorillas(boolean debug) {
		super("Gorillas");
		setDebug(debug);
	}

	public static void setDebug(boolean debuging) {
		debug = debuging;
	}

	@Override
	public void initStatesList(GameContainer gameContainer)
			throws SlickException {
		
		// Add states to the StateBasedGame
		// The first added one will be the started one
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameSetupState(GAMESETUPSTATE));
		this.addState(new GamePlayState(GAMEPLAYSTATE));
		this.addState(new HighScoreState(HIGHSCORESTATE));
		this.addState(new OptionState(OPTIONSTATE));
		this.addState(new InstructionState(INSTRUCTIONSSTATE));

		// Add states to the StateBasedEntityManager
		StateBasedEntityManager.getInstance().addState(MAINMENUSTATE);
		StateBasedEntityManager.getInstance().addState(GAMESETUPSTATE);
		StateBasedEntityManager.getInstance().addState(GAMEPLAYSTATE);
		StateBasedEntityManager.getInstance().addState(HIGHSCORESTATE);
		StateBasedEntityManager.getInstance().addState(OPTIONSTATE);
		StateBasedEntityManager.getInstance().addState(INSTRUCTIONSSTATE);
	}

	@Override
	protected URL getThemeURL() {
		return getClass().getResource("/theme.xml");
	}
}
