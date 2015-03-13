package de.tu_darmstadt.gdi1.gorillas.ui.states;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;





import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;
import de.tu_darmstadt.gdi1.gorillas.main.Jukeboxibox;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class GameSetupState extends OwnState {

	private EditField playername1Textbox;
	private EditField playername2Textbox;
	private Label playername1Label;
	private Label playername2Label;
	
	@SuppressWarnings("rawtypes")
	private JComboBox liedbox;
	private JFrame frame;

	private Jukeboxibox jukebox = Jukeboxibox.getInstanz();
	private InputOutput io;

	
	private String errormessage;

	public GameSetupState(int sid) {
		super(sid);

		io = new InputOutput();
		errormessage = "";
		names = new String[] { "Zurueck", "Spiel starten" };
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		initBackground();
		initEntities();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);

		g.drawString(names[0], 85, 66);
		g.drawString("Spiel starten", windowWidth / 2 - 35,
				windowHeight / 2 + 36);
		g.drawString(errormessage, 220, 500);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

		Input input = container.getInput();

		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			mouseLeftButton_Click(container, game, input.getMouseX(),
					input.getMouseY());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();

		playername1Textbox = new EditField();
		playername1Textbox.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {
				playername1Textbox_TextChanged();
			}

		});

		playername2Textbox = new EditField();
		playername2Textbox.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {
				playername2Textbox_TextChanged();
			}
		});
		
		
		
		playername1Label = new Label();
		playername1Label.setText("Name von Spieler 1:");

		playername2Label = new Label();
		playername2Label.setText("Name von Spieler 2:");

		
		
		rp.add(playername1Textbox);
		rp.add(playername2Textbox);
		rp.add(playername1Label);
		rp.add(playername2Label);
		
		setPlayername1TextboxText("Player 1");
		setPlayername2TextboxText("Player 2");

		// Jukeboxpart
		if(!MasterGame.isJukeboxRunning()){
		
		liedbox = new JComboBox(InputOutput.FindeLieder());
		frame = new JFrame("Jukebox 600 XS LIMITED EDITION");
		frame.setSize(370, 300);
		frame.setBounds(300,450,370,100);
		liedbox.setBounds(150,200,200,100);
		liedbox.setVisible(true);
		liedbox.setEditable(false);
		liedbox.setSelectedIndex(0);
		frame.add(liedbox);
		frame.setVisible(true);
		frame.setAlwaysOnTop(false);
		MasterGame.setIsJukeboxRunning(true);
		}
		
		// Jukeboxpart ende		
		
		
		
		
		return rp;
	}

	@Override
	protected void layoutRootPane() {
		super.layoutRootPane();

		int width = windowWidth / 2;
		int height = windowHeight / 2;

	
		
		playername1Label.adjustSize();
		playername1Label.setPosition(width - 150, height - 100);

		playername1Textbox.adjustSize();
		playername1Textbox.setSize(130, 20);
		playername1Textbox.setPosition(width, height - 100);

		playername2Label.adjustSize();
		playername2Label.setPosition(width - 150, height - 50);

		playername2Textbox.adjustSize();
		playername2Textbox.setSize(130, 20);
		playername2Textbox.setPosition(width, height - 50);
		
		
	}

	protected void initEntities() throws SlickException {
		Entity newGameEntity = createMenuEntity(names[1], new Vector2f(
				windowWidth / 2, windowHeight / 2 + 50));

		Entity zurueckEntity = createMenuEntity(names[0], new Vector2f(120, 80));

		Event zurueckEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		
		
		ChangeStateInitAction zurueckAction = new ChangeStateInitAction(
				Gorillas.MAINMENUSTATE);
		zurueckEvent.addAction(zurueckAction);
		zurueckEntity.addComponent(zurueckEvent);

		entityManager.addEntity(getID(), newGameEntity);
		entityManager.addEntity(getID(), zurueckEntity);
	}

	public void playername1Textbox_TextChanged() {
		

		String name1, name2;

		name1 = playername1Textbox.getText();
		name2 = playername2Textbox.getText();

		if (name1 != null && name2 != null) {
			if (!name1.isEmpty() && !name2.isEmpty()) {

				if (!name1.equals(name2)) {
					setPlayerOneName(name1);
					setPlayerTwoName(name2);
				}
			}
		}
	}

	public void playername2Textbox_TextChanged() {


		String name1, name2;

		name1 = playername1Textbox.getText();
		name2 = playername2Textbox.getText();

		if (name1 != null && name2 != null) {
			if (!name1.isEmpty() && !name2.isEmpty()) {

				if (!name1.equals(name2)) {
					setPlayerOneName(name1);
					setPlayerTwoName(name2);
				}
			}
		}
	}

	public void mouseLeftButton_Click(GameContainer gc, StateBasedGame sbg,
			int x, int y) throws SlickException {
		Image i = getMenuEntryImage();

		double width = i.getWidth() * 0.35;
		double height = i.getHeight() * 0.35;

		double ix = windowWidth / 2 - width / 2;
		double iy = windowHeight / 2 + 50 - height / 2;

		if (x >= ix && x <= ix + width) {
			if (y >= iy && y <= iy + height) {

				String name1 = playername1Textbox.getText();
				String name2 = playername2Textbox.getText();

				if (name1 == null || name2 == null || name1.isEmpty()
						|| name2.isEmpty()) {
					errormessage = "Die Spielernamen duerfen nicht leer sein!";
					return;
				}

				if (name1.equals(name2)) {
					errormessage = "Bitte unterschiedliche Spielernamen eingeben!";
					return;
				}

				errormessage = "";

				
				changeState(gc, sbg, Gorillas.GAMEPLAYSTATE);
			
				jukebox.spiele(liedbox.getSelectedItem().toString());}}
			
			
		
	}

	public void setPlayername1TextboxText(String text) {
		playername1Textbox.setText(text);
	}

	public void setPlayername2TextboxText(String text) {
		playername2Textbox.setText(text);
	}

	public void setPlayerOneName(String name) {
		if (name == null || name.isEmpty()
				|| name.equals(MasterGame.getPlayerTwo().getName()))
			return;

		MasterGame.getPlayerOne().setName(name);
	}

	public void setPlayerTwoName(String name) {
		if (name == null || name.isEmpty()
				|| name.equals(MasterGame.getPlayerOne().getName()))
			return;

		MasterGame.getPlayerTwo().setName(name);
	}

	public void letzteNamen() {

	}
}