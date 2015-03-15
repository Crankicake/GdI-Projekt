package de.tu_darmstadt.gdi1.gorillas.main;

import java.io.Serializable;


@SuppressWarnings("serial") // Es wird keine SerialID benoetigt, da Sender und Empfaenger gleich sind
public class Highscore implements Serializable{

	private String name;
	private int anzahlRunden;
	private int anzahlGewonnen;
	private int anzahlBananen;
	
	public Highscore(){
		name = "";
		anzahlRunden = 0;
		anzahlGewonnen = 0;
		anzahlBananen = 0;
	}
	
	public Highscore(String Name, int AnzRunden, int AnzGewonnen, int AnzBananen){
		name = Name;
		anzahlRunden = AnzRunden;
		anzahlGewonnen = AnzGewonnen;
		anzahlBananen = AnzBananen;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAnzahlRunden() {
		return anzahlRunden;
	}
	public void setAnzahlRunden(int anzahlRunden) {
		this.anzahlRunden = anzahlRunden;
	}
	public int getAnzahlGewonnen() {
		return anzahlGewonnen;
	}
	public void setAnzahlGewonnen(int anzahlGewonnen) {
		this.anzahlGewonnen = anzahlGewonnen;
	}
	public int getAnzahlBananen() {
		return anzahlBananen;
	}
	public void setAnzahlBananen(int anzahlBananen) {
		this.anzahlBananen = anzahlBananen;
	}
	

}
