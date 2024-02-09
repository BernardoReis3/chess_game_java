package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	
	private List<Piece> piecesBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	

	public ChessMatch() {
		this.board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetUp();
	}
	
	
	public int getTurn() {
		return turn;
	}


	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	private void changeTurn() {
		turn++;
		currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
	}
	
	private Color getOpponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> piecesFiltered = piecesBoard.stream().filter(y -> ((ChessPiece) y).getColor() == color).collect(Collectors.toList());
		for (Piece p : piecesFiltered) {
			if(p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + "king on the board");
	
	}

	private boolean testCheck(Color color) {
		Position kingPos = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesBoard.stream().filter(y -> ((ChessPiece) y).getColor() == getOpponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] possibleMoves = p.possibleMoves();
			if(possibleMoves[kingPos.getRow()][kingPos.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	

	public ChessPiece[][] getPieces(){
		ChessPiece[][] match = new ChessPiece[board.getnRows()][board.getnColumns()];
		for(int i=0; i < board.getnRows(); i++) {
			for(int j=0; j < board.getnColumns(); j++) {
				match[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		
		return match;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourceP){
		Position position = sourceP.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position sourceP = sourcePosition.toPosition();
		Position targetP = targetPosition.toPosition();
		validateSourcePosition(sourceP);
		validateTargetPosition(sourceP, targetP);
		Piece capturedPiece = makeMove(sourceP, targetP);
		
		if(testCheck(currentPlayer)) {
			undoMove(sourceP, targetP, capturedPiece);
			throw new ChessException("You put yourself in check: undo move");
		}
		
		check = testCheck(getOpponent(currentPlayer)) ? true : false;
		changeTurn();
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturedPiece != null) {
			piecesBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece piece = board.removePiece(target);
		board.placePiece(piece, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesBoard.add(capturedPiece);
		}
	}
	
	private void validateSourcePosition(Position source) {
		if(!board.thereIsAPiece(source)) {
			throw new ChessException("There is no piece on this position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(source)).getColor()) {
			throw new ChessException("The chosen piece is an opponent piece"); 
		}
		if(!board.piece(source).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for this piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("The piece to move can´t be moved to the target position");
		}
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesBoard.add(piece);
	}
	
	public void initialSetUp() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
	
	
}
