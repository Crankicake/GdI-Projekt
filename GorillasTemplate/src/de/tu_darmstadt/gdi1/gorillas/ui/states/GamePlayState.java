package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

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
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.GorillasException;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import de.tu_darmstadt.gdi1.gorillas.main.Player;
import de.tu_darmstadt.gdi1.gorillas.main.PlayerImageState;
import de.tu_darmstadt.gdi1.gorillas.main.Projectile;
import eea.engine.action.Action;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.DestructibleImageEntity;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.interfaces.IDestructible;

public class GamePlayState extends OwnState {

	protected static Projectile projectile;
	protected Player playerOne;
	protected Player playerTwo;

	protected Button throwButton;
	protected EditField velocityTextField;
	protected EditField angleTextField;
	protected Label velocityLabel;
	protected Label angleLabel;
	protected Label playerLabel;

	protected String oldVelocity = "";
	protected String oldAngle = "";

	private int whichPlayersDraw;
	public static double wind = 0;
	public static double windScale = 0.2;
	public static double timeScale = 0.01;

	private Image arrow;
	private Vector2f arrowPosition;
	
	public GamePlayState(int sid) {
		super(sid);

		playerOne = MasterGame.getPlayerOne();
		playerTwo = MasterGame.getPlayerTwo();

		whichPlayersDraw = 1;
		wind = new Random().nextInt(31) - 15;

		System.out.println(wind);

		playerOne.setImageState(PlayerImageState.LeftHandRised);

		names = new String[] { "Background", "Sun", "Building_",
				"BuildingDestrucable_", "Banana"

		};
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		initBackground();
		initBuildings();
		initProjectile();
		initWindIndicator();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		entityManager.renderEntities(gc, sbg, g);

		if (wind != 0)
			g.drawImage(arrow, arrowPosition.x, arrowPosition.y);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		Input input = gc.getInput();

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			MasterGame.setIsAGameRunning(true);

			changeState(gc, sbg, Gorillas.MAINMENUSTATE);
		}
		
		if(input.isKeyPressed(Input.KEY_ENTER)) {
			String one = velocityTextField.getText(), two = angleTextField.getText();
			
			if(one != null && two != null && !one.isEmpty() && !two.isEmpty()) {
				throwButton_Click();
			}
		}	

		if (arrowPosition == null)
			arrowPosition = new Vector2f(0, 0);

		arrowPosition.x += wind;

		if (arrowPosition.x > windowWidth + 30) {
			arrowPosition.x = 0;
		} else if (arrowPosition.x < -30) {
			arrowPosition.x = windowWidth;
		}

