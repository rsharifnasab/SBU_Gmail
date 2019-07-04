package sbu.clientAPP.controllers;

import sbu.common.*;
import sbu.clientAPP.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.scene.*;

import java.io.*;
import java.util.*;

import java.net.URL;


/**
	parrent of all controller that will extend this
	it is not controller of any page
	it has some good methods for other controllers to use
**/
public class ParentController {

	/**
		open a dialog to choose image address and return loaded image
	**/
	public Image chooseImage() {
		System.out.println("ok now choose profile photo");
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog( ClientEXE.pStage.getScene().getWindow() );
		return new Image( file.toURI().toString() );
	}

	/**
		show that all fields are not completed and user should fill it All
		this methid will use in compose mail and login and signup :)
	**/
	public void showFillRequiredFieldsDialog(){
		String title = "Incomplete information";
		String contentText = "Please fill all of the required fields";
		makeAndShowInformationDialog( title, contentText );
	}

	/**
		check if client is connected to server or Not
		it will use for login and signup button , they check if we are connected to server and then let user do the stuff
		if it is not connected to server, it shows a dialog for inform client to he/she is not Connected to server
	**/
	public boolean connectionToServerCheck(){
		if ( ClientNetworker.isConnected() ) return true;
		String title = "you are not connected to server";
		String contentText = "please connect to server";
		this.makeAndShowInformationDialog( title, contentText );
		return false;
	}

	/**
		it is used is sIGNUP Part to check passwrod is good or not
		first it check if 2 password are same or not
		if they are not same, it will show a dialog box to inform user
		after that it estimate if password is string or not
		if its not string enough it will warn user and force it to choose another password
	**/
	public boolean isValidPassword(String pass1,String pass2){
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

	/**
		check if user birth year is valid or no
		first of all it use Profile static method to findout if the age is valid or not (from zero to 220 year) and also make sure it is all digits
		after that it make sure user is above 13
	**/
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

	/**
		give string address from input and load the fxml page with the spicied addreess
	**/
	public void loadPage( String address ) {
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

	/**
		thats very userful method
		it get title and content text and show an information dialog to user
	**/
	public void makeAndShowInformationDialog( String title, String contentText ) {
		Alert alert = new Alert( Alert.AlertType.INFORMATION );
		alert.setTitle( title );
		alert.setHeaderText( null );
		alert.setContentText( contentText );
		alert.showAndWait();
	}
}
