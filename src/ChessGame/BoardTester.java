
package ChessGame;

import BasicClasses.Cord;
import ChessGame.ChizaayeMohrehaa.Mohre;
import ChessGame.ChizaayeMohrehaa.SpecificMohre.*;
import Enums.Color;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardTester {

	public static void main(String[] args) {

		Scanner in = new Scanner( System.in );

		Board board = new Board( true );
		System.out.println( board );

		Color turn = Color.WHITE;
		while ( true ) {
			boolean dorost = false;
			int x = 0, y = 0;
			while ( !dorost ) {
				System.out.println( "x o y?" );
				x = in.nextInt();
				y = in.nextInt();
				if ( !( board.getBlocks()[y][x].getMohre() == null || board.getBlocks()[y][x].getMohre().getColor() != turn ) )
					dorost = true;
			}
			Mohre mohre = board.getBlocks()[y][x].getMohre();
			System.out.println( mohre.getCopy().getValidDests( board.getCopy(), true ).toString() );
			System.out.println( mohre.getCopy().getValidDests( board.getCopy(), true ).toString() );
			int destX = 0, destY = 0;
			dorost = false;
			while ( !dorost ) {
				System.out.println("dest x and y?");
				destX = in.nextInt();
				destY = in.nextInt();
				if ( destX == 10 || destY == 10 )
					break;
				if ( mohre.getCopy().getValidDests( board.getCopy(), true ).contains( new Cord( destX, destY ) ) )
					dorost = true;
			}
			if ( destX == 10 || destY == 10 )
				continue;
			board.getBlocks()[destY][destX].setMohre( mohre );
			board.getBlocks()[destY][destX].getMohre().setCord( new Cord( destX, destY ) );
			board.getBlocks()[y][x].setMohre( null );
			System.out.println( board );
			turn = ( turn == Color.WHITE ? Color.BLACK : Color.WHITE );

		}

	}

}
