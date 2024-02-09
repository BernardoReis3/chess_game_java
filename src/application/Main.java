package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import boardgame.Board;
import boardgame.Position;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		List<ChessPiece> capturedPieces = new ArrayList<>();
		
		while(!chessMatch.getCheckMate()) {
			try {
			UI.clearScreen();
			UI.printMatch(chessMatch, capturedPieces);
			System.out.println();
			System.out.print("Source Pos: ");
			ChessPosition source = UI.readChessPosition(scanner);
			
			boolean[][] possibleMoves = chessMatch.possibleMoves(source);
			UI.clearScreen();
			UI.printBoard(chessMatch.getPieces(), possibleMoves);
			
			System.out.println();
			System.out.print("Target Pos: ");
			ChessPosition target = UI.readChessPosition(scanner);
			
			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			
			if(capturedPiece != null) {
				capturedPieces.add(capturedPiece);
			}
			
			}
			catch (ChessException ce) {
				System.out.println(ce.getMessage());
				scanner.nextLine();
			}
			catch (InputMismatchException ime) {
				System.out.println(ime.getMessage());
				scanner.nextLine();
			}
			
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, capturedPieces);
	}

}
