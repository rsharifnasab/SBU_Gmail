package ClientAndHandlerCommunication.Commands.NewChallengeCommands;

import ClientAndHandlerCommunication.Commands.Command;
import Game.Match;

import java.util.List;

public final class DeleteChallengesCommand implements Command {
    private List<Match> list;
    private Match active;

    public DeleteChallengesCommand(List<Match> list,Match active){
        this.list=list;
        this.active=active;

    }

    public List<Match> getList() {
        return list;
    }

    public Match getActive() {
        return active;
    }
}
