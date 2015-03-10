package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class InstructionState extends OwnState{

	public InstructionState(int sid) {
		super(sid);
		
		names = new String[] {
			"Zurueck"	
		};
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		super.initBackground();
		
		// Entitaet zum Zurueckkehren zum Menuebildschirm
		Entity zurueckE = createMenuEntity(names[0], new Vector2f(120, 80));

		/*
				// Entitaet des 1. Screenshots
				String screen1 = "";
				Entity screen1E = new Entity(screen1);

				// Setzen der Komponenten
				screen1E.setPosition(new Vector2f(370, 200));
				screen1E.setScale(1f);
				screen1E.addComponent(new ImageRenderComponent(
						new org.newdawn.slick.Image(
								"assets/gorillas/background/screen1.png")));
				
				// Entitaet des 2. Screenshots
				String screen2 = "";
				Entity screen2E = new Entity(screen2);

				// Setzen der Komponenten
				screen2E.setPosition(new Vector2f(370, 200));
				screen2E.setScale(1f);
				screen2E.addComponent(new ImageRenderComponent(
						new org.newdawn.slick.Image(
								"assets/gorillas/background/screen2.png")));
				
				// Entitaet des 3. Screenshots
				String screen3 = "";
				Entity screen3E = new Entity(screen3);

				// Setzen der Komponenten
				screen3E.setPosition(new Vector2f(370, 200));
				screen3E.setScale(1f);
				screen3E.addComponent(new ImageRenderComponent(
						new org.newdawn.slick.Image(
								"assets/gorillas/background/screen3.png")));
				
				// Entitaet des 4. Screenshots
				String screen4 = "";
				Entity screen4E = new Entity(screen4);

				// Setzen der Komponenten
				screen4E.setPosition(new Vector2f(370, 200));
				screen4E.setScale(1f);
				screen4E.addComponent(new ImageRenderComponent(
						new org.newdawn.slick.Image(
								"assets/gorillas/background/screen4.png")));
				*/
				
		// Events und Actions
		Event zurueckEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		// Zurueck Action
		ChangeStateInitAction zurueckAction = new ChangeStateInitAction(Gorillas.MAINMENUSTATE);

		zurueckEvent.addAction(zurueckAction);
		zurueckE.addComponent(zurueckEvent);

		// Hinzufuegen der Entity zum Entitymanager
		entityManager.addEntity(getID(), zurueckE);
		
	 	/* entityManager.addEntity(getID(), screen1E);
		 * entityManager.addEntity(getID(), screen2E);
		 * entityManager.addEntity(getID(), screen3E);
		 * entityManager.addEntity(getID(), screen4E);*/
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);

		//  Schreiben der Texte
		g.drawString("Zurueck", 85, 66);
		g.drawString("Willkommen bei Gorillas!", 250, 130);
		g.drawString("In der folgenden Anleitung werden sie Schritt fuer", 50, 160);
		g.drawString("Schritt in die Welt von Gorillas eingefuehrt!", 50, 190);
		g.drawString("1. Schritt: Waehlen der Spielernamen", 30, 250);
		//  g.drawString("1. Schritt: Waehlen der Spielernamen", 325, 365);
		g.drawString("2. Schritt: Starten des Spiels", 30, 410);
		//	g.drawString("1. Schritt: Waehlen der Spielernamen", 325, 390);
		g.drawString("3. Schritt: Waehlen der Flugbahn", 450, 250);
		//	g.drawString("1. Schritt: Waehlen der Spielernamen", 325, 405);
		g.drawString("4. Schritt: Viel Erfolg!", 450, 410);
		// g.drawString("1. Schritt: Waehlen der Spielernamen", 325, 420);
	}
}
