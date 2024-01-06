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
}
