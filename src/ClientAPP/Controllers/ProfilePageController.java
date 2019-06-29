package ClientAPP.Controllers;

import ClientAPP.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import java.util.*;

public class ProfilePageController extends ParentController implements Initializable{

	@FXML
	TextField usernameField;
	@FXML
	Button usernameChangeButton;
	@FXML
	TextField passwordField;
	@FXML
	Button passwordChangeButton;
	@FXML
	TextField nameField;
	@FXML
	Button nameChangeButton;
	@FXML
	TextField ratingField;
	@FXML
	Button ratingChangeButton;
	@FXML
	ImageView profilePicture;

	@FXML
	Button backButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//this.usernameField.setText(ClientEXE.getProfile().getUserName() );
		//this.nameField.setText( ClientEXE.getProfile().getName() );
		//this.passwordField.setText( ClientEXE.getProfile().getPassword() );
		//this.profilePicture.setImage( new Image( ClientEXE.getProfile().getImageAddress() ) );
	}

	public void tryChangingUsername() {
		String newUsername = this.makeAndShowTextInputDialog( "username", "You are about to change your username", "Please enter a username!" );
		//TODO : check username existance
		if (false ) {
			this.makeAndShowInformationDialog("Invalid username", "Username already exists!");
		}
		else {
			ClientEXE.getProfile().setUserName( this.usernameField.getText() );
		}
	}

	public void goToMainMenu() {
		this.loadPage( "MainMenu" );
	}

}
