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
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import de.tu_darmstadt.gdi1.gorillas.main.Player;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

public class GamePlayState extends BasicTWLGameState {

	protected StateBasedEntityManager sbem;
	protected int stateID;

	protected Player playerOne;
	protected Player spielerZwei;

	// Die 3 sind atm nur Platzhalter.
	public static double wind = 0;
	public static double windScale = 0;
	public static double timeScale = 0;

	protected Button throwButton;
	protected EditField velocityTextField;
	protected EditField angleTextField;
	protected String oldVelocity = "";
	protected String oldAngle = "";

	public GamePlayState(int sid) {
		stateID = sid;
		sbem = StateBasedEntityManager.getInstance();

		playerOne = new Player("Christoph");
		spielerZwei = new Player("Fabian");
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		initBackground();
		initBuildings();
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

		// Sind die Entitäten zerstörbar? Ich glaube es gibt da eine extra
		// destructable entity...
		// Sowas gibt es tats�chlich, hat aber einen komischen Konstruktor, hab mich damit noch nciht auseinander gesetzt.
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
				spielerZwei.createEntity(new Vector2f(buildingX,
						buildingY - 321));
				sbem.addEntity(stateID, spielerZwei);
			}

			buildings[i].setScale(Launcher.SCALE);
			buildings[i].setPassable(false);
			buildings[i].setRotation(0.0f);

			sbem.addEntity(stateID, buildings[i]);

		}
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

				if(oldVelocity.equals(oldText))
					return;

				Integer number = Integer.parseInt(trimString(oldText));

				if (number < 0) {
					number = 0;
				} else if (number > 200) {
					number = 200;
				}

				oldText = number.toString();
				
				oldVelocity = oldText;
				velocityTextField.setText(oldText);
			}
		});

		angleTextField = new EditField();
		angleTextField.setText("0");
		angleTextField.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {
				String oldText = angleTextField.getText();

				if(oldAngle.equals(oldText))
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

		throwButton = new Button();
		throwButton.setText("Wurf");
		throwButton.addCallback(new Runnable() {
			@Override
			public void run() {
				System.out.println("Click");
			}
		});

		rp.add(velocityTextField);
		rp.add(angleTextField);
		rp.add(throwButton);

		return rp;
	}

	@Override
	protected void layoutRootPane() {

		velocityTextField.adjustSize();
		velocityTextField.setPosition(20, 20);

		angleTextField.adjustSize();
		angleTextField.setPosition(20, 55);

		throwButton.adjustSize();
		throwButton.setPosition(20, 90);
		throwButton.setSize(88, throwButton.getHeight());
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