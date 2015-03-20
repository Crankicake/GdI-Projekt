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
 * Der 2. Instructionstate für mehr Instructions
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */

public class InstructionState2 extends OwnState {

	public InstructionState2(int sid) {
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
				Gorillas.INSTRUCTIONSSTATE);

		zurueckEvent.addAction(zurueckAction);
		zurueckE.addComponent(zurueckEvent);

		// Entitaet der Step_Bilder
		Entity step3 = new Entity(names[0]);
		Entity step4 = new Entity(names[0]);

		// Setzen der Komponenten

		step3.setPosition(new Vector2f(windowWidth / 2 - 220,
				windowHeight / 2 - 80));
		step3.setScale(0.35f);
		if (!MasterGame.getDebug()) {
			step3.addComponent(new ImageRenderComponent(
					new org.newdawn.slick.Image(
							"assets/gorillas/instructions/step3.png")));
		}
		step4.setPosition(new Vector2f(windowWidth / 2 - 220, windowHeight / 2
				- -150));
		step4.setScale(0.35f);
		if (!MasterGame.getDebug()) {
			step4.addComponent(new ImageRenderComponent(
					new org.newdawn.slick.Image(
							"assets/gorillas/instructions/step4.png")));
		}
		
		// Hinzufuegen der Entity zum Entitymanager
		entityManager.addEntity(getID(), zurueckE);
		entityManager.addEntity(getID(), step3);
		entityManager.addEntity(getID(), step4);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);

		g.setColor(org.newdawn.slick.Color.white);
		// Schreiben der Texte
		g.drawString("Zurueck", 85, 66);

		// Schreiben der Texte fuer Pic3
		g.drawString("9.  Eingabe der Wurfstaerke ", 350, 150);
		g.drawString("10. Eingabe des Wurfwinkels ", 350, 190);
		g.drawString("11. Bestaetigen des Wurfs ", 350, 230);
		g.drawString("12. Richtung und Staerke des Windes ", 350, 270);

		// Schreiben der Texte fuer Pic4
		g.drawString("Viel Erfolg und Spass beim Spielen!!! ", 350, 400);

	}
}
