package ClientAndHandlerCommunication.Commands;

import Enums.JoinerType;
import Game.Match;
import Game.Profile;

public final class JoinGameCommand implements Command {
     private Match match;
     private Profile profile;
     private JoinerType joinerType;

     public JoinGameCommand(Match match, Profile profile,JoinerType joinerType){

         this.profile=profile;
         this.joinerType=joinerType;
         this.match=match;
     }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public JoinerType getJoinerType() {
        return joinerType;
    }

    public Profile getProfile() {
        return profile;
    }
}
