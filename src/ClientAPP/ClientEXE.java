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

public class ClientEXE extends Application {

//	Stage-e Asli-e Barnaame!
	public static Stage pStage;

	public static LoginInformation loginInfo = null;
	public static Profile profile;
	static String serverAddress;
	static final int PORT = 8888;

	private static boolean isConnected = false;
	public static Socket socket;
	public static ObjectInputStream socketIn;
	public static ObjectOutputStream socketOut;


	public static boolean isConnected(){
		return isConnected;
	}

	public static Profile getProfile(){
		return profile;
	}

	public static void setProfile(Profile profile){
		ClientEXE.profile = profile;
	}

	public static Boolean connectToServer(){
		System.out.println("trying to connect to server");
		if(socket != null) return false;
		try{
			socket = new Socket( serverAddress, PORT);
			System.out.println("socket ok");
			socketOut = new ObjectOutputStream( socket.getOutputStream() );
			socketIn = new ObjectInputStream( socket.getInputStream() );
			System.out.println("inpout output stream ok");
			System.out.println("conected to server!");
			isConnected = true;
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
			System.out.println("disconncted from server Sucesfully");
			isConnected = false;
			socket = null;
			socketIn = null;
			socketOut = null;
			return true;
		}
		catch( NullPointerException e){
			System.out.println("wasnt connect BTW");
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
	@SuppressWarnings("deprecation")
	public void start(Stage primaryStage) { // Avvalesh, MainMenu ro load mikonim!

		pStage = primaryStage;
		Parent root = null;
		try {
			root = FXMLLoader.load( getClass().getResource( "/ClientAPP/FXMLs/FirstPage.fxml" ) );
			System.out.println("loaded xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		pStage.setTitle( "Main Menu" );
		pStage.setScene( new Scene( root, 1280, 720 ) );
		pStage.show();
	}


}
