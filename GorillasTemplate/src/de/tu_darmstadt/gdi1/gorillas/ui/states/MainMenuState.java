package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;
import de.tu_darmstadt.gdi1.gorillas.main.Jukeboxibox;
import de.tu_darmstadt.gdi1.gorillas.main.MasterGame;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class MainMenuState extends OwnState {

	
	@SuppressWarnings("rawtypes")
	private JComboBox liedbox;
	private JFrame frame;

	
	private Jukeboxibox jukebox = Jukeboxibox.getInstanz();
	
	public MainMenuState(int sid) {
		super(sid);

		names = new String[] { "Neues Spiel", "Highscore", "Anleitung",
				"About", "Beenden" };
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.initBackground();

		// Entitaet zum starten eines neues Spiels
		Entity neuesSpielE = createMenuEntity(names[0], new Vector2f(
				windowWidth / 2, windowHeight / 2 - 120));

		// Entitaet zum einsehen des Highscores
		Entity highscoreE = createMenuEntity(names[1], new Vector2f(
				windowWidth / 2, windowHeight / 2 - 60));

		// Entitaet zum lesen der Anleitung
		Entity anleitungE = createMenuEntity(names[2], new Vector2f(
				windowWidth / 2, windowHeight / 2));

		// Entitaet zum lesen des Abouts
		Entity aboutE = createMenuEntity(names[3], new Vector2f(
				windowWidth / 2, windowHeight / 2 + 60));

		// Entitaet zum Beenden des Spiels
		Entity beendenE = createMenuEntity(names[4], new Vector2f(
				windowWidth / 2, windowHeight / 2 + 120));

		// Events und Actions
		ANDEvent neuesSpielEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());
		ANDEvent highscoreEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());
		ANDEvent anleitungEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());
		ANDEvent aboutEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());
		ANDEvent spielBeendenEvent = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());

		// Neues Spiel Action
		ChangeStateInitAction neueAction = new ChangeStateInitAction(
				Gorillas.GAMESETUPSTATE);

		// Highscore Action
		ChangeStateAction highscoreAction = new ChangeStateAction(
				Gorillas.HIGHSCORESTATE);

		// Anleitung Action
		ChangeStateAction anleitungAction = new ChangeStateAction(
				Gorillas.INSTRUCTIONSSTATE);

		// About Action
		ChangeStateAction aboutAction = new ChangeStateAction(
				Gorillas.OPTIONSTATE);

		// Spiel beenden Action
		QuitAction beendenAction = new QuitAction();

		neuesSpielEvent.addAction(neueAction);
		highscoreEvent.addAction(highscoreAction);
		anleitungEvent.addAction(anleitungAction);
		aboutEvent.addAction(aboutAction);
		spielBeendenEvent.addAction(beendenAction);

		neuesSpielE.addComponent(neuesSpielEvent);
		highscoreE.addComponent(highscoreEvent);
		anleitungE.addComponent(anleitungEvent);
		aboutE.addComponent(aboutEvent);
		beendenE.addComponent(spielBeendenEvent);

		// Hinzufuegen der Entity zum Entitymanager
		entityManager.addEntity(getID(), neuesSpielE);
		entityManager.addEntity(getID(), highscoreE);
		entityManager.addEntity(getID(), anleitungE);
		entityManager.addEntity(getID(), aboutE);
		entityManager.addEntity(getID(), beendenE);
		
		// Jukeboxpart
				if(!MasterGame.isJukeboxRunning()){
				
				liedbox = new JComboBox(InputOutput.findeLieder());
				frame = new JFrame("Jukebox 600 XS LIMITED EDITION");
				frame.setSize(370, 300);
				frame.setBounds(150,450,370,100);
				liedbox.setBounds(150,200,200,100);
				liedbox.setVisible(true);
				liedbox.setEditable(false);
				liedbox.setSelectedIndex(0);
				liedbox.addItemListener(new ItemListener() {
					
					@Override
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange()==ItemEvent.SELECTED)
							jukebox.spiele(liedbox.getSelectedItem().toString());
						
					}
				});
				
				frame.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(java.awt.event.WindowEvent e) {
					
					}
					
					@Override
					public void windowIconified(java.awt.event.WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(java.awt.event.WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(java.awt.event.WindowEvent e) {
												
					}
					
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						jukebox.stoppe();
						MasterGame.setIsJukeboxRunning(false);
						
					}
					
					@Override
					public void windowClosed(java.awt.event.WindowEvent e) {
						
						
					}
					
					@Override
					public void windowActivated(java.awt.event.WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				
				frame.add(liedbox);
				frame.setVisible(true);
				frame.setAlwaysOnTop(false);
				MasterGame.setIsJukeboxRunning(true);
				}
				
				
				// Jukeboxpart ende		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		Input input = container.getInput();
		
		if (MasterGame.isAGameRunning() && input.isKeyPressed(Input.KEY_ESCAPE)) {
			changeState(container, game, Gorillas.GAMEPLAYSTATE);
			
		}
		
		if (input.isKeyPressed(Input.KEY_N)) {
			changeState(container, game, Gorillas.GAMESETUPSTATE);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);

		g.drawString(names[0], windowWidth / 2 - 35, windowHeight / 2 - 134);
		g.drawString(names[1], windowWidth / 2 - 35, windowHeight / 2 - 74);
		g.drawString(names[2], windowWidth / 2 - 35, windowHeight / 2 - 14);
		g.drawString(names[3], windowWidth / 2 - 35, windowHeight / 2 + 46);
		g.drawString(names[4], windowWidth / 2 - 35, windowHeight / 2 + 106);
	}
}
