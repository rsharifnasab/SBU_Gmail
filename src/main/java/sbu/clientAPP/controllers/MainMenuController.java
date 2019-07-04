package sbu.clientAPP.controllers;

import sbu.common.*;
import sbu.clientAPP.*;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import java.net.*;
import java.util.*;

/*
 controll mail page of user that can recieve and view mails
 it implements initializazble for implementing intialize method to making page suitable for user (getting mail before showing whole page)
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
    Button searchButton;

    @FXML
    Label senderLabel;
    @FXML
    Label subjectLabel;
    @FXML
    TextArea textLabel;
    @FXML
    Label emailTimeLabel;

    @FXML
    Button replyButton;
    @FXML
    Button forwardButton;
    @FXML
    Button deleteButton;

    @FXML
    TextArea searchField;

    /**
      first of all before showing page, check mail from server and show it to fill page
    **/
  	@Override
  	public void initialize(URL location, ResourceBundle resources) {
      checkMail();
    }

    /**
      connected to edit profile button
      this will load profile edit page, nothing else
    **/
    public void showProfile() {
        this.loadPage( "ProfilePage" );
    }

    /**
      connected to logout button
      this will load back first page(to login again)
      it also send server request to dissconnet socket with aPI.logout
      and also free local ports and sockets to let user connect to another server after logout
      it also make current user profile in ClientEXE.profile empty
    **/
    public void logout(){
      API.logout();
      ClientNetworker.disconnectFromServer();
      ClientEXE.profile = null;
      this.loadPage ( "FirstPage" );
    }

    /**
      connected to compese mail button
      it will show compoese page
    **/
    public void composeMail(){
      this.loadPage("ComposeMail");
    }

    /**
      connected to check for new mail button
      it will bring all mails from server and show them in list view
    **/
    public void checkMail(){
      ClientEXE.updateMailFromServer();
      showMail();
    }

    /**
      it will handle showing mail from local list in memmory to list view
      first of all it get mails from ClientEXE depend of which section (inbox , outbox or sent) we are
      and then it create special list (javafx collection)
      and send it to list view to show it
    **/
    @SuppressWarnings("unchecked")
    public void showMail(){
      ObservableList<Mail> mail2show = FXCollections.observableArrayList(ClientEXE.getMailsToShow());
      mailsListView.setItems(mail2show);
    }

    /**
      go to trash folder
      it will set : curernt mail folder to trash folder and get trash mails from server (by help of checkMail() )
    **/
    public void goToTrash(){
      ClientEXE.mailFolder = MailFolder.TRASH;
      checkMail();
    }

    /**
      go to inbox folder
      it will set : curernt mail folder to inbox folder and get onbox mails from server (by help of checkMail() )
    **/
    public void goToInbox(){
      ClientEXE.mailFolder = MailFolder.INBOX;
      checkMail();
    }

    /**
      go to sent folder
      it will set : curernt mail folder to sent folder and get sent mails from server (by help of checkMail() )
    **/
    public void goToSent(){
      ClientEXE.mailFolder = MailFolder.SENT;
      checkMail();
    }

    /**
      go to out folder
      it will set : curernt mail folder to outbox folder and get out mails from clientEXE (thats local)
      note that outbox will be always empty because all mails are handled at the moment
    **/
    public void goToOutbox(){
      ClientEXE.mailFolder = MailFolder.OUTBOX;
      checkMail();
    }

    /**
      find current showing mail from text of currentmail.toString()
      if findMailByString return null it will return an empty mail!
    **/
    public Mail showingMail(){
      int index = mailsListView.getSelectionModel().getSelectedIndex();
      //mailStr = mailsListView.getSelectionModel().getSelectedItem().toString();
      return ClientEXE.findMailByIndex(index);
    }

    /**
      connected to every mail on click
      if user click on each mail, this  method will called
      if we are in inbox, this will set showing mail to red and update it in server with updateMail api
      it will find current showing mail by help of toString (explained in showingMail() )
    **/
    @FXML
    public void showOneMail() {
      Mail mail = showingMail();
      if (mail == null) return;
      if(ClientEXE.mailFolder == MailFolder.INBOX){
         mail.read(); // TODO
         API.changeMail(mail);
      }
      senderLabel.setText(mail.getSender()+Profile.POST_FIX);
      subjectLabel.setText(mail.getSubject());
      textLabel.setText(mail.getMessage());
      emailTimeLabel.setText(mail.getTimeString());


      //showMail();
    }


  /**
    connected to delete Button
    make an email trash ( and untrash if it is in trash folder )
    it uses API.changeMail
    changed mail is old mail but just called .trash()
    it will call checkmail again to get new list from server
  **/
  public void deleteMail(){
      Mail mail = showingMail();
      if (mail == null) return;
      mail.trash();
      API.changeMail(mail);
      checkMail();
    }

    /**
      this is connected to forward button
      it will create a template for compose mail with text of current mail
      after loading compose mail, it will load the template (that we save now)
      it change subject and message a bit (you can read changes below :D )
      after all it will load compose mail
    **/
    public void forwardMail(){
      Mail mail = showingMail();
      if (mail == null) return;
      String sender = "";
      String reciever = "";
      String subject = "frw: " + mail.getSubject();
      String message = "forwarded text : \n" + mail.getMessage() + " \n ---------------- \n";
      ClientEXE.composeTemplete = new Mail(sender, reciever, subject, message);
      composeMail();
    }

    /**
      first of all it will create a template mail from this mail for compose mail to load it later
      it save this template mail in ClientEXE
      after all it will load compose mail
    **/
    public void replyMail(){
      Mail mail = showingMail();
      if (mail == null) return;
      String sender = "";
      String reciever = mail.getSender();
      String subject = "re: " + mail.getSubject();
      String message = "replied text : \n" +  mail.getMessage() + " \n ---------------- \n";
      ClientEXE.composeTemplete = new Mail(sender, reciever, subject, message);
      composeMail();
    }

    /**
      it is connected to search button
      it filters messages that conatain the enterrd text and show them
    **/
    public void search(){
      ClientEXE.mailFolder = MailFolder.SEARCH;
      String textSearch = searchField.getText();
      ClientEXE.searchText = textSearch;
      showMail();


    }
}
