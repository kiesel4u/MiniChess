/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

import java.io.*;

public class State {

	char[][] squares = new char[6][5];
	int round;
	char turn;
	
	public State() {
		createChessBoard("1 W\nkqbnr\nppppp\n.....\n.....\nPPPPP\nRNBQK");
	}

	public State(String s) {
		createChessBoard(s);
	}
	
	public State(Reader reader) throws IOException {
		String boardString = "";
		int readChar;
		while ((readChar = reader.read()) != -1) {
			boardString += (char) readChar;
		}
		createChessBoard(boardString);
	}
	
	public State(State state) {
		for(int i = 0; i < squares.length; i++) {
			this.squares[i] = state.squares[i].clone();
		}
		this.round = state.round;
		this.turn = state.turn;
	}

	private void createChessBoard(String s) { //push values in char array
		String[] splitString = s.split("\n");
		if (splitString.length != 7)
			throw new Error("false String input: got " + splitString.length + ", estimated 7 rows!");

		if (splitString[0].length() == 3) {
			turn = splitString[0].charAt(2);
			round = Integer.parseInt(splitString[0].substring(0, 1));
		} else if (splitString[0].length() == 4) { //round > 10
			turn = splitString[0].charAt(3);
			round = Integer.parseInt(splitString[0].substring(0, 2));
		} else
			throw new Error(
					"false String input: incorrect format");

		int i = 6;
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 5; col++) {
				squares[row][col] = splitString[i].charAt(col);
			}
			i--;
		}
	}

	public void print() { //create string that represents the chess board and print it
		String print = round + " " + turn
				+ System.getProperty("line.separator");
		for (int row = 5; row >= 0; row--) {
			for (int col = 0; col < 5; col++) {
				print += squares[row][col];
			}
			print += System.getProperty("line.separator");
		}
		print += System.getProperty("line.separator");
		System.out.print(print);
	}

}
