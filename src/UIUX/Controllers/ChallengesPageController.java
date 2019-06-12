package UIUX.Controllers;

import BasicClasses.ChallengeFilter;
import ClientAndHandlerCommunication.Commands.JoinGameCommand;
import ClientAndHandlerCommunication.Commands.NewChallengeCommands.DeleteChallengesCommand;
import ClientAndHandlerCommunication.Commands.NewChallengeCommands.GetChallengesCommand;
import ClientAndHandlerCommunication.Responses.JoinedGameResponse;
import ClientAndHandlerCommunication.Responses.NewChallengeResponse.GetChallengesResponse;
import Enums.JoinerType;
import Game.Match;
import Game.Profile;
import NetworkStuff.ClientSide.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChallengesPageController extends ParentController implements Initializable {

    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    @FXML
    VBox challengesVBox;
    private int i = 0;


    private List<Match> challenges = new ArrayList<>();

    private ChallengeFilter filter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("AAAAAYYY MELLLAT MAAAAAN " + Client.getProfile());
/*		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() {
						final CountDownLatch latch = new CountDownLatch(1);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									while ( true ) {
										//Thread.sleep( 10000 );
										Client.userOut.writeObject(new GetChallengesCommand());
										GetChallengesResponse response = (GetChallengesResponse) Client.userIn.readObject();
										ChallengesPageController.this.createHbox(response.getChallenges());
									}
								} catch (IOException|ClassNotFoundException ioexception) {
									System.exit( 3 );
								} finally {
									latch.countDown();
								}
							}
						});
						try {
							latch.await();
						} catch (InterruptedException e) {
							System.exit( 4 );
						}
						return null;
					}
				};
			}
		};
		service.start();*/
/*		new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						while ( true ) {
							try {
								Thread.sleep(500);
                                System.out.println( "Getttting challenges lists!" );
								Client.userOut.writeObject(new GetChallengesCommand());
								GetChallengesResponse response = (GetChallengesResponse) Client.userIn.readObject();
                                System.out.println( response.getChallenges() );
								createHbox(response.getChallenges());
                                System.out.println( "HBox meghdRdehi shod" );
							} catch ( InterruptedException|IOException|ClassNotFoundException e ) {
								System.exit( 5 );
							}
						}
					}
				});
			}
		}).start();*/
/*        Task task = new Task < Void > () {
            @Override
            protected Void call() {
                Platform.runLater( () -> {
                    while ( true ) {
                        try {
                            //Thread.sleep(500);
                            Client.userOut.writeObject(new GetChallengesCommand());
                            GetChallengesResponse response = (GetChallengesResponse) Client.userIn.readObject();
                            createHbox(response.getChallenges());
                        } catch (IOException|ClassNotFoundException e ){
                            System.exit( 5 );
                        }
                    }
                });
                return null;
            }
        };
        new Thread( task ).start();*/
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //while ( true ) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Client.userOut.writeObject(new GetChallengesCommand());
                            GetChallengesResponse response = (GetChallengesResponse) Client.userIn.readObject();
                            createHbox(response.getChallenges());
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.exit(5);
                        }
                    }
                });
                //}
            }
        }, 1, 500, TimeUnit.MILLISECONDS);

        //System.out.println( "Thread started!" );
    }

    public void createNewChallenge() {
        scheduler.shutdown();
        this.loadPage("NewChallengePage");

    }

    public void backToMenu() {
        scheduler.shutdown();
        this.loadPage("MainMenu");

    }

    public void createHbox(Map<Match, Profile> challenges) {

        challengesVBox.getChildren().clear();

        for (Match match : challenges.keySet()) {

                if (Client.getProfile().getChallengeFilter().isChallengeOk(match)) {
                    HBox matchBox = match.getMatchTile();
                    if (match.getGuestProfile() != null)
                        matchBox.setStyle("-fx-background-color: green;");
                    matchBox.setStyle("-fx-border-style: solid inside ;" + "-fx-border-width: 2px;" + "-fx-border-color: black;");
                    matchBox.setOnMouseEntered((event1) -> {
                        matchBox.setEffect(new DropShadow());
                    });
                    matchBox.setOnMouseExited((event2) -> {
                        matchBox.setEffect(null);
                    });
                    matchBox.setOnMouseClicked((event3) -> {
                        joinMatchAlert(match);

                    });
                    challengesVBox.getChildren().add(matchBox);
                }
        }
    }

    public void joinMatchAlert(Match match) {
        if (!match.getHostProfile().equals(Client.getProfile())) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Join Match");
            alert.setHeaderText("Do you Want to join " + match.getHostProfile().getUserName() + "'s challenge?");
            alert.setContentText("Join as...");

            ButtonType asAudienceButton = new ButtonType("Audience");
            ButtonType asContestandButton = new ButtonType("Contestant");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);


            alert.getButtonTypes().setAll(asAudienceButton, asContestandButton, cancel);

            if (match.getGuestProfile() != null) {
                alert.getButtonTypes().remove(asContestandButton);
                System.out.println("removing button");
            }
            Optional<ButtonType> as = alert.showAndWait();

            if (as.get().equals(asAudienceButton)) {
                Client.getProfile().setActiveMatch(match);
                match.getAudience().add(Client.getProfile());
                //if (match.getGuestProfile()!=null)
                this.sendjoinGameCommand(new JoinGameCommand(match, Client.getProfile(), JoinerType.AUDIENCE));
                try {
                    JoinedGameResponse joinedGameResponse = (JoinedGameResponse) Client.joinGameIn.readObject();
                    Client.getProfile().setActiveMatch(joinedGameResponse.getMatch());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                this.sendUserCommand(new DeleteChallengesCommand(new ArrayList<>(Client.getProfile().getRequestedMatches()), null));
                scheduler.shutdown();

                loadPage("GameRoomPage");
            } else if (as.get().equals(asContestandButton)) {

                Client.getProfile().setActiveMatch(match);
                match.setGuestProfile(Client.getProfile());
                this.sendjoinGameCommand(new JoinGameCommand(match, Client.getProfile(), JoinerType.GUEST));
                try {
                    JoinedGameResponse joinedGameResponse = (JoinedGameResponse) Client.joinGameIn.readObject();
                    Client.getProfile().setActiveMatch(joinedGameResponse.getMatch());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //System.out.println(Client.getProfile().getRequestedMatches());
                this.sendUserCommand(new DeleteChallengesCommand(new ArrayList<>(Client.getProfile().getRequestedMatches()), null));

                scheduler.shutdown();

                loadPage("GameRoomPage");


            } else {

            }
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("View Match");
            alert.setHeaderText("Do you Want to View your match?");
            alert.setContentText("");

            ButtonType view = new ButtonType("View");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(view, cancel);
            Optional<ButtonType> as = alert.showAndWait();
            if (as.get().equals(view)) {
                Client.getProfile().setActiveMatch(match);
                scheduler.shutdown();
                loadPage("GameRoomPage");
            } else {

            }


        }

    }

    public void createFilter() {
        loadPage("CreateFilterPage");
        scheduler.shutdown();

    }


    public void setChallenges(List<Match> challenges) {
        this.challenges = challenges;
    }
}
