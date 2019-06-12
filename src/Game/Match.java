package Game;

import ChessGame.Board;
import Enums.Color;
import Game.ClockNiggas.Clock;
import Game.ClockNiggas.Clockability;
import Game.ClockNiggas.Clocked;
import Game.RateNiggas.Ratability;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
Vaghti ye baaZ misaaZm, ye object az in celass saakhre mishe!
 */

public class Match implements Runnable,Serializable {


	//handler haye user yahe bazi
	private List<Profile> audience = new ArrayList<>();
	//	private HBox matchTile;

	//	In ke baaZ-e mored-e nazar, rated hastesh yaa unrated!
	private Ratability ratability;

//  In ke baaZ-e mored-e nazar, clock dRe yaa na :))
    private Clockability clockability;
//saate bazimoon
	private Clock clock;
	//	Har baaZ do ta player daare, kasi ke baaZ ro sakhte va kasi ke join shode!
	private Profile hostProfile, guestProfile;
	private Color hostColor, guestColor;
	private Profile currentPlayer;
	private Color currentColor;

//	Board-e baaZmoon
	private Board board;

	public Match() {
		board = new Board( false );
	}

	public Match(Profile hostProfile) {
		this.hostProfile = hostProfile;
		board = new Board( true );
		if ( this.getHostColor() == Color.WHITE )
			this.setCurrentPlayer( this.getHostProfile() );
		else this.setCurrentPlayer( this.getGuestProfile() );
		this.setCurrentColor( Color.WHITE );
	}

	//	Ettelaa'aat-e baaZ ro be soorat-e ye HBox barmigardoone
	public HBox getMatchTile() {
		//System.out.println("gettin tile");
		HBox matchTile = new HBox();
		Label hostNameLabel=new Label(this.hostProfile.getUserName());
		hostNameLabel.setFont( new Font( 20 ) );
		Label hostRateLabel=new Label(String.valueOf(this.hostProfile.getRating()));
		hostRateLabel.setFont( new Font( 17 ) );
		Label rateLabel = new Label(ratability.toString());
		rateLabel.setFont( new Font( 17 ) );

		Label clockLabel = null;
		if (clockability instanceof Clocked)
			clockLabel=new Label(clock.toString());
		else
			clockLabel=new Label("Un-Clocked");
		clockLabel.setFont( new Font( 16 ) );

		matchTile.getChildren().addAll(hostNameLabel,hostRateLabel,rateLabel,clockLabel);
		matchTile.setSpacing(150);

		matchTile.setAlignment(Pos.CENTER);
		return matchTile;
	}

	@Override
	public void run() {

	}

	@Override
	public boolean equals(Object obj) {
		Match temp=(Match)obj;

		return (temp.getHostProfile().equals(this.hostProfile) && temp.getClock().toString().equals(this.getClock().toString()) && this.ratability.toString().equals(temp.ratability.toString()));
	}

	@Override
	public int hashCode() {
		String cordString=this.clock.toString()+this.ratability.toString()+this.getHostProfile().getUserName();
		return cordString.hashCode();
	}

	public List<Profile> getAudience() {
		return audience;
	}

	public void setAudience(List<Profile> audience) {
		this.audience = audience;
	}

	public Profile getGuestProfile() {
		return guestProfile;
	}

	public Profile getHostProfile() {
		return hostProfile;
	}

	public void setGuestProfile(Profile guestProfile) {
		this.guestProfile = guestProfile;
	}

	public void setHostProfile(Profile hostProfile) {
		this.hostProfile = hostProfile;
	}

	public Ratability getRatability() {
		return ratability;
	}

	public void setRatability(Ratability ratability) {
		this.ratability = ratability;
	}

	public Clockability getClockability() {
		return clockability;
	}

	public void setClockability(Clockability clockability) {
		this.clockability = clockability;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public Clock getClock() {
		return clock;
	}

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

	public Color getHostColor() {
		return hostColor;
	}

	public void setHostColor(Color hostColor) {
		this.hostColor = hostColor;
	}

	public Color getGuestColor() {
		return guestColor;
	}

	public void setGuestColor(Color guestColor) {
		this.guestColor = guestColor;
	}

	public Profile getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Profile currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void changeCurrentPlayer() {
		System.out.print( "Changing current player... currentPlayer: " + this.getCurrentPlayer() );
		this.setCurrentPlayer( this.getCurrentPlayer().equals( this.getHostProfile() ) ? this.getGuestProfile() : this.getHostProfile() );
		System.out.println( " and it becomes " + this.getCurrentPlayer() );
	}

	public Color getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

	public void changeCurrentColor() {
		this.setCurrentColor( this.getCurrentColor() == Color.BLACK ? Color.WHITE : Color.BLACK );
	}

}
