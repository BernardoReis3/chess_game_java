package application;

import java.util.Scanner;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		while(true) {
			UI.printBoard(chessMatch.getPieces());
			System.out.println();
			System.out.print("Source Pos: ");
			ChessPosition source = UI.readChessPosition(scanner);
			
			System.out.println();
			System.out.print("Target Pos: ");
			ChessPosition target = UI.readChessPosition(scanner);
			
			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		}
	}

}
