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
	TextField passwordField;
	@FXML
	TextField passwordConfirmField;

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
		ageField.setText(new Integer(profile.getBirthYear()).toString());
		phoneField.setText(profile.getPhoneNumber());
		profilePicture.setImage( new Image( ClientEXE.getProfile().getImageAddress() ) );
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

	public void changeProfile(){
		System.out.println("profile changed?");
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
