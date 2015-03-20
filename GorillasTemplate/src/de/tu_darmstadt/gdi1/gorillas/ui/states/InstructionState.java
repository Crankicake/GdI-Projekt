package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.changed.OwnChangeStateAction;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

/**
 * Der 1. Instructionstate für Instructions
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */

public class InstructionState extends OwnState {

	public InstructionState(int sid) {
		super(sid);

		names = new String[] { "Zurueck" };
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		super.initBackground();

		// Entitaet zum Zurueckkehren zum Menuebildschirm
		Entity zurueckE = createMenuEntity(names[0], new Vector2f(120, 80));

		// Events und Actions
		Event zurueckEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		// Zurueck Action
		OwnChangeStateAction zurueckAction = new OwnChangeStateAction(
				Gorillas.MAINMENUSTATE);

		zurueckEvent.addAction(zurueckAction);
		zurueckE.addComponent(zurueckEvent);

		// Entitaet fuer Weitergehen zu Seite2
		Entity weiterE = createMenuEntity(names[0], new Vector2f(670, 80));

		// Events und Actions
		Event weiterEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		// Zurueck Action
		OwnChangeStateAction weiterAction = new OwnChangeStateAction(
				Gorillas.INSTRUCTIONSSTATE2);

		weiterEvent.addAction(weiterAction);
		weiterE.addComponent(weiterEvent);

		// Entitaet der Step_Bilder
		Entity step1 = new Entity(names[0]);
		Entity step2 = new Entity(names[0]);

		// Setzen der Komponenten
		step1.setPosition(new Vector2f(windowWidth / 2 - 220,
				windowHeight / 2 - 80));
		step1.setScale(0.35f);
		if (!MasterGame.getDebug()) {
			step1.addComponent(new ImageRenderComponent(
					new org.newdawn.slick.Image(
							"assets/gorillas/instructions/step1.png")));
			step2.setPosition(new Vector2f(windowWidth / 2 - 220, windowHeight
					/ 2 - -150));
		}
		step2.setScale(0.35f);
		if (!MasterGame.getDebug()) {
			step2.addComponent(new ImageRenderComponent(
					new org.newdawn.slick.Image(
							"assets/gorillas/instructions/step2.png")));
		}

		// Hinzufuegen der Entity zum Entitymanager
		entityManager.addEntity(getID(), zurueckE);
		entityManager.addEntity(getID(), weiterE);
		entityManager.addEntity(getID(), step1);
		entityManager.addEntity(getID(), step2);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);

		g.setColor(org.newdawn.slick.Color.white);

		// Schreiben der Texte
		g.drawString("Zurueck", 85, 66);
		g.drawString("Weiter", 635, 66);

		// Schreiben der Texte fuer Pic1
		g.drawString("1. Neues Spiel beginnen ", 350, 130);
		g.drawString("2. Rangliste der besten Spieler ", 350, 170);
		g.drawString("3. Spielanleitung ", 350, 210);
		g.drawString("4. Infos ueber Version und Namen der Entwickler ", 350,
				250);
		g.drawString("5. Spiel beenden ", 350, 290);

		// Schreiben der Texte fuer Pic2
		g.drawString("6. Zum Hauptmenue zurrueckkehren ", 350, 400);
		g.drawString("7. Auswahl der Spielernamen ", 350, 440);
		g.drawString("8. Spiel starten ", 350, 480);

	}
}
