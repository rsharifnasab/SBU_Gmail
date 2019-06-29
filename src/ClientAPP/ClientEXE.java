package ClientAPP

import Enums.Ports;
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

public class Client extends Application {

//	Stage-e Asli-e Barnaame!
	public static Stage pStage;

	private static Profile profile = null;
	private static String serverAddress;

	public static Socket socket;
	public static ObjectInputStream socketIn;
	public static ObjectOutputStream socketOut;

	public static Boolean connectToServer(){
		System.out.println("trying to connect to server");
		try{
			socket = new Socket( serverAddress, Ports.PORT);
			socketIn = new ObjectInputStream( socket.getInputStream() );
			socketOut = new ObjectOutputStream( socket.getOutputStream() );
			System.out.println("conected to server!");
			return true;
		} catch (IOException e) {
			System.out.println("failed to connect to server");
			e.printStackTrace();
		}
		return false;
	}

	public static Boolean disconnectFromServer(){
		try{

			socketIn.close();
			socketOut.close();
			socket.close();
			return true;

		catch( NullPointerException e){
			System.out.println("wasnt connect BTW")
		}
		catch( Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {

		System.out.println("main ran!");
		launch( args );

	}

	@Override
	public void start(Stage primaryStage) { // Avvalesh, MainMenu ro load mikonim!

		Client.pStage = primaryStage;
		Parent root = null;
		try {
			root = FXMLLoader.load( getClass().getResource( "/UIUX/FXMLs/FirstPage.fxml" ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		Client.pStage.setTitle( "Main Menu" );
		Client.pStage.setScene( new Scene( root, 1280, 720 ) );
		Client.pStage.show();
	}


}
