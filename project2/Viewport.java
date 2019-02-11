package Fresh;

final class Viewport {
	private int row;
	private int col;
	private int numRows;
	private int numCols;

	public Viewport(int numRows, int numCols) {
		this.setNumRows(numRows);
		this.setNumCols(numCols);
	}

	public boolean contains(Point p) {
		return p.getY() >= this.getRow() && p.getY() < this.getRow() + this.getNumRows()
				&& p.getX() >= this.getCol() && p.getX() < this.getCol() + this.getNumCols();
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
}
