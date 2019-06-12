package ChessGame.ChizaayeMohrehaa.SpecificMohre;

import BasicClasses.Cord;
import ChessGame.Block;
import ChessGame.Board;
import ChessGame.ChizaayeMohrehaa.Mohre;
import Enums.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Shaah extends Mohre {

	public Shaah(Color color, Cord cord ) {
		super( color, cord );
	}

	private Shaah( Color color, Cord cord, boolean isMoved ) {
		super( color, cord, isMoved );
	}

	/*
	| | | |
	| | | |
	| | | |   //
	| | | |  //
	OOOOOOOO//
	OOOOOOOO/
	OOOOOOOO
	OOOOOOOO
	 */

	@Override
	public ArrayList<Cord> getValidDests(Board boardIn, boolean isKishImportant) {
		Board board = boardIn.getCopy();
		ArrayList < Cord > returnValue = new ArrayList < Cord > ();
		int x = this.getCord().getX();
		int y = this.getCord().getY();
		this.checkKishAndPut( board.getCopy(), returnValue, new Cord( x - 1, y - 1 ), isKishImportant );
		this.checkKishAndPut( board.getCopy(), returnValue, new Cord( x, y - 1 ), isKishImportant );
		this.checkKishAndPut( board.getCopy(), returnValue, new Cord( x + 1, y - 1 ), isKishImportant );
		this.checkKishAndPut( board.getCopy(), returnValue, new Cord( x + 1, y ), isKishImportant );
		this.checkKishAndPut( board.getCopy(), returnValue, new Cord( x + 1, y + 1 ), isKishImportant );
		this.checkKishAndPut( board.getCopy(), returnValue, new Cord( x, y + 1 ), isKishImportant );
		this.checkKishAndPut( board.getCopy(), returnValue, new Cord( x - 1, y + 1 ), isKishImportant );
		this.checkKishAndPut( board.getCopy(), returnValue, new Cord( x - 1, y ), isKishImportant );

//		In chizHaaE ke paEn neveshtam, baraa-e inan ke bebinim taraf mitoone ghal'e kone yaa na!
		if ( !this.isMoved() ) {	//Age Shaah harekat nadarde bood
			Mohre rokheEhtemaaliChap = board.getBlocks()[y][0].getMohre();	//Rokhi ke shaayad samt-e chap baashe ro zakhire mikonim
//			age mohrehe vojood daasht va rokh bood va harekat nakarde bood
			if ( rokheEhtemaaliChap != null && rokheEhtemaaliChap instanceof Rokh && !rokheEhtemaaliChap.isMoved() )
//			    Age Masiremoon khaali bood
				if ( board.getBlocks()[y][1].getMohre() == null
						&& board.getBlocks()[y][2].getMohre() == null
						&& board.getBlocks()[y][3].getMohre() == null )
					this.checkKishAndPut( board, returnValue, new Cord( 2, y ), isKishImportant );
//					returnValue.add( new Cord( 2, y ) );
			Mohre rokheEhtemaaliRast = board.getBlocks()[y][Board.SIZE-1].getMohre();	//Rokhi ke shaayad samt-e raast baashe ro zakhire mikonim
//			Age mohrehe vojood daasht va rokh bood va harekat nakarde bood
			if ( rokheEhtemaaliRast != null && rokheEhtemaaliRast instanceof Rokh && !rokheEhtemaaliRast.isMoved() )
//				Age masiremoon khaali bood
				if ( board.getBlocks()[y][6].getMohre() == null
						&& board.getBlocks()[y][5].getMohre() == null )
					this.checkKishAndPut( board, returnValue, new Cord( Board.SIZE - 2, y ), isKishImportant );
//					returnValue.add( new Cord( Board.SIZE - 2, y ) );

		}

		return returnValue;
	}

	private boolean isKish( Board board ) {
		Set < Cord > harifValidCords = this.getHarifValidCords( board );
		return ( harifValidCords.contains( this.getCord() ) );
	}

	private void checkKishAndPut( Board board, ArrayList < Cord > returnValue, Cord cord, boolean isKishImportant ) {
		if ( !isKishImportant )
			this.addCorToValidCors( board, this, returnValue, cord, false );
		else {
			Set<Cord> harifValidCords = this.getHarifValidCords(board);
			if (!harifValidCords.contains(cord))
				this.addCorToValidCors(board, this, returnValue, cord, false);
		}
	}

	@Override
	public char symbol() {
		return ( this.getColor() == Color.WHITE ? 'K' : 'k' );
	}

	@Override
	public String label() {
		return "shaah";
	}

	public Shaah getCopy() {
		return new Shaah( this.getColor(), this.getCord().getCopy(), this.isMoved() );
	}

}
