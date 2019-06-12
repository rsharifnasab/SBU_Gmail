package ChessGame.ChizaayeMohrehaa.SpecificMohre;

import BasicClasses.Cord;
import ChessGame.Board;
import ChessGame.ChizaayeMohrehaa.Mohre;
import Enums.Color;

import java.util.ArrayList;

public class Vazir extends Mohre {

    public Vazir(Color color, Cord cord) {
        super(color, cord);
    }

    private Vazir( Color color, Cord cord, boolean isMoved ) {
        super( color, cord, isMoved );
    }

    @Override
    public ArrayList<Cord> getValidDests(Board board, boolean isKishImportant) {
        ArrayList<Cord> returnValue = new ArrayList<Cord>();
        Fil fil = new Fil(this.getColor(), this.getCord());
        returnValue.addAll(fil.getValidDests(board.getCopy(), isKishImportant));
        Rokh rokh = new Rokh(this.getColor(), this.getCord());
        returnValue.addAll(rokh.getValidDests(board.getCopy(), isKishImportant));
        return returnValue;
    }

    @Override
	public char symbol() {
		return ( this.getColor() == Color.WHITE ? 'Q' : 'q' );
	}

    @Override
    public String label() {
        return "vazir";
    }

    public Vazir getCopy () {
        return new Vazir( this.getColor(), this.getCord().getCopy(), isMoved() );
    }
}

