package de.tu_darmstadt.gdi1.gorillas.main;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;
import javafx.stage.Stage;
import java.net.URI;
import java.nio.file.Paths;




 

public class Jukeboxibox extends Application {

	private JFXPanel fxPanel = new JFXPanel();
	private static Jukeboxibox instanz;
	
	URI pfad = Paths.get("ressources/Musik/salim.wav").toUri();
	Media affe = new Media(pfad.toString());
	MediaPlayer mp= new MediaPlayer(affe);
	
	static {
		instanz = new Jukeboxibox();
	}
	
	public static Jukeboxibox getInstanz(){
		return instanz;
	}
	
	public String getPfad(){
		return pfad.toString();
	}
	
	
	
	public void spiele(String lied){
		
		// Falls gerade schon ein Lied gespielt wird und ein anderes ausgewaehlt wird
		if(mp.getStatus().equals(MediaPlayer.Status.PLAYING)){
			mp.stop();
			 pfad = Paths.get("ressources/Musik/"+lied).toUri();
			 affe = new Media(pfad.toString());
			 mp = new MediaPlayer(affe);
			mp.play();
		}
		// Falls das Lied pausiert wurde
		else if(mp.getStatus().equals(MediaPlayer.Status.PAUSED)&& lied ==""){
			mp.play();
		}
		// Falls noch gar nicht oder gestoppt
		else if((mp.getStatus().equals(MediaPlayer.Status.READY) || (mp.getStatus().equals(MediaPlayer.Status.STOPPED)))){
			 pfad = Paths.get("ressources/Musik/"+lied).toUri();
			 affe = new Media(pfad.toString());
			 mp = new MediaPlayer(affe);
			mp.play();
		}
	
	}


	public void pausieren(){ mp.pause(); }
	public void stoppe(){mp.stop();}
	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}

}