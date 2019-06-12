package ChessGame.ChizaayeMohrehaa.SpecificMohre;

import BasicClasses.Cord;
import ChessGame.Board;
import ChessGame.ChizaayeMohrehaa.Mohre;
import Enums.Color;

import java.util.ArrayList;

public class Fil extends Mohre {

	public Fil(Color color, Cord cord ) {
		super( color, cord );
	}

	public Fil( Color color, Cord cord, boolean isMoved ) {
		super( color, cord, isMoved );
	}

	@Override
	public ArrayList<Cord> getValidDests(Board board, boolean isKishImportant) {
		ArrayList < Cord > returnValue = new ArrayList < Cord > ();
		int x = this.getCord().getX(), y = this.getCord().getY();

//		Baalaa Raast
		for ( int i = 1; i + x < Board.SIZE && y - i >= 0; i++ ){
			if ( board.getBlocks()[ y - i ][ x + i ].getMohre() == null )
				this.addCorToValidCors( board.getCopy(),this, returnValue, new Cord( x + i, y - i ), isKishImportant );
			else {
				if (board.getBlocks()[y - i][x + i].getMohre().getColor() != this.getColor())
					this.addCorToValidCors(board.getCopy(), this, returnValue, new Cord(x + i, y - i), isKishImportant);
				break;
			}
		}

//		PaaEn Raast
		for ( int i = 1; x + i < Board.SIZE && y + i < Board.SIZE; i++ ){
			if ( board.getBlocks()[ y + i ][ x + i ].getMohre() == null )
				this.addCorToValidCors( board.getCopy(), this, returnValue, new Cord( x + i, y + i ), isKishImportant );
			else {
				if (board.getBlocks()[y + i][x + i].getMohre().getColor() != this.getColor())
					this.addCorToValidCors(board.getCopy(), this, returnValue, new Cord(x + i, y + i), isKishImportant);
				break;
			}
		}

//		PaaEn Chap
		for ( int i = 1; x - i >= 0 && y + i < Board.SIZE; i++ ) {
			if ( board.getBlocks()[ y + i ][ x - i ].getMohre() == null )
				this.addCorToValidCors( board.getCopy(), this, returnValue, new Cord( x - i, y + i ), isKishImportant );
			else {
				if (board.getBlocks()[y + i][x - i].getMohre().getColor() != this.getColor())
					this.addCorToValidCors(board.getCopy(), this, returnValue, new Cord(x - i, y + i), isKishImportant);
				break;
			}
		}

//		Baalaa Chap
		for ( int i = 1; x - i >= 0 && y - i >= 0; i++ ){
			if ( board.getBlocks()[ y - i ][ x - i ].getMohre() == null )
				this.addCorToValidCors( board.getCopy(), this, returnValue, new Cord( x - i, y - i ), isKishImportant );
			else {
				if (board.getBlocks()[y - i][x - i].getMohre().getColor() != this.getColor())
					this.addCorToValidCors(board.getCopy(), this, returnValue, new Cord(x - i, y - i), isKishImportant);
				break;
			}
		}
		return returnValue;
	}

	@Override
	public char symbol() {
		return ( this.getColor() == Color.WHITE ? 'B' : 'b' );
	}

	@Override
	public String label() {
		return "fil";
	}

	@Override
	public Fil getCopy () {
		return new Fil( this.getColor(), this.getCord().getCopy(), this.isMoved() );
	}

}
