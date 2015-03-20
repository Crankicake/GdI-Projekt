package de.tu_darmstadt.gdi1.gorillas.main;

import org.newdawn.slick.SlickException;

import de.tu_darmstadt.gdi1.gorillas.entity.Player;

/**
 * In dieser Klasse laufen alle Elementaren Informationen zusammen und koennen
 * abgefragt und gesetzt werden (Verwaltung)
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */
public class MasterGame {

	private static Player playerOne;
	private static Player playerTwo;

	private static boolean applyPlayerNames;
	private static boolean running;
	private static boolean debug;
	private static boolean laeuftJukebox;

	private static float grav;

	private static float windScale;
	private static float timeScale;
	private static int wind;

	private static String[] remarks;

	static {
		applyPlayerNames = false;
		running = false;
		laeuftJukebox = false;
		debug = false;
		grav = 9.81f;

		windScale = 0.02f;
		timeScale = 0.005f;
		wind = 0;

		remarks = new String[] {
				"Geh doch lieber AngryBirds spielen!",
				"Du stellst dich hier genauso gut an, \n wie ich in der Kuehlkammer",
				"BOOOM!", "Die Russen sind da!!!", "Klappe zu Affe tot!",
				"Na Du Nudel"

		};
	}

	/**
	 * @return Rueckgabe der Instanz von Spieler 1
	 */
	public static Player getPlayerOne() {
		return playerOne;
	}

	/**
	 * @return Rueckgabe der Instanz von Spieler 1
	 */
	public static Player getPlayerTwo() {
		return playerTwo;
	}

	/**
	 * @return
	 */
	public static boolean getApplyPlayerNames() {
		return applyPlayerNames;
	}

	/**
	 * 
	 * @param Legt
	 *            fest ob das Spiel laeuft
	 */
	public static void setIsAGameRunning(boolean b) {
		if (b != running)
			running = b;
	}

	/**
	 * 
	 * @return ruft ab, ob das Spiel gerade laeuft
	 */
	public static boolean isAGameRunning() {
		return running;
	}

	/**
	 * 
	 * @return wird derzeit ein Titel abgespielt
	 */
	public static boolean isJukeboxRunning() {
		return laeuftJukebox;
	}

	/**
	 * 
	 * @param Festlegen
	 *            des Zustands der Jukebox
	 */
	public static void setIsJukeboxRunning(boolean b) {
		laeuftJukebox = b;
	}

	/**
	 * 
	 * @param Die
	 *            Gravitation wird mittels g uebergeben und auf Basis dessen
	 *            festgelegt
	 */
	public static void setGravitation(float g) {
		if (Double.isNaN(g) || Double.isInfinite(g))
			return;

		grav = g;
	}

	/**
	 * 
	 * @return aktuelle Anziehungskraft
	 */
	public static float getGravitation() {
		return grav;
	}

	/**
	 * 
	 * @return relative Skalierung der Windstaerke
	 */
	public static float getWindScale() {
		return windScale;
	}

	/**
	 * 
	 * @return Aktuelle ZeitSkalierung
	 */
	public static float getTimeScale() {
		return timeScale;
	}

	/**
	 * 
	 * @return Windstaerke
	 */
	public static int getWind() {
		return wind;
	}

	/**
	 * 
	 * @param Festlegen
	 *            der Windstaerke
	 */
	public static void setWind(int d) {
		wind = d;
	}

	/**
	 * erstellen von Spieler 1 und 2 als Entity inklusive Namensfestlegung
	 */
	public static void createPlayer() {
		playerOne = new Player("One");
		playerTwo = new Player("Two");

		try {
			playerOne.createEntity();
			playerTwo.createEntity();
		} catch (SlickException s) {

		}

		playerOne.setName("Payer 1");
		playerTwo.setName("Player 2");
	}

	/**
	 * 
	 * @return in remarks sind alle Kommentare der Sonne abgespeichert
	 */
	public static String[] getRemarks() {
		return remarks;
	}

	/**
	 * 
	 * @return Debugmodus
	 */
	public static boolean getDebug() {
		return debug;
	}

	/**
	 * 
	 * @param Festlegen
	 *            des Debugmodus
	 */
	public static void setDebug(boolean b) {
		debug = b;
	}
}
