package NetworkStuff.ClientSide;

import Enums.Ports;
import Game.Profile;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

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

	public static Socket joinGameSocket;
	public static ObjectInputStream joinGameIn;
	public static ObjectOutputStream joinGameOut;

	public static Socket gameSocket;
	public static ObjectInputStream gameIn;
	public static ObjectOutputStream gameOut;



	public static void main(String[] args) {
		try {
			Client.userSocket = new Socket( "localhost", Ports.USER_PORT);
			System.out.println("ewfwfw");
			Client.userIn = new ObjectInputStream( Client.userSocket.getInputStream() );
			Client.userOut = new ObjectOutputStream( Client.userSocket.getOutputStream() );
			System.out.println("hiii");
			Client.chatSocket=new Socket("localhost",Ports.CHAT_PORT);
			System.out.println("chatsocket created");
			Client.chatIn=new ObjectInputStream(Client.chatSocket.getInputStream());
			Client.chatOut=new ObjectOutputStream(Client.chatSocket.getOutputStream());

			Client.joinGameSocket=new Socket("localhost",Ports.JOINGAME_PORT);
			System.out.println("hoingamesocket created");
			Client.joinGameIn=new ObjectInputStream(Client.joinGameSocket.getInputStream());
			Client.joinGameOut=new ObjectOutputStream(Client.joinGameSocket.getOutputStream());

			Client.gameSocket=new Socket("localhost",Ports.GAME_PORT);
			Client.gameIn=new ObjectInputStream(Client.gameSocket.getInputStream());
			Client.gameOut=new ObjectOutputStream(Client.gameSocket.getOutputStream());


			System.out.println( "Created userIn and userOut!" );
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
			System.out.println("salam?");
			root = FXMLLoader.load( getClass().getResource( "/UIUX/FXMLs/FirstPage.fxml" ) );

		} catch (IOException e) {
			System.out.println("heh error?");
			System.out.println(" error is : " + e);
			System.out.println("-=-----");
			e.printStackTrace();
			System.out.println("-----------");
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
		alert.setContentText("you will lose current running tournoments and challenges");

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
