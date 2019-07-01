package ClientAPP.Controllers;

import ClientAPP.*;
import BasicClasses.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;

/*
menu-e asli-e har kaarbar
*/

public class MainMenuController extends ParentController {

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


    public void MainMenuController(){
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



}
