
package ChessGame.ChizaayeMohrehaa;

import BasicClasses.Cord;
import ChessGame.Block;
import ChessGame.Board;
import Enums.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Mohre implements Serializable {

	public final static String MOHRE_IMAGES_ADDRESS = "/Assets/Chess/Mohre/";

//	private static Map< String, ImageView>;

	private Color color;
	private Cord cord;
	private boolean isMoved;

	public void move( Board board, Cord dest ){
		this.setMoved( true );
		board.getBlocks()[dest.getY()][dest.getX()].setMohre( this );
		board.getBlocks()[this.getCord().getY()][this.getCord().getX()].setMohre( null );
		this.setCord( dest );
	}

/*	public boolean isMoveValid( Board board, Cord dest ){
		return ( this.getValidDests( board ).contains( dest ) );
	}*/

	public boolean aayaaKishMishim( Board board, Mohre mohre, Cord destCord ) {
		Board boardCopy = board.getCopy();
		mohre.move( board, destCord );
/*		System.out.println( board );
		System.out.println();
		System.out.println();
		System.out.println( boardCopy );*/
		boolean returnValue = ( this.getHarifValidCords( board ).contains( board.getShaah( this.getColor() ).getCord() ) );
		board = new Board( boardCopy );
		return returnValue;
	}

	public void addCorToValidCors( Board board, Mohre mohre, ArrayList< Cord > cords, Cord theCord, boolean isKishImportant ){ //Cor manfi nadaashte baashe... va mohre-e khoD oonjaa nabaashe!
		if ( theCord.getX() >= 0 && theCord.getX() < Board.SIZE && theCord.getY() >= 0 && theCord.getY() < Board.SIZE ) {
//			System.out.println( board.getBlocks()[2][1].getMohre() );
			Mohre currentMohre = board.getBlocks()[theCord.getY()][theCord.getX()].getMohre();
			if ( currentMohre == null || currentMohre.getColor() != this.getColor() )
				if ( !isKishImportant || ( isKishImportant && !this.aayaaKishMishim(board, mohre, theCord) ) )
					cords.add(theCord);
		}
	}

	public Set < Cord > getHarifValidCords( Board board ) {
		Set<Cord> harifValidCords = new HashSet<Cord>();
		for (Block[] satr : board.getBlocks())
			for (Block block : satr)
				if ( block.getMohre() != null && block.getMohre().getColor() != this.getColor())
					harifValidCords.addAll(block.getMohre().getValidDests(board,false));
		return harifValidCords;
	}

	public abstract ArrayList< Cord > getValidDests( Board board, boolean isKishImportant );

	public Image getTile() {
		return new Image( Mohre.MOHRE_IMAGES_ADDRESS + this.toString() );
	}

	public Mohre(Color color, Cord cord) {
	    this( color, cord, false );
	}

	public Mohre( Color color, Cord cord, boolean isMoved ) {
		this.setColor( color );
		this.setCord( cord );
		this.setMoved( isMoved );
	}

	public Mohre() {

	}

/*	@Override
	protected Object clone() throws CloneNotSupportedException {
		return this.clone();
	}*/

	public abstract String label();
	public abstract char symbol();

	@Override
	public String toString() {
		return this.label() + this.getColor().toString();
	}

	public abstract Mohre getCopy();

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Cord getCord() {
		return cord;
	}

	public void setCord(Cord cord) {
		this.cord = cord;
	}

	public boolean isMoved() {
		return isMoved;
	}

	public void setMoved(boolean moved) {
		isMoved = moved;
	}

}
