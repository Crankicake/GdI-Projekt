package de.tu_darmstadt.gdi1.gorillas.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Diese nur statische Klasse generiert zufaellig Gebaeude
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */

public class Building {

	/**
	 * @return The random picture
	 * @throws IOException
	 *             : Thrown, if the Images can't be loaded
	 */
	public static BufferedImage generateBuilding() throws IOException {

		Random random = new Random();

		int mode = random.nextInt(2);

		// Die Grundgrafik, auf die die spaeteren Bilder gezeichnet werden
		BufferedImage combined = new BufferedImage(100, 600,
				BufferedImage.TYPE_INT_ARGB);

		Graphics g = combined.getGraphics();

		BufferedImage baseBuilding;
		BufferedImage window1;
		BufferedImage window2;

		if (mode == 0) {
			baseBuilding = ImageIO.read(new File(
					"assets/gorillas/buildings/building_brickstone.png"));
			window1 = ImageIO.read(new File(
					"assets/gorillas/buildings/window1_brickstone.png"));
			window2 = ImageIO.read(new File(
					"assets/gorillas/buildings/window2_brickstone.png"));
		} else {
			baseBuilding = ImageIO.read(new File(
					"assets/gorillas/buildings/building_modern.png"));
			window1 = ImageIO.read(new File(
					"assets/gorillas/buildings/window1_modern.png"));
			window2 = ImageIO.read(new File(
					"assets/gorillas/buildings/window2_modern.png"));
		}

		g.drawImage(baseBuilding, 0, 0, null);

		int maxY = mode == 0 ? 12 : 12;

		// Algorithmus zum Zusammenbasteln:
		// Ein zufaelliges Fenster wird ausgewaehlt & an die ensprechende Position
		// gesetzt
		for (int y = 0; y < maxY; ++y) {
			if (mode == 0) {

				if (random.nextInt(2) == 0) {
					g.drawImage(window1, 6, 26 + y * 52, null);
				} else {
					g.drawImage(window2, 6, 26 + y * 52, null);
				}

				if (random.nextInt(2) == 0) {
					g.drawImage(window1, 37, 26 + y * 52, null);
				} else {
					g.drawImage(window2, 37, 26 + y * 52, null);
				}

				if (random.nextInt(2) == 0) {
					g.drawImage(window1, 68, 26 + y * 52, null);
				} else {
					g.drawImage(window2, 68, 26 + y * 52, null);
				}
			} else {

				if (random.nextInt(2) == 0) {
					g.drawImage(window1, 6, 26 + y * 52, null);
				} else {
					g.drawImage(window2, 6, 26 + y * 52, null);
				}

				if (random.nextInt(2) == 0) {
					g.drawImage(window1, 56, 26 + y * 52, null);
				} else {
					g.drawImage(window2, 56, 26 + y * 52, null);
				}
			}
		}

		return combined;
	}
}