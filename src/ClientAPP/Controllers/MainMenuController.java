package ClientAPP.Controllers;

import ClientAPP.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
}
