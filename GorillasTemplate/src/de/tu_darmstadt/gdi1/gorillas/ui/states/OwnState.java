package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

/**
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 *
 *         Diese Klasse dient als Basisklasse fuer alle unsere States. Hier
 *         werden Attribute und Methoden deklariert und implementiert, die wir
 *         in allen/ vielen States brauchen.
 */

public abstract class OwnState extends BasicTWLGameState {

	protected StateBasedEntityManager entityManager;
	protected int stateID;

	protected int windowWidth;
	protected int windowHeight;

	protected String[] names;

	private static Image menuEntry;
	private static Image background;

	static {
		// Da die Bilder fuer den Menueeintrag und den Hintergrund oftmals
		// geladen
		// werden
		// werden sie hier im static-Konstruktor geladen, um sicherzugehen, dass
		// das ganze nur einmal geschieht.
		// Sollte eine Exception auftreten, dann werden beiden als in ein
		// Pseudobild geesetzt,
		// damit sie nicht null sind und andere Exceptions ausloesen. Zur Not
		// haben wir keine Bilder

		try {
			menuEntry = new Image("assets/gorillas/background/entry.png");
			background = new Image(
					"/assets/gorillas/background/MenuBackground.jpg");
		} catch (SlickException e) {
			e.printStackTrace();
			try {
				menuEntry = new Image(0, 0);
				background = new Image(0, 0);
			} catch (SlickException e1) {
			}
		}
	}

	/**
	 * @param sid
	 *            : Die ID, die intern diesem State zugeordnet ist
	 */
	public OwnState(int sid) {
		// Der Konstruktor setzt nicht nur die StateID, sondern auch den
		// EntityManager,
		// da der in jeder erbenden Klasse gebraucht wird.
		// Zusaetzlich werden noch die Hoehe und die Breite des Fensters
		// gespeichert,
		// um dadurch einen einfacheren Zugriff zu erlauben

		stateID = sid;

		entityManager = StateBasedEntityManager.getInstance();

		windowWidth = Launcher.FRAME_WIDTH;
		windowHeight = Launcher.FRAME_HEIGHT;
	}

	/**
	 * @param container
	 *            : Der Container, in dem dieses Spiel ausgefuehrt wird
	 * @param game
	 *            : Das Spiel
	 * @param delta
	 *            : Die verstrichene Zeit seit dem letzten Update in ms
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// Die Standartupdatemethodenimplementation, die einfach einmal alle
		// Entities updatet

		entityManager.updateEntities(container, game, delta);
	}

	/**
	 * @param container
	 *            : Der Container, in dem dieses Spiel ausgefuehrt wird
	 * @param game
	 *            : Das Spiel
	 * @param g
	 *            : Die Zeichenlogik
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// Die Standartrendermethodenimplementation, die einfach einmal alle
		// Entities rendert

		entityManager.renderEntities(container, game, g);
	}

	/**
	 * @return: Das RootPane ist die Basis fuer alle gezeichneten GUI-Elemente
	 */
	@Override
	protected RootPane createRootPane() {
		// Der Standartaufruf fuer das RootPane aus der Elternklasse

		return super.createRootPane();
	}

	@Override
	protected void layoutRootPane() {
		// Zwar ist aktuell die Methode super.layoutRootPane() leer, sollte sie
		// aber eine Implementation bekommen,
		// schadet es uns nicht, sie aufzurufen. Zukunftsorientiert ;)

		super.layoutRootPane();
	}

	/**
	 * @return: Gibt die StateID zurueck
	 */
	@Override
	public int getID() {
		// Ein einfacher getter fuer die StateID

		return stateID;
	}

	/**
	 * @param container
	 *            : Der Container, in dem dieses Spiel ausgefuehrt wird
	 * @param game
	 *            : Das Spiel
	 * @param state
	 *            : Die interne StateID, in die gewechselt werden soll
	 */
	public void changeState(GameContainer container, StateBasedGame game,
			int state) {
		// CODE GENOMMEN AUS StateChangeAction !!!!!!!!
		// Erleichert das wechseln zwischen States, da hier keine Action und
		// kein Event von Noeten sind.

		game.enterState(state);

		entityManager.clearEntitiesFromState(state);

		try {
			container.getInput().clearKeyPressedRecord();
			container.getInput().clearControlPressedRecord();
			container.getInput().clearMousePressedRecord();
			game.init(container);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		if (container.isPaused())
			container.resume();
	}

	/**
	 * @throws SlickException
	 *             : Steht hier wegen Kompatibilitaetsgruenden drinne
	 */
	protected void initBackground() throws SlickException {
		// Erstellung der Standartentitaet fuer den Hintergrund
		// und das Konfigurieren der einzelnen Parameter werden hier
		// vorgenommen.
		// Bei Bedarf einfach ueberschreiben.

		Entity backgroundEntity = new Entity("Menue");

		backgroundEntity.setPosition(new Vector2f(windowWidth / 2,
				windowHeight / 2));
		backgroundEntity.setScale(1.5f);
		backgroundEntity.setPassable(true);
		backgroundEntity.setRotation(0.0f);
		backgroundEntity.addComponent(new ImageRenderComponent(
				getBackgroundImage()));

		entityManager.addEntity(getID(), backgroundEntity);
	}

	/**
	 * @param name
	 *            : Der Name, den die erstellte Entitaet erhalten soll
	 * @param position
	 *            : Die Position, an der die Entitaet erstellt werden soll
	 * @return: Die erstellte Entitaet
	 */
	protected Entity createMenuEntity(String name, Vector2f position)
			throws SlickException {
		// CreateMenuEntity erstellt eine Entity, die die Standarteigenschaften
		// fuer einen von uns erstellten Pseudobutton hat. Erleichert vieles :D

		Entity menuItem = new Entity(name);

		menuItem.setPosition(position);
		menuItem.setScale(0.35f);
		menuItem.setPassable(true);
		menuItem.setRotation(0.0f);
		menuItem.addComponent(new ImageRenderComponent(getMenuEntryImage()));

		return menuItem;
	}

	/**
	 * @return: Das Standartbild fuer Menueeintraege
	 */
	protected Image getMenuEntryImage() {
		// Ein einfacher getter fuer das Bild eines Menueeintrags

		return menuEntry;
	}

	/**
	 * @return: Das Standartbild fuer den Hintergrund
	 */
	protected Image getBackgroundImage() {
		// Ein einfacher getter fuer das Hintergrundbild

		return background;
	}
}