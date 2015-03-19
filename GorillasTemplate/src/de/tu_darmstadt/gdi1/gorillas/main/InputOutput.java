package de.tu_darmstadt.gdi1.gorillas.main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Diese Klasse ist für alle Input- und Outputbelange gedacht.
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 *
 */
public class InputOutput {
	/**
	 * Diese Methode speichert in der Datei "Namen.bin" den eingegebenen
	 * Spielernamen, der als Parameter übergeben wird
	 * 
	 * @param name
	 */
	public void speichereName(String name) {
		File datei = new File("Namen.bin");
		name = name + ";";
		if (datei.exists()) {
			try {
				FileOutputStream bos = new FileOutputStream(datei, true);

				byte[] puffer = name.getBytes();
				for (int i = 0; i < puffer.length; i++) {
					puffer[i] = (byte) (puffer[i] ^ 1001001);
					bos.write(puffer[i]);
					bos.flush();
				}
				bos.flush();
				bos.close();
				System.out.println("Datei existierte...");
			} catch (IOException e) {
				System.out.println("Fehler beim Ah�ngen des Namens: "
						+ e.toString());
			}
		} else {
			try {
				FileOutputStream bos = new FileOutputStream(datei, false);
				byte[] puffer = name.getBytes();
				for (int i = 0; i < puffer.length; i++) {
					puffer[i] = (byte) (puffer[i] ^ 1001001);
					bos.write(puffer[i]);
					bos.flush();
				}
				bos.flush();
				bos.close();
				System.out.println("Datei existierte nicht...");
			} catch (IOException e) {
				System.out.println("Fehler beim Schreiben des Namens: "
						+ e.toString());
			}
		}
	}

	/**
	 * Diese Klasse durchsucht die Datei "Namen.bin" nach einem String der als
	 * Parameter übergeben wird
	 * 
	 * @param n
	 *            - The Name to search for
	 * @return The found name or the parameter n
	 */
	public String findeNamen(String n) {

		Path datei = Paths.get("Namen.bin");
		String[] segs = null;

		try {

			byte[] puffer = Files.readAllBytes(datei);
			for (int i = 0; i < puffer.length; i++) {
				puffer[i] = (byte) (puffer[i] ^ 1001001);
			}
			String str = new String(puffer, "UTF-8");
			segs = str.split(Pattern.quote(";"));

			for (int i = 0; i < segs.length; i++) {
				if (segs[i].startsWith(n))
					return segs[i].toString();
			}

		} catch (IOException e) {
			
		}
		return n; // Falls der eingegebene Name nicht gefunden wird
	}

	/**
	 * Diese Methode liest alle Higscore Einträge aus der "Highscore.hcs" Datei
	 * und liefert ein Array aus Highscores zurück
	 * 
	 * @return Array of Highscores
	 */

	public Highscore[] leseHighscore() {
		File datei = new File("Highscore.hcs");
		InputStream fis = null;
		Highscore tmp1[] = new Highscore[10000];
		Highscore tmp2;
		int i = 0;

		if (datei.exists()) {
			try {
				fis = new FileInputStream(datei);
				ObjectInputStream ois = new ObjectInputStream(fis);

				int anzahl = anzahlHighscore();
				
				while (i <= anzahl) {
					tmp1[i] = (Highscore) ois.readObject();
					i++;
				}

				fis.close();
				ois.close();

			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
				
			}

			// Sortierung
			for (int k = 0; k < anzahlHighscore(); k++) {
				for (int j = 1; j < anzahlHighscore(); j++) {
					if ((tmp1[j - 1].getAnzahlRunden()
							/ tmp1[j - 1].getAnzahlGewonnen() > (tmp1[j]
							.getAnzahlRunden() / tmp1[j].getAnzahlGewonnen()))) {
						tmp2 = tmp1[j];
						tmp1[j] = tmp1[j - 1];
						tmp1[j - 1] = tmp2;
					}
				}
			}
		}
		
		Highscore[] temp = new Highscore[i];
		
		for(int x = 0; x < i; ++x) {
			temp[x] = tmp1[x];
		}
		
		return temp;
	}

	public int anzahlHighscore() {
		File datei = new File("Highscore.hcs");
		InputStream fis = null;
		Highscore tmp1[] = new Highscore[10000];

		int i = 0;

		if (datei.exists()) {
			try {
				fis = new FileInputStream(datei);
				ObjectInputStream ois = new ObjectInputStream(fis);

				while (true) {
					tmp1[i] = (Highscore) ois.readObject();
					i++;
				}

				//fis.close();
				//ois.close();

			} catch (EOFException e) {
				return i;
			} catch (ClassNotFoundException e) {
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		} else
			return 0;
		return i;

	}

	public void resetHighscore() {
		// Muss noch gemacht
	}

	/**
	 * Diese Methode fügt einen neuen Highscore in die Datei "Highscore.hcs"
	 * hinzu bzw. updated sie mit dem übergebenen Parameter
	 * 
	 * @param hsc
	 */
	public void addHighscore(Highscore hsc) {
		File datei = new File("Highscore.hcs");
		OutputStream fos = null;
		Highscore[] tmp;

		LinkedList<Highscore> liste = new LinkedList<Highscore>();
		
		boolean flag = false;

		if (datei.exists()) {
			tmp = leseHighscore();
			
			try {
				int counter = tmp.length;
				
				for (int i = 0; i < counter; i++) {
					if (tmp[i].getName().equals(hsc.getName())) {
						tmp[i].setAnzahlRunden(tmp[i].getAnzahlRunden()
								+ hsc.getAnzahlRunden());
						tmp[i].setAnzahlGewonnen(tmp[i].getAnzahlGewonnen()
								+ hsc.getAnzahlGewonnen());
						tmp[i].setAnzahlBananen(tmp[i].getAnzahlBananen()
								+ hsc.getAnzahlBananen());
						flag = true;
					} 
					
					liste.add(tmp[i]);
				}

				if(!flag)
					liste.add(hsc);
				
				fos = new FileOutputStream(datei, false);
				
				if(liste.size() > 0)
				{
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(liste.removeFirst());
					oos.flush();
					oos.close();
				}
				
				fos.close();
				
				fos = new FileOutputStream(datei, true);
				
				AppendingObjectOutputStream aoos = new AppendingObjectOutputStream(
						fos);
				
				while(liste.size() > 0) {
					aoos.writeObject(liste.removeFirst());
				}
				
				aoos.flush();
				aoos.close();
				fos.close();
			} catch (IOException e) {
			}
		} else {
			try {
				fos = new FileOutputStream(datei);

				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(hsc);
				oos.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}

	}

	/**
	 * Diese Methode durchsucht den Pfad "../ressources/Musik/" nach allen dort
	 * vorhandenen Audiodateien und liefert einen String mit deren Namen zurück
	 * 
	 * @return Array of Strings with filenames of the audiofiles
	 */
	public static String[] findeLieder() {
		File Pfad = new File("ressources/Musik/");
		return Pfad.list();
	}

	/**
	 * Diese Methode liefert die Anzahl der Audiodateien im Pfad
	 * "../ressources/Musik/" zurück
	 * 
	 * @return The amount of Audiofiles
	 */
	public static int anzahlLieder() {
		return findeLieder().length;
	}
}