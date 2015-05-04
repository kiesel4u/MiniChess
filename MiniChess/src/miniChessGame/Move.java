/**
 * 
 */
package miniChessGame;

/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
public class Move {

	public Square from;
	public Square to;

	/**
	 * only in format: a1,f4
	 * 
	 * @param string
	 */
	public Move(String string) {
		if (string.length() != 5 || string.charAt(2) != '-')
			throw new Error("wrong move constructor input");

		this.from = new Square(string.charAt(0) + "" + string.charAt(1));
		this.to = new Square(string.charAt(3) + "" + string.charAt(4));
	}

	public String toString() {
		return this.from.toString() + "-" + this.to.toString();
	}

	public static void main(String args[]) {
		Move move = new Move("a1-d3");
		System.out.println(move.toString());
	}
}

