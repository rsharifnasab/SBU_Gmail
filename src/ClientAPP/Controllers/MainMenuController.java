package ClientAPP.Controllers;

import ClientAPP.*;
import BasicClasses.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import java.net.*;
import java.util.*;

/*
menu-e asli-e har kaarbar
*/

public class MainMenuController extends ParentController implements Initializable {

    @FXML
    Button profileButton;
    @FXML
    Button logoutButton;
    @FXML
    Button composeButton;
    @FXML
    Button checkMailButton;

    @FXML
    ListView mailsListView;

    @FXML
    Button goToInboxButton;
    @FXML
    Button goToOutboxButton;
    @FXML
    Button goToSentButton;
    @FXML
    Button goToTrashButton;

    @FXML
    Label senderLabel;
    @FXML
    Label subjectLabel;
    @FXML
    TextArea textLabel;

    @FXML
    Button replyButton;
    @FXML
    Button forwardButton;
    @FXML
    Button deleteButton;


  	@Override
  	public void initialize(URL location, ResourceBundle resources) {
      checkMail();
    }

    public void showProfile() {
        this.loadPage( "ProfilePage" );
    }

    public void logout(){
      API.logout();
      ClientNetworker.disconnectFromServer();
      ClientEXE.profile = null;
      this.loadPage ( "FirstPage" );
    }

    public void composeMail(){
      this.loadPage("ComposeMail");
    }

    public void checkMail(){
      ClientEXE.updateMailFromServer();
      showMail();
    }

    @SuppressWarnings("unchecked")
    public void showMail(){
      ObservableList<Mail> mail2show = FXCollections.observableArrayList(ClientEXE.getMailsToShow());
      mailsListView.setItems(mail2show);
    }

    public void goToTrash(){
      ClientEXE.mailFolder = MailFolder.TRASH;
      checkMail();
    }

    public void goToInbox(){
      ClientEXE.mailFolder = MailFolder.INBOX;
      checkMail();
    }

    public void goToSent(){
      ClientEXE.mailFolder = MailFolder.SENT;
      checkMail();
    }

    public void goToOutbox(){
      ClientEXE.mailFolder = MailFolder.OUTBOX;
      checkMail();
    }


    public Mail showingMail(){
      try{
        String mailStr = mailsListView.getSelectionModel().getSelectedItem().toString();
        return ClientEXE.findMailByString(mailStr);
      }catch(NullPointerException e){
        return new Mail("","","","");
      }
    }
    @FXML
    public void showOneMail() {
      Mail mail = showingMail();
      if(ClientEXE.mailFolder == MailFolder.INBOX){
         mail.read();
         API.changeMail(mail);
      }
      senderLabel.setText(mail.getSender()+Profile.POST_FIX);
      subjectLabel.setText(mail.getSubject());
      textLabel.setText(mail.getMessage());
      showMail();
    }


    public void deleteMail(){
      Mail mail = showingMail();
      mail.trash();
      API.changeMail(mail);
      checkMail();
    }

    public void forwardMail(){
      Mail mail = showingMail();
      String sender = "";
      String reciever = "";
      String subject = "frw: " + mail.getSubject();
      String message = "forwarded text : \n" + mail.getMessage() + " \n ---------------- \n";
      ClientEXE.composeTemplete = new Mail(sender, reciever, subject, message);
      composeMail();
    }

    public void replyMail(){
      Mail mail = showingMail();
      String sender = "";
      String reciever = mail.getSender();
      String subject = "re: " + mail.getSubject();
      String message = "replied text : \n" +  mail.getMessage() + " \n ---------------- \n";
      ClientEXE.composeTemplete = new Mail(sender, reciever, subject, message);
      composeMail();
    }

}
