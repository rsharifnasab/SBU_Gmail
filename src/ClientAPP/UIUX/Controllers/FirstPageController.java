package UIUX.Controllers;

import BasicClasses.LoginInformation;
import Handlers.*;

import BasicClasses.*;
import Network.ClientSide.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstPageController extends ParentController implements Initializable {

	private final static String ASSETS_FOLDER = "/Assets/FirstPage/";
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image( FirstPageController.PROFILE_PICTURE_DEFAULT );
		this.profilePicture.setImage( image );
	}

	public void doLoginStuff() {
		if ( loginUsernameField.getText().isEmpty() || loginPasswordField.getText().isEmpty() ) {
			this.showFillRequiredFieldsDialog();
			return;
		}
		LoginInformation loginInformation = new LoginInformation( loginUsernameField.getText(), loginPasswordField.getText() );
		if ( ! this.isLoginInformationValid( loginInformation ) ){	
			this.showInvalidLoginDialog();
			return;
		}
//		age be injaa-e taabe reside baashim ya'ni hamechiz ok boode... LoginInformation ro daashte baash va boro soraagh-e menu!
        this.setClientProfile( loginInformation.getUsername() );
		this.loadPage( "MainMenu" );
	}

//	VaghT taraf dokme-e signup ro mizare, in taabe' sedaa zade mishe
public void doSignupStuff() {

		if ( signupUsernameField.getText().isEmpty()
				|| signupPasswordField.getText().isEmpty()
				|| signupConfirmPasswordField.getText().isEmpty()
				|| signupNameField.getText().isEmpty() ) {
			this.showFillRequiredFieldsDialog();
			return;
		}

		// not equals password
		if ( ! signupPasswordField.getText().equals( signupConfirmPasswordField.getText() ) ) {
			this.showMismatchPasswordsDialog();
			return;
		}

		if ( this.doesUsernameExist( signupUsernameField.getText() ) ){
			this.showUsernameExistsDialog();
			return;
		}
		//profile seems valid
		Profile justCreatedProfile = this.makeProfileFromPageContent();
		ProfileCreationResponse response = this.addProfile( justCreatedProfile );
		this.showProfileCreatedDialog();
		this.clearFields();
	}

	private void setClientProfile( String username ) {
		Command command = new GetProfileCommand( username );
		GetProfileResponse response = (GetProfileResponse) this.sendUserCommand( command );
		Client.setProfile( response.getProfile() );
		command = new SetProfileCommand( response.getProfile() );
		this.sendUserCommand( command );
	}

	private boolean isLoginInformationValid( LoginInformation loginInformation ) {
		Command loginValidation = new CheckLoginValidnessCommand( loginInformation );
		LoginIsValidResponse response = (LoginIsValidResponse) this.sendUserCommand( loginValidation );
		return response.getAnswer();
	}

	private ProfileCreationResponse addProfile( Profile profile ) {
		CreateProfileCommand command = new CreateProfileCommand( profile );
		ProfileCreationResponse response = (ProfileCreationResponse) this.sendUserCommand( command );
		return response;
	}

//	FieldHaa-e marboot be ghesmat-e signup ro paak mikone
	private void clearFields() {
		this.signupUsernameField.setText( null );
		this.signupPasswordField.setText( null );
		this.signupConfirmPasswordField.setText( null );
		this.signupNameField.setText( null );
		Image defaultImage = new Image( FirstPageController.PROFILE_PICTURE_DEFAULT );
		this.profilePicture.setImage( defaultImage );
	}

//	Az chizHaaE ke tooye safhe hastan, ye profile misaaze!
	@SuppressWarnings("deprecation")
	private Profile makeProfileFromPageContent() {
		Profile returnValue = new Profile();
		returnValue.setUserName( signupUsernameField.getText() );
		returnValue.setPassword( signupPasswordField.getText() );
		returnValue.setName( signupNameField.getText() );
		returnValue.setAge( signupAgeField.getText() );

		returnValue.setImageAddress( profilePicture.getImage().impl_getUrl().toString() ); //TODO
		return returnValue;
	}

//	VaghT taraf roo-e profile picturesh click mikone, ye safheE baaz mishe ke komak mikone ye profile picture entekhaab kone!
	public void chooseProfilePicture() {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog( Client.pStage.getScene().getWindow() );
		if ( file != null ) {
			Image image = new Image( file.toURI().toString() );
			this.profilePicture.setImage( image );
		}
	}


//	AlertHaa-e mokhtalef ro injaa misaazim... bad baa komak-e makeAndShowInformationDialog neshooneshoon midim!

	public void profileCreationFailedDialog( ProfileCreationResponse response ) {
		String title = "Failed to create profile";
		String contentText = response.getMessage();
		this.makeAndShowInformationDialog( title, contentText );
	}

	public void showUsernameExistsDialog() {
		String title = "Failed to create profile";
		String contentText = "Username already exists, choose another one!";
		this.makeAndShowInformationDialog( title, contentText );
	}

	public void showProfileCreatedDialog( ){
		String title = "Success";
		String contentText = "profile created succesfully!";
		this.makeAndShowInformationDialog( title, contentText );
	}

	public void showFillRequiredFieldsDialog(){
		String title = "Incomplete information";
		String contentText = "Please fill all of the required fields";
		this.makeAndShowInformationDialog( title, contentText );
	}

	public void showInvalidLoginDialog() {
	    String title = "Error in login";
	    String contentText = "Can not find a user with this information\nTry again or sign up";
	    this.makeAndShowInformationDialog( title, contentText );
	}

	public void showMismatchPasswordsDialog() {
		String title = "Error in sign up";
		String contentText = "Passwords don't match";
		this.makeAndShowInformationDialog( title, contentText );
	}

}
