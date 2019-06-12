package ChessGame;

import ChessGame.ChizaayeMohrehaa.Mohre;

import java.io.Serializable;

public class Block implements Serializable{

	private Mohre mohre;    //MohreE ke tooshe!

	private boolean isSelected; //vaghT roo-e ye mohre click mikone, khooneHaaE ke mitoone bere ro barash moshakhas mikonim... in hamoon ro neshoon mide!

	public Block( Mohre mohre ) {
		this.setMohre( mohre );
	}

	public Block() {

    }

    public Block getCopy() {
		return new Block( this.getMohre().getCopy() );
	}

	public Mohre getMohre() {
		return mohre;
	}

	public void setMohre(Mohre mohre) {
		this.mohre = mohre;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

}
