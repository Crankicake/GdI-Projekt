package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.GorillasException;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import de.tu_darmstadt.gdi1.gorillas.main.Player;
import de.tu_darmstadt.gdi1.gorillas.main.Projectile;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

public class GamePlayState extends BasicTWLGameState {

	protected StateBasedEntityManager sbem;
	protected int stateID;

	protected Projectile projectile;

	protected Player playerOne;
	protected Player playerTwo;

	// Die 3 sind atm nur Platzhalter.
	public static double wind = 0;
	public static double windScale = 1;
	public static double timeScale = 0.1;

	protected Button throwButton;
	protected EditField velocityTextField;
	protected EditField angleTextField;
	protected Label velocityLabel;
	protected Label angleLabel;
	protected Label playerLabel;

	protected String oldVelocity = "";
	protected String oldAngle = "";

	protected int whichPlayersDraw;

	public GamePlayState(int sid) {
		stateID = sid;
		sbem = StateBasedEntityManager.getInstance();

		playerOne = MasterGame.getPlayerOne();
		playerTwo = MasterGame.getPlayerTwo();

		whichPlayersDraw = 1;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		initBackground();
		initBuildings();
		initProjectile();
	}

	protected void initBackground() throws SlickException {

		Entity background = new Entity("Background");
		background.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/background/background.png")));
		background.setPosition(new Vector2f(Launcher.FRAME_WIDTH / 2,
				Launcher.FRAME_HEIGHT / 2));
		background.setScale(Launcher.SCALE);
		background.setPassable(true);
		background.setRotation(0.0f);

		Entity sun = new Entity("Sun");
		sun.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/sun/sun_smiling.png")));
		sun.setPosition(new Vector2f(Launcher.FRAME_WIDTH / 2,
				Launcher.FRAME_HEIGHT / 8));
		sun.setScale(Launcher.SCALE);
		sun.setPassable(true);
		sun.setRotation(0.0f);

		sbem.addEntity(stateID, background);
		sbem.addEntity(stateID, sun);
	}

	protected void initBuildings() throws SlickException {

		// Sind die Entit√§ten zerst√∂rbar? Ich glaube es gibt da eine extra
		// destructable entity...
		// Sowas gibt es tats‰chlich, hat aber einen komischen Konstruktor, hab
		// mich damit noch nciht auseinander gesetzt.
		Entity[] buildings = new Entity[8];

		Random r = new Random();

		int indexFirstApe = r.nextInt(3);
		int indexSecondApe = r.nextInt(3) + 5;

		float buildingX, buildingY;

		for (int i = 0; i < 8; ++i) {
			buildings[i] = new Entity("Building_" + i);

			switch (r.nextInt(3)) {
			case 0:
				buildings[i].addComponent(new ImageRenderComponent(new Image(
						"/assets/gorillas/background/building_green.png")));
				break;
			case 1:
				buildings[i].addComponent(new ImageRenderComponent(new Image(
						"/assets/gorillas/background/building_red.png")));
				break;
			case 2:
				buildings[i].addComponent(new ImageRenderComponent(new Image(
						"/assets/gorillas/background/building_nazi.png")));
				break;
			}

			buildingX = (50f + 100f * i) * Launcher.FRAME_WIDTH / 800;
			buildingY = Launcher.FRAME_HEIGHT + (r.nextInt(7) - 3)
					* Launcher.FRAME_HEIGHT / 20;

			buildings[i].setPosition(new Vector2f(buildingX, buildingY));

			if (i == indexFirstApe) {
				playerOne
						.createEntity(new Vector2f(buildingX, buildingY - 321));
				sbem.addEntity(stateID, playerOne);
			} else if (i == indexSecondApe) {
				playerTwo
						.createEntity(new Vector2f(buildingX, buildingY - 321));
				sbem.addEntity(stateID, playerTwo);
			}

			buildings[i].setScale(Launcher.SCALE);
			buildings[i].setPassable(false);
			buildings[i].setRotation(0.0f);

			sbem.addEntity(stateID, buildings[i]);

		}
	}

