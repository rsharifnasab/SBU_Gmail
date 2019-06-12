package UIUX.Controllers;

import BasicClasses.ChallengeFilter;
import BasicClasses.Rating;
import BasicClasses.Time;
import Game.ClockNiggas.Clock;
import NetworkStuff.ClientSide.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class FilterPageController extends ParentController {
    @FXML
    TextField minRate;
    @FXML
    TextField maxRate;
    @FXML
    TextField minClock;
    @FXML
    TextField maxClock;

    public void createFilter(){
        Rating minRatingf=new Rating(0);
        Rating maxRatingf=new Rating(Integer.MAX_VALUE);
        Clock maxClockf=new Clock(new Time(3600));
        Clock minClockf=new Clock(new Time(0));

        if (!minRate.getText().equals("")){
            minRatingf=new Rating(Integer.parseInt(minRate.getText()));
        }
        if (!maxRate.getText().equals("")){
            maxRatingf=new Rating(Integer.parseInt(maxRate.getText()));
        }
        if (!minClock.getText().equals("")){
            minClockf=new Clock(new Time(60*Integer.parseInt(minClock.getText())));
        }
        if (!maxClock.getText().equals("")){
            maxClockf=new Clock(new Time(60*Integer.parseInt(maxClock.getText())));
        }



        ChallengeFilter filter=new ChallengeFilter(minRatingf,maxRatingf,minClockf,maxClockf);
        Client.getProfile().setChallengeFilter(filter);
        loadPage("ChallengesPage");

    }




}
