package de.tu_darmstadt.gdi1.gorillas.main;

import java.io.Serializable;

/**
 * Diese Klasse repräsentiert die Struktur eines Highscoreeintrags. Sie ist Serialisierbar, da das von Nöten ist,
 * um diese Struktur später in der {@link InputOutput} in die entsprechende Datei schreiben zu können.
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappas
 *
 */

@SuppressWarnings("serial") // Es wird keine SerialID benoetigt, da Sender und Empfaenger gleich sind
public class Highscore implements Serializable{

	/**
	 * Deklaration der benötigten Attribute
	 */
	private String name;
	private int anzahlRunden;
	private int anzahlGewonnen;
	private int anzahlBananen;
	
	/**
	 * Konstruktor zur Initialisierung der Attribute
	 */
	public Highscore(){
		name = "";
		anzahlRunden = 0;
		anzahlGewonnen = 0;
		anzahlBananen = 0;
	}
	
	/**
	 * Überladener Konstruktor zur direkten Initialisierung der Attribute
	 * 
	 * @param Name
	 * @param AnzRunden
	 * @param AnzGewonnen
	 * @param AnzBananen
	 */
	public Highscore(String Name, int AnzRunden, int AnzGewonnen, int AnzBananen){
		name = Name;
		anzahlRunden = AnzRunden;
		anzahlGewonnen = AnzGewonnen;
		anzahlBananen = AnzBananen;
	}

	/**
	 * Getter für den Namen
	 * @return The Name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter für den Namen
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter für Anzahl der Runden
	 * @return The amount of played rounds
	 */
	public int getAnzahlRunden() {
		return anzahlRunden;
	}
	
	/**
	 * Setter für Anzahl der Runden
	 * @param anzahlRunden
	 */
	public void setAnzahlRunden(int anzahlRunden) {
		this.anzahlRunden = anzahlRunden;
	}
	
	/**
	 * Getter für Anzahl der gewonnenen Runden
	 * @return The amount of won rounds
	 */
	public int getAnzahlGewonnen() {
		return anzahlGewonnen;
	}
	
	/**
	 * Setter für Anzahl der gewonnenen Runden
	 * @param anzahlGewonnen
	 */
	public void setAnzahlGewonnen(int anzahlGewonnen) {
		this.anzahlGewonnen = anzahlGewonnen;
	}
	
	/**
	 * Getter für Anzahl der Bananen
	 * @return The amount of thrown bananas
	 */
	public int getAnzahlBananen() {
		return anzahlBananen;
	}
	
	/**
	 * Setter für Anzahl der Bananen
	 * @param anzahlBananen
	 */
	public void setAnzahlBananen(int anzahlBananen) {
		this.anzahlBananen = anzahlBananen;
	}
	

}
