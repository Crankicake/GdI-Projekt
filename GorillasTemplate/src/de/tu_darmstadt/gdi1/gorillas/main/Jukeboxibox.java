package de.tu_darmstadt.gdi1.gorillas.main;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowListener;
import java.net.URI;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * Diese Klasse repraesentiert die Jukebox. Die Jukebox ist in einem extra Frame
 * implementiert. Die Lieder werden im Pfad ~/ressources/Musik/ gesucht und
 * aufgelistet. Ein Liedwechsel ist jederzeit ueber die Dropdownbox moeglich.
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 *
 */
public class Jukeboxibox extends Application {

	private static String[] lieder;
	@SuppressWarnings("rawtypes")
	private static JComboBox liedbox;
	private static JFrame frame;
	@SuppressWarnings("unused")
	private static JFXPanel fxPanel;
	private static MediaPlayer mp;
	private static Media affe;
	private static URI pfad;

	private static Jukeboxibox instanz;

	static {
		instanz = new Jukeboxibox();
	}

	private Jukeboxibox() {
		fxPanel = new JFXPanel();

		lieder = InputOutput.findeLieder();

		pfad = Paths.get("ressources/Musik/" + lieder[0]).toUri();
		affe = new Media(pfad.toString());
		mp = new MediaPlayer(affe);
	}

	public static Jukeboxibox getInstanz() {
		return instanz;
	}

	public static String getPfad() {
		return pfad.toString();
	}

	public static void spiele(String lied) {

		// Falls gerade schon ein Lied gespielt wird und ein anderes ausgewaehlt
		// wird
		if (mp.getStatus().equals(MediaPlayer.Status.PLAYING)) {
			mp.stop();
			pfad = Paths.get("ressources/Musik/" + lied).toUri();
			affe = new Media(pfad.toString());
			mp = new MediaPlayer(affe);
			mp.play();
		}
		// Falls das Lied pausiert wurde
		else if (mp.getStatus().equals(MediaPlayer.Status.PAUSED) && lied == "") {
			mp.play();
		}
		// Falls noch gar nicht oder gestoppt
		else if ((mp.getStatus().equals(MediaPlayer.Status.READY) || (mp
				.getStatus().equals(MediaPlayer.Status.STOPPED)))) {
			pfad = Paths.get("ressources/Musik/" + lied).toUri();
			affe = new Media(pfad.toString());
			mp = new MediaPlayer(affe);
			mp.play();
		}

	}

	public static void pausieren() {
		mp.pause();
	}

	public static void stoppe() {
		mp.stop();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage arg0) throws Exception {
		liedbox = new JComboBox(InputOutput.findeLieder());
		frame = new JFrame("Jukebox 600 XS LIMITED EDITION");
		frame.setSize(370, 300);
		frame.setBounds(150, 450, 370, 100);
		liedbox.setBounds(150, 200, 200, 100);
		liedbox.setVisible(true);
		liedbox.setEditable(false);
		liedbox.setSelectedIndex(0);
		liedbox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					Jukeboxibox.spiele(liedbox.getSelectedItem().toString());

			}
		});

		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

			}

			@Override
			public void windowIconified(java.awt.event.WindowEvent e) {

			}

			@Override
			public void windowDeiconified(java.awt.event.WindowEvent e) {

			}

			@Override
			public void windowDeactivated(java.awt.event.WindowEvent e) {

			}

			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				Jukeboxibox.stoppe();
				MasterGame.setIsJukeboxRunning(false);
			}

			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {

			}

			@Override
			public void windowActivated(java.awt.event.WindowEvent e) {

			}
		});

		frame.add(liedbox);
		if (!MasterGame.getDebug())
			frame.setVisible(true);
		frame.setAlwaysOnTop(false);
	}
}