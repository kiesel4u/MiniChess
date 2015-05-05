/**
 * 
 */
package pawnWars;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;

/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
public class State {

	char[][] squares = new char[6][5];
	int round;
	char turn;
	
	char gameStatus = '?';
	
	public State() {
		makeBoard("1 W\nkqbnr\nppppp\n.....\n.....\nPPPPP\nRNBQK");
	}

	public State(String s) {
		makeBoard(s);
	}
	
	public State(Reader reader) throws IOException {
		String makeBoardString = "";
		int readChar;
		while ((readChar = reader.read()) != -1) {
			makeBoardString += (char) readChar;
		}
		makeBoard(makeBoardString);
	}
	
	public State(State board) {
		for(int i = 0; i < squares.length; i++) {
			this.squares[i] = board.squares[i].clone();
		}
		this.round = board.round;
		this.turn = board.turn;
	}

	private void makeBoard(String s) {
		String[] splitString = s.split("\n");
		System.out.println(splitString.length);
		if (Array.getLength(splitString) != 7)
			throw new Error("false String input (not 7 rows)");

		if (splitString[0].length() == 3) {
			turn = splitString[0].charAt(2);
			round = Integer.parseInt(splitString[0].substring(0, 1));
		} else if (splitString[0].length() == 4) {
			turn = splitString[0].charAt(3);
			round = Integer.parseInt(splitString[0].substring(0, 2));
		} else
			throw new Error(
					"false String input (not 3 or 4 characters in first row)");

		int i = 6;
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 5; col++) {
				squares[row][col] = splitString[i].charAt(col);
			}
			i--;
		}
	}

	public String toString() {
		String returnValue = round + " " + turn
				+ System.getProperty("line.separator");
		for (int row = 5; row >= 0; row--) {
			for (int col = 0; col < 5; col++) {
				returnValue += squares[row][col];
			}
			returnValue += System.getProperty("line.separator");
		}
		returnValue += System.getProperty("line.separator");
		return returnValue;
	}

	public void print(Writer writer) throws IOException {
		writer.write(this.toString());
	}

	public char tileColor(char tile) {
		if (Character.isUpperCase(tile))
			return 'W';
		if (Character.isLowerCase(tile))
			return 'B';
		throw new Error("not a tile on this square");
	}
}
