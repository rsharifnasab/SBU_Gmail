package NetworkStuff.ClientSide;

import ClientAndHandlerCommunication.Commands.NewChallengeCommands.DeleteChallengesCommand;
import ClientAndHandlerCommunication.Responses.JoinedGameResponse;
import Enums.JoinerType;
import UIUX.Controllers.ParentController;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;

public class WaitForContestant extends ParentController implements Runnable {


    @Override
    public void run() {
        boolean hasntjoinedYet=true;
        synchronized (Client.joinGameIn) {
            while (hasntjoinedYet) {

                try {
                    System.out.println("waiting for a player to join");

                    JoinedGameResponse response = (JoinedGameResponse) Client.joinGameIn.readObject();

                    if (response.getJoinerType() == JoinerType.GUEST) {
                        hasntjoinedYet=false;
                        Client.getProfile().setActiveMatch(response.getMatch());

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                loadPage("GameRoomPage");
                            }
                        });
                        this.sendUserCommand(new DeleteChallengesCommand(new ArrayList<>(Client.getProfile().getRequestedMatches()), Client.getProfile().getActiveMatch()));
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }


        }

    }


}
