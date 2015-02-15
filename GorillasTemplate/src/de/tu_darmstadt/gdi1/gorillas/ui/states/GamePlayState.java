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
import de.tu_darmstadt.gdi1.gorillas.main.Spielergorilla;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

public class GamePlayState extends BasicTWLGameState {

	// Das hier ist einfach das Attribut, abgeschrieben von DOW :�
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

	// Ich hab das Initialiesieren in 2 Methoden ausgelagert, f�r die Affen noch
	// eine weitere bitte, der �bersichtlichkeit halber.
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		initBackground();
		initBuildings();
	}

	// Ich hab bei jeder Entit�t lieber mal alle Werte gesetzt.
	protected void initBackground() throws SlickException {
		Entity background = new Entity("Background");
		// Name der Entit�t, unter der sie sp�ter gefunden werden kann

		background.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/background/background.png")));
		// Das Bild. Leider wei� ich noch nicht, wie 2 Bilder laden und tauschen
		// kann. Theme Sonne etc.

		float scaleX = ((float) Launcher.FRAME_WIDTH / 800f);
		float scaleY = ((float) Launcher.FRAME_HEIGHT / 600f);
		// Hier berechnen wir, um wie viel gestreckt das Bild pro Richtung sein
		// muss. F�r 800 * 600 sind die Grafiken entwickelt, deswegen nehmen wir
		// das als Grundwert

		background.setPosition(new Vector2f(Launcher.FRAME_WIDTH / 2,
				Launcher.FRAME_HEIGHT / 2));
		// Die Position ist relativ zur links-oberen Ecke und gibt die Mitte vom
		// Bild an. Bissi komisch

		background.setScale(scaleX > scaleY ? scaleX : scaleY);
		// Die gr��ere Streckung gibt die Gesamtstreckung. Problem hier:
		// Was machen, wenn das Fenster nicht 4:3 ist? Eine Dimension passt
		// dann, die andere nicht.

		background.setPassable(true);
		background.setRotation(0.0f);

		Entity sun = new Entity("Sun");
		sun.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/sun/sun_smiling.png")));

		sun.setPosition(new Vector2f(Launcher.FRAME_WIDTH / 2,
				Launcher.FRAME_HEIGHT / 6));
		sun.setScale(scaleX > scaleY ? scaleX : scaleY);
		sun.setPassable(true);
		sun.setRotation(0.0f);

		sbnm.addEntity(stateID, background);
		sbnm.addEntity(stateID, sun);
		// Beide hinzuf�gen
	}

	/**
	 * @throws SlickException
	 */
	protected void initBuildings() throws SlickException {

		// Sind die Entitäten zerstörbar? Ich glaube es gibt da eine extra
		// destructable entity...
		Entity[] buildings = new Entity[8];

		float scale = (float) Launcher.FRAME_WIDTH / 800f;

		
		//Affenentitäten
		Entity firstApe = new Entity("Ape_1");
	
		Entity secondApe = new Entity("Ape_2");

		Random r = new Random();

		

		//Zufallszahlen zum platzieren der affen zwischen dem 1. und 3.
		// bzw dann 5. und 7. hochhaus
		int indexFirstApe = r.nextInt(2) + 1;
		

		// Hochhäuser setzen
		for (int i = 0; i < 8; ++i) {
			buildings[i] = new Entity("Building_" + i);

			// per Zufall w�hlen, welches Geb�udebild wir nehmen. Atm sind es
			// nur 3 Farben, aber nun gut

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

			// X und Y koordinaten für die Hochhäuser
			float buildingX = (50 + 100 * i) * scale;
			float buildingY = (r.nextInt(9) - 4)* Launcher.FRAME_HEIGHT / 20
					+ Launcher.FRAME_HEIGHT; 
			
			

			buildings[i].setPosition(new Vector2f(buildingX, buildingY ));

			// Linken Affen setzen
			// Zufallszahl um das gebäude zu bestimmen
			
			
			// Derzeit noch null, da noch unfertig
			// später soll hier die zufallszahl eingestetzt werden
			if ( 0 == i) {
				// indexFirstApe -
				
				
				firstApe.addComponent(new ImageRenderComponent(new Image(
						"/assets/gorillas/gorillas/gorilla.png")));
			
				float apeX = buildingX;
				//X koordinate korrekt, lediglich Y noch zu fertigen 
				float apeY = (buildingY - 26) / 2;
				
				System.out.println(apeY);
				
				firstApe.setPosition(new Vector2f(apeX, apeY));

				

				
				firstApe.setScale(1);
				firstApe.setPassable(true);
				firstApe.setRotation(0.0f);
				
				sbnm.addEntity(stateID, firstApe);
			}

			// Hier rechnen wir aus, wo das Geb�ude hin soll.
			// "+ Launcher.FRAME_HEIGHT" setzt die Geb�ude so,
			// dass sie genau in der Mitte vom Fenster aufh�ren. Dann +/- 4 *
			// mit einer Konstante. Diese hab ich mal als
			// Launcher.FRAME_HEIGHT / 20 festgelegt, wenn der Divisor gr��er
			// wird, wird der Spielraum kleiner.

			buildings[i].setScale(scale);
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