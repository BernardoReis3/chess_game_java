package boardgame;

public class Board {
	
	private int nRows;
	private int nColumns;
	private Piece[][] pieces;
	

	public Board(int nRows, int nColumns) {
		this.nRows = nRows;
		this.nColumns = nColumns;
		pieces = new Piece[nRows][nColumns];
	}
	
	
	public int getnRows() {
		return nRows;
	}

	public void setnRows(int nRows) {
		this.nRows = nRows;
	}

	public int getnColumns() {
		return nColumns;
	}

	public void setnColumns(int nColumns) {
		this.nColumns = nColumns;
	}
	
	public Piece piece(int row, int column) {
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; 
	}
}
