package ChessGame.ChizaayeMohrehaa.SpecificMohre;

import BasicClasses.Cord;
import ChessGame.Board;
import ChessGame.ChizaayeMohrehaa.Mohre;
import Enums.Color;

import java.util.ArrayList;

public class Rokh extends Mohre {

	public Rokh(Color color, Cord cord ) {
		super( color, cord );
	}

	public Rokh( Color color, Cord cord, boolean isMoved ) {
		super( color, cord, isMoved ) ;
	}

	@Override
	public ArrayList<Cord> getValidDests(Board boardIn, boolean isKishImportant) {
		Board board = boardIn.getCopy();
		int x = this.getCord().getX(), y = this.getCord().getY();
		ArrayList < Cord > returnValue = new ArrayList < Cord > ();

//		Baalaa
		for ( int i = 1; y - i >= 0; i++ ){
			if ( board.getBlocks()[ y - i ][ x ].getMohre() == null )
				this.addCorToValidCors( board.getCopy(), this, returnValue, new Cord( x, y - i ), isKishImportant );
			else {
				if (board.getBlocks()[y - i][x].getMohre().getColor() != this.getColor())
					this.addCorToValidCors(board.getCopy(), this, returnValue, new Cord(x, y - i), isKishImportant);
				break;
			}
		}

//		Rast
		for ( int i = 1; x + i < Board.SIZE; i++ ) {
			if ( board.getBlocks()[ y ][ x + i ].getMohre() == null )
				this.addCorToValidCors( board.getCopy(), this, returnValue, new Cord( x + i, y ), isKishImportant );
			else {
				if (board.getBlocks()[y][x + i].getMohre().getColor() != this.getColor())
					this.addCorToValidCors(board.getCopy(), this, returnValue, new Cord(x + i, y), isKishImportant);
				break;
			}
		}

//		PaaEn
		for ( int i = 1; y + i < Board.SIZE; i++ ){
			if ( board.getBlocks()[ y + i ][ x ].getMohre() == null )
				this.addCorToValidCors( board.getCopy(), this, returnValue, new Cord( x, y + i ), isKishImportant );
			else {
				if (board.getBlocks()[y + i][x].getMohre().getColor() != this.getColor())
					this.addCorToValidCors(board.getCopy(), this, returnValue, new Cord(x, y + i), isKishImportant);
				break;
			}
		}

//		Chap
		for ( int i = 1; x - i >= 0; i++ ) {
			if ( board.getBlocks()[ y ][ x - i ].getMohre() == null )
				this.addCorToValidCors( board.getCopy(), this, returnValue, new Cord( x - i, y ), isKishImportant );
			else {
				if (board.getBlocks()[y][x - i].getMohre().getColor() != this.getColor())
					this.addCorToValidCors(board.getCopy(), this, returnValue, new Cord(x - i, y), isKishImportant);
				break;
			}
		}
		return returnValue;
	}

	@Override
	public char symbol() {
		return ( this.getColor() == Color.WHITE ? 'R' : 'r' );
	}

	@Override
	public String label() {
		return "rokh";
	}

	@Override
	public Rokh getCopy () {
		return new Rokh( this.getColor(), this.getCord().getCopy(), this.isMoved() );
	}

}
