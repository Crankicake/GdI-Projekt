package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.dropofwater.Launch;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class MainMenuState extends BasicTWLGameState {

	private int stateID;
	private StateBasedEntityManager entityManager;



	public MainMenuState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// Erstellung der Entität für den Hintergrund
		Entity backgroundE = new Entity("Menue");
		
		backgroundE.setPosition(new Vector2f(Launcher.FRAME_WIDTH / 2,
				Launcher.FRAME_HEIGHT / 2));
		backgroundE.setScale(1.5f);
		
		// Hintergrundbild für das Menü
		backgroundE.addComponent(new ImageRenderComponent(new org.newdawn.slick.Image("/assets/gorillas/background/MenuBackground.jpg")))
		;
	
		// Hintergrund Entität wird an entityManager übergeben
		entityManager.addEntity(stateID, backgroundE);
		
		
		
		// Entität zum starten eines neues Spiels
		String neuesSpiel = "Neues Spiel";
		Entity neuesSpielE = new Entity(neuesSpiel);
		
		
		// Entität zum einsehen des Highscores 
		String highscore = "Highscore";
		Entity highscoreE = new Entity(highscore);
		
		
		// Entität zum lesen der Anleitung
		String anleitung = "Anleitung";
		Entity anleitungE = new Entity(anleitung);
		
		// Entität zum lesen des Abouts
		String about ="About";
		Entity aboutE = new Entity(about);
		
		
		// Entität zum Beenden des Spiels
		String beenden = "Beenden";
		Entity beendenE = new Entity(beenden);
		
		
		
		// Setzen der Komponenten 
		neuesSpielE.setPosition(new Vector2f(120,80));
		 neuesSpielE.setScale(0.35f);
		neuesSpielE.addComponent(
				new ImageRenderComponent(new org.newdawn.slick.Image("assets/gorillas/background/entry.png")));
		
		
		highscoreE.setPosition(new Vector2f(120,140));
		highscoreE.setScale(0.35f);
		highscoreE.addComponent(
				new ImageRenderComponent(new org.newdawn.slick.Image("assets/gorillas/background/entry.png")));
		
		
		anleitungE.setPosition(new Vector2f(120,200));
		anleitungE.setScale(0.35f);
		anleitungE.addComponent(
				new ImageRenderComponent(new org.newdawn.slick.Image("assets/gorillas/background/entry.png")));
		
		aboutE.setPosition(new Vector2f(400,80));
		aboutE.setScale(0.35f);
		aboutE.addComponent(
				new ImageRenderComponent(new org.newdawn.slick.Image("assets/gorillas/background/entry.png")));
		
		beendenE.setPosition(new Vector2f(120,260));
		beendenE.setScale(0.35f);
		beendenE.addComponent(
			new ImageRenderComponent(new org.newdawn.slick.Image("assets/gorillas/background/entry.png")));
	
		
		
		
		// Events und Actions
		Event neuesSpielEvent = new ANDEvent(new MouseEnteredEvent(),new MouseClickedEvent());
		Event highscoreEvent =  new ANDEvent(new MouseEnteredEvent(),new MouseClickedEvent());
		Event anleitungEvent =  new ANDEvent(new MouseEnteredEvent(),new MouseClickedEvent());
		Event aboutEvent =  new ANDEvent(new MouseEnteredEvent(),new MouseClickedEvent());
		Event spielBeendenEvent = new ANDEvent(new MouseEnteredEvent(),new MouseClickedEvent());
		
		
		// Neues Spiel Action
		Action neueAction = new ChangeStateInitAction(Gorillas.GAMESETUPSTATE);
		
		// Highscore Action
		Action highscoreAction = new ChangeStateInitAction(Gorillas.HIGHSCORESTATE);
		
		// Anleitung Action
		Action anleitungAction = new ChangeStateInitAction(Gorillas.INSTRUCTIONSSTATE);
		
		// About Action
		Action aboutAction = new ChangeStateInitAction(Gorillas.OPTIONSTATE);
		
		// Spiel beenden Action
		Action beendenAction = new QuitAction();
		
		
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
		entityManager.addEntity(this.stateID, neuesSpielE);
		entityManager.addEntity(this.stateID, highscoreE);
		entityManager.addEntity(this.stateID, anleitungE);
		entityManager.addEntity(this.stateID, aboutE);
		entityManager.addEntity(this.stateID, beendenE);
		entityManager.addEntity(this.stateID, nListener);
		
		
		
		
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
		
	
		g.drawString("Neues Spiel",85,70);
		g.drawString("Highscore",85,130);
		g.drawString("Anleitung", 85, 190);
		g.drawString("Spiel beenden", 85, 250);
		g.drawString("About",385,70);
		
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	protected RootPane createRootPane() {

		RootPane rp = super.createRootPane();

		
		return rp;
	}

	@Override
	protected void layoutRootPane() {

		
}}
