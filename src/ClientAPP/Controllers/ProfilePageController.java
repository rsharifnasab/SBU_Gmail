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
	RadioButton male;
	@FXML
	RadioButton female;
	@FXML
	RadioButton notBinary;
	@FXML
	RadioButton notSay;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Profile profile = ClientEXE.getProfile();
		nameField.setText( profile.getName() );
		ageField.setText( profile.getBirthYear()+"" );
		phoneField.setText(profile.getPhoneNumber());
		profilePicture.setImage( new Image( profile.getImageAddress() ) );

		male.setSelected(false);
		female.setSelected(false);
		notBinary.setSelected(false);
		notSay.setSelected(false);

		switch(profile.getGender()){
			case MALE:
				male.setSelected(true);
				break;
			case FEMALE:
				female.setSelected(true);
				break;
			case NOT_BINARY:
				notBinary.setSelected(true);
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
		if (male.isSelected()) return Gender.MALE;
		if (female.isSelected()) return Gender.FEMALE;
		if (notBinary.isSelected()) return Gender.NOT_BINARY;
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
		profile.setImageAddress( profilePicture.getImage().impl_getUrl().toString() );

		System.out.println("profile changed!");
	}

	public void clearMale(){
			female.setSelected(false);
			notBinary.setSelected(false);
			notSay.setSelected(false);
	}

	public void clearFemale(){
			male.setSelected(false);
			notBinary.setSelected(false);
			notSay.setSelected(false);
	}

	public void clearNotBinary(){
			male.setSelected(false);
			female.setSelected(false);
			notSay.setSelected(false);
	}

	public void clearNotSay(){
			male.setSelected(false);
			female.setSelected(false);
			notBinary.setSelected(false);
	}


	public void goToMainMenu() {
		this.loadPage( "MainMenu" );
	}

}
