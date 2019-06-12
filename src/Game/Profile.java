
package Game;

import BasicClasses.ChallengeFilter;
import BasicClasses.Rating;
import Game.ClockNiggas.Clocked;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
Profile Dge... ettelaa'aat-e shakhs ro toosh daarim! ChizHaaE mesl-e
username o password o inaa...
 */

public class Profile implements Serializable {

    private String userName, password;
    private String name;
    private String imageAddress;
    private Rating rating;
    private int challengesNumber; //number of challenges this user crated

    private ChallengeFilter challengeFilter =new ChallengeFilter();

    private List<Match> requestedMatches=new ArrayList<>();
    private Match activeMatch;

    public Profile() {

        this.rating = new Rating( Rating.DEFAULT_RATING);
        this.challengesNumber =0;

    }


    @Override
    public int hashCode() {
        return userName.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        return this.userName.equals(((Profile)obj).getUserName());
    }

    public HBox getProfileTile() {
        HBox proTile = new HBox();
        Label proName=new Label(this.getUserName());
        proName.setFont( new Font( 10) );
        Label proRate=new Label(String.valueOf(this.getRating()));
        proRate.setFont( new Font( 10 ) );




        proTile.getChildren().addAll(proName,proRate);
        proTile.setSpacing(30);

        proTile.setAlignment(Pos.CENTER);
        return proTile;
    }


    @Override
    public String toString() {
        return "[" + userName + ": " + name + " " + rating + "]";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public long getRating() {
        return rating.getValue();
    }

    public int getChallengesNumber() {
        return challengesNumber;
    }

    public void setChallengesNumber(int challengesNumber) {
        this.challengesNumber = challengesNumber;
    }

    private void setRating(long rating) {
        this.rating.setValue( rating );
    }

    public List<Match> getRequestedMatches() {
        return requestedMatches;
    }

    public Match getActiveMatch() {
        return activeMatch;
    }

    public void setActiveMatch(Match activeMatch) {
        this.activeMatch = activeMatch;
    }

    public void setRequestedMatches(List<Match> requestedMatches) {
        this.requestedMatches = requestedMatches;
    }

    public ChallengeFilter getChallengeFilter() {
        return challengeFilter;
    }

    public void setChallengeFilter(ChallengeFilter challengeFilter) {
        this.challengeFilter = challengeFilter;
    }
}
