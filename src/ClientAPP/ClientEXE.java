package ClientAPP;

import BasicClasses.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.application.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public class ClientEXE extends Application {

//	Stage-e Asli-e Barnaame!
	public static Stage pStage;

	public static Profile profile;

	public static Set<Mail> outbox = new ConcurrentSkipListSet<>();


	public static Profile getProfile(){
		return profile;
	}

	public static void setProfile(Profile profile){
		ClientEXE.profile = profile;
	}

	public static void main(String[] args) {

		launch( args );

	}

	@Override
	@SuppressWarnings("deprecation")
	public void start(Stage primaryStage) { // Avvalesh, MainMenu ro load mikonim!

		pStage = primaryStage;
		Parent root = null;
		try {
			root = FXMLLoader.load( getClass().getResource( "/ClientAPP/FXMLs/FirstPage.fxml" ) );
		} catch (Exception e) {
			e.printStackTrace();
		}
		pStage.setTitle( "Main Menu" );
		pStage.setScene( new Scene( root, 1280, 720 ) );
		pStage.show();
	}


}
