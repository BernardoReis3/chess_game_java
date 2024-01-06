package application;

import chess.ChessPiece;

public class UI {
	
	public static void printBoard(ChessPiece[][] match) {
		for (int i = 0; i < match.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0; j < match.length; j++) {
				printPiece(match[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPiece(ChessPiece piece) {
		if(piece == null) {
			System.out.print("-");
		}
		else {
			System.out.print(piece);
		}
		System.out.print(" ");
	}

}
