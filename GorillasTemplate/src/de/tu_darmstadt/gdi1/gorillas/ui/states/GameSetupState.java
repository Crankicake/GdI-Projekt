package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Launcher;

public class GameSetupState extends BasicTWLGameState {

	private int stateID;
	private Button applyButton;

	private EditField playername1Textbox;
	private EditField playername2Textbox;

	private Label playername1Label;
	private Label playername2Label;

	public GameSetupState(int sid) {
		stateID = sid;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	protected RootPane createRootPane() {

		RootPane rp = super.createRootPane();

		applyButton = new Button();
		applyButton.setText("Anwenden");
		applyButton.addCallback(new Runnable() {
			@Override
			public void run() {

			}
		});

		playername1Textbox = new EditField();
		playername1Textbox.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {

			}
		});

		playername2Textbox = new EditField();
		playername2Textbox.addCallback(new Callback() {
			@Override
			public void callback(int arg0) {

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
		applyButton.setSize(50, 20);
		applyButton.setPosition(Launcher.FRAME_WIDTH / 2 + 25, 100);

		playername1Textbox.adjustSize();
		playername1Textbox.setSize(50, 20);
		playername1Textbox.setPosition(70, 20);

		playername2Textbox.adjustSize();
		playername2Textbox.setSize(50, 20);
		playername2Textbox.setPosition(Launcher.FRAME_WIDTH - 70, 50);

		playername1Label.adjustSize();
		playername1Label.setPosition(70, 20);
		
		playername2Label.adjustSize();
		playername2Label.setPosition(Launcher.FRAME_WIDTH - 70, 50);
	}

	@Override
	public int getID() {
		return stateID;
	}
}