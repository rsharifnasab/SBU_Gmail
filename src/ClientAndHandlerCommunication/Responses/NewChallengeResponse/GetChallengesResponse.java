package ClientAndHandlerCommunication.Responses.NewChallengeResponse;

import ClientAndHandlerCommunication.Responses.Response;
import Game.Match;
import Game.Profile;

import java.util.List;
import java.util.Map;

public final class GetChallengesResponse implements Response {
   private Map<Match, Profile> challenges;

    public Map<Match, Profile> getChallenges() {
        return challenges;
    }

    public void setChallenges(Map<Match, Profile> challenges) {
        this.challenges = challenges;
    }
}
