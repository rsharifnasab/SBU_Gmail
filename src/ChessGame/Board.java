package ChessGame;

import BasicClasses.Cord;
import ChessGame.ChizaayeMohrehaa.Mohre;
import ChessGame.ChizaayeMohrehaa.SpecificMohre.*;
import Enums.Color;

import java.io.Serializable;

public class Board implements Serializable {

	public static final int SIZE = 8;

	private Block[][] blocks;

	public Board( boolean doTheInitialization ) {
		this.blocks = new Block[ Board.SIZE ][ Board.SIZE ];
		if ( doTheInitialization )
			this.initializeBlocks();
		else
			this.initializeWithEmptyBlocks();
	}

	public Board( Board board ) {
		this.blocks = new Block[ Board.SIZE ][ Board.SIZE ];
		for ( int i = 0; i < board.getBlocks().length; i++ )
			for ( int j = 0; j < board.getBlocks()[i].length; j++ ) {
		        Mohre currentMohreToCopy = board.getBlocks()[i][j].getMohre();
		        if ( currentMohreToCopy == null )
		        	this.getBlocks()[i][j] = new Block();
		        else this.getBlocks()[i][j] = new Block( currentMohreToCopy.getCopy() );
			}
	}

	public Mohre getShaah( Color color ) {
	    for ( int i = 0; i < Board.SIZE; i++ )
	    	for ( int j = 0; j < Board.SIZE; j++ ){
	        	Mohre currentMohre = this.getBlocks()[i][j].getMohre();
	        	if ( currentMohre instanceof Shaah && currentMohre.getColor() == color )
	        		return currentMohre;
			}
		return null;
	}

	private void initializeWithEmptyBlocks() {
		for ( int i = 0; i < this.getBlocks().length; i++ )
			for ( int j = 0; j < this.getBlocks()[i].length; j++ )
				this.getBlocks()[i][j] = new Block();
	}

	private void initializeBlocks() {
	    this.initializeWithEmptyBlocks();
        this.putFirstRow( 0, Color.BLACK );
        this.putSarbaazhaa( 1, Color.BLACK );
        this.putFirstRow( Board.SIZE - 1, Color.WHITE );
        this.putSarbaazhaa( Board.SIZE - 2, Color.WHITE );
	}

	private void putFirstRow( int satr, Color color ) {
	    this.blocks[satr][0] = new Block( new Rokh( color, new Cord( 0, satr ) ) );
	    this.blocks[satr][1] = new Block( new Asb( color, new Cord( 1, satr ) ) );
	    this.blocks[satr][2] = new Block( new Fil( color, new Cord( 2, satr ) ) );
	    this.blocks[satr][3] = new Block( new Vazir( color, new Cord( 3, satr ) ) );
	    this.blocks[satr][4] = new Block( new Shaah( color, new Cord( 4, satr ) ) );
	    this.blocks[satr][5] = new Block( new Fil( color, new Cord( 5, satr ) ) );
	    this.blocks[satr][6] = new Block( new Asb( color, new Cord( 6, satr ) ) );
	    this.blocks[satr][7] = new Block( new Rokh( color, new Cord( 7, satr ) ) );
    }

    private void putSarbaazhaa( int satr, Color color ) {
	    for ( int i = 0; i < Board.SIZE; i++ )
	        this.blocks[satr][i] = new Block( new Sarbaaz( color, new Cord( i, satr ) ) );
    }

    public Board getCopy () {
	    Board returnValue = new Board( false );
	    for ( int i = 0; i < returnValue.getBlocks().length; i++ )
	        for ( int j = 0; j < returnValue.getBlocks()[i].length; j++ ) {
	    		if ( this.getBlocks()[i][j].getMohre() == null )
	    			returnValue.getBlocks()[i][j] = new Block();
				else returnValue.getBlocks()[i][j] = this.getBlocks()[i][j].getCopy();
			}
	    return returnValue;
    }

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}

	@Override
	public String toString() {
		String returnValue = new String();
		for ( int i = 0; i < this.getBlocks().length; i++ ) {
			for (int j = 0; j < this.getBlocks()[i].length; j++) {
				if ( this.getBlocks()[i][j].getMohre() == null )
					returnValue = returnValue.concat( "-" );
				else returnValue = returnValue.concat(Character.toString(this.getBlocks()[i][j].getMohre().symbol()));
			}
			returnValue = returnValue.concat( "\n" );
		}
		return returnValue;
	}
}
