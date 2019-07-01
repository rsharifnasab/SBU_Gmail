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

	public static MailFolder mailFolder = MailFolder.INBOX;

	public static List<Mail> outbox = new CopyOnWriteArrayList<>();
	public static List<Mail> inbox = new CopyOnWriteArrayList<>();
	public static List<Mail> sent = new CopyOnWriteArrayList<>();

	public static List<Mail> getMailsToShow(){
		switch(mailFolder){
			case INBOX:
				return inbox;
			case SENT:
				return sent;
			case OUTBOX:
				return outbox;
		}
		return new CopyOnWriteArrayList<Mail>();
	}

	public static Profile getProfile(){
		return profile;
	}

	public static void updateMailFromServer(){
		inbox = new CopyOnWriteArrayList<>(API.getInbox());
		sent = new CopyOnWriteArrayList<>(API.getSent());
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
