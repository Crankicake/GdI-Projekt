package de.tu_darmstadt.gdi1.gorillas.main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;


public class InputOutput {

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
				System.out.println("Fehler beim Ahängen des Namens: "
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

	public void speichereHighscore(String name, int highscore) {
		File datei = new File("Highscore.hcs");
		if (datei.exists()) {
			try {
				FileOutputStream bos = new FileOutputStream(datei, true);
				byte[] puffer = name.getBytes();
				bos.write(puffer);
				bos.write(highscore); // Eventuell probleme beim einlesen
				bos.close();
			} catch (IOException e) {
				System.out
						.println("Fehler beim Anhängen eines neuen Highscores: "
								+ e.toString());
			}
		} else {
			try {
				FileOutputStream bos = new FileOutputStream(datei, false);
				byte[] puffer = name.getBytes();
				bos.write(puffer);
				bos.write(highscore);
				bos.close();
			} catch (IOException e) {
				System.out.println("Fehler beim Schreiben des Highscores: "
						+ e.toString());
			}
		}
	}

	public static String[] FindeLieder(){
			File Pfad = new File("ressources/Musik/");
			String[] dateien = Pfad.list();		
			return dateien;
	}
	
	public static int anzahlLieder(){
		
		return FindeLieder().length;
	}
	
	public String FindeNamen(String n) {

		Path datei = Paths.get("Namen.bin");
		String[] segs = null;

		try {

			byte[] puffer = Files.readAllBytes(datei);
			for (int i = 0; i < puffer.length; i++) {
				puffer[i] = (byte) (puffer[i] ^ 1001001);
			}
			String str = new String(puffer, "UTF-8");
			// System.out.print(str);
			segs = str.split(Pattern.quote(";"));

			for (int i = 0; i < segs.length; i++) {
				if (segs[i].startsWith(n))
					return segs[i].toString();
			}

		}

		catch (IOException e) {
		}
		return n; // Falls der eingegebene Name nicht gefunden wird

	}
}
