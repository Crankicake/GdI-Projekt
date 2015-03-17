package de.tu_darmstadt.gdi1.gorillas.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Building{

	private static BufferedImage baseBuilding;
	private static BufferedImage window1;
	private static BufferedImage window2;
	private static Random random;

	static {
		random = new Random();
	}

	public static BufferedImage generateBuilding() throws IOException {

		int mode = random.nextInt(2);

		BufferedImage combined = new BufferedImage(100, 600,
				BufferedImage.TYPE_INT_ARGB);

		Graphics g = combined.getGraphics();

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
