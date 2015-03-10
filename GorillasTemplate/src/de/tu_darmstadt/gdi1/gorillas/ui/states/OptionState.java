package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class OptionState extends OwnState {

	public OptionState(int sid) {
		super(sid);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		// Erstellung der Entität für den Hintergrund
		Entity backgroundE = new Entity("Menue");

		backgroundE.setPosition(new Vector2f(windowWidth / 2,
				windowHeight / 2));
		backgroundE.setScale(1.5f);

		// Hintergrundbild für das About-Fenster
		backgroundE.addComponent(new ImageRenderComponent(
				new org.newdawn.slick.Image(
						"/assets/gorillas/background/MenuBackground.jpg")));

		// Entität zum Zurückkehren zum Menübildschirm
		String zurueck = "Zurück";
		Entity zurueckE = createMenuEntity(zurueck, new Vector2f(120, 80));

		// Events und Actions
		Event zurueckEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		// Neues Spiel Action
		ChangeStateInitAction zurueckAction = new ChangeStateInitAction(Gorillas.MAINMENUSTATE);

		zurueckEvent.addAction(zurueckAction);
		zurueckE.addComponent(zurueckEvent);

		// Entität des Gorilla Logos
		String gorilla_logo = "GorillaLogo";
		Entity gorilla_logoE = new Entity(gorilla_logo);

		// Setzen der Komponenten
		gorilla_logoE.setPosition(new Vector2f(370, 200));
		gorilla_logoE.setScale(1f);
		gorilla_logoE.addComponent(new ImageRenderComponent(
				new org.newdawn.slick.Image(
						"assets/gorillas/background/Banner.png")));

		// Hinzufügen der Entity zum Entitymanager
		entityManager.addEntity(getID(), backgroundE);
		entityManager.addEntity(getID(), zurueckE);
		entityManager.addEntity(getID(), gorilla_logoE);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		super.render(container, game, g);

		// Schreiben der Texte
		g.drawString("Zurück", 85, 66);
		g.drawString("Version: 1.0", 320, 260);
		g.drawString("Gorillas wird entwickelt von:", 260, 320);
		g.drawString("Fabian Czappa", 320, 360);
		g.drawString("Christoph Gombert", 320, 380);
		g.drawString("Salim Karacaoglan", 320, 400);
		g.drawString("Simon Foitzik", 320, 420);
	}
}
