package de.tu_darmstadt.gdi1.gorillas.main;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;
import javafx.stage.Stage;
import java.net.URI;
import java.nio.file.Paths;




 

public class Jukeboxibox extends Application {

	JFXPanel fxPanel = new JFXPanel();
	URI pfad = Paths.get("fatjoe.mp3").toUri();
	Media affe = new Media(pfad.toString());
	MediaPlayer mp = new MediaPlayer(affe);
	
	
	public void spiele(){
		
		mp.play();
	}


	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}

}