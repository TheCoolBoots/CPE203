package project2_4_19;

final class Viewport {
	private int row;
	private int col;
	private int numRows;
	private int numCols;

	public Viewport(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
	}

	public static void shift(Viewport viewport, int col, int row) {
		viewport.col = col;
		viewport.row = row;
	}

	public static boolean contains(Viewport viewport, Point p) {
		return p.y >= viewport.row && p.y < viewport.row + viewport.numRows && p.x >= viewport.col
				&& p.x < viewport.col + viewport.numCols;
	}


	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

}
