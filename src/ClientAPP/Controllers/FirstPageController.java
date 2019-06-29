package ClientAPP.Controllers;

import ClientAPP.*;

import BasicClasses.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
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
		LoginInformation currenLoginInformation = new LoginInformation( loginUsernameField.getText(), loginPasswordField.getText() );

		System.out.println(" TODO : check loginInformation");

    ClientEXE.loginInfo = currenLoginInformation;
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

	public boolean isValidPassword(){

		System.out.println("checking password sameness");

		if ( ! signupPasswordField.getText().equals( signupConfirmPasswordField.getText() ) ) {
			String title = "Error in sign up";
			String contentText = "Passwords don't match";
			makeAndShowInformationDialog( title, contentText );
			return false;
		}
		char[] toCheck = signupPasswordField.getText().toCharArray();

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
	public boolean isValidBirth(){
		String year = signupAgeField.getText();
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

		boolean exists = false;
		//TODO check existance

		if ( exists){
			String title = "Failed to create profile";
			String contentText = "Username already exists, choose another one!";
			this.makeAndShowInformationDialog( title, contentText );
		}

		return !exists;
	}

//	VaghT taraf dokme-e signup ro mizare, in taabe' sedaa zade mishe
	public void doSignupStuff() {

		if ( hasEmptyField() ) return;
		if (!isValidPassword() ) return;
		if (!isValidBirth()) return;
		if (!isValidUsername()) return;

		//profile seems valid

		Profile justCreatedProfile = this.makeProfileFromPageContent();
		ClientEXE.setProfile(justCreatedProfile);
		//TODO: send profile to server
		showProfileCreatedDialog();
		clearFields();
		loadPage( "ProfilePage" );
	}

	private boolean isLoginInformationValid( LoginInformation loginInformation ) {
		//TODO
		return true;
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
		Profile returnValue = new Profile();
		returnValue.setUserName( signupUsernameField.getText() );
		returnValue.setPassword( signupPasswordField.getText() );
		returnValue.setName( signupNameField.getText() );
		returnValue.setBirthYear( signupAgeField.getText() );
		returnValue.setPhoneNumber(null);
		returnValue.setGender(Gender.NOT_SAY);

		returnValue.setImageAddress( profilePicture.getImage().impl_getUrl().toString() ); //TODO
		return returnValue;
	}

//	VaghT taraf roo-e profile picturesh click mikone, ye safheE baaz mishe ke komak mikone ye profile picture entekhaab kone!
	public void chooseProfilePicture() {
		System.out.println("ok now choose profile photo");
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog( ClientEXE.pStage.getScene().getWindow() );
		if ( file != null ) {
			Image image = new Image( file.toURI().toString() );
			this.profilePicture.setImage( image );
		}
	}



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

	public void showFillRequiredFieldsDialog(){
		System.out.println("incomplete");
		String title = "Incomplete information";
		String contentText = "Please fill all of the required fields";
		makeAndShowInformationDialog( title, contentText );
	}

	public void showInvalidLoginDialog() {
	    String title = "Error in login";
	    String contentText = "Can not find a user with this information\nTry again or sign up";
	    this.makeAndShowInformationDialog( title, contentText );
	}

}
