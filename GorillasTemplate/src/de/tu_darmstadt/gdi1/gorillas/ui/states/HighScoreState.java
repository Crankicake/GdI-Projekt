package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.Highscore;
import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;
import de.tu_darmstadt.gdi1.gorillas.main.OwnChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class HighScoreState extends OwnState {

	private InputOutput io;
	private Highscore[] hsc;
	
	public HighScoreState(int sid) {
		super(sid);
		
		names = new String[] {
				"Zurueck",
				"GorillaLogo"
		};
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

		//Zurueck Action
		OwnChangeStateAction zurueckAction = new OwnChangeStateAction(Gorillas.MAINMENUSTATE);

		zurueckEvent.addAction(zurueckAction);
		zurueckE.addComponent(zurueckEvent);

		// Entitaet des Gorilla Logos
		Entity gorilla_logoE = new Entity(names[1]);

		// Setzen der Komponenten
		gorilla_logoE.setPosition(new Vector2f(windowWidth / 2 - 30, windowHeight / 2 - 100));
		gorilla_logoE.setScale(1f);
		gorilla_logoE.addComponent(new ImageRenderComponent(
				new org.newdawn.slick.Image(
						"assets/gorillas/background/Banner_highscore.png")));

		// Hinzufuegen der Entity zum Entitymanager
		entityManager.addEntity(getID(), zurueckE);
		entityManager.addEntity(getID(), gorilla_logoE);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		io = new InputOutput();
		
		int y = 80;
		
		Highscore mm = new Highscore("Simon", 12, 4, 22);
		io.addHighscore(mm);
		mm = new Highscore("Salim", 123, 44, 200);
		io.addHighscore(mm);
		
		hsc = io.leseHighscore();
		g.setColor(org.newdawn.slick.Color.white);
		
		for(int i = 0;i<io.anzahlHighscore();i++){
			g.drawString("Platz :" + i +"    "+  hsc[i].getName() + "   " + hsc[i].getAnzahlRunden() +
					"    " + hsc[i].getAnzahlGewonnen() +"      "+ (hsc[i].getAnzahlGewonnen()/hsc[i].getAnzahlRunden())*100 +"%" +
					"        Trefferquote:" + (hsc[i].getAnzahlGewonnen()/hsc[i].getAnzahlBananen()) , 100, y+=20);
		}
		g.drawString("Zurueck", 85, 66);
	}
}
