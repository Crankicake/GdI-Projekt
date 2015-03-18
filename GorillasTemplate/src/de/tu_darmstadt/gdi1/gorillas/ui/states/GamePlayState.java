package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.io.IOException;
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
import de.tu_darmstadt.gdi1.gorillas.main.Building;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.GorillasException;
import de.tu_darmstadt.gdi1.gorillas.main.Highscore;
import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import de.tu_darmstadt.gdi1.gorillas.main.Player;
import de.tu_darmstadt.gdi1.gorillas.main.PlayerImageState;
import de.tu_darmstadt.gdi1.gorillas.main.Projectile;
import de.tu_darmstadt.gdi1.gorillas.main.Sun;
import de.tu_darmstadt.gdi1.gorillas.main.SunMode;
import eea.engine.action.Action;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.DestructibleImageEntity;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.interfaces.IDestructible;

public class GamePlayState extends OwnState {

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

	private String oldVelocity;
	private String oldAngle;

	private Vector2f arrowPosition;

	private boolean readyForHit;
	private boolean rundeEnde;

	private int whichPlayersDraw;

	private int explosionTimer;
	private int messageTimer;
	private int flyingTimer;
	private int hitTimer;

	public GamePlayState(int sid) {
		super(sid);

		setAttributes();
	}

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
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		entityManager.renderEntities(gc, sbg, g);

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
				.append(playerOne.getScore()).append("          ")
				.append(playerTwo.getName()).append(": ")
				.append(playerTwo.getScore());

		g.drawString(sb.toString(), windowWidth / 2 - 130, 10);

		/*
		 * render(sun.getHitbox(), g); render(playerOne.getHitbox(), g);
		 * render(playerTwo.getHitbox(), g);
		 */
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {

		entityManager.updateEntities(gc, sbg, i);

		updateInput(gc, sbg, i);
		updateWind(gc, sbg, i);
		updateProjectile(gc, sbg, i);
		updateExplosion(gc, sbg, i);
		updateHitboxes(gc, sbg, i);
		updateMessageBoxes(gc, sbg, i);
	}

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

	private void setAttributes() {
		playerOne = MasterGame.getPlayerOne();
		playerOne.setImageState(PlayerImageState.LeftHandRised);
		playerOne.setFormerAngle(0);
		playerOne.setFormerVelocity(0);

		playerTwo = MasterGame.getPlayerTwo();
		playerTwo.setImageState(PlayerImageState.NoHandsForYou);
		playerTwo.setFormerAngle(0);
		playerTwo.setFormerVelocity(0);

		names = new String[] { "Background", "Sun", "Building_",
				"BuildingDestrucable_", "Banana" };

		oldVelocity = "";
		oldAngle = "";

		readyForHit = false;
		rundeEnde = false;

		whichPlayersDraw = 1;

		explosionTimer = 150;
		messageTimer = 150;
		flyingTimer = 0;
		hitTimer = 350;

		MasterGame.setWind(new Random().nextInt(31) - 15);

		if (playerLabel != null)
			playerLabel.setText(playerOne.getName() + ":");
	}

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

	private void initBuildings() throws SlickException {

		DestructibleImageEntity[] buildings = new DestructibleImageEntity[8];

		Random r = new Random();

		int indexFirstApe = r.nextInt(3);
		int indexSecondApe = r.nextInt(3) + 5;

		float buildingX, buildingY;

		for (int i = 0; i < 8; ++i) {

			try {
				buildings[i] = new DestructibleImageEntity(names[3] + i,
						Building.generateBuilding(),
						"dropofwater/destruction.png", MasterGame.getDebug());
			} catch (IOException e) {
				e.printStackTrace();
			}

			buildingX = (50f + 100f * i) * windowWidth / 800;
			buildingY = windowHeight + (r.nextInt(7) - 3) * windowHeight / 20;

			buildings[i].setPosition(new Vector2f(buildingX, buildingY));

			if (indexFirstApe == i) {

				playerOne.setPosition(new Vector2f(buildingX, buildingY - 321));

				entityManager.addEntity(stateID, playerOne);
			}

			else if (indexSecondApe == i) {
				playerTwo.setPosition(new Vector2f(buildingX, buildingY - 321));

				entityManager.addEntity(stateID, playerTwo);
			}

			buildings[i].setScale(scale);
			buildings[i].setPassable(false);
			buildings[i].setRotation(0.0f);

			entityManager.addEntity(stateID, buildings[i]);
		}
	}

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
				CollisionEvent collider = (CollisionEvent) event;
				Entity entity = collider.getCollidedEntity();

				if (!(entity instanceof IDestructible)) {
					// return;
				}

				explosionTimer = 0;

				if (entity instanceof IDestructible) {
					IDestructible destructible = (IDestructible) entity;
					destructible.impactAt(event.getOwnerEntity().getPosition());

					destructible.impactAt(event.getOwnerEntity().getPosition());
				}

