package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.Highscore;
import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
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

		names = new String[] { "Zurueck", "GorillaLogo" };

		io = new InputOutput();

		Highscore mm = new Highscore("Simon", 12, 4, 22);
		io.addHighscore(mm);
		mm = new Highscore("Salim", 123, 44, 200);
		io.addHighscore(mm);

		hsc = io.leseHighscore();
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

		// Entitaet des Gorilla Logos
		Entity gorilla_logoE = new Entity(names[1]);

		// Setzen der Komponenten
		gorilla_logoE.setPosition(new Vector2f(windowWidth / 2 - 30,
				windowHeight / 2 - 100));
		gorilla_logoE.setScale(0.5f);
		if (!MasterGame.getDebug()) {
			gorilla_logoE.addComponent(new ImageRenderComponent(new Image(
					"assets/gorillas/background/Banner_highscore.png")));
		}

		// Hinzufuegen der Entity zum Entitymanager
		entityManager.addEntity(getID(), zurueckE);
		entityManager.addEntity(getID(), gorilla_logoE);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);

		g.setColor(org.newdawn.slick.Color.blue);
		g.drawString("Platzierung", windowWidth/2-300, windowHeight/2-200);
		g.drawString("Name",windowWidth/2-180, windowHeight/2-200);
		g.drawString("Gespielte Runden",windowWidth/2-120, windowHeight/2-200);
		g.drawString("Gewonnene Runden", windowWidth/2+50, windowHeight/2-200);
		g.drawString("Trefferquote", windowWidth/2+250,windowHeight/2-200);

		int y = 120;
		g.setColor(org.newdawn.slick.Color.white);
		for (int i = 0; i < io.anzahlHighscore(); i++) {
			double anzahlR =0,anzahlG=0,anzahlB=0,ergebnis1=0,ergebnis2=0;
			anzahlR = hsc[i].getAnzahlRunden();
			anzahlG = hsc[i].getAnzahlGewonnen();
			anzahlB = hsc[i].getAnzahlBananen();
			ergebnis1 = (anzahlG/anzahlR)*100;
			ergebnis2 = anzahlG/anzahlB;
			g.drawString((i+1)+"",windowWidth/2-300,y);
			g.drawString(hsc[i].getName(),windowWidth/2-180,y);
			g.drawString(""+hsc[i].getAnzahlRunden(),windowWidth/2-100,y);
			g.drawString(""+hsc[i].getAnzahlGewonnen(),windowWidth/2+50,y);
			g.drawString("--("+Math.round(ergebnis1 * 100)/100+")%",windowWidth/2+100,y);
			g.drawString(""+ Math.round(ergebnis2*100.00)/100.00, windowWidth/2+250, y);
			y+=20;
		}
		g.drawString("Zurueck", 85, 66);
	}
}
