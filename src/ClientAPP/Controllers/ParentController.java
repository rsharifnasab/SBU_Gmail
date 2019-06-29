package ClientAPP.Controllers;

import ClientAPP.*;

import javafx.fxml.*;
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
		System.out.println("loading "+address);
		Parent root = null;
		try {
			root = FXMLLoader.load( getClass().getResource( "/ClientAPP/FXMLs/" + address + ".fxml" ) );
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene( root );
		ClientEXE.pStage.setScene( scene );
		ClientEXE.pStage.show();
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
