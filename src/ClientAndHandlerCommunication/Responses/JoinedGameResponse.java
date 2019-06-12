package ClientAndHandlerCommunication.Responses;

import Enums.JoinerType;
import Game.Match;
import Game.Profile;

public final class JoinedGameResponse implements Response {
    private Match match;
    private Profile profile;
    private JoinerType joinerType;

    public JoinedGameResponse(Match match,Profile profile,JoinerType joinerType){

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

    public Profile getProfile() {
        return profile;
    }

    public JoinerType getJoinerType() {
        return joinerType;
    }
}
