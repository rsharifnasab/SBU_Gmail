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
import java.util.*;

/**
	controll the first page (login and signup)
	it implements intializable to iverride intialize method and do basic things to make page ready
**/
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
	Circle onlineSphere;

	/**
		initialize the page to load default profile photo for signup page
		set online sphere visibility from conection to server status
	**/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image( FirstPageController.PROFILE_PICTURE_DEFAULT );
		this.profilePicture.setImage( image );
		onlineSphere.setVisible(ClientNetworker.isConnected());
	}

	/**
		it handle connect to server button requests
		after connecting succesfully is make online sphere visible
		if it cant connect to server, show a dialog for checking server is running and other things
		also after connecting successfully it hide connect button
	**/
	public void connectToServer(){
			ClientNetworker.connectToServer();
			if ( ClientNetworker.isConnected() ) {
				onlineSphere.setVisible(true);
				connectButton.setVisible(false);
				return;
			}
			String title = "server connection problem";
			String contentText = "please check server is running and server ip and all connections";
			this.makeAndShowInformationDialog( title, contentText );
	}

	/**
		handle logon button
		first of all it check you are connected to server or not
		after that it check empty fields
		if everything is ok, send username and password to server (by API.login) and expect our profile
		if return value is null, it shows that username is not exists or password doesnt math
		if everything is ok and we get profile from server, save this profile to ClientEXE static method
		after succesfull login it redirects to mail menu
	**/
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
		this.loadPage( "MainMenu" );
	}


	/**
		check if signup page has empty field or Not
		if is has empty field show a dialog to client that he/she should complete all fields
	**/
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

	/**
		check if new username is valid and unique or Not
		it use API.isUserNameExists to check uniqueness
		local validation is checking that username only contains lowercase and uppercase and dot
		it also show user dialog about validness and uniqness of username
	**/
	public boolean isValidUsername(String username){

		char[] toCheck = username.toCharArray();

		boolean isValid = true;

		for(Character c : toCheck){
			boolean flag = false;
			if(Character.isUpperCase(c)) flag = true;
			if(Character.isLowerCase(c)) flag = true;
			if(Character.isDigit(c)) flag = true;
			if (c == '.') flag = true;
			if (!flag) isValid = false;
		}
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

	/**
		it connected to profile phot click to change default profile photo
		it show a panel to choose image file
	**/
	public void chooseProfilePicture(){
		profilePicture.setImage( chooseImage() );
	}

	/**
		a method to create and show not connected to server dialog, it shows up when user try to
		login or sign up when its not connected to server
	**/
	public void showNotConnectedDialog(){
		String title = "not connected to server";
		String contentText = "you are not connected to server yet, please use connection panel!";
		this.makeAndShowInformationDialog( title, contentText );

	}

	/**
		it connected to signup button
		first of all check if user is connected to server and if not show a dialog
		after that check empty fields and if there are any empty field in sign up page it forbids to sign up and show a dialog
		it also check validness of username (unique and good characters) and strong password and valid age
		if age < 13 it forbids you to login
		if eveything is ok, it create a profile from your content and sent to server with signup api
		after that it redirect you to profile page to add more info
	**/
	public void doSignupStuff() {
		if (!ClientNetworker.isConnected()){
			showNotConnectedDialog();
			return;
		}
		if ( hasEmptyField() ) return;
		if (!isValidPassword(signupPasswordField.getText() , signupConfirmPasswordField.getText() ) ) return;
		if (!isValidBirth(signupAgeField.getText())) return;
		if (!isValidUsername(signupUsernameField.getText())) return;

		//profile seems valid

		Profile justCreatedProfile = this.makeProfileFromPageContent();
		ClientEXE.setProfile(justCreatedProfile);
		API.signUp(justCreatedProfile);
		showProfileCreatedDialog();
		loadPage( "ProfilePage" );
	}



	/**
		clear signup fields to make it ready for next signup1
		this is @deprecated because it was used in last structure, now after login you will go to profile page, not signup again :)
	**/
	private void clearFields() {
		this.signupUsernameField.setText( null );
		this.signupPasswordField.setText( null );
		this.signupConfirmPasswordField.setText( null );
		this.signupNameField.setText( null );
		this.signupAgeField.setText( null );
		Image defaultImage = new Image( FirstPageController.PROFILE_PICTURE_DEFAULT );
		this.profilePicture.setImage( defaultImage );
	}

	/**
		this will create profile from all of signup neccesary fields
		ir finally retirn the created profile

	**/
	private Profile makeProfileFromPageContent() {
		Profile returnValue = new Profile(signupUsernameField.getText());
		returnValue.setPassword( signupPasswordField.getText() );
		returnValue.setName( signupNameField.getText() );
		returnValue.setBirthYear( signupAgeField.getText() );
		returnValue.setPhoneNumber(null);
		returnValue.setGender(Gender.NOT_SAY);

		//returnValue.setImage( profilePicture.getImage() );  TODO
		return returnValue;
	}


	/**
		show the dialog that tell user , profile created successfully
	**/
	public void showProfileCreatedDialog( ){
		String title = "Success";
		String contentText = "profile created succesfully!";
		this.makeAndShowInformationDialog( title, contentText );
	}

	/**
		tell user that cant login due to username or password wrong
	**/
	public void showInvalidLoginDialog() {
	    String title = "Error in login";
	    String contentText = "invalid username or password\nTry again or sign up";
	    this.makeAndShowInformationDialog( title, contentText );
	}

	/**
		connected to change signup fullname Field
		it will predict a good usrename based on current typed fullname
		it also checks that predicted username is valid :)
		it only work if you are connected to server for checking username validness
	**/
	public void predictUsername(){
		if (!ClientNetworker.isConnected())	return;

		String predictedUsername = "";
		int counter = 0;
		final int MAX_TRY = 2;
		do {
			String fullName = signupNameField.getText();
			String firstName = fullName.split(" ")[0];
			Random rnd = new Random();
			String postFix = rnd.nextInt(1000) + "";
			predictedUsername = firstName + postFix;
			counter++;
			if(counter > MAX_TRY) return;
		} while (!isValidUsername(predictedUsername));

		signupUsernameField.setText(predictedUsername);
	}

}
