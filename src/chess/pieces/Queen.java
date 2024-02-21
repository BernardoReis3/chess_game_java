package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece{

	public Queen(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getnRows()][getBoard().getnColumns()];
		
		Position pos = new Position(0, 0);
		
		//above
		pos.setValues(position.getRow() - 1, position.getColumn());
		while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
			pos.setRow(pos.getRow() - 1);
		}
		if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//left
		pos.setValues(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
			pos.setColumn(pos.getColumn() - 1);
		}
		if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//right
		pos.setValues(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
			pos.setColumn(pos.getColumn() + 1);
		}
		if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//below
		pos.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
			pos.setRow(pos.getRow() + 1);
		}
		if(getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
			matrix[pos.getRow()][pos.getColumn()] = true;
		}
		
		//above and left
				
		
		return matrix;
	}

}
