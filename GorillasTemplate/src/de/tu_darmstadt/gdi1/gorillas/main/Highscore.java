package de.tu_darmstadt.gdi1.gorillas.main;

import java.io.Serializable;

/**
 * Diese Klasse repraesentiert die Struktur eines Highscoreeintrags. Sie ist
 * Serialisierbar, da das von Noeten ist, um diese Struktur spaeter in der
 * {@link InputOutput} in die entsprechende Datei schreiben zu koennen.
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 *
 */

@SuppressWarnings("serial")
// Es wird keine SerialID benoetigt, da Sender und Empfaenger gleich sind
public class Highscore implements Serializable {

	/**
	 * Deklaration der benoetigten Attribute
	 */
	private String name;
	private int anzahlRunden;
	private int anzahlGewonnen;
	private int anzahlBananen;
	private double prozentGewonnen;
	private double trefferquote;

	/**
	 * Konstruktor zur Initialisierung der Attribute
	 * 
	 * public Highscore(){ name = ""; anzahlRunden = 0; anzahlGewonnen = 0;
	 * anzahlBananen = 0; prozentGewonnen = 0; setTrefferquote(0); }
	 */

	/**
	 * Ueberladener Konstruktor zur direkten Initialisierung der Attribute
	 * 
	 * @param Name
	 * @param AnzRunden
	 * @param AnzGewonnen
	 * @param AnzBananen
	 */
	public Highscore(String Name, int AnzRunden, int AnzGewonnen, int AnzBananen) {
		name = Name;
		anzahlRunden = AnzRunden;
		anzahlGewonnen = AnzGewonnen;
		anzahlBananen = AnzBananen;
		calculateValues();
	}

	/**
	 * Getter fuer den Namen
	 * 
	 * @return The Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter fuer den Namen
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter fuer Anzahl der Runden
	 * 
	 * @return The amount of played rounds
	 */
	public int getAnzahlRunden() {
		return anzahlRunden;
	}

	/**
	 * Setter fuer Anzahl der Runden
	 * 
	 * @param anzahlRunden
	 */
	public void setAnzahlRunden(int anzahlRunden) {
		this.anzahlRunden = anzahlRunden;
	}

	/**
	 * Getter fuer Anzahl der gewonnenen Runden
	 * 
	 * @return The amount of won rounds
	 */
	public int getAnzahlGewonnen() {
		return anzahlGewonnen;
	}

	/**
	 * Setter fuer Anzahl der gewonnenen Runden
	 * 
	 * @param anzahlGewonnen
	 */
	public void setAnzahlGewonnen(int anzahlGewonnen) {
		this.anzahlGewonnen = anzahlGewonnen;
	}

	/**
	 * Getter fuer Anzahl der Bananen
	 * 
	 * @return The amount of thrown bananas
	 */
	public int getAnzahlBananen() {
		return anzahlBananen;
	}

	/**
	 * Setter fuer Anzahl der Bananen
	 * 
	 * @param anzahlBananen
	 */
	public void setAnzahlBananen(int anzahlBananen) {
		this.anzahlBananen = anzahlBananen;
	}

	/*
	 * public double getGenauigkeit() { return anzahlRunden / anzahlGewonnen; }
	 */
	/**
	 * Getter fuer Prozentzahl der gewonnenen Runden
	 * 
	 * @return The percentage of won rounds
	 */
	public double getProzentGewonnen() {
		return prozentGewonnen;
	}

	public void setProzentGewonnen(double prozentGewonnen) {
		this.prozentGewonnen = prozentGewonnen;
	}

	/**
	 * Getter fuer die Trefferquote
	 * 
	 * @return the trefferquote
	 */
	public double getTrefferquote() {
		return trefferquote;
	}

	/**
	 * Setter fuer die Trefferquote
	 * 
	 * @param trefferquote
	 *            the trefferquote to set
	 */
	public void setTrefferquote(double trefferquote) {
		this.trefferquote = trefferquote;
	}

	public void calculateValues() {
		setProzentGewonnen((double)anzahlGewonnen / (double)anzahlRunden);
		setTrefferquote((double)anzahlBananen / (double)anzahlGewonnen);
	}
}