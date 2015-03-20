package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.Jukeboxibox;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;


/**
 * Diese Klasse repraesentiert den State des Hauptmenues. Hier hat der Spieler
 * die Auswahl ein neues Spiel zu starten, die Highscores einzusehen, die
 * Anleitung zu lesen oder das Spiel zu beenden.
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 *
 */

public class MainMenuState extends OwnState {

	private Jukeboxibox jukebox;

	public MainMenuState(int sid) {
		super(sid);

		names = new String[] { "Neues Spiel", "Highscore", "Anleitung",
				"About", "Beenden" };

		jukebox = Jukeboxibox.getInstanz();

		MasterGame.createPlayer();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.initBackground();

		// Entitaet zum starten eines neues Spiels
		Entity neuesSpielE = createMenuEntity(names[0], new Vector2f(
				windowWidth / 2, windowHeight / 2 - 120));

		// Entitaet zum einsehen des Highscores
		Entity highscoreE = createMenuEntity(names[1], new Vector2f(
				windowWidth / 2, windowHeight / 2 - 60));

		// Entitaet zum lesen der Anleitung
		Entity anleitungE = createMenuEntity(names[2], new Vector2f(
				windowWidth / 2, windowHeight / 2));

		// Entitaet zum lesen des Abouts
		Entity aboutE = createMenuEntity(names[3], new Vector2f(
				windowWidth / 2, windowHeight / 2 + 60));

		// Entitaet zum Beenden des Spiels
		Entity beendenE = createMenuEntity(names[4], new Vector2f(
				windowWidth / 2, windowHeight / 2 + 120));

		// Events und Actions
		ANDEvent neuesSpielEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());
		ANDEvent highscoreEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());
		ANDEvent anleitungEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());
		ANDEvent aboutEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());
		ANDEvent spielBeendenEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		// Neues Spiel Action
		ChangeStateInitAction neueAction = new ChangeStateInitAction(
				Gorillas.GAMESETUPSTATE);

		// Highscore Action
		ChangeStateAction highscoreAction = new ChangeStateAction(
				Gorillas.HIGHSCORESTATE);

		// Anleitung Action
		ChangeStateAction anleitungAction = new ChangeStateAction(
				Gorillas.INSTRUCTIONSSTATE);

		// About Action
		ChangeStateAction aboutAction = new ChangeStateAction(
				Gorillas.OPTIONSTATE);

		// Spiel beenden Action
		QuitAction beendenAction = new QuitAction();

		neuesSpielEvent.addAction(neueAction);
		highscoreEvent.addAction(highscoreAction);
		anleitungEvent.addAction(anleitungAction);
		aboutEvent.addAction(aboutAction);
		spielBeendenEvent.addAction(beendenAction);

		neuesSpielE.addComponent(neuesSpielEvent);
		highscoreE.addComponent(highscoreEvent);
		anleitungE.addComponent(anleitungEvent);
		aboutE.addComponent(aboutEvent);
		beendenE.addComponent(spielBeendenEvent);

		// Hinzufuegen der Entity zum Entitymanager
		entityManager.addEntity(getID(), neuesSpielE);
		entityManager.addEntity(getID(), highscoreE);
		entityManager.addEntity(getID(), anleitungE);
		entityManager.addEntity(getID(), aboutE);
		entityManager.addEntity(getID(), beendenE);

		// Jukeboxpart
		if (!MasterGame.isJukeboxRunning()) {

			jukebox = Jukeboxibox.getInstanz();
			try {
				jukebox.start(null);
			} catch (Exception e) {
				e.printStackTrace();
			}

			MasterGame.setIsJukeboxRunning(true);
		}

		// Jukeboxpart ende
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		Input input = container.getInput();

		if (MasterGame.isAGameRunning() && input.isKeyPressed(Input.KEY_ESCAPE)) {
			changeState(container, game, Gorillas.GAMEPLAYSTATE);

		}

		if (input.isKeyPressed(Input.KEY_N)) {
			changeState(container, game, Gorillas.GAMESETUPSTATE);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);

		g.setColor(org.newdawn.slick.Color.white);
		g.drawString(names[0], windowWidth / 2 - 35, windowHeight / 2 - 134);
		g.drawString(names[1], windowWidth / 2 - 35, windowHeight / 2 - 74);
		g.drawString(names[2], windowWidth / 2 - 35, windowHeight / 2 - 14);
		g.drawString(names[3], windowWidth / 2 - 35, windowHeight / 2 + 46);
		g.drawString(names[4], windowWidth / 2 - 35, windowHeight / 2 + 106);
	}
}
