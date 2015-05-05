/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

public class Move {

	public Square origin;
	public Square destination;

	public Move(String description) {
		if (description.length() != 5 || description.charAt(2) != '-')
			throw new Error("wrong move format");

		this.origin = new Square(description.charAt(0) + "" + description.charAt(1));
		this.destination = new Square(description.charAt(3) + "" + description.charAt(4));
	}

}

