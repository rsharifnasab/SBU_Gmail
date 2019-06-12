package UIUX.Controllers;

import BasicClasses.Time;
import ClientAndHandlerCommunication.Commands.NewChallengeCommands.CreateMatchCommand;
import Enums.Color;
import Exceptions.IllegalTimeInput;
import Game.ClockNiggas.Clock;
import Game.ClockNiggas.Clockability;
import Game.ClockNiggas.Clocked;
import Game.ClockNiggas.UnClocked;
import Game.Match;
import Game.Profile;
import Game.RateNiggas.Ratability;
import Game.RateNiggas.Rated;
import Game.RateNiggas.UnRated;
import NetworkStuff.ClientSide.Client;
import NetworkStuff.ClientSide.WaitForContestant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NewChallengePageController extends ParentController implements Initializable {
    @FXML
    RadioButton ratedRadio;
    @FXML
    RadioButton unratedRadio;
    @FXML
    RadioButton clockedRadio;
    @FXML
    RadioButton unClockedRadio;
    @FXML
    HBox clockBox;
    @FXML
    TextField minutes;
    @FXML
    TextField seconds;
    @FXML
    Label clockWarning;
    @FXML
    RadioButton whiteRadio;
    @FXML
    RadioButton blackRadio;

    private   Thread jointhread=null;
    private WaitForContestant waitForContestant =null;


    public void goBack() {
        loadPage("ChallengesPage");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup ratingRadios = new ToggleGroup();
        ratedRadio.setToggleGroup(ratingRadios);
        unratedRadio.setToggleGroup(ratingRadios);
        unratedRadio.setSelected(true);

        ToggleGroup clockRadio = new ToggleGroup();
        clockedRadio.setToggleGroup(clockRadio);
        unClockedRadio.setToggleGroup(clockRadio);
        clockedRadio.setSelected(true);

        ToggleGroup colorRadio = new ToggleGroup();
        whiteRadio.setToggleGroup(colorRadio);
        blackRadio.setToggleGroup(colorRadio);
        whiteRadio.setSelected(true);


    }

    public void hideClock() {
        clockBox.setVisible(false);
    }

    public void showClock() {
        clockBox.setVisible(true);
    }

    public void createChallenge() {

        try {
            Time temptime = getTimeInput();

           // GetProfileResponse profileGET = (GetProfileResponse) this.sendUserCommand(new GetProfileCommand(Client.getProfile().getUserName()));
            Profile myProfile = Client.getProfile();
            Match match = new Match(myProfile);
            Clock matchclock = new Clock(temptime);
            Clockability gameClockability;
            Ratability gameratabillity;


            if (unratedRadio.isSelected())
                gameratabillity = new UnRated();
            else
                gameratabillity = new Rated();

            if (clockedRadio.isSelected())
                gameClockability = new Clocked();
            else
                gameClockability = new UnClocked();

            if (whiteRadio.isSelected()){
                match.setHostProfile( Client.getProfile() );
                match.setCurrentPlayer( match.getHostProfile() );
                match.setHostColor(Color.WHITE);
                match.setGuestColor(Color.BLACK);
            }
            else{
                match.setHostColor(Color.BLACK);
                match.setGuestColor(Color.WHITE);
            }

            match.setClock(matchclock);
            match.setClockability(gameClockability);
            match.setRatability(gameratabillity);


            this.sendUserCommand(new CreateMatchCommand(match));

            myProfile.setChallengesNumber(myProfile.getChallengesNumber() + 1);
            myProfile.getRequestedMatches().add(match);

            if (jointhread==null){
                waitForContestant =new WaitForContestant();
                jointhread=new Thread(waitForContestant);
                jointhread.start();
            }
            /*Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("running");
                                JoinedGameResponse response = (JoinedGameResponse) Client.joinGameIn.readObject();
                                System.out.println(response.getMatch().getClock().toString());
                                loadPage("GameRoomPage");
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            };
            Thread thread=new Thread(runnable);
            thread.setDaemon(true);
            thread.start();*/
            /*final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
            scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    //while ( true ) {

                            try {
                                System.out.println("running");
                                JoinedGameResponse response = (JoinedGameResponse) Client.joinGameIn.readObject();
                                System.out.println(response.getMatch().getClock().toString());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadPage("GameRoomPage");
                                    }
                            });
                            } catch ( Exception e ) {
                                System.exit( 5 );
                            }


                    //}
                }
            }, 1, 500, TimeUnit.MILLISECONDS );*/
            //System.out.println("saddaaaada");
            loadPage("ChallengesPage");

        } catch (Exception e) {
            e.printStackTrace();
            clockWarning.setText(new IllegalTimeInput().toString());
        }


    }

    public void disableClock() {
        unClockedRadio.setDisable(true);
        clockedRadio.setSelected(true);
        this.showClock();
    }

    public void enableClock() {
        unClockedRadio.setDisable(false);

    }

    private Time getTimeInput() throws Exception {
        int tempSeconds = 0;
        int tempMinutes = 0;
        if (unClockedRadio.isSelected()) {
            tempMinutes = 59;
            tempSeconds = 59;
        } else {
            tempSeconds = Integer.parseInt(seconds.getText());
            tempMinutes = Integer.parseInt(minutes.getText());
        }
        if (ratedRadio.isSelected() && tempMinutes == 0 && tempSeconds == 0)
            throw new IllegalTimeInput();
        if (tempMinutes > 59 || tempMinutes < 0 || tempSeconds > 59 || tempSeconds < 0) {
            throw new IllegalTimeInput();
        }
        tempSeconds += tempMinutes * 60;
        return new Time(tempSeconds);


    }


}
