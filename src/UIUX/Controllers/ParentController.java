package UIUX.Controllers;

import ClientAndHandlerCommunication.Commands.*;
import ClientAndHandlerCommunication.Commands.ParentCommands.*;
import ClientAndHandlerCommunication.Responses.*;
import NetworkStuff.ClientSide.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;

import java.io.*;
import java.util.*;

/*
All Controllers extend this class
 */
public class ParentController {

//	Baraa-e Load Kardan-e Safahaat-e Digar
	public void loadPage( String address ) {

		Parent root = null;
		try {
			root = FXMLLoader.load( getClass().getResource( "/UIUX/FXMLs/" + address + ".fxml" ) );
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene( root );
		Client.pStage.setScene( scene );
		Client.pStage.show();
	}

	public Response sendUserCommand( Command command ) {
		try {
			Client.userOut.writeObject( command );
		} catch (IOException e) {
			e.printStackTrace();
		}
		Response response = null;
		try {
			response = (Response) Client.userIn.readObject();
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();
		}
		return response;
	}

	public void sendChatCommand(SendChatCommand sendChatCommand){

		try {
			Client.chatOut.writeObject(sendChatCommand);
		} catch (IOException e){
			e.printStackTrace();
		}

	}

	public void sendjoinGameCommand(Command command){
		try {
			Client.joinGameOut.writeObject(command);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public boolean doesUsernameExist( String username ) {
		UsernameExistenceRespond respond = (UsernameExistenceRespond) this.sendUserCommand( new UsernameExistenceCommand( username ) );
		return respond.isAnswer();
	}

//	Title va Matn-e badane ro migire, va ye alert baa oon mohtaviaat neshoon mide!
	public void makeAndShowInformationDialog( String title, String contentText ) {
		Alert alert = new Alert( Alert.AlertType.INFORMATION );
		alert.setTitle( title );
		alert.setHeaderText( null );
		alert.setContentText( contentText );
		alert.showAndWait();
	}

//	Title va MatnHaa-e badane ro migire, baahaashoon ye TextInputDialog misaaze!
	public String makeAndShowTextInputDialog( String title, String headerText, String contentText ) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle( title );
		dialog.setHeaderText( headerText );
		dialog.setContentText( contentText );

		Optional<String> result = dialog.showAndWait();
		return ( result.isPresent() ? result.get() : null );
	}

}
