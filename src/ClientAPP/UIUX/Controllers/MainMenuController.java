package UIUX.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/*
menu-e asli-e har kaarbar
*/

public class MainMenuController extends ParentController {

    @FXML
    Button profileButton;

    public void toChallengePage(){
        this.loadPage("ChallengesPage");
    }

    public void showProfile() {
        this.loadPage( "ProfilePage" );
    }

}
