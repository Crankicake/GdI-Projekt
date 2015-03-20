package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.entity.Player;
import de.tu_darmstadt.gdi1.gorillas.entity.PlayerImageState;
import de.tu_darmstadt.gdi1.gorillas.entity.Projectile;
import de.tu_darmstadt.gdi1.gorillas.entity.Sun;
import de.tu_darmstadt.gdi1.gorillas.entity.SunMode;
import de.tu_darmstadt.gdi1.gorillas.main.Building;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.Highscore;
import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.action.Action;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.DestructibleImageEntity;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.interfaces.IDestructible;

/**
 * Die groesste Klasse, sie implementiert die Spiellogik und erbt von OwnState
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */
public class GamePlayState extends OwnState {

	// Alle Attribute, die wir so brauchen

	private Projectile projectile;
	private Player playerOne;
	private Player playerTwo;
	private Entity explosion;
	private Entity apeHit;
	private Image arrow;
	private Sun sun;

	private Button throwButton;
	private static EditField velocityTextField;
	private static EditField angleTextField;
	private Label velocityLabel;
	private Label angleLabel;
	private Label playerLabel;

	private int oldVelocity;
	private int oldAngle;

	private Vector2f arrowPosition;

	private boolean readyForHit;
	private boolean rundeEnde;

	private int whichPlayersDraw;

	private int explosionTimer;
	private int messageTimer;
	private int flyingTimer;
	private int hitTimer;

	private float buildingX, buildingY;
	private ArrayList<Vector2f> buildingCoordinates;

	private boolean displayOutOfWindowComment;
	private boolean displayBuildingHitComment;
	private boolean displayApeHitComment;

	private String buildingHitComment;
	private String apeHitComment;
	private String outOfWindowComment;

	/**
	 * Setzt alle Attribute und ruft den Konstruktor von OwnState auf
	 * 
	 * @param sid
	 */
	public GamePlayState(int sid) {
		super(sid);

		setAttributes(1);
	}

