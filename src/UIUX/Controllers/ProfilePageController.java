package UIUX.Controllers;

import NetworkStuff.ClientSide.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

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
		this.usernameField.setText(Client.getProfile().getUserName() );
		this.nameField.setText( Client.getProfile().getName() );
		this.passwordField.setText( Client.getProfile().getPassword() );
		this.ratingField.setText( Long.toString( Client.getProfile().getRating() ) );
		this.profilePicture.setImage( new Image( Client.getProfile().getImageAddress() ) );
	}

	public void tryChangingUsername() {
		String newUsername = this.makeAndShowTextInputDialog( "username", "You are about to change your username", "Please enter a username!" );
		if ( this.doesUsernameExist( newUsername ) ) {
			this.makeAndShowInformationDialog("Invalid username", "Username already exists!");
			return;
		}
		else {
			Client.getProfile().setUserName( this.usernameField.getText() );

		}
	}

	public void goToMainMenu() {
		this.loadPage( "MainMenu" );
	}

}
