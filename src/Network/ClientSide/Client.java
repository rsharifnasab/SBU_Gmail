package Network.ClientSide;

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

	private static Profile profile;

	public static Socket userSocket;
	public static ObjectInputStream userIn;
	public static ObjectOutputStream userOut;

	public static Socket chatSocket;
	public static ObjectInputStream chatIn;
	public static ObjectOutputStream chatOut;

	public static void main(String[] args) {
		try {
			Client.userSocket = new Socket( "localhost", Ports.USER_PORT);
			Client.userIn = new ObjectInputStream( Client.userSocket.getInputStream() );
			Client.userOut = new ObjectOutputStream( Client.userSocket.getOutputStream() );


			Client.chatSocket=new Socket("localhost",Ports.CHAT_PORT);
			Client.chatIn=new ObjectInputStream(Client.chatSocket.getInputStream());
			Client.chatOut=new ObjectOutputStream(Client.chatSocket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println( "Launching..." );
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
		Client.pStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				createExitAlert(event);
			}
		});
		Client.pStage.show();
	}

	public static Profile getProfile() {
		return profile;
	}

	public static void setProfile(Profile profile) {
		Client.profile = profile;
	}

	public void createExitAlert(WindowEvent event){

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exit Application");
		alert.setHeaderText("Are you sure you want to Exit the program? ");
		alert.setContentText("you won't lose everything!");

		ButtonType yes = new ButtonType("yes");
		ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(yes, no);

		Optional<ButtonType> as = alert.showAndWait();

		if (as.get().equals(yes)) {
			Client.pStage.close();
			System.exit(0);
		}
		else {
			event.consume();
		}
	}

}
