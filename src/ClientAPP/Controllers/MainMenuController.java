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

    public void showProfile() {
        this.loadPage( "ProfilePage" );
    }

}
