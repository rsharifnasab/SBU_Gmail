package ClientAPP.Controllers;

import ClientAPP.*;
import BasicClasses.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import java.util.*;

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


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Profile profile = ClientEXE.getProfile();
		nameField.setText( profile.getName() );
		ageField.setText( profile.getBirthYear()+"" );
		phoneField.setText(profile.getPhoneNumber());
		profilePicture.setImage( profile.getImage() );

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

	public boolean changePassword(){
		if (
		!isValidPassword(passwordField.getText(),passwordConfirmField.getText())
		) return false;

		ClientEXE.getProfile().setPassword( passwordField.getText() );
		return true;
	}

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

	public Gender getGender(){
		if (man.isSelected()) return Gender.MAN;
		if (woman.isSelected()) return Gender.WOMAN;
		if (nonBinary.isSelected()) return Gender.NON_BINARY;
		return Gender.NOT_SAY;
	}

	public void chooseProfilePicture(){
		profilePicture.setImage( chooseImage() );
	}

	@SuppressWarnings("deprecation")
	public void changeProfile(){
		System.out.println("has empty fieled check");
		if (hasEmptyField()) return;
		System.out.println("birth year check");
		if (!isValidBirth(ageField.getText())) return;
		System.out.println("phone check");
		if(!isValidPhone()) return;
		System.out.println("change pass");
		if (!passwordField.getText().isEmpty()) changePassword();

		// it is VALID
		Profile profile = ClientEXE.getProfile();
		profile.setName( nameField.getText() );
		profile.setBirthYear( ageField.getText() );
		profile.setPhoneNumber( phoneField.getText() );
		profile.setGender( getGender() );
		profile.setImage( profilePicture.getImage() );

		API.updateProfile(profile);

		System.out.println("profile changed!");
	}

	public void clearMan(){
			woman.setSelected(false);
			nonBinary.setSelected(false);
			notSay.setSelected(false);
	}

	public void clearWoman(){
			man.setSelected(false);
			nonBinary.setSelected(false);
			notSay.setSelected(false);
	}

	public void clearNonBinary(){
			man.setSelected(false);
			woman.setSelected(false);
			notSay.setSelected(false);
	}

	public void clearNotSay(){
			man.setSelected(false);
			woman.setSelected(false);
			nonBinary.setSelected(false);
	}


	public void goToMainMenu() {
		this.loadPage( "MainMenu" );
	}

}
