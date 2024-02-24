package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulmerable;
	private ChessPiece promoted;
	
	
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
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulmerable;
	}
	
	public ChessPiece getPromotedPiece() {
		return promoted;
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
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> piecesFiltered = piecesBoard.stream().filter(y -> ((ChessPiece) y).getColor() == color).collect(Collectors.toList());
		for(Piece p : piecesFiltered) {
			boolean[][] possibleMoves = p.possibleMoves();
			for (int i=0; i < board.getnRows(); i++) {
				for (int j=0; j < board.getnColumns(); j++) {
					if(possibleMoves[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
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
	
	public ChessPiece replacePromotedPiece(String typePiece) {
		if(promoted == null) {
			throw new IllegalStateException("Cannot promote a non existent piece");
		}
		if(!typePiece.equals("B") && !typePiece.equals("N") && !typePiece.equals("R") && !typePiece.equals("Q")) {
			return promoted;
		}
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesBoard.remove(p);
		
		ChessPiece replacedTypePiece = newPiece(typePiece, promoted.getColor());
		board.placePiece(replacedTypePiece, pos);
		piecesBoard.add(replacedTypePiece);
		
		return replacedTypePiece;
	}
	
	public ChessPiece newPiece(String type, Color color) {
		if(type.equals("B"))
			return new Bishop(board, color);
		if(type.equals("N"))
			return new Knight(board, color);
		if(type.equals("Q"))
			return new Queen(board, color);
		return new Rook(board, color);
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position sourceP = sourcePosition.toPosition();
		Position targetP = targetPosition.toPosition();
		validateSourcePosition(sourceP);
		validateTargetPosition(sourceP, targetP);
		Piece capturedPiece = makeMove(sourceP, targetP);
		
		ChessPiece movedPiece = (ChessPiece) board.piece(targetP);
		
		//promotion move
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if(movedPiece.getColor() == Color.WHITE && targetP.getRow() == 0 || (movedPiece.getColor() == Color.BLACK && targetP.getRow() == 7)) {
				promoted = (ChessPiece) board.piece(targetP);
				promoted = replacePromotedPiece("Q");
			}
		}
		
				
		if(testCheck(currentPlayer)) {
			undoMove(sourceP, targetP, capturedPiece);
			throw new ChessException("You put yourself in check: undo move");
		}
		
		check = testCheck(getOpponent(currentPlayer)) ? true : false;
		
		if(testCheckMate(currentPlayer)) {
			checkMate = true;
		}
		changeTurn();
		
		// enPassant move
		if (movedPiece instanceof Pawn && (targetP.getRow() == sourceP.getRow() + 2 || targetP.getRow() == sourceP.getRow() - 2)) {
			enPassantVulmerable = movedPiece;
		}
		else {
			enPassantVulmerable = null;
		}
		
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturedPiece != null) {
			piecesBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// castling king side
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
			board.placePiece(rook, targetRook);
			rook.increaseMoveCount();
		}
		
		// castling queen side
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceRook);
			board.placePiece(rook, targetRook);
			rook.increaseMoveCount();
		}
		
		//em passant 
		if (p instanceof Pawn) {
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawn1;
				if(p.getColor() == Color.WHITE) {
					pawn1 = new Position(target.getRow() + 1, target.getColumn());
				}
				else {
					pawn1 = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawn1);
				capturedPieces.add(capturedPiece);
				piecesBoard.remove(capturedPiece);
				
			}
		}		
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece piece = (ChessPiece) board.removePiece(target);
		piece.decreaseMoveCount();
		board.placePiece(piece, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesBoard.add(capturedPiece);
		}
		
		// castling king side
		if (piece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, sourceRook);
			rook.decreaseMoveCount();
		}
				
		// castling queen side
		if (piece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetRook);
			board.placePiece(rook, sourceRook);
			rook.decreaseMoveCount();
		}
		
		//en passant
		if (piece instanceof Pawn) {
			if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulmerable) {
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				
				Position pawn1;
				if(piece.getColor() == Color.WHITE) {
					pawn1 = new Position(3, target.getColumn());
				}
				else {
					pawn1 = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawn1);
				
			}
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
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
	
	
}
