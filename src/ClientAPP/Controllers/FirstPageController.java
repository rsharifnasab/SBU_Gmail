package ClientAPP.Controllers;

import ClientAPP.*;

import BasicClasses.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import javafx.stage.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstPageController extends ParentController implements Initializable {

	private final static String ASSETS_FOLDER = "ClientAPP/Assets/";
	private final static String PROFILE_PICTURE_DEFAULT = FirstPageController.ASSETS_FOLDER + "default_contact.png";

	@FXML
	TextField loginUsernameField;
	@FXML
	PasswordField loginPasswordField;

	@FXML
	TextField signupUsernameField;
	@FXML
	PasswordField signupPasswordField;
	@FXML
	PasswordField signupConfirmPasswordField;

	@FXML
	ImageView profilePicture;
	@FXML
	TextField signupNameField;
	@FXML
	TextField signupAgeField;

	@FXML
	TextField serverAddressField;
	@FXML
	Button connectButton;
	@FXML
	Sphere onlineSphere;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image( FirstPageController.PROFILE_PICTURE_DEFAULT );
		this.profilePicture.setImage( image );
	}


	public void connectToServer(){
			ClientNetworker.connectToServer();
			if ( ClientNetworker.isConnected() ) {
				System.out.println("making online sphere visible");
				onlineSphere.setVisible(true);
				connectButton.setVisible(false);
				return;
			}
			String title = "server connection peoblem";
			String contentText = "please check server is running and server ip and all connections";
			this.makeAndShowInformationDialog( title, contentText );
	}

	public void doLoginStuff() {
		if (!ClientNetworker.isConnected()){
			showNotConnectedDialog();
			return;
		}

		String username = loginUsernameField.getText();
		String password = loginPasswordField.getText();

		if ( username.isEmpty() || password.isEmpty() ) {
			this.showFillRequiredFieldsDialog();
			return;
		}

		Profile profile = API.login(username,password);
		if(profile == null){
			showInvalidLoginDialog();
			return;
		}
		ClientEXE.setProfile(profile);
		System.out.println("loading main menu");
		this.loadPage( "MainMenu" );
	}



	public boolean hasEmptyField(){
		if ( signupUsernameField.getText().isEmpty()
			|| signupPasswordField.getText().isEmpty()
			|| signupConfirmPasswordField.getText().isEmpty()
			|| signupNameField.getText().isEmpty()
			|| signupAgeField.getText().isEmpty()
			) {
					showFillRequiredFieldsDialog();
					return true;
			}
		return false;
	}


	public boolean isValidUsername(){
		char[] toCheck = signupUsernameField.getText().toCharArray();

		boolean isValid = true;

		for(Character c : toCheck){
			boolean flag = false;
			if(Character.isUpperCase(c)) flag = true;
			if(Character.isLowerCase(c)) flag = true;
			if(Character.isDigit(c)) flag = true;
			if (c == '.') flag = true;
			if (!flag) isValid = false;
		}
		System.out.println("username good is :"+isValid);
		if (! isValid ){
			String title = "invalid username";
			String contentText = "username must only have alphabet and digit  and dot characters!";
			makeAndShowInformationDialog( title, contentText );
			return false;
		}

		boolean exists = API.isUserNameExists(signupUsernameField.getText());

		if ( exists){
			String title = "Failed to create profile";
			String contentText = "Username already exists, choose another one!";
			this.makeAndShowInformationDialog( title, contentText );
		}

		return !exists;
	}

	public void chooseProfilePicture(){
		profilePicture.setImage( chooseImage() );
	}

	public void showNotConnectedDialog(){
		String title = "not connected to server";
		String contentText = "you are not connected to server yet, please use connection panel!";
		this.makeAndShowInformationDialog( title, contentText );

	}
//	VaghT taraf dokme-e signup ro mizare, in taabe' sedaa zade mishe
	public void doSignupStuff() {
		System.out.println("signing up");

		if (!ClientNetworker.isConnected()){
			showNotConnectedDialog();
			return;
		}
		System.out.println("server connection ok");
		if ( hasEmptyField() ) return;
		if (!isValidPassword(signupPasswordField.getText() , signupConfirmPasswordField.getText() ) ) return;
		if (!isValidBirth(signupAgeField.getText())) return;
		if (!isValidUsername()) return;
		System.out.println("empty fileds ok");

		//profile seems valid

		Profile justCreatedProfile = this.makeProfileFromPageContent();
		ClientEXE.setProfile(justCreatedProfile);
		System.out.println("profile created, sending to server");
		API.signUp(justCreatedProfile);
		showProfileCreatedDialog();
		clearFields();
		loadPage( "ProfilePage" );
	}



//	FieldHaa-e marboot be ghesmat-e signup ro paak mikone
	private void clearFields() {
		this.signupUsernameField.setText( null );
		this.signupPasswordField.setText( null );
		this.signupConfirmPasswordField.setText( null );
		this.signupNameField.setText( null );
		this.signupAgeField.setText( null );
		Image defaultImage = new Image( FirstPageController.PROFILE_PICTURE_DEFAULT );
		this.profilePicture.setImage( defaultImage );
	}

//	Az chizHaaE ke tooye safhe hastan, ye profile misaaze!
	@SuppressWarnings("deprecation")
	private Profile makeProfileFromPageContent() {
		Profile returnValue = new Profile(signupUsernameField.getText());
		returnValue.setPassword( signupPasswordField.getText() );
		returnValue.setName( signupNameField.getText() );
		returnValue.setBirthYear( signupAgeField.getText() );
		returnValue.setPhoneNumber(null);
		returnValue.setGender(Gender.NOT_SAY);

		returnValue.setImageAddress( profilePicture.getImage().impl_getUrl().toString() ); //TODO
		return returnValue;
	}

//	VaghT taraf roo-e profile picturesh click mikone, ye safheE baaz mishe ke komak mikone ye profile picture entekhaab kone!



	public void profileCreationFailedDialog() {
		String title = "Failed to create profile";
		String contentText = "please try again!";
		this.makeAndShowInformationDialog( title, contentText );
	}



	public void showProfileCreatedDialog( ){
		String title = "Success";
		String contentText = "profile created succesfully!";
		this.makeAndShowInformationDialog( title, contentText );
	}

	public void showInvalidLoginDialog() {
	    String title = "Error in login";
	    String contentText = "invalid username or password\nTry again or sign up";
	    this.makeAndShowInformationDialog( title, contentText );
	}

}
