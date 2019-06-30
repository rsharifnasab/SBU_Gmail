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
  TextField senderField;

  @FXML
  Button sendButton;

  @FXML
  Button attachButton;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    String sender = ClientEXE.getProfile().getFullUserName();
    senderField.setText(sender);

  }

  public void send(){
    String reciever = recieverField.getText();
    String message = textField.getText();
    String subject = textField.getText();
    Mail mail = new Mail(ClientEXE.getProfile().getUserName(), reciever, subject, message);
    API.sendMail(mail);
  }

  public void addAttach(){
    //TODO
  }


}
