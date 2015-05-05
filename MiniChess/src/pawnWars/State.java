/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

import java.io.*;

import miniChessGame.Move;
import miniChessGame.Square;

public class State {

	char[][] squares = new char[6][5];
	int round;
	char turn;
	char endOfTheGame = '?';
	
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
	
	public char colorOfPiece(char piece) {
		if (Character.isUpperCase(piece))
			return 'W';
		if (Character.isLowerCase(piece))
			return 'B';
		throw new Error("empty square / there is not a piece on this square");
	}
		
	public char colorOfPiece(Square square) {
		return this.colorOfPiece(squares[square.row][square.col]);
	}

	public char[] move(Move mov) {
		char[] returnPieces = new char[2];
		if (squares[mov.from.row][mov.from.col] == '.') {
			throw new Error("empty square / there is no piece on this square!");
		}
		if (colorOfPiece(mov.from) != this.turn) {
			throw new Error("only the other color ist allowed to move. CurrentColor: " + this.turn);
		}

		if(squares[mov.to.row][mov.to.col] == 'k') {
			endOfTheGame = 'W';
		} else if(squares[mov.to.row][mov.to.col] == 'K') {
			endOfTheGame = 'B';
		} else {
			endOfTheGame = '?';
		}

		returnPieces[0] = squares[mov.to.row][mov.to.col]; // Save captured Piece
		returnPieces[1] = squares[mov.from.row][mov.from.col]; // Save captured Piece
		
		squares[mov.to.row][mov.to.col] = squares[mov.from.row][mov.from.col];
		squares[mov.from.row][mov.from.col] = '.';
		
		if(squares[mov.to.row][mov.to.col] == 'p' && mov.to.row == 0) { 
			squares[mov.to.row][mov.to.col] = 'q';
		} else if(squares[mov.to.row][mov.to.col] == 'P' && mov.to.row == 5) {
			squares[mov.to.row][mov.to.col] = 'Q';
		}
		if (turn == 'W')
			turn = 'B';
		else {
			turn = 'W';
			round++;
		}
		
		if(round == 41) {
			endOfTheGame = '=';
		}
		
		return returnPieces;
	}
	
		public char[] move(String moveDesc) {
			Move move = new Move(moveDesc);
			return move(move);
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
