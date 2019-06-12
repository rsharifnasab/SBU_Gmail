package BasicClasses;

import Game.ClockNiggas.Clock;
import Game.Match;

import java.io.Serializable;

public class ChallengeFilter implements Serializable {
    private Rating minRating;
    private Rating maxRating;

    private Clock maxClock;
    private Clock minClock;

    public ChallengeFilter(Rating minRating,Rating maxRating,Clock minClock,Clock maxClock){
        this.minClock=minClock;
        this.maxClock=maxClock;
        this.minRating=minRating;
        this.maxRating=maxRating;
    }

    public ChallengeFilter(){
        this.minClock=new Clock(new Time(0));
        this.maxClock=new Clock(new Time(3600));
        this.minRating=new Rating(0);
        this.maxRating=new Rating(3600);
    }


    public boolean isChallengeOk (Match match){
        return  (match.getHostProfile().getRating()<=this.maxRating.getValue())&&(match.getHostProfile().getRating()>=this.minRating.getValue())&&(match.getClock().getTime().getTimeInseconds()<=this.maxClock.getTime().getTimeInseconds())&&(match.getClock().getTime().getTimeInseconds()>=this.minClock.getTime().getTimeInseconds());
    }

}
