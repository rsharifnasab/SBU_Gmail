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
import javafx.scene.control.Button;


public class ComposeMailController extends ParentController {

  @FXML
  TextField subjectField;

  @FXML
  TextField textField;

  @FXML
  TextField recieverField;

  @FXML
  Button sendButton;

  @FXML
  Button attachButton;

  @FXML
  Button backButton;


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

  public boolean hasEmpty(){
    boolean  hasEmpty = (
      recieverField.getText().isEmpty() ||
      subjectField.getText().isEmpty() ||
      textField.getText().isEmpty()
    );
    if(hasEmpty) showFillRequiredFieldsDialog();
    return hasEmpty;
  }

  public void makeSuccesfullDialog(){
    String title = "succesfull";
    String contentText = "email sent";
    this.makeAndShowInformationDialog( title, contentText );
  }

  public void back(){
    this.loadPage( "MainMenu" );
  }

  public void addAttach(){
    //TODO
  }


}
