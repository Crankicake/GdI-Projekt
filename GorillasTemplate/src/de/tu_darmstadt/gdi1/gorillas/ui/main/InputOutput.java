package de.tu_darmstadt.gdi1.gorillas.ui.main;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InputOutput {

	
public void speichereName(String name){
	File datei = new File("Namen.bin");
	name = name + ";";
	if(datei.exists()){
	try {
		 FileOutputStream bos = new FileOutputStream(datei,true);
		  byte[] puffer= name.getBytes();
		   bos.write(puffer);
		   bos.flush();
		   bos.close();
		   System.out.println("Datei existierte...");  
      }
      catch (IOException e) {
        System.out.println("Fehler beim Ahängen des Namens: "+e.toString());
      }
	}
	else{
		try {
			FileOutputStream bos = new FileOutputStream(datei,false);
			   byte[] puffer= name.getBytes();
			   bos.write(puffer);
			   bos.flush();
			   
			   bos.close();
			   System.out.println("Datei existierte nicht...");  
	      }
	      catch (IOException e) {
	        System.out.println("Fehler beim Schreiben des Namens: "+e.toString());
	      }
	    }}



public void speichereHighscore(String name, int highscore){
	File datei = new File("Highscore.hcs");
	if(datei.exists()){
		try {
			 FileOutputStream bos = new FileOutputStream(datei,true);
			   byte[] puffer= name.getBytes();
			   bos.write(puffer);
			   bos.write(highscore); //Eventuell probleme beim einlesen
			   bos.close();
	      }
	      catch (IOException e) {
	        System.out.println("Fehler beim Anhängen eines neuen Highscores: "+e.toString());
	      }
	    }
		else{
			try {
				FileOutputStream bos = new FileOutputStream(datei,false);
				   byte[] puffer= name.getBytes();
				   bos.write(puffer);
				   bos.write(highscore);
				   bos.close();
		      }
		      catch (IOException e) {
		        System.out.println("Fehler beim Schreiben des Highscores: "+e.toString());
		      }
		    }
		}



public String FindeNamen(String f) throws IOException{
	String temp = null;
	File datei = new File("Namen.bin");
	ArrayList<String> namen = new ArrayList<String>();
	if(datei.exists()){
		  try{  
			  Scanner s = new Scanner(datei);
			  while (s.hasNext()){
			      namen.add(s.next());
			  }
			  s.close();  
			   
			  }catch(Exception e){System.out.println(e);}  
			 } 
	
	for(int i = 0;i<namen.size();i++)
	{
		if(namen.get(i).toString().equals(f))
			temp = namen.get(i).toString();			
	}
	
	return temp;
	}}










