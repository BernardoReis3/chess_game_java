package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	public Pawn(Board board, Color color) {
		super(board, color);
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
		}
		return matrix;
	}
	
	public String toString() {
		return "P";
	}

}
