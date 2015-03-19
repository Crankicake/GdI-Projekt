package de.tu_darmstadt.gdi1.gorillas.main;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Diese Klasse ist fuer alle Input- und Outputbelange gedacht.
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 *
 */
public class InputOutput {

	File dateiHighscore;
	File dateiNamen;

	public InputOutput() {
		dateiNamen = new File("Namen.bin");
		dateiHighscore = new File("Highscore.hsc");
	}

	/**
	 * Diese Methode speichert in der Datei "Namen.bin" den eingegebenen
	 * Spielernamen, der als Parameter übergeben wird
	 * 
	 * @param name
	 */
	public void speichereName(String name) {
		name += ";";

		if (dateiHighscore.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(dateiHighscore,
						true);

				try {
					byte[] puffer = name.getBytes();

					for (int i = 0; i < puffer.length; i++) {
						puffer[i] = (byte) (puffer[i] ^ 1001001);
						fos.write(puffer[i]);
						fos.flush();
					}

					fos.flush();
				} finally {
					fos.close();
				}
			} catch (IOException e) {
			}
		} else {
			try {
				FileOutputStream fos = new FileOutputStream(dateiHighscore,
						false);
				try {
					byte[] puffer = name.getBytes();

					for (int i = 0; i < puffer.length; i++) {
						puffer[i] = (byte) (puffer[i] ^ 1001001);
						fos.write(puffer[i]);
						fos.flush();
					}

					fos.flush();
				} finally {
					fos.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Diese Klasse durchsucht die Datei "Namen.bin" nach einem String der als
	 * Parameter uebergeben wird
	 * 
	 * @param n
	 *            - The Name to search for
	 * @return The found name or the parameter n
	 */
	public String findeNamen(String n) {
		try {
			byte[] puffer = Files.readAllBytes(dateiNamen.toPath());

			for (int i = 0; i < puffer.length; i++) {
				puffer[i] = (byte) (puffer[i] ^ 1001001);
			}

			String str = new String(puffer, "UTF-8");

			String[] segs = str.split(Pattern.quote(";"));

			for (int i = 0; i < segs.length; i++) {
				if (segs[i].startsWith(n))
					return segs[i].toString();
			}

		} catch (IOException e) {

		}
		return n; // Falls der eingegebene Name nicht gefunden wird
	}

	/**
	 * Diese Methode liest alle Higscore Einträge aus der "Highscore.hsc" Datei
	 * und liefert ein Array aus Highscores zurück
	 * 
	 * @return Array of Highscores
	 */
	public Highscore[] leseHighscore() {
		Highscore[] tmp1 = null;
		int anzahl = anzahlHighscore();
		int i = 0;

		if (dateiHighscore.exists()) {
			ObjectInputStream ois = null;
			FileInputStream fis = null;
			try {

				fis = new FileInputStream(dateiHighscore);
				ois = new ObjectInputStream(fis);

				tmp1 = new Highscore[anzahl];

				while (true) {
					tmp1[i] = (Highscore) ois.readObject();
					i++;
				}

			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			} catch (OutOfMemoryError e) {
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
					}
				}

				if (ois != null) {
					try {
						ois.close();
					} catch (IOException e) {
					}
				}
			}
		}

		Highscore[] temp = new Highscore[i];

		for (int x = 0; x < i; ++x) {
			temp[x] = tmp1[x];
		}

		return temp;
	}
	
	/**
	 * Diese Methode liefert die Anzahl der Highscoreeintraege in der Highscoredatei zurueck
	 * @return Amount of highscore entries
	 */
	public int anzahlHighscore() {
		InputStream fis = null;
		ObjectInputStream ois = null;
		Highscore tmp1[] = new Highscore[50];

		int i = 0;

		if (dateiHighscore.exists()) {
			try {
				fis = new FileInputStream(dateiHighscore);
				ois = new ObjectInputStream(fis);

				while (true) {
					tmp1[i] = (Highscore) ois.readObject();
					i++;
				}
			} catch (EOFException e) {
			} catch (ClassNotFoundException e) {
			} catch (IOException e) {
			} catch (OutOfMemoryError e) {
			}

			finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
					}
				}

				if (ois != null) {
					try {
						ois.close();
					} catch (IOException e) {
					}
				}
			}
		} else
			return 0;
		return i;
	}

	/**
	 * Diese Methode reseted die Highscoredatei
	 * 
	 */
	public void resetHighscore() {
		try {
			FileOutputStream fos = new FileOutputStream(dateiHighscore, false);
			try {
				fos.write(0);
				fos.flush();
				fos.close();
			} catch (IOException ioe) {
				fos.close();
			}
		} catch (IOException ioe) {

		}
	}

	/**
	 * Diese Methode fügt einen neuen Highscore in die Datei "Highscore.hsc"
	 * hinzu bzw. updated sie mit dem übergebenen Parameter
	 * 
	 * @param hsc
	 */
	public void addHighscore(Highscore hsc) {
		LinkedList<Highscore> liste = new LinkedList<Highscore>();
		Highscore[] tmp;

		if (dateiHighscore.exists()) {
			tmp = leseHighscore();

			try {
				boolean flag = false;

				int counter = tmp.length;

				for (int i = 0; i < counter; i++) {
					if (tmp[i].getName().equals(hsc.getName())) {
						tmp[i].setAnzahlRunden(tmp[i].getAnzahlRunden()
								+ hsc.getAnzahlRunden());
						tmp[i].setAnzahlGewonnen(tmp[i].getAnzahlGewonnen()
								+ hsc.getAnzahlGewonnen());
						tmp[i].setAnzahlBananen(tmp[i].getAnzahlBananen()
								+ hsc.getAnzahlBananen());
						tmp[i].calculateValues();
						flag = true;
					}

					liste.add(tmp[i]);
				}

				if (!flag)
					liste.add(hsc);

				Highscore tempItem;

				int anzahl = liste.size();
				
				Highscore[] temp = liste.toArray(new Highscore[anzahl]);
				
				for (int k = 0; k < anzahl; k++) {
					for (int j = 1; j < anzahl; j++) {
						if (temp[j - 1].getProzentGewonnen() < temp[j].getProzentGewonnen()) {
							tempItem = temp[j];
							temp[j] = temp[j - 1];
							temp[j - 1] = tempItem;
						}
						if(temp[j-1].getProzentGewonnen() == temp[j].getProzentGewonnen()){
							if(temp[j-1].getTrefferquote() < temp[j].getTrefferquote()){
								tempItem = temp[j];
								temp[j] = temp[j-1];
								temp[j-1] = tempItem;
							}
						}
					}
				}
				
				
				liste = new LinkedList<Highscore>(Arrays.asList(temp));
			
				FileOutputStream fos = new FileOutputStream(dateiHighscore,
						false);

				if (liste.size() > 0) {
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(liste.removeFirst());
					oos.flush();
					oos.close();
				}

				fos.close();

				FileOutputStream nfos = new FileOutputStream(dateiHighscore,
						true);

				AppendingObjectOutputStream aoos = new AppendingObjectOutputStream(
						nfos);

				while (liste.size() > 0) {
					aoos.writeObject(liste.removeFirst());
				}

				aoos.flush();
				aoos.close();
				nfos.close();
			} catch (IOException e) {
			}
		} else {
			try {
				FileOutputStream fos = new FileOutputStream(dateiHighscore);

				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(hsc);
				oos.close();
				fos.close();
			} catch (IOException e) {
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