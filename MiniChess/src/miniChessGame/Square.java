/**
 * 
 */
package miniChessGame;

/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
public class Square {

	public int row;
	public int col;

	public Square(String desc) {
		if (desc.length() != 2)
			throw new Error("illegal square desc lenght");

		char colStr = desc.charAt(0);
		char rowStr = desc.charAt(1);

		if (colStr < 'a' || colStr > 'e')
			throw new Error("illegal square desc column");
		if (rowStr < '1' || rowStr > '6')
			throw new Error("illegal square desc row");

		this.row = rowStr - 49;
		this.col = colStr - 97;
	}

	public Square(int row, int col) {
		if (col < 0 || col > 4)
			throw new Error("illegal square desc column");
		if (row < 0 || row > 5)
			throw new Error("illegal square desc row");

		this.row = row;
		this.col = col;

	}

	public String toString() {
		String columnString = String.valueOf((char) (col + 97));
		return columnString + Integer.toString(row + 1);
	}
}