				try {
					initExplosion(event.getOwnerEntity().getPosition());

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

	private void initSun() throws SlickException {
		sun = new Sun(names[1], null);
		if (!MasterGame.getDebug()) {
			sun.addImage(new Image("/assets/gorillas/sun/sun_smiling.png"));
			sun.addImage(new Image("/assets/gorillas/sun/sun_astonished.png"));
		}
		sun.setPosition(new Vector2f(windowWidth / 2, windowHeight / 8));
		sun.setScale(scale);
		sun.setPassable(true);
		sun.setRotation(0.0f);

		entityManager.addEntity(stateID, sun);
	}

	private void initWindIndicator() throws SlickException {
		if (MasterGame.getWind() < 0) {
			arrowPosition = new Vector2f(windowWidth - 30, windowHeight - 20);
			if (!MasterGame.getDebug()) {
				arrow = new Image("/assets/gorillas/arrow.png");
			}
			arrow.rotate(180);
		} else if (MasterGame.getWind() > 0) {
			arrowPosition = new Vector2f(30, windowHeight - 20);
			if (!MasterGame.getDebug()) {
				arrow = new Image("/assets/gorillas/arrow.png");
			}
		}
	}

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

	private void initHit() throws SlickException {
		apeHit = new Entity("Apehit");

		if (!MasterGame.getDebug()) {
			apeHit.addComponent(new ImageRenderComponent(new Image(
					"gorillas/gorillaHit.png")));
		}
		
		apeHit.setVisible(false);

		entityManager.addEntity(getID(), apeHit);
	}

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

	private void updateWind(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		if (arrowPosition == null)
			arrowPosition = new Vector2f(0, 0);

		arrowPosition.x += MasterGame.getWind();

		if (arrowPosition.x > windowWidth + 30) {
			arrowPosition.x = 0;
		} else if (arrowPosition.x < -30) {
			arrowPosition.x = windowWidth;
		}
	}

	private void updateProjectile(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		if (projectile.isFlying()) {
			if (flyingTimer < 500) {
				flyingTimer += i;
			} else {
				readyForHit = true;
			}

			try {
				projectile.updateOwn(gc, sbg, i);
			} catch (GorillasException ex) {
				setVisibility(true);

				flyingTimer = 0;

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
				System.out.println("Normal");
			}
		}
	}

	private void updateExplosion(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		if (explosionTimer >= 150) {
			explosion.setVisible(false);
		} else {
			explosionTimer += i;
			explosion.setVisible(true);
		}
	}

	private void updateHitboxes(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {

		/*
		 * Vector2f posPro = projectile.getPosition();
		 * 
		 * if (Arrays.asList(sun.getHitbox()).contains(posPro)) { if
		 * (sun.getSunMode() != SunMode.astonished) {
		 * sun.setSunMode(SunMode.astonished); System.out.println("Astonished");
		 * } }
		 * 
		 * if (readyForHit) { if
		 * (Arrays.asList(playerOne.getHitbox()).contains(posPro)) {
		 * explode(playerOne, playerTwo, posPro); return; }
		 * 
		 * if (Arrays.asList(playerOne.getHitbox()).contains(posPro)) {
		 * explode(playerTwo, playerOne, posPro); return; }
		 * 
		 * }
		 */

		for (Vector2f v : sun.getHitbox()) {
			if (compareVectors(v, projectile.getPosition())) {
				if (sun.getSunMode() != SunMode.astonished) {
					sun.setSunMode(SunMode.astonished);
					System.out.println("Astonished");
				}
				break;
			}
		}

		if (readyForHit) {
			for (Vector2f v : playerOne.getHitbox()) {
				if (compareVectors(v, projectile.getPosition())) {
					explode(playerOne, playerTwo, v);
					return;
				}
			}

			for (Vector2f v : playerTwo.getHitbox()) {
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

			restart();
		} else if (messageTimer < 350 && rundeEnde) {
			messageTimer += i;
		}
	}

	public void throwButton_Click() {
		throwBanana();
	}

	public void velocityTextField_TextChanged() {
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

	public void angleTextField_TextChanged() {
		String oldText = angleTextField.getText();

		if (oldAngle.equals(oldText))
			return;

		try {
			Integer number = Integer.parseInt(trimString(oldText));

			if (number < 0) {
				number = 0;
			} else if (number > 360) {
				number = 360;
			}

			oldText = number.toString();

			oldAngle = oldText;
			angleTextField.setText(oldText);
		} catch (NumberFormatException nfe) {

		}
	}

	private void restart() {
		setAttributes();
		clearInput();

		try {
			entityManager.clearEntitiesFromState(getID());

			init(null, null);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void explode(Player victum, Player other, Vector2f pos) {

		projectile.explode();

		victum.setVisible(false);
		other.increaseScore();

		hitTimer = 0;
		messageTimer = 0;
		explosionTimer = 0;

		rundeEnde = true;
		readyForHit = false;
		explosion.setPosition(pos);
	}

	private void throwBanana() {
		projectile.setParameter(Integer.parseInt(angleTextField.getText()),
				Integer.parseInt(velocityTextField.getText()),
				MasterGame.getGravitation(), whichPlayersDraw);

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

	private void clearInput() {
		angleTextField.setText("0");
		velocityTextField.setText("0");
	}

	private void setVisibility(boolean b) {

		throwButton.setVisible(b);
		playerLabel.setVisible(b);
		angleLabel.setVisible(b);
		angleTextField.setVisible(b);
		velocityLabel.setVisible(b);
		velocityTextField.setVisible(b);
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public int getRounds() {
		return playerOne.getScore() + playerTwo.getScore();
	}

	private String trimString(String s) {
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

	private boolean compareVectors(Vector2f one, Vector2f two) {
		return one.equals(two);
	}

	@SuppressWarnings("unused")
	private void render(Vector2f[] vectoren, Graphics g) {
		g.setColor(Color.red);
		for (Vector2f v : vectoren) {
			g.fillRect(v.x, v.y, 1, 1);
			g.flush();
		}
	}

	public void setVelocity(String value) {
		velocityTextField.setText(velocityTextField.getText() + value);
	}

	public void setAngle(String value) {
		angleTextField.setText(angleTextField.getText() + value);
	}

	public void clearFields() {
		velocityTextField.setText("-1");
		angleTextField.setText("-1");
	}

	public Vector2f getNextBananaPosition() {
		return projectile.nextPosition();
	}
}