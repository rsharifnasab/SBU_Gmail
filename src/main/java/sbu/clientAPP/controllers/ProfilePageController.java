package sbu.clientAPP.controllers;

import sbu.clientAPP.*;
import sbu.common.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import java.util.*;

/**
	tho controller of page : edit profile
	it implements initializable for implementing iitialize that initialize page from current profile fields
**/
public class ProfilePageController extends ParentController implements Initializable{

	@FXML
	PasswordField passwordField;
	@FXML
	PasswordField passwordConfirmField;

	@FXML
	TextField nameField;
	@FXML
	TextField ageField;
	@FXML
	TextField phoneField;

	@FXML
	ImageView profilePicture;

	@FXML
	Button backButton;
	@FXML
	Button changeButton;

	@FXML
	RadioButton man;
	@FXML
	RadioButton woman;
	@FXML
	RadioButton nonBinary;
	@FXML
	RadioButton notSay;

	/**
		the initialization method, its overriding same method in initializable interface
		it  will get curent profile from ClientEXE and show them in this page's fields
	**/
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Profile profile = ClientEXE.getProfile();
		nameField.setText( profile.getName() );
		ageField.setText( profile.getBirthYear()+"" );
		phoneField.setText(profile.getPhoneNumber());
//		profilePicture.setImage( profile.getImage() );

		man.setSelected(false);
		woman.setSelected(false);
		nonBinary.setSelected(false);
		notSay.setSelected(false);

		switch(profile.getGender()){
			case MAN:
				man.setSelected(true);
				break;
			case WOMAN:
				woman.setSelected(true);
				break;
			case NON_BINARY:
				nonBinary.setSelected(true);
				break;
			case NOT_SAY:
				notSay.setSelected(true);
				break;
		}
	}

	/**
		check if all requierd fields are emfilled or not
		it will retrn true if at least one of required fields are empty
	**/
	public boolean hasEmptyField(){
		if(
			nameField.getText().isEmpty()||
			ageField.getText().isEmpty()
		){
				showFillRequiredFieldsDialog();
				return true;
		}
		return false;
	}

	/**
		it user try to change profile this method will invoke first to make sire what will happen to password
		if passwrod fields are empty nothing will change, this means user didnt attemp to change password at All
		if fields are ok, it check validdness of password same as signup page and update profile
	**/
	public boolean changePassword(){
		if (
		!isValidPassword(passwordField.getText(),passwordConfirmField.getText())
		) return false;

		ClientEXE.getProfile().setPassword( passwordField.getText() );
		return true;
	}

	/**
		check if entered string as phone number is valid or not
		if it is null or zero, it seems user dont want to enter anything so it is true
		if we can parse it to long, ir mean its valid number
		if  it has characters or too long, we show a dialog to user that please enter in that format
	**/
	public boolean isValidPhone(){
		String phoneNumber = phoneField.getText();
		if ( phoneNumber == null ) return true;
		if ( phoneNumber.equals("0") || phoneNumber.equals("")  ) return true;
		try{
			Long number = Long.parseLong(phoneNumber);
		} catch (Exception e) {
			String title = "Error in phone number";
			String contentText = "please enter in this format : 09000000000";
			makeAndShowInformationDialog( title, contentText );
			return false;
		}
		return true;
	}

	/**
		get gender from which radio button is selected
	**/
	public Gender getGender(){
		if (man.isSelected()) return Gender.MAN;
		if (woman.isSelected()) return Gender.WOMAN;
		if (nonBinary.isSelected()) return Gender.NON_BINARY;
		return Gender.NOT_SAY;
	}

	/**
		profile picure clicked is connected to here
		it show a dialog that wants from user to find a profile picture
	**/
	public void chooseProfilePicture(){
		profilePicture.setImage( chooseImage() );
	}

	/**
		change proflie button is connected to this method
		it update profile from this page's INFO
		it also checks if all fields are valid
		then it use API.changeProfile to update profile in server side
	**/
	@SuppressWarnings("deprecation")
	public void changeProfile(){
		if (hasEmptyField()) return;
		if (!isValidBirth(ageField.getText())) return;
		if(!isValidPhone()) return;
		if (!passwordField.getText().isEmpty()) changePassword();

		// it is VALID
		Profile profile = ClientEXE.getProfile();
		profile.setName( nameField.getText() );
		profile.setBirthYear( ageField.getText() );
		profile.setPhoneNumber( phoneField.getText() );
		profile.setGender( getGender() );
	//	profile.setImage( profilePicture.getImage() );

		API.updateProfile(profile);
	}

	/**
		in case man radio button selected
		clean all radio buttons except man field
	**/
	public void clearMan(){
			woman.setSelected(false);
			nonBinary.setSelected(false);
			notSay.setSelected(false);
	}

	/**
		in case woman radio button selected
		clean all radio buttons except woman field
	**/
	public void clearWoman(){
			man.setSelected(false);
			nonBinary.setSelected(false);
			notSay.setSelected(false);
	}

	/**
		in case non-binary radio button selected
		clean all radio buttons except non-binary field
	**/
	public void clearNonBinary(){
			man.setSelected(false);
			woman.setSelected(false);
			notSay.setSelected(false);
	}

	/**
		in case not say radio button selected
		clean all radio buttons except not say field
	**/
	public void clearNotSay(){
			man.setSelected(false);
			woman.setSelected(false);
			nonBinary.setSelected(false);
	}

	/**
		it connects to beck button
		discard all changes that are not saved and go back to main manu
	**/
	public void goToMainMenu() {
		this.loadPage( "MainMenu" );
	}

}
