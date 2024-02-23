package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	private ChessMatch match;
	
	public King(Board board, Color color, ChessMatch match) {
		super(board, color);
		this.match = match;
	}
	
	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece)getBoard().piece(position);
		return piece != null && piece.getColor() != getColor();
	}
	
	private boolean testRookCastlingMove(Position pos) {
		ChessPiece piece = (ChessPiece) getBoard().piece(pos);
		return piece != null && piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getnRows()][getBoard().getnColumns()];
		
		Position pos = new Position(0, 0);
		
		//above
		pos.setValues(position.getRow() - 1, position.getColumn());
		if(getBoard().positionExists(pos) && canMove(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//below
		pos.setValues(position.getRow() - 1, position.getColumn());
		if(getBoard().positionExists(pos) && canMove(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//left
		pos.setValues(position.getRow(), position.getColumn() - 1);
		if(getBoard().positionExists(pos) && canMove(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//right
		pos.setValues(position.getRow(), position.getColumn() + 1);
		if(getBoard().positionExists(pos) && canMove(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//left above
		pos.setValues(position.getRow() - 1, position.getColumn() - 1);
		if(getBoard().positionExists(pos) && canMove(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//right above
		pos.setValues(position.getRow() - 1, position.getColumn() + 1);
		if(getBoard().positionExists(pos) && canMove(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//left below
		pos.setValues(position.getRow() + 1, position.getColumn() - 1);
		if(getBoard().positionExists(pos) && canMove(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//right below
		pos.setValues(position.getRow() + 1, position.getColumn() + 1);
		if(getBoard().positionExists(pos) && canMove(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//Castling Move
		if(getMoveCount() == 0 && !match.getCheck()) {
			//king side
			Position pos1 = new Position(position.getRow(), position.getColumn() + 3);
			if(testRookCastlingMove(pos1)) {
				Position posRight1 = new Position(position.getRow(), position.getColumn() + 1);
				Position posRight2 = new Position(position.getRow(), position.getColumn() + 2);
				if(getBoard().piece(posRight1) == null && getBoard().piece(posRight2) == null) {
					matrix[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			//queen side
			Position pos2 = new Position(position.getRow(), position.getColumn() - 4);
			if(testRookCastlingMove(pos2)) {
				Position posLeft1 = new Position(position.getRow(), position.getColumn() - 1);
				Position posLeft2 = new Position(position.getRow(), position.getColumn() - 2);
				Position posLeft3 = new Position(position.getRow(), position.getColumn() - 3);
				if(getBoard().piece(posLeft1) == null && getBoard().piece(posLeft2) == null && getBoard().piece(posLeft3) == null) {
					matrix[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		
		return matrix;
	}

}
