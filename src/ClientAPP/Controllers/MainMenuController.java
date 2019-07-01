package ClientAPP.Controllers;

import ClientAPP.*;
import BasicClasses.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
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



}
