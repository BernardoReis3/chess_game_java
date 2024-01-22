package boardgame;

public class Board {
	
	private int nRows;
	private int nColumns;
	private Piece[][] pieces;
	

	public Board(int nRows, int nColumns) {
		if(nRows < 1 || nColumns < 1) {
			throw new BoardException("Error creating board: there needs to at least 1 row and 1 column");
		}
		this.nRows = nRows;
		this.nColumns = nColumns;
		pieces = new Piece[nRows][nColumns];
	}
	
	
	public int getnRows() {
		return nRows;
	}


	public int getnColumns() {
		return nColumns;
	}

	
	public Piece piece(int row, int column) {
		if(!positionExists(row, column)) {
			throw new BoardException("Error: Position does not exist");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Error: Position does not exist");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("Error: There is already a piece on:" + position); 
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; 
	}
	
	public Piece removePiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Error: Position does not exist");
		}
		if(piece(position) == null) {
			return null;
		}
		Piece aux_piece = piece(position);
		aux_piece.position = null;
		pieces[position.getColumn()][position.getColumn()] = null;
		
		return aux_piece;
	}
	
	private boolean positionExists(int row, int column) {
		if(row >= 0 && row < nRows && column >= 0 && column < nColumns) {
			return true;
		}
		return false;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Error: Position does not exist");
		}
		if(piece(position) != null) {
			return true;
		}
		return false;
	}
}
