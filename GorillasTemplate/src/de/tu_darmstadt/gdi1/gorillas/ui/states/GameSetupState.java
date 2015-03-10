package de.tu_darmstadt.gdi1.gorillas.ui.states;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;









import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditFieldAutoCompletionWindow;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.model.AutoCompletionDataSource;
import de.matthiasmann.twl.model.AutoCompletionResult;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.ApplyEvent;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import de.tu_darmstadt.gdi1.gorillas.main.PlayerNameEvent;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.KeyPressedEvent;

public class GameSetupState extends BasicTWLGameState {

	protected StateBasedEntityManager sbem;
	private ApplyEvent applyEvent;
	private int stateID;
	
	private EditField playername1Textbox;
	private EditField playername2Textbox;
	private Label playername1Label;
	private Label playername2Label;
	private Button applyButton;
	

	
	public GameSetupState(int sid) {
		stateID = sid;

		sbem = StateBasedEntityManager.getInstance();
		
	}


	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		initBackground();
		initGamePlayState();
	}

	protected void initBackground() throws SlickException {

		Entity background = new Entity("Background");
		background.addComponent(new ImageRenderComponent(new Image(
				"/assets/gorillas/background/MenuBackground.jpg")));
		background.setPosition(new Vector2f(Launcher.FRAME_WIDTH / 2,
				Launcher.FRAME_HEIGHT / 2));
		background.setScale(1.5f);
		background.setPassable(true);
		background.setRotation(0.0f);

		sbem.addEntity(stateID, background);
	}

	protected void initGamePlayState() throws SlickException {
		Entity newGameEntity = new Entity("Spiel wird gestartet");

		// Setze Position und Bildkomponente
		newGameEntity.setPosition(new Vector2f(0, 0));
		newGameEntity.setScale(Launcher.SCALE);
		
		// Pr�ft ob Tab gedr�ckt wurde
				Entity tabListener = new Entity("Tab_Listener");
				KeyPressedEvent tabPressed = new KeyPressedEvent(Input.KEY_TAB);
			// Fabian mach	nPressed.addComponent
				tabListener.addComponent(tabPressed);

		applyEvent = new ApplyEvent();
		// Erstelle das Ausloese-Event und die zugehoerige Action
		ANDEvent mainEvents = new ANDEvent(new PlayerNameEvent(), applyEvent);

		Action newGameAction = new ChangeStateInitAction(Gorillas.GAMEPLAYSTATE);
		mainEvents.addAction(newGameAction);
		newGameEntity.addComponent(mainEvents);
		
		
		
		

		// Fuege die Entity zum StateBasedEntityManager hinzu
		sbem.addEntity(this.stateID, newGameEntity);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		sbem.renderEntities(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		sbem.updateEntities(gc, sbg, delta);
	}

	public void setPlayerOneName(String name) {
		if(name == null || name.isEmpty())
			return;
		
		MasterGame.getPlayerOne().setName(name);
	}
	
	public void setPlayerTwoName(String name) {
		if(name == null || name.isEmpty())
			return;
		
		MasterGame.getPlayerTwo().setName(name);
	}
	
	public void letzteNamen(){
		
	}
	
	public void playername1Textbox_TextChanged(){
		/*AutoCompletionDataSource acds = new AutoCompletionDataSource() {
			
			@Override
			public AutoCompletionResult collectSuggestions(String arg0, int arg1,
					AutoCompletionResult arg2) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		EditFieldAutoCompletionWindow ficken = new EditFieldAutoCompletionWindow(playername1Textbox);
		playername1Textbox.setAutoCompletionWindow(ficken);
		setPlayerOneName(io.FindeNamen(playername1Textbox.getText()));
		*/
		
		if(playername1Textbox.getText() != null && playername2Textbox.getText() != null)
		{
			if(!playername1Textbox.getText().isEmpty() && !playername2Textbox.getText().isEmpty()) {
				setPlayerOneName(playername1Textbox.getText());
				setPlayerTwoName(playername2Textbox.getText());
				
				applyButton.setEnabled(true);
			}
			else
			{
				applyButton.setEnabled(false);
			}
		}
		else
		{
			applyButton.setEnabled(true);
		}	
	}
	InputOutput io = new InputOutput();
	public void playername2Textbox_TextChanged(){
		
		//playername2Textbox.setText(io.FindeNamen(playername2Textbox.getText()));
		
		if(playername1Textbox.getText() != null && playername2Textbox.getText() != null)
		{
			if(!playername1Textbox.getText().isEmpty() && !playername2Textbox.getText().isEmpty()) {
				setPlayerOneName(playername1Textbox.getText());
				
				setPlayerTwoName(playername2Textbox.getText());
				
				
				
				
				applyButton.setEnabled(true);
			}
			else
			{
				applyButton.setEnabled(false);
			}
		}
		else
		{
			applyButton.setEnabled(true);
		}
	}
	
	
	public void applyButton_Click() {
		applyEvent.SetPerformAction(true);
		io.speichereName(playername1Textbox.getText());
		io.speichereName(playername2Textbox.getText());
		io.FindeNamen(playername1Textbox.getText());
	}
	
	protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();

		applyButton = new Button();
		applyButton.setText("Anwenden");
		applyButton.setEnabled(false);
		applyButton.addCallback(new Runnable() {
			@Override
			public void run() {
				applyButton_Click();
			}
		});

		playername1Textbox = new EditField();
		playername1Textbox.addCallback(new Callback() {

			@Override
			public void callback(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
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

		rp.add(applyButton);
		rp.add(playername1Textbox);
		rp.add(playername2Textbox);
		rp.add(playername1Label);
		rp.add(playername2Label);
		
		return rp;
	}

	@Override
	protected void layoutRootPane() {
		applyButton.adjustSize();
		applyButton.setSize(86, 30);
		applyButton.setPosition(Launcher.FRAME_WIDTH / 2 - 43,
				Launcher.FRAME_HEIGHT - 100);

		playername1Label.adjustSize();
		playername1Label.setPosition(30, 20);

		playername1Textbox.adjustSize();
		playername1Textbox.setSize(130, 20);
		playername1Textbox.setPosition(30, 50);

		playername2Label.adjustSize();
		playername2Label.setPosition(30, 80);

		playername2Textbox.adjustSize();
		playername2Textbox.setSize(130, 20);
		playername2Textbox.setPosition(30, 110);
	}

	protected String removeUnusefullChars(String value) {
		StringBuilder sb = new StringBuilder(value.length());

		for (char c : value.toCharArray()) {
			if (c != ' ' && c != '\n' && c != '\t') {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	@Override
	public int getID() {
		return stateID;
	}
}