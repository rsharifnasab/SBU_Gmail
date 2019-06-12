package ClientAndHandlerCommunication.Commands;

import BasicClasses.Cord;
import Game.Match;
import Game.Profile;

public final class MadeAMoveCommand implements Command {

    private Match match;
    private Profile moveOwner;

    private Cord start, end;

    public MadeAMoveCommand(Match match, Profile moveOwner, Cord start, Cord end ) {
        this.match = match;
        this.moveOwner = moveOwner;
        this.start = start;
        this.end = end;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Profile getMoveOwner() {
        return moveOwner;
    }

    public void setMoveOwner(Profile moveOwner) {
        this.moveOwner = moveOwner;
    }

    public Cord getStart() {
        return start;
    }

    public void setStart(Cord start) {
        this.start = start;
    }

    public Cord getEnd() {
        return end;
    }

    public void setEnd(Cord end) {
        this.end = end;
    }

}
