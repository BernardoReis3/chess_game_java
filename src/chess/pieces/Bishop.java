package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{

	public Bishop(Board board, Color color) {
		super(board, color);
		
	}
	
	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getnRows()][getBoard().getnColumns()];
		
		Position pos = new Position(0, 0);
		
		//above and left
		pos.setValues(position.getRow() - 1, position.getColumn() - 1);
		while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
			pos.setRow(pos.getRow() - 1);
			pos.setColumn(pos.getColumn() - 1);
		}
		if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//above and right
		pos.setValues(position.getRow() - 1, position.getColumn() + 1);
		while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
			pos.setColumn(pos.getColumn() + 1);
			pos.setRow(pos.getRow() - 1);
		}
		if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//below and right
		pos.setValues(position.getRow() + 1, position.getColumn() + 1);
		while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
			pos.setColumn(pos.getColumn() + 1);
			pos.setRow(pos.getRow() + 1);
		}
		if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//below and left
		pos.setValues(position.getRow() + 1, position.getColumn() - 1);
		while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
			pos.setRow(pos.getRow() + 1);
			pos.setColumn(pos.getColumn() - 1);
		}
		if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		return matrix;
	}
	
	

}
