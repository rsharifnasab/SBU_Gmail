package sbu.clientAPP.controllers;

import sbu.clientAPP.*;
import sbu.common.*;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;
import javafx.stage.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

/**
  controller of compose mail page, it extends parrent controller to use some util methids
  it also implement Initializable for using initialize method to set value of fields in case of frw and reply
**/
public class ComposeMailController extends ParentController implements Initializable {

  @FXML
  TextField subjectField;

  @FXML
  TextArea textField;

  @FXML
  TextField recieverField;

  @FXML
  Button sendButton;

  @FXML
  Button attachButton;

  @FXML
  Button backButton;


  /**
    this method initialize page and set value to subject and reciever and text Field
    it use ClientEXE.composeTemplate that we set it in mainMenuMontroller in case of forward and reply
    if compose template is null, it shows that it isnt replay or forward case so we have nothing to do
    also after setting fields we set compose mail to null becasue it should be used once and we used it just now
  **/
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Mail mail = ClientEXE.composeTemplete;
    if (mail == null) return;
    subjectField.setText(mail.getSubject());
    recieverField.setText(mail.getReciever());
    textField.setText(mail.getMessage());

    ClientEXE.composeTemplete = null;
  }

  /**
    it is connected to send button
    if you try to send mail this method runs
    first of all check if all essential fields are filled or not, if not show dialog
    if eveything ok, it create new mail from what client filled (and client username)
    is use API.sendMail to send it ti server (and not wait for special response!)
    after all it show succesfull dialog to make client happy!
  **/
  public void send(){
    if (hasEmpty()) return;
    String reciever = recieverField.getText();
    reciever = Profile.usernameCleaner(reciever);
    String message = textField.getText();
    String subject = subjectField.getText();
    Mail mail = new Mail(ClientEXE.getProfile().getUserName(), reciever, subject, message);
    API.sendMail(mail);
    makeSuccesfullDialog();
    back();
  }

  /**
    check if all of fields are filled or not
    if it return true it mean user cant send mail and should other fields
    if also show a dialog itself in case of having empty field
  **/
  public boolean hasEmpty(){
    boolean  hasEmpty = (
      recieverField.getText().isEmpty() ||
      subjectField.getText().isEmpty() ||
      textField.getText().isEmpty()
    );
    if(hasEmpty) showFillRequiredFieldsDialog();
    return hasEmpty;
  }

  /**
    tell client that email created and sent to server
  **/
  public void makeSuccesfullDialog(){
    String title = "succesfull";
    String contentText = "email sent";
    this.makeAndShowInformationDialog( title, contentText );
  }

  /**
    go back to mail menu
  **/
  public void back(){
    this.loadPage( "MainMenu" );
  }

  public void addAttach(){
    //TODO
  }

}