		if (projectile.isFlying()) {
			try {
				projectile.updateOwn(gc, sbg, i);
			} catch (GorillasException ex) {
				setVisibility(true);

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
		}

		entityManager.updateEntities(gc, sbg, i);
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
		throwButton.setText("Werfen");
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
		velocityTextField.adjustSize();
		velocityTextField.setPosition(70, 20);

		velocityLabel.adjustSize();
		velocityLabel.setPosition(12, 23);

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

	@Override
	protected void initBackground() throws SlickException {

		Entity background = new Entity(names[0]);
		background.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/background/background.png")));
		background.setPosition(new Vector2f(Launcher.FRAME_WIDTH / 2,
				Launcher.FRAME_HEIGHT / 2));
		background.setScale(Launcher.SCALE);
		background.setPassable(true);
		background.setRotation(0.0f);

		Entity sun = new Entity(names[1]);
		sun.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/sun/sun_smiling.png")));
		sun.setPosition(new Vector2f(Launcher.FRAME_WIDTH / 2,
				Launcher.FRAME_HEIGHT / 8));
		sun.setScale(Launcher.SCALE);
		sun.setPassable(true);
		sun.setRotation(0.0f);

		entityManager.addEntity(stateID, background);
		entityManager.addEntity(stateID, sun);
	}

	protected void initBuildings() throws SlickException {

		// Sind die Entitäten zerstörbar? Ich glaube es gibt da eine extra
		// destructable entity...
		// Sowas gibt es tatsaechlich, hat aber einen komischen Konstruktor, hab
		// mich damit noch nciht auseinander gesetzt.

		DestructibleImageEntity[] buildings = new DestructibleImageEntity[8];
		
		// Graphics2D[] theArry = new Graphics2D[8];

		Random r = new Random();

		int indexFirstApe = r.nextInt(3);
		int indexSecondApe = r.nextInt(3) + 5;

		float buildingX, buildingY;

		for (int i = 0; i < 8; ++i) {
			// ImageRenderComponent image = new ImageRenderComponent(new Image(
			// "/assets/gorillas/background/building_green.png"));

			// DestructibleImageEntity imd = new

			// DestructibleImageEntity en = new
			// DestructibleImageEntity("Building" + i, image,
			// "/assets/gorillas/expolosions/explosion_1.png", false);

			// DestructibleImageEntity es = new DestructibleImageEntity
			// ("Building" + i,"/assets/gorillas/background/building_green.png",
			// "gorillas/expolosions/explosion_1.png",false);

			BufferedImage img = new BufferedImage(100, 600,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D blueHouse = img.createGraphics();
			blueHouse.setColor(new Color(23, 100, 201));
			blueHouse.fillRect(0, 0, 100, 600);

			BufferedImage img2 = new BufferedImage(100, 600,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D greenHouse = img2.createGraphics();
			greenHouse.setColor(new Color(15, 252, 165));
			greenHouse.fillRect(0, 0, 100, 600);

			BufferedImage img3 = new BufferedImage(100, 600,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D redHouse = img3.createGraphics();
			redHouse.setColor(new Color(239, 60, 60));
			redHouse.fillRect(0, 0, 100, 600);

			/**
			 * 
			 * Nuetzliche Links:
			 * http://www.rapidtables.com/web/color/RGB_Color.html
			 * http://www.dpunkt
			 * .de/java/Programmieren_mit_Java/Grafikprogrammierung/5.htm
			 * https:/
			 * /moodle.informatik.tu-darmstadt.de/mod/forum/discuss.php?d=34033
			 */

			// graphic.drawImage(img, x, y, null);

			switch (r.nextInt(3)) {
			case 0:
				buildings[i] = new DestructibleImageEntity(names[3] + i, img,
						"dropofwater/destruction.png", false);
				break;
			case 1:
				buildings[i] = new DestructibleImageEntity(names[3] + i, img2,
						"dropofwater/destruction.png", false);
				break;
			case 2:
				buildings[i] = new DestructibleImageEntity(names[3] + i, img3,
						"dropofwater/destruction.png", false);
				break;
			}

			buildingX = (50f + 100f * i) * windowWidth / 800;
			buildingY = windowHeight + (r.nextInt(7) - 3) * windowHeight / 20;

			buildings[i].setPosition(new Vector2f(buildingX, buildingY));

			if (i == indexFirstApe) {
				playerOne
						.createEntity(new Vector2f(buildingX, buildingY - 321));
				entityManager.addEntity(stateID, playerOne);
			} else if (i == indexSecondApe) {
				playerTwo
						.createEntity(new Vector2f(buildingX, buildingY - 321));
				entityManager.addEntity(stateID, playerTwo);
			}

			buildings[i].setScale(Launcher.SCALE);
			buildings[i].setPassable(false);
			buildings[i].setRotation(0.0f);

			entityManager.addEntity(stateID, buildings[i]);

			// sbem.addEntity(stateID, theArry[i]);
		}
	}

	protected void initProjectile() throws SlickException {
		projectile = new Projectile(names[4]);
		projectile.setPosition(playerOne.getPosition());
		projectile.createEntity();

		CollisionEvent collisionEvent = new CollisionEvent();
		collisionEvent.addAction(new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				CollisionEvent collider = (CollisionEvent) event;
				Entity entity = collider.getCollidedEntity();

				IDestructible destructible = null;
				if (entity instanceof IDestructible) {
					destructible = (IDestructible) entity;
				} else {
					return;
				}

				destructible.impactAt(event.getOwnerEntity().getPosition());
			}
		});
		
		collisionEvent.addAction(new DestroyEntityAction());
		projectile.addComponent(collisionEvent);

		entityManager.addEntity(stateID, projectile);
	}

	private void initWindIndicator() throws SlickException {
		if (wind < 0) {
			arrowPosition = new Vector2f(windowWidth - 30, windowHeight - 20);

			arrow = new Image("/assets/gorillas/arrow.png");

			arrow.rotate(180);
		} else if (wind > 0) {
			arrowPosition = new Vector2f(30, windowHeight - 20);

			arrow = new Image("/assets/gorillas/arrow.png");
		}
	}

	public void throwButton_Click() {
		projectile.setParameter(Integer.parseInt(angleTextField.getText()),
				Integer.parseInt(velocityTextField.getText()), MasterGame.getGravitation(),
				whichPlayersDraw);

		playerLabel.setText((whichPlayersDraw == 1 ? playerTwo.getName()
				: playerOne.getName()) + ":");

		whichPlayersDraw = whichPlayersDraw == 1 ? 2 : 1;
		setVisibility(false);
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
}