	/**
	 * ueberschreibt die Initmethode und ruft alle anderen Inits auf
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		initBackground();
		initBuildings();
		initProjectile();
		initSun();
		initWindIndicator();
		initExplosion(new Vector2f());
		initHit();

		/*
		 * ArrayList<Vector2f> bc = new ArrayList<Vector2f>(); bc.add(new
		 * Vector2f(0, 570));
		 * 
		 * Vector2f leftGorillaCoordinate = new Vector2f(50, 549); Vector2f
		 * rightGorillaCoordinate = new Vector2f(950, 549);
		 * 
		 * createMap(1000, 600, 100, bc, leftGorillaCoordinate,
		 * rightGorillaCoordinate);
		 */
	}

	/**
	 * ueberschreibt die Rendermethoden und rendert alle noetigen Sachen
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		if (!MasterGame.getDebug())
			entityManager.renderEntities(gc, sbg, g);
		else
			projectile.render(gc, sbg, g);

		if (MasterGame.getWind() != 0)
			g.drawImage(arrow, arrowPosition.x, arrowPosition.y);

		if (whichPlayersDraw == 1) {
			float x = playerOne.getPosition().x;
			float y = playerOne.getPosition().y - 30;

			g.setColor(Color.green);
			g.drawString(playerOne.getName(), x, y);
		} else {
			float x = playerTwo.getPosition().x;
			float y = playerTwo.getPosition().y - 30;

			g.setColor(Color.green);
			g.drawString(playerTwo.getName(), x, y);
		}

		g.setColor(Color.white);
		StringBuilder sb = new StringBuilder();

		sb.append(playerOne.getName()).append(": ")
				.append(playerOne.getScore())
				.append("                              ")
				.append(playerTwo.getName()).append(": ")
				.append(playerTwo.getScore());

		g.drawString(sb.toString(), windowWidth / 2 - 180, 10);

		if (displayOutOfWindowComment) {
			g.drawString(outOfWindowComment, sun.getPosition().x + 100,
					sun.getPosition().y + 50);
		}

		if (displayBuildingHitComment) {
			g.drawString(buildingHitComment, sun.getPosition().x + 100,
					sun.getPosition().y + 50);
		}

		if (displayApeHitComment) {
			g.drawString(apeHitComment, sun.getPosition().x + 100,
					sun.getPosition().y + 50);

		}

		/*
		 * render(sun.getHitbox(), g); render(playerOne.getHitbox(), g);
		 * render(playerTwo.getHitbox(), g);
		 */

	}

	/**
	 * Updated alle Entities und ruft alle anderen Updatemethoden auf
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {

		if (!MasterGame.getDebug()) {
			entityManager.updateEntities(gc, sbg, i);
		} else {
			// projectile.update(gc, sbg, i);
		}

		updateInput(gc, sbg, i);
		updateWind(gc, sbg, i);
		updateProjectile(gc, sbg, i);
		updateExplosion(gc, sbg, i);
		updateHitboxes(gc, sbg, i);
		updateMessageBoxes(gc, sbg, i);
	}

	/**
	 * @return: Der Frame mit den GUI Elementen
	 */
	@Override
	protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();

		velocityTextField = new EditField();
		velocityTextField.setText("0");
		velocityTextField.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {
				velocityTextField_TextChanged();
			}
		});

		velocityLabel = new Label();
		velocityLabel.setText("Staerke:");

		angleTextField = new EditField();
		angleTextField.setText("0");
		angleTextField.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {
				angleTextField_TextChanged();
			}
		});

		angleLabel = new Label();
		angleLabel.setText("Winkel: ");

		throwButton = new Button();
		throwButton.setText("           Werfen");
		throwButton.adjustSize();
		throwButton.addCallback(new Runnable() {
			@Override
			public void run() {
				throwButton_Click();
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

	/**
	 * 
	 * @param d
	 *            die neue Gravitation
	 */
	@Override
	protected void layoutRootPane() {

		playerLabel.adjustSize();
		playerLabel.setPosition(12, 10);

		velocityTextField.adjustSize();
		velocityTextField.setPosition(70, 40);

		velocityLabel.adjustSize();
		velocityLabel.setPosition(12, 43);

		angleTextField.adjustSize();
		angleTextField.setPosition(70, 75);

		angleLabel.adjustSize();
		angleLabel.setPosition(12, 78);

		throwButton.adjustSize();
		throwButton.setPosition(10, 110);
		throwButton.setSize(148, throwButton.getHeight());
	}

	/**
	 * Setzt alle Attribute auf den Standardwert
	 * @param whichPlayersDraw: Mansche Werte muessen angepasst werden, je nach dem, welcher Spieler dran ist
	 */
	private void setAttributes(int whichPlayersDraw) {
		playerOne = MasterGame.getPlayerOne();

		buildingCoordinates = new ArrayList<Vector2f>();

		displayOutOfWindowComment = false;
		displayBuildingHitComment = false;
		displayApeHitComment = false;

		buildingHitComment = "";
		apeHitComment = "";
		outOfWindowComment = "";

		if (whichPlayersDraw == 1) {
			playerOne.setImageState(PlayerImageState.LeftHandRised);
		} else {
			playerOne.setImageState(PlayerImageState.NoHandsForYou);
		}

		playerOne.setFormerAngle(0);
		playerOne.setFormerVelocity(0);

		playerTwo = MasterGame.getPlayerTwo();

		if (whichPlayersDraw == 1) {
			playerTwo.setImageState(PlayerImageState.NoHandsForYou);
		} else {
			playerTwo.setImageState(PlayerImageState.LeftHandRised);
		}

		playerTwo.setFormerAngle(0);
		playerTwo.setFormerVelocity(0);

		names = new String[] { "Background", "Sun", "Building_",
				"BuildingDestrucable_", "Banana" };

		oldVelocity = -1;
		oldAngle = -1;

		readyForHit = false;
		rundeEnde = false;

		this.whichPlayersDraw = whichPlayersDraw;

		explosionTimer = 350;
		messageTimer = 150;
		flyingTimer = 0;
		hitTimer = 350;

		MasterGame.setWind(new Random().nextInt(31) - 15);

		if (playerLabel != null) {
			if (whichPlayersDraw == 1) {
				playerLabel.setText(playerOne.getName() + ":");
			} else {
				playerLabel.setText(playerTwo.getName() + ":");
			}
		}
	}

	/**
	 * ueberschreibt die Methode aus OwnState und laedt ein zufaelliges Hintergrund rein
	 */
	@Override
	protected void initBackground() throws SlickException {
		Entity background = new Entity(names[0]);

		if (!MasterGame.getDebug()) {
			switch (new Random().nextInt(3)) {
			case 0:
				background.addComponent(new ImageRenderComponent(new Image(
						"/assets/gorillas/background/Skyline_Frankfurt.png")));
				break;
			case 1:
				background.addComponent(new ImageRenderComponent(new Image(
						"/assets/gorillas/background/Skyline_Sydney.png")));
				break;
			case 2:
				background.addComponent(new ImageRenderComponent(new Image(
						"/assets/gorillas/background/Skyline_New_York.png")));
				break;
			}
		}
		background.setPosition(new Vector2f(windowWidth / 2, windowHeight / 2));
		background.setScale(scale);
		background.setPassable(true);
		background.setRotation(0.0f);

		entityManager.addEntity(stateID, background);
	}

	/**
	 * Generiert eine zufaellige Skyline und erstellt daraus eine zerstoerbare Entitaet
	 * @throws SlickException
	 */
	private void initBuildings() throws SlickException {

		Random r = new Random();

		int indexFirstApe = r.nextInt(3);
		int indexSecondApe = r.nextInt(3) + 5;

		DestructibleImageEntity des;

		BufferedImage b = new BufferedImage(windowWidth, windowHeight,
				BufferedImage.TYPE_INT_ARGB);

		java.awt.Graphics g = b.getGraphics();

		for (int i = 0; i < 8; ++i) {

			float x = (50f + 100f * i) * scale;
			float y = windowHeight + (r.nextInt(7) - 3) * windowHeight / 20;

			Vector2f pos = new Vector2f(x, y);

			Vector2f p = new Vector2f(pos.x, pos.y);

			p.x -= 50f * scale;
			p.y -= 321;

			buildingCoordinates.add(p);

			try {
				g.drawImage(Building.generateBuilding(), (int) pos.x - 50,
						(int) pos.y - 300, null);
			} catch (IOException e) {
			}

			if (indexFirstApe == i) {
				playerOne.setPosition(new Vector2f(x, y - 321));
				entityManager.addEntity(getID(), playerOne);
			}

			else if (indexSecondApe == i) {
				playerTwo.setPosition(new Vector2f(x, y - 321));
				entityManager.addEntity(getID(), playerTwo);
			}
		}

		des = new DestructibleImageEntity("Des", b,
				"dropofwater/destruction.png", MasterGame.getDebug());

		des.setPosition(new Vector2f(windowWidth / 2, windowHeight / 2));

		entityManager.addEntity(getID(), des);
	}

	/**
	 * Initialisiert das Projektil und das Kollisionsevent
	 * @throws SlickException
	 */
	private void initProjectile() throws SlickException {
		projectile = new Projectile(names[4]);
		projectile.setRotation(0);
		projectile.createEntity();

		if (whichPlayersDraw == 1) {
			projectile.setPosition(playerOne.getPosition());
			playerOne.setImageState(PlayerImageState.LeftHandRised);
			playerTwo.setImageState(PlayerImageState.NoHandsForYou);
		} else {
			projectile.setPosition(playerTwo.getPosition());
			playerOne.setImageState(PlayerImageState.NoHandsForYou);
			playerTwo.setImageState(PlayerImageState.LeftHandRised);
		}

		CollisionEvent collisionEvent = new CollisionEvent();
		collisionEvent.addAction(new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				if (!readyForHit)
					return;

				CollisionEvent collider = (CollisionEvent) event;
				Entity entity = collider.getCollidedEntity();

				if (!(entity instanceof IDestructible)) {
					// return;
				}

				explosionTimer = 0;

				if (entity instanceof IDestructible) {
					IDestructible destructible = (IDestructible) entity;
					destructible.impactAt(event.getOwnerEntity().getPosition());
				}

				try {
					initExplosion(event.getOwnerEntity().getPosition());

					displayBuildingHitComment = true;
					displayApeHitComment = false;
					displayOutOfWindowComment = false;

					buildingHitComment = sun.getComment(1);

					for (Entity e : entityManager.getEntitiesByState(getID())) {
						if (!(e instanceof IDestructible)) {
							continue;
						}

						if (explosion.collides(e))
							((IDestructible) e).impactAt(event.getOwnerEntity()
									.getPosition());
					}

					initProjectile();
					setVisibility(true);
				} catch (SlickException setExpl) {

				}
			}
		});

		collisionEvent.addAction(new DestroyEntityAction());
		projectile.addComponent(collisionEvent);

		entityManager.addEntity(stateID, projectile);
	}

	/**
	 * Initialisiert die Sonne
	 * @throws SlickException
	 */
	private void initSun() throws SlickException {
		sun = new Sun(names[1]);
		if (!MasterGame.getDebug()) {
			sun.addImage(new Image("/assets/gorillas/sun/sun_smiling.png"));
			sun.addImage(new Image("/assets/gorillas/sun/sun_astonished.png"));
		}
		sun.setPosition(new Vector2f(windowWidth / 2 - 50, 5));
		sun.setScale(scale * 100f / 56f);
		sun.setPassable(true);
		sun.setRotation(0.0f);

		entityManager.addEntity(stateID, sun);
	}

	/**
	 * Initialisiert den Windpfeil
	 * @throws SlickException
	 */
	private void initWindIndicator() throws SlickException {
		if (MasterGame.getWind() < 0) {
			arrowPosition = new Vector2f(windowWidth - 30, windowHeight - 20);
			if (!MasterGame.getDebug()) {
				arrow = new Image("/assets/gorillas/arrow.png");
				arrow.rotate(180);
			}
		} else if (MasterGame.getWind() > 0) {
			arrowPosition = new Vector2f(30, windowHeight - 20);
			if (!MasterGame.getDebug()) {
				arrow = new Image("/assets/gorillas/arrow.png");
			}
		}
	}

	/**
	 * Initialisiert die Explosion an der angegebenen Position
	 * @param pos Die Position
	 * @throws SlickException
	 */
	private void initExplosion(Vector2f pos) throws SlickException {
		explosion = new Entity("Explo");
		if (!MasterGame.getDebug()) {
			explosion.addComponent(new ImageRenderComponent(new Image(
					"/assets/gorillas/explosions/explosion_1.png")));
		}

		explosion.setPosition(pos);

		explosion.setVisible(false);

		entityManager.addEntity(getID(), explosion);
	}

	/**
	 * Initialisiert den roten Rahmen
	 * @throws SlickException
	 */
	private void initHit() throws SlickException {
		apeHit = new Entity("Apehit");

		if (!MasterGame.getDebug()) {
			apeHit.addComponent(new ImageRenderComponent(new Image(
					"gorillas/gorillaHit.png")));
		}

		apeHit.setVisible(false);

		entityManager.addEntity(getID(), apeHit);

	}

	/**
	 * Updated den Input, also die Maus und Tasten
	 */
	private void updateInput(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		Input input = gc.getInput();

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			MasterGame.setIsAGameRunning(true);

			changeState(gc, sbg, Gorillas.MAINMENUSTATE);
		}

		if (input.isKeyPressed(Input.KEY_ENTER) && !projectile.isFlying()) {
			String one = velocityTextField.getText(), two = angleTextField
					.getText();

			if (one != null && two != null && !one.isEmpty() && !two.isEmpty()) {
				throwBanana();
			}
		}
	}

	/**
	 * Updated den Windpfeil, skaliert mit dem Windscale und delta
	 */
	private void updateWind(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		if (arrowPosition == null)
			arrowPosition = new Vector2f(0, 0);

		arrowPosition.x += ((float)MasterGame.getWind() * MasterGame.getWindScale() * i);
				
		if (arrowPosition.x > windowWidth + 30) {
			arrowPosition.x = 0;
		} else if (arrowPosition.x < -30) {
			arrowPosition.x = windowWidth;
		}
	}

	/**
	 * Updated das Projektil, je nach dem, ob es fliegt, werden andere Dinge gemacht
	 */
	private void updateProjectile(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		if (projectile.isFlying()) {
			if (flyingTimer < 10) {
				flyingTimer += i;
			} else {
				readyForHit = true;
			}

			try {
				projectile.updateOwn(gc, sbg, i);
			} catch (Exception ex) {
				setVisibility(true);

				flyingTimer = 0;

				displayOutOfWindowComment = true;
				displayApeHitComment = false;
				displayBuildingHitComment = false;
				outOfWindowComment = sun.getComment(0);

				readyForHit = false;

				if (whichPlayersDraw == 1) {
					projectile.setPosition(playerOne.getPosition());
					projectile.setRotation(0);
					playerOne.setImageState(PlayerImageState.LeftHandRised);
					playerTwo.setImageState(PlayerImageState.NoHandsForYou);
				} else {
					projectile.setPosition(playerTwo.getPosition());
					projectile.setRotation(0);
					playerOne.setImageState(PlayerImageState.NoHandsForYou);
					playerTwo.setImageState(PlayerImageState.LeftHandRised);
				}

			}
		} else {

			if (sun.getSunMode() != SunMode.normal) {
				sun.setSunMode(SunMode.normal);
			}
		}
	}

	/**
	 * Zeigt die Explosion 350 ms an, danach wird sie wieder ausgeblendet
	 */
	private void updateExplosion(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		if (explosionTimer >= 350) {
			explosion.setVisible(false);
		} else {
			explosionTimer += i;
			explosion.setVisible(true);
		}
	}

	/**
	 * Updated die Hitboxen und prueft, ob die Banane in denen ist
	 */
	private void updateHitboxes(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		Vector2f[] hitbox = sun.getHitbox();

		if (!projectile.isFlying())
			return;

		for (Vector2f v : hitbox) {
			if (compareVectors(v, projectile.getPosition())) {
				if (sun.getSunMode() != SunMode.astonished) {
					sun.setSunMode(SunMode.astonished);
				}
				break;
			}
		}

		if (readyForHit) {
			hitbox = playerOne.getHitbox();

			for (Vector2f v : hitbox) {
				if (compareVectors(v, projectile.getPosition())) {
					explode(playerOne, playerTwo, v);
					return;
				}
			}

			hitbox = playerTwo.getHitbox();

			for (Vector2f v : hitbox) {
				if (compareVectors(v, projectile.getPosition())) {
					explode(playerTwo, playerOne, v);
					return;
				}
			}
		}

		if (hitTimer >= 350) {
			apeHit.setVisible(false);
		} else {
			hitTimer += i;
			// apeHit.setVisible(true);
		}
	}

	/**
	 * Updated die Messageboxen, falls jemand 3 Punkte erreicht hat
	 */
	private void updateMessageBoxes(GameContainer gc, StateBasedGame sbg, int i) {

		if (messageTimer >= 350 && rundeEnde) {

			if (playerOne.getScore() == 3) {
				JOptionPane.showMessageDialog(null, playerOne.getName()
						+ " gewinnt!", "Achtung!", JOptionPane.PLAIN_MESSAGE);

				MasterGame.setIsAGameRunning(false);

				InputOutput io = new InputOutput();

				Highscore h1 = new Highscore(playerOne.getName(), getRounds(),
						playerOne.getScore(), playerOne.getTries());
				Highscore h2 = new Highscore(playerTwo.getName(), getRounds(),
						playerTwo.getScore(), playerTwo.getTries());

				io.addHighscore(h1);
				io.addHighscore(h2);

				changeState(gc, sbg, Gorillas.MAINMENUSTATE);
			} else if (playerTwo.getScore() == 3) {
				JOptionPane.showMessageDialog(null, playerTwo.getName()
						+ " gewinnt!", "Achtung!", JOptionPane.PLAIN_MESSAGE);

				InputOutput io = new InputOutput();

				Highscore h1 = new Highscore(playerOne.getName(), getRounds(),
						playerOne.getScore(), playerOne.getTries());
				Highscore h2 = new Highscore(playerTwo.getName(), getRounds(),
						playerTwo.getScore(), playerTwo.getTries());

				io.addHighscore(h1);
				io.addHighscore(h2);

				MasterGame.setIsAGameRunning(false);
				changeState(gc, sbg, Gorillas.MAINMENUSTATE);
			}

			restart(whichPlayersDraw);
		} else if (messageTimer < 350 && rundeEnde) {
			messageTimer += i;
		}
	}

	/**
	 * Simuliert einen Klick auf den Werfenbutton
	 */
	public void throwButton_Click() {
		displayBuildingHitComment = false;
		displayOutOfWindowComment = false;
		displayApeHitComment = false;

		throwBanana();
	}

	/**
	 * Das Event, wenn die StaerkeTextBox einen anderen Wert erhaelt
	 */
	public void velocityTextField_TextChanged() {
		String oldText = velocityTextField.getText();

		if ((String.valueOf(oldVelocity)).equals(oldText))
			return;

		try {
			Integer number = Integer.parseInt(trimString(oldText));

			if (number < 0) {
				number = -1;
			} else if (number > 200) {
				number = Integer.parseInt(trimString(String
						.valueOf(oldVelocity)));
			}

			oldVelocity = number;
			velocityTextField.setText(String.valueOf(oldVelocity));
		} catch (NumberFormatException nfe) {

		}
	}

	/**
	 * Das Event, wenn die WinkelTextBox einen anderen Wert erhaelt
	 */
	public void angleTextField_TextChanged() {
		String oldText = angleTextField.getText();

		if ((String.valueOf(oldAngle)).equals(oldText))
			return;

		try {
			Integer number = Integer.parseInt(trimString(oldText));

			if (number < 0) {
				number = -1;
			} else if (number > 360) {
				number = Integer.parseInt(trimString(String.valueOf(oldAngle)));
			}

			oldAngle = number;
			angleTextField.setText(String.valueOf(oldAngle));
		} catch (NumberFormatException nfe) {

		}
	}

	/**
	 * Startet das Spiel neu und gibt dem angegebenen Spieler die Banane
	 * @param whichPlayersDraw der bevorzugte Spieler
	 */
	private void restart(int whichPlayersDraw) {

		setAttributes(whichPlayersDraw);
		clearInput();

		try {
			entityManager.clearEntitiesFromState(getID());

			init(null, null);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		displayApeHitComment = true;
		apeHitComment = sun.getComment(2);
		displayBuildingHitComment = false;
		displayOutOfWindowComment = false;

	}

	/**
	 * Wird ausgeloesst, sobald die Banane explodieren soll
	 * @param victum Das 'victim', das weggebombt wird
	 * @param other Der, der den Punkt bekommt
	 * @param pos Die Position, an die die Explosion gerendert wird
	 */
	private void explode(Player victum, Player other, Vector2f pos) {

		projectile.explode();

		hitTimer = 0;
		messageTimer = 0;
		explosionTimer = 0;

		rundeEnde = true;
		readyForHit = false;

		victum.setVisible(false);
		other.increaseScore();

		explosion.setPosition(pos);
	}

	/**
	 * Methode, um die Banane zu werfen
	 */
	private void throwBanana() {
		projectile.setParameter(oldAngle, oldVelocity, whichPlayersDraw);

		playerLabel.setText((whichPlayersDraw == 1 ? playerTwo.getName()
				: playerOne.getName()) + ":");

		if (whichPlayersDraw == 1) {
			playerOne.increaseTries();
		} else {
			playerTwo.increaseTries();
		}

		whichPlayersDraw = whichPlayersDraw == 1 ? 2 : 1;

		setVisibility(false);

		saveInput();
	}

	/**
	 * Speichert den Input in den Textboxen
	 */
	private void saveInput() {
		if (whichPlayersDraw == 2) {
			playerOne
					.setFormerAngle(Integer.parseInt(angleTextField.getText()));
			playerOne.setFormerVelocity(Integer.parseInt(velocityTextField
					.getText()));

			angleTextField.setText(String.valueOf(playerTwo.getFormerAngle()));
			velocityTextField.setText(String.valueOf(playerTwo
					.getFormerVelocity()));
		} else {
			playerTwo
					.setFormerAngle(Integer.parseInt(angleTextField.getText()));
			playerTwo.setFormerVelocity(Integer.parseInt(velocityTextField
					.getText()));

			angleTextField.setText(String.valueOf(playerOne.getFormerAngle()));
			velocityTextField.setText(String.valueOf(playerOne
					.getFormerVelocity()));
		}
	}

	/**
	 * Leert die Textboxen
	 */
	private void clearInput() {
		angleTextField.setText("0");
		velocityTextField.setText("0");
	}

	/**
	 * Setzt die GUI Elemente auf b
	 * @param b ob die GUI Elemente angezeigt werden oder nicht
	 */
	private void setVisibility(boolean b) {

		throwButton.setVisible(b);
		playerLabel.setVisible(b);
		angleLabel.setVisible(b);
		angleTextField.setVisible(b);
		velocityLabel.setVisible(b);
		velocityTextField.setVisible(b);
	}

	/**
	 * Getter fuer das Projektil
	 * @return projektil
	 */
	public Projectile getProjectile() {
		return projectile;
	}

	/**
	 * Getter fuer die Runden
	 * @return Anzahlrunden
	 */
	public int getRounds() {
		return playerOne.getScore() + playerTwo.getScore();
	}

	/**
	 * Entfernt alle nicht-zahlen aus dem string s und gibt den zurueck
	 * @param s unbearbeiteter string
	 * @return nur-zahlen-string
	 */
	private String trimString(String s) {
		StringBuilder sb = new StringBuilder(s.length());

		char[] array = s.toCharArray();

		for (int i = 0; i < array.length; ++i) {
			switch (array[i]) {
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
				sb.append(array[i]);
				break;
			}
		}

		return sb.toString();
	}

	/**
	 * Vergleicht 2 Vektoren, ob sie annaehernd gleich sind
	 * @param one v1
	 * @param two v2
	 * @return gleichheit
	 */
	private boolean compareVectors(Vector2f one, Vector2f two) {
		Vector2f t1 = new Vector2f((int) one.x, (int) one.y);
		Vector2f t2 = new Vector2f((int) two.x, (int) two.y);

		return t1.equals(t2);
	}

	/**
	 * Rendert die hitboxen
	 * @param vectoren Hitbox
	 * @param g
	 */
	@SuppressWarnings("unused")
	private void render(Vector2f[] vectoren, Graphics g) {
		g.setColor(Color.red);
		for (Vector2f v : vectoren) {
			g.fillRect(v.x, v.y, 1, 1);
			g.flush();
		}
	}

	/**
	 * Simuliert einen Tastendruck in der VelocityTextbox
	 * @param newChar neues Zeichen
	 */
	public void addCharToVelocity(char newChar) {
		String newString = "-1";

		try {
			newString = trimString(oldVelocity + String.valueOf(newChar));
		} catch (NumberFormatException nfe) {
			return;
		}

		if (oldVelocity == -1) {
			newString = String.valueOf(newChar);
		}

		try {
			Integer number = Integer.parseInt(newString);
			if (number < 0) {
				number = -1;
				return;
			}

			if (number > 200) {
				number = oldVelocity;
			}

			oldVelocity = number;
		} catch (NumberFormatException nfe) {

		}

	}

	/**
	 * Simuliert einen Tastendruck in der AngleTextbox
	 * @param newChar neues Zeichen
	 */
	public void addCharToAngle(char newChar) {
		String newString = "-1";

		try {
			newString = trimString(oldAngle + String.valueOf(newChar));
		} catch (NumberFormatException nfe) {
			return;
		}

		if (oldAngle == -1) {
			newString = String.valueOf(newChar);
		}

		try {
			Integer number = Integer.parseInt(newString);

			if (number < 0) {
				number = -1;
				return;
			}

			if (number > 360) {
				number = oldAngle;
			}

			oldAngle = number;
		} catch (NumberFormatException nfe) {

		}
	}

	/**
	 * Fuegt den text in die velocitytextbox mit ein
	 * @param value
	 */
	public void setVelocity(String value) {
		velocityTextField.setText(velocityTextField.getText() + value);
	}

	/**
	 * Fuegt den text in die AngleTextBox mit ein
	 * @param value
	 */
	public void setAngle(String value) {
		angleTextField.setText(angleTextField.getText() + value);
	}

	/**
	 * Leert den input und die gespeicherten werte
	 */
	public void clearFields() {
		oldAngle = -1;
		oldVelocity = -1;

		velocityTextField.setText("0");
		angleTextField.setText("0");
	}

	/**
	 * Gibt die staerke zurueck
	 * @return
	 */
	public String getVelocity() {
		return String.valueOf(oldVelocity);
	}

	/**
	 * gibt den winkel zurueck
	 * @return
	 */
	public String getAngle() {
		return String.valueOf(oldAngle);
	}

	/**
	 * Gibt die Bananenposition zurueck
	 * @return
	 */
	public Vector2f getNextBananaPosition() {
		return projectile.getPosition();
	}

	/**
	 * Getter fuer die Sonne
	 * @return
	 */
	public Sun getSun() {
		return sun;
	}

	/**
	 * Getter fuer die gabaeudekoordinaten
	 * @return
	 */
	public ArrayList<Vector2f> getbuildingCoordinates() {
		return buildingCoordinates;
	}

	/**
	 * gibt die windowbreite zurueck
	 * @return
	 */
	public float getWindowWidth() {
		return windowWidth;
	}

	/**
	 * gibt die windowhoehe zurueck
	 * @return
	 */
	public float getWindowHeight() {
		return windowHeight;
	}

	/**
	 * gibt zurueck, welcher spieler dran ist
	 * @return
	 */
	public int getWhichPlayersDraw() {
		return whichPlayersDraw;
	}

	/**
	 * Erstellt eine Map mit den Parametern:
	 * @param paneWidth breite vom fenster
	 * @param paneHeight hoehe vom fenster
	 * @param yOffsetCity offset
	 * @param buildingCoordinates alle koordinaten der gebaeude
	 * @param leftGorillaCoordinate position des linken affen
	 * @param rightGorillaCoordinate position der rechten affen
	 */
	public void createMap(int paneWidth, int paneHeight, int yOffsetCity,
			ArrayList<Vector2f> buildingCoordinates,
			Vector2f leftGorillaCoordinate, Vector2f rightGorillaCoordinate) {
		entityManager.clearEntitiesFromState(getID());

		windowHeight = paneHeight;
		windowWidth = paneWidth;

		MasterGame.setWind(0);

		try {
			initBackground();
			initProjectile();
			initExplosion(new Vector2f());
			initWindIndicator();
			initSun();
		} catch (SlickException e) {
			e.printStackTrace();
		}

		DestructibleImageEntity[] buildings = new DestructibleImageEntity[buildingCoordinates
				.size()];

		for (int i = 0; i < buildingCoordinates.size(); ++i) {
			buildings[i] = generateBuildingEntity(i,
					buildingCoordinates.get(i).x + 50,
					buildingCoordinates.get(i).y);
			entityManager.addEntity(getID(), buildings[i]);
		}

		playerOne.setPosition(leftGorillaCoordinate);
		playerTwo.setPosition(rightGorillaCoordinate);

		entityManager.addEntity(getID(), playerOne);
		entityManager.addEntity(getID(), playerTwo);

		if (whichPlayersDraw == 1)
			projectile.setPosition(playerOne.getPosition());
		else
			projectile.setPosition(playerTwo.getPosition());
	}

	/**
	 * Gibt eine DIE zurueck, die f breit und y hoch ist.
	 * @param i das i. gebaeude
	 * @param f
	 * @param y
	 * @return
	 */
	private DestructibleImageEntity generateBuildingEntity(int i, float f,
			float y) {

		DestructibleImageEntity building = null;

		try {
			building = new DestructibleImageEntity(names[3] + i,
					Building.generateBuilding(), "dropofwater/destruction.png",
					MasterGame.getDebug());
		} catch (IOException e) {
			e.printStackTrace();
		}

		buildingX = f;
		buildingY = y;

		Vector2f pos = new Vector2f(buildingX, buildingY);

		building.setPosition(pos);

		Vector2f p = new Vector2f(pos.x, pos.y);

		p.x -= 50f * scale;
		p.y -= 321;

		buildingCoordinates.add(i, p);

		building.setScale(scale);
		building.setPassable(false);
		building.setRotation(0.0f);

		return building;
	}
}