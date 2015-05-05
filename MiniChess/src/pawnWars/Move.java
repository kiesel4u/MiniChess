/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

public class Move {

	public Square from;
	public Square to;

	public Move(String description) {
		if (description.length() != 5 || description.charAt(2) != '-')
			throw new Error("wrong move format");

		this.from = new Square(description.charAt(0) + "" + description.charAt(1));
		this.to = new Square(description.charAt(3) + "" + description.charAt(4));
	}

}

