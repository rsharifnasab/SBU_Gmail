package ChessGame.ChizaayeMohrehaa.SpecificMohre;

import BasicClasses.Cord;
import ChessGame.Board;
import ChessGame.ChizaayeMohrehaa.Mohre;
import Enums.Color;

import java.util.ArrayList;

public class Sarbaaz extends Mohre{

	public Sarbaaz( Color color, Cord cord ) {
		super( color, cord );
	}

	public Sarbaaz( Color color, Cord cord, boolean isMoved ){
		super( color, cord, isMoved );
	}

	@Override
	public ArrayList<Cord> getValidDests(Board boardIn, boolean isKishImportant) {
		Board board = boardIn.getCopy();

		ArrayList < Cord > returnValue = new ArrayList < Cord > ();
		int x = this.getCord().getX(), y = this.getCord().getY();
		int zarib = ( this.getColor() == Color.WHITE ? -1 : 1 );	//Ye jooraaE dREm jahat-e jolo ro baraa-e sefid o siaah moshakhas mikonim
		if ( board.getBlocks()[ y + 1 * zarib ][ x ].getMohre() == null ) {
			this.addCorToValidCors( board.getCopy(), this, returnValue, new Cord( x, y + 1 * zarib ), isKishImportant );
//			returnValue.add( new Cord( x , y + 1 * zarib ) );
/*			if ( !this.isMoved() && board.getBlocks()[ y + 2 * zarib ][ x ].getMohre() == null ) {
				this.addCorToValidCors(board.getCopy(), this, returnValue, new Cord(x, y + 2 * zarib), isKishImportant);
//				System.out.println( this + " " + new Cord( x, y + 2 * zarib ) );
			}*/
//				returnValue.add( new Cord( x, y + 2 * zarib ) );
		}
		if ( x > 0
				&& board.getBlocks()[y + 1 * zarib][ x - 1 ].getMohre() != null
				&& board.getBlocks()[ y + 1 * zarib ][ x - 1 ].getMohre().getColor() != this.getColor() )
			this.addCorToValidCors( board.getCopy(), this, returnValue, new Cord( x - 1, y + 1 * zarib ), isKishImportant );
//			returnValue.add( new Cord( x - 1, y + 1 * zarib ) );
		if ( x < Board.SIZE - 1
				&& board.getBlocks()[ y + 1 * zarib ][ x + 1 ].getMohre() != null
				&& board.getBlocks()[ y + 1 * zarib ][ x + 1 ].getMohre().getColor() != this.getColor() )
			this.addCorToValidCors( board.getCopy(), this, returnValue, new Cord( x + 1, y + 1 * zarib ), isKishImportant );
//			returnValue.add( new Cord( x + 1, y + 1 * zarib ) );

		return returnValue;
	}

	@Override
	public char symbol() {
		return ( this.getColor() == Color.WHITE ? 'P' : 'p' );
	}

	@Override
	public String label() {
		return "sarbaaz";
	}

	public Sarbaaz getCopy () {
		return new Sarbaaz( this.getColor(), this.getCord().getCopy(), this.isMoved() );
	}

}
