package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	} 
	
	
	public static ChessPosition readChessPosition(Scanner scanner) {
		try {
			String s = scanner.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1)); 
			return new ChessPosition(column, row);
		}
		catch(InputMismatchException ime) {
			throw new InputMismatchException("Error: input parameters do not correspond to row or column in board");
		}
		
	}
	
	public static void printMatch(ChessMatch match, List<ChessPiece> capturedPieces) {
		printBoard(match.getPieces());
		System.out.println();
		printCapturedPieces(capturedPieces);
		System.out.println("Turn: " + match.getTurn());
		if(!match.getCheckMate()) {
			System.out.println("Waiting for player: " + match.getCurrentPlayer());
			if(match.getCheck()) {
				System.out.println("Check!");
			}
		}
		else {
			System.out.println("CheckMate!");
			System.out.println("The winner is: " + match.getCurrentPlayer());
		}
		
		
	}
	
	public static void printBoard(ChessPiece[][] match) {
		for (int i = 0; i < match.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0; j < match.length; j++) {
				printPiece(match[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printBoard(ChessPiece[][] match, boolean[][] possibleMoves) {
		for (int i = 0; i < match.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0; j < match.length; j++) {
				printPiece(match[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPiece(ChessPiece piece, boolean background) {
		if(background) {
			System.out.print(ANSI_GREEN_BACKGROUND);
		}
		if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
		System.out.print(" ");
	}
	
	private static void printCapturedPieces(List<ChessPiece> capturedPieces) {
		List<ChessPiece> capturedWhite = capturedPieces.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
		List<ChessPiece> capturedBlack = capturedPieces.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
		
		System.out.println("Captured Pieces: ");
		System.out.println("White: " + Arrays.toString(capturedWhite.toArray()));
		System.out.println("Black: " + Arrays.toString(capturedBlack.toArray()));
	}

}
