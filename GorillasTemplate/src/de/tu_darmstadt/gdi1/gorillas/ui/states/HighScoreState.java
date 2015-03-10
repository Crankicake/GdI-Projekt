package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class HighScoreState extends OwnState {

	public HighScoreState(int sid) {
		super(sid);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		// Erstellung der Entität für den Hintergrund
		Entity backgroundE = new Entity("Menue");

		backgroundE.setPosition(new Vector2f(Launcher.FRAME_WIDTH / 2,
				Launcher.FRAME_HEIGHT / 2));
		backgroundE.setScale(1.5f);

		// Hintergrundbild für das About-Fenster
		backgroundE.addComponent(new ImageRenderComponent(
				new org.newdawn.slick.Image(
						"/assets/gorillas/background/MenuBackground.jpg")));

		// Hintergrund Entität wird an entityManager übergeben
		entityManager.addEntity(stateID, backgroundE);

		// Entität zum Zurückkehren zum Menübildschirm
		String zurueck = "Zurück";
		Entity zurueckE = new Entity(zurueck);

		// Setzen der Komponenten
		zurueckE.setPosition(new Vector2f(120, 80));
		zurueckE.setScale(0.35f);
		zurueckE.addComponent(new ImageRenderComponent(
				new org.newdawn.slick.Image(
						"assets/gorillas/background/entry.png")));

		// Events und Actions
		Event zurueckEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		//Zurück Action
		Action zurueckAction = new ChangeStateInitAction(Gorillas.MAINMENUSTATE);

		zurueckEvent.addAction(zurueckAction);
		zurueckE.addComponent(zurueckEvent);

		// Entität des Gorilla Logos
		String gorilla_logo = "";
		Entity gorilla_logoE = new Entity(gorilla_logo);

		// Setzen der Komponenten
		gorilla_logoE.setPosition(new Vector2f(370, 200));
		gorilla_logoE.setScale(1f);
		gorilla_logoE.addComponent(new ImageRenderComponent(
				new org.newdawn.slick.Image(
						"assets/gorillas/background/Banner_highscore.png")));

		// Hinzufügen der Entity zum Entitymanager
		entityManager.addEntity(this.stateID, zurueckE);
		entityManager.addEntity(this.stateID, gorilla_logoE);

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

		// Schreiben der Texte
		g.drawString("Zurück", 85, 70);
	}

	@Override
	protected RootPane createRootPane() {

		RootPane rp = super.createRootPane();

		return rp;
	}

	@Override
	protected void layoutRootPane() {

	}
}
