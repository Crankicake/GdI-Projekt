package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class MainMenuState extends OwnState {

	public MainMenuState(int sid) {
		super(sid);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// Erstellung der Entität für den Hintergrund
		Entity backgroundE = new Entity("Menue");

		backgroundE
				.setPosition(new Vector2f(windowWidth / 2, windowHeight / 2));
		backgroundE.setScale(1.5f);

		// Hintergrundbild für das Menü
		backgroundE.addComponent(new ImageRenderComponent(
				new org.newdawn.slick.Image(
						"/assets/gorillas/background/MenuBackground.jpg")));

		// Entität zum starten eines neues Spiels
		String neuesSpiel = "Neues Spiel";
		Entity neuesSpielE = createMenuEntity(neuesSpiel, new Vector2f(120, 80));

		// Entität zum einsehen des Highscores
		String highscore = "Highscore";
		Entity highscoreE = createMenuEntity(highscore, new Vector2f(120, 140));

		// Entität zum lesen der Anleitung
		String anleitung = "Anleitung";
		Entity anleitungE = createMenuEntity(anleitung, new Vector2f(120, 200));

		// Entität zum lesen des Abouts
		String about = "About";
		Entity aboutE = createMenuEntity(about, new Vector2f(120, 260));

		// Entität zum Beenden des Spiels
		String beenden = "Beenden";
		Entity beendenE = createMenuEntity(beenden, new Vector2f(120, 320));

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
		ChangeStateInitAction highscoreAction = new ChangeStateInitAction(
				Gorillas.HIGHSCORESTATE);

		// Anleitung Action
		ChangeStateInitAction anleitungAction = new ChangeStateInitAction(
				Gorillas.INSTRUCTIONSSTATE);

		// About Action
		ChangeStateInitAction aboutAction = new ChangeStateInitAction(
				Gorillas.OPTIONSTATE);

		// Spiel beenden Action
		QuitAction beendenAction = new QuitAction();

		// Prüft ob N gedrückt wurde
		Entity nListener = new Entity("N_Listener");
		KeyPressedEvent nPressed = new KeyPressedEvent(Input.KEY_N);
		nPressed.addAction(new ChangeStateAction(Gorillas.GAMESETUPSTATE));
		nListener.addComponent(nPressed);

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

		// Hinzufügen der Entity zum Entitymanager
		entityManager.addEntity(getID(), backgroundE);
		entityManager.addEntity(getID(), neuesSpielE);
		entityManager.addEntity(getID(), highscoreE);
		entityManager.addEntity(getID(), anleitungE);
		entityManager.addEntity(getID(), aboutE);
		entityManager.addEntity(getID(), beendenE);
		entityManager.addEntity(getID(), nListener);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

		Input input = container.getInput();

		if (MasterGame.isAGameRunning() && input.isKeyPressed(Input.KEY_ESCAPE))
			changeState(container, game, Gorillas.GAMEPLAYSTATE);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);

		g.drawString("Neues Spiel", 85, 66);
		g.drawString("Highscore", 85, 126);
		g.drawString("Anleitung", 85, 186);
		g.drawString("Spiel beenden", 85, 306);
		g.drawString("About", 85, 246);
	}
}
