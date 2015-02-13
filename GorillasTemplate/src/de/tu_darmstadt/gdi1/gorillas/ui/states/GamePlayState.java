package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

public class GamePlayState extends BasicTWLGameState {
	
	// Das hier ist einfach das Attribut, abgeschrieben von DOW :§
	private StateBasedEntityManager sbnm;
	private int stateID;

	// Die 3 sind atm nur Platzhalter.
	public static double wind = 0;
	public static double windScale = 0;
	public static double timeScale = 0;

	public GamePlayState(int sid) {
		stateID = sid;
		sbnm = StateBasedEntityManager.getInstance();
	}

	// Ich hab das Initialiesieren in 2 Methoden ausgelagert, für die Affen noch eine weitere bitte, der Übersichtlichkeit halber.
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		initBackground();
		initBuildings();
	}

	// Ich hab bei jeder Entität lieber mal alle Werte gesetzt.
	protected void initBackground() throws SlickException {
		Entity background = new Entity("Background"); // Name der Entität, unter der sie später gefunden werden kann
		background.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/background/background.png"))); // Das Bild. Leider weiß ich noch nicht, wie 2 Bilder laden und tauschen kann. Theme Sonne etc.
		background.setPosition(new Vector2f(400, 300)); // Die Position ist relativ zur links-oberen Ecke und gibt die Mitte vom Bild an. Bissi komisch
		background.setScale(1.0f);
		background.setPassable(true);
		background.setRotation(0.0f);

		Entity sun = new Entity("Sun");
		sun.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/sun/sun_smiling.png")));
		sun.setPosition(new Vector2f(400, 100));
		sun.setScale(1.0f);
		sun.setPassable(true);
		sun.setRotation(0.0f);

		sbnm.addEntity(stateID, background); // Beide hinzufügen
		sbnm.addEntity(stateID, sun);
	}

	protected void initBuildings() throws SlickException {

		// Hier wird dynamisch berechnet, wie viele Gebäude das Spielfeld breit ist. Jedes Gebäude ist 100p breit.
		int anzahl = Launcher.FRAME_WIDTH / 100;
		
		// Falls die Rechnung nicht glatt aufgeht, noch eins Zeichnen.
		// Später vielleicht mit scaling so ändern, dass es (fast) genau passt?
		// Warnung ist da weil wir 2 Konstanten miteinander verrechnen. Einfach ignorieren
		if(Launcher.FRAME_WIDTH % 100 != 0)
			++anzahl;
		
		Entity[] buildings = new Entity[anzahl];

		Random r = new Random();

		for (int i = 0; i < anzahl; ++i) {
			buildings[i] = new Entity("Building_" + i);
			
			// per Zufall wählen, welches Gebäudebild wir nehmen. Atm sind es nur 3 Farben, aber nun gut
			
			switch (r.nextInt(3)) {
			case 0:
				buildings[i].addComponent(new ImageRenderComponent(new Image(
						"/assets/gorillas/background/building_green.png")));
				break;
			case 1:
				buildings[i].addComponent(new ImageRenderComponent(new Image(
						"/assets/gorillas/background/building_red.png")));
				break;
			case 2:
				buildings[i].addComponent(new ImageRenderComponent(new Image(
						"/assets/gorillas/background/building_nazi.png")));
				break;
			}			
			
			buildings[i].setPosition(new Vector2f(50 + 100 * i, 25 * r.nextInt(11) + Launcher.FRAME_HEIGHT));
			
			// Hier rechnen wir aus, wo das Gebäude hin soll. Dabei ist der Spielraum zwischen 0 und 250.
			// Gerne kann man das erweitern oder die Schritte verfeinern/ vergröbern.
			
			buildings[i].setScale(1.0f);
			buildings[i].setPassable(false);
			buildings[i].setRotation(0.0f);
			
			sbnm.addEntity(stateID, buildings[i]);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		sbnm.renderEntities(gc, sbg, g);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		
		sbnm.updateEntities(gc, sbg, i);

	}

	@Override
	public int getID() {
		return stateID;
	}
}