package ClientAPP.Controllers;

import ClientAPP.*;

import BasicClasses.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.scene.*;

import java.io.*;
import java.util.*;

import java.net.URL;


/*
All Controllers extend this class
 */
public class ParentController {


	public Image chooseImage() {
		System.out.println("ok now choose profile photo");
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog( ClientEXE.pStage.getScene().getWindow() );
		return new Image( file.toURI().toString() );
	}

	public void showFillRequiredFieldsDialog(){
		System.out.println("incomplete");
		String title = "Incomplete information";
		String contentText = "Please fill all of the required fields";
		makeAndShowInformationDialog( title, contentText );
	}

	public boolean connectionToServerCheck(){
		if ( ClientEXE.isConnected() ) return true;
		String title = "you are not connected to server";
		String contentText = "please connect to server";
		this.makeAndShowInformationDialog( title, contentText );
		return false;
	}

	public boolean isValidPassword(String pass1,String pass2){

		System.out.println("checking password sameness");

		if ( ! pass1.equals( pass2 ) ) {
			String title = "Error in sign up";
			String contentText = "Passwords don't match";
			makeAndShowInformationDialog( title, contentText );
			return false;
		}

		char[] toCheck = pass1.toCharArray();

		boolean goodLength = toCheck.length >= 8 ; //check password length
		boolean hasDigit = false;
		boolean hasUppercase = false;
		boolean hasLowercase = false;

		for(Character c : toCheck){
			if(Character.isUpperCase(c)) hasUppercase = true;
		}
		for(Character c : toCheck){
			if(Character.isDigit(c)) hasDigit = true;
		}
		for(Character c : toCheck){
			if(Character.isLowerCase(c)) hasLowercase = true;
		}

		boolean isValid = goodLength && hasDigit && hasLowercase && hasLowercase ;
		if (! isValid ){
			String title = "invalid passwrod";
			String contentText = "password should be strong!";
			makeAndShowInformationDialog( title, contentText );
			return false;
		}
		return isValid;
	}

	public boolean isValidBirth(String year){
		if (! Profile.isValidBirthYear(year)) {
			String title = "year should be a valid integer";
			String contentText = "please check year again";
			this.makeAndShowInformationDialog( title, contentText );
			return false;
		}
		if (  (2019 - Integer.parseInt(year)) < 13){
			String title = "you should be at least 13";
			String contentText = "please wait few years :D";
			this.makeAndShowInformationDialog( title, contentText );
			return false;
		}
		return true;
	}

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
