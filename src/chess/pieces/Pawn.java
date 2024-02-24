package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	private ChessMatch match;
	
	public Pawn(Board board, Color color, ChessMatch match) {
		super(board, color);
		this.match = match;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getnRows()][getBoard().getnColumns()];
		
		Position pos = new Position(0, 0);
		
		//white piece
		if(getColor() == Color.WHITE) {
			pos.setValues(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
				matrix[pos.getRow()][pos.getColumn()] = true;
			}
			pos.setValues(position.getRow() - 2, position.getColumn());
			Position pos2 = new Position(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos) && getBoard().positionExists(pos2) && !getBoard().thereIsAPiece(pos2) && getMoveCount() == 0) {
				matrix[pos.getRow()][pos.getColumn()] = true;
			}
			pos.setValues(position.getRow() - 1, position.getColumn() - 1);
			if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
				matrix[pos.getRow()][pos.getColumn()] = true;
			}
			pos.setValues(position.getRow() - 1, position.getColumn() + 1);
			if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
				matrix[pos.getRow()][pos.getColumn()] = true;
			}
			// en passant white side
			if(position.getRow() == 3) {
				Position leftSide = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(leftSide) && isThereOpponentPiece(leftSide) && getBoard().piece(leftSide) == match.getEnPassantVulnerable()) {
					matrix[leftSide.getRow() - 1][leftSide.getColumn()] = true;
				}
				Position rightSide = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(rightSide) && isThereOpponentPiece(rightSide) && getBoard().piece(rightSide) == match.getEnPassantVulnerable()) {
					matrix[rightSide.getRow() - 1][leftSide.getColumn()] = true;
				}
				
			}
					
		}
		else {
			pos.setValues(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
				matrix[pos.getRow()][pos.getColumn()] = true;
			}
			pos.setValues(position.getRow() + 2, position.getColumn());
			Position pos2 = new Position(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos) && getBoard().positionExists(pos2) && !getBoard().thereIsAPiece(pos2) && getMoveCount() == 0) {
				matrix[pos.getRow()][pos.getColumn()] = true;
			}
			pos.setValues(position.getRow() + 1, position.getColumn() - 1);
			if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
				matrix[pos.getRow()][pos.getColumn()] = true;
			}
			pos.setValues(position.getRow() + 1, position.getColumn() + 1);
			if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
				matrix[pos.getRow()][pos.getColumn()] = true;
			}
			
			// en passant black side
			if(position.getRow() == 4) {
				Position leftSide = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(leftSide) && isThereOpponentPiece(leftSide) && getBoard().piece(leftSide) == match.getEnPassantVulnerable()) {
					matrix[leftSide.getRow() + 1][leftSide.getColumn()] = true;
				}
				Position rightSide = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(rightSide) && isThereOpponentPiece(rightSide) && getBoard().piece(rightSide) == match.getEnPassantVulnerable()) {
					matrix[rightSide.getRow() + 1][leftSide.getColumn()] = true;
				}
							
			}
		}
		return matrix;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
