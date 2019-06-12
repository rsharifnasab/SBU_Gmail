package ClientAndHandlerCommunication.Commands.NewChallengeCommands;

import ClientAndHandlerCommunication.Commands.Command;
import Game.Match;

public final class CreateMatchCommand implements Command {
    private Match match;

    public CreateMatchCommand(Match match) {
        this.match = match;
    }

    public Match getMatch() {

        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