	protected void initProjectile() throws SlickException {
		projectile = new Projectile("Banane");
		projectile.setMyPosition(playerOne.getPosition());
		projectile.createEntity();
		sbem.addEntity(stateID, projectile);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		sbem.renderEntities(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {

		sbem.updateEntities(gc, sbg, i);

		if (projectile.isFlying()) {
			try {
				projectile.updateOwn(gc, sbg, i);
			} catch (GorillasException ex) {
				throwButton.setEnabled(true);
				projectile.setMyPosition(whichPlayersDraw == 1 ? playerOne
						.getPosition() : playerTwo.getPosition());
			}
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	protected RootPane createRootPane() {

		RootPane rp = super.createRootPane();

		velocityTextField = new EditField();
		velocityTextField.setText("0");
		velocityTextField.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {
				String oldText = velocityTextField.getText();

				if (oldVelocity.equals(oldText))
					return;

				try {
					Integer number = Integer.parseInt(trimString(oldText));

					if (number < 0) {
						number = 0;
					} else if (number > 200) {
						number = 200;
					}

					oldText = number.toString();

					oldVelocity = oldText;
					velocityTextField.setText(oldText);
				} catch (NumberFormatException nfe) {

				}

			}
		});

		velocityLabel = new Label();
		velocityLabel.setText("St‰rke:");

		angleTextField = new EditField();
		angleTextField.setText("0");
		angleTextField.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {
				String oldText = angleTextField.getText();

				if (oldAngle.equals(oldText))
					return;

				Integer number = Integer.parseInt(trimString(oldText));

				if (number < 0) {
					number = 0;
				} else if (number > 360) {
					number = 360;
				}

				oldText = number.toString();

				oldAngle = oldText;
				angleTextField.setText(oldText);
			}
		});

		angleLabel = new Label();
		angleLabel.setText("Winkel: ");

		throwButton = new Button();
		throwButton.setText("Werfen");
		throwButton.addCallback(new Runnable() {
			@Override
			public void run() {

				projectile.setParamter(
						Integer.parseInt(angleTextField.getText()),
						Integer.parseInt(velocityTextField.getText()),
						9.81);

				playerLabel.setText((whichPlayersDraw == 1 ? playerTwo
						.getName() : playerOne.getName()) + ":");

				whichPlayersDraw = whichPlayersDraw == 1 ? 2 : 1;
				throwButton.setEnabled(false);
			}
		});

		playerLabel = new Label();
		playerLabel.setText(playerOne.getName() + ":");

		rp.add(velocityTextField);
		rp.add(velocityLabel);
		rp.add(angleTextField);
		rp.add(angleLabel);
		rp.add(throwButton);
		rp.add(playerLabel);
		return rp;
	}

	@Override
	protected void layoutRootPane() {
		
		velocityTextField.adjustSize();
		velocityTextField.setPosition(70, 20);

		velocityLabel.adjustSize();
		velocityLabel.setPosition(20, 23);

		angleTextField.adjustSize();
		angleTextField.setPosition(70, 55);

		angleLabel.adjustSize();
		angleLabel.setPosition(15, 58);

		throwButton.adjustSize();
		throwButton.setPosition(70, 90);
		throwButton.setSize(88, throwButton.getHeight());

		playerLabel.adjustSize();
		playerLabel.setPosition(3, 93);
	}

	protected String trimString(String s) {
		StringBuilder sb = new StringBuilder(s.length());

		for (char c : s.toCharArray()) {
			switch (c) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				sb.append(c);
				break;
			}
		}

		return sb.toString();
	}

	protected int stringToInt(String s) {

		int length = s.length();
		int number = 0;

		for (int index = 0; index < length; ++index) {

			number += (Math.pow(10, length - index) * s.charAt(index));
		}

		return number;
	}
}