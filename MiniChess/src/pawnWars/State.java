/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

import java.io.*;
import java.util.ArrayList;

public class State {

	char[][] squares = new char[6][5];
	int round;
	char turn;
	char endOfTheGame = '?';
	
	final float pawn = 1.0f;
	final float bishop = 3.0f;
	final float knight = 3.0f;
	final float rook = 5.0f;
	final float queen = 9.0f;
	final float king = 10000.0f;
	
	float whitePoints = 0.0f;
	float blackPoints = 0.0f;
	
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

	public ArrayList<Move> scan(int row, int col, int directionRow, int directionCol, 
			boolean singleStep, boolean pawnOrtho, boolean pawnDia, char colorOfPiece) {
		int currentRow = row;
		int currentCol = col;
		ArrayList<Move> scanAndAdd = new ArrayList<Move>();
		do
		{
			currentRow = currentRow + directionRow;
			currentCol = currentCol + directionCol;

			if (currentRow < 0 || currentCol < 0 || currentRow >= 6	|| currentCol >= 5) // out of bounds
				break;

			if(squares[currentRow][currentCol] != '.') 	// there is a piece p at x, y 
			{
				if(colorOfPiece(new Square(currentRow, currentCol)) == colorOfPiece)	// the color of p is c 
					break;
				if(!pawnOrtho)	// the color of p is not c and capture is not possible
					break;
				singleStep = true;
				
			}
			else if(!pawnDia)
				break;			
			scanAndAdd.add(new Move(new Square(row, col).toString() + "-" + new Square(currentRow, currentCol).toString()));		
			
		}
		while(!singleStep);
		return scanAndAdd;

	}
	
	public ArrayList<Move> scanDiag(int row, int col, boolean singleStep, char colorOfPiece) {
		ArrayList<Move> moveList = new ArrayList<Move>();
		moveList.addAll(scan(row, col, -1, -1, singleStep, true, true, colorOfPiece));
		moveList.addAll(scan(row, col, -1, 1, singleStep, true, true, colorOfPiece));
		moveList.addAll(scan(row, col, 1, -1, singleStep, true, true, colorOfPiece));
		moveList.addAll(scan(row, col, 1, 1, singleStep, true, true, colorOfPiece));

		return moveList;
	}

	public ArrayList<Move> scanOrtho(int row, int col, boolean singleStep, char colorOfPiece) {
		ArrayList<Move> moveList = new ArrayList<Move>();
		moveList.addAll(scan(row, col, 0, 1, singleStep, true, true, colorOfPiece));
		moveList.addAll(scan(row, col, 0, -1, singleStep, true, true, colorOfPiece));
		moveList.addAll(scan(row, col, 1, 0, singleStep, true, true, colorOfPiece));
		moveList.addAll(scan(row, col, -1, 0, singleStep, true, true, colorOfPiece));

		return moveList;
	}
	
	public ArrayList<Move> generateMovementOfPiece(int row, int col, char piece) {
		ArrayList<Move> pieceMovement = new ArrayList<Move>();
		char colorOfPiece = colorOfPiece(piece);
		
		switch (piece) {
		// King
		case 'K':
		case 'k':
			pieceMovement.addAll(scanOrtho(row, col, true, colorOfPiece));
			pieceMovement.addAll(scanDiag(row, col, true, colorOfPiece));
			break;
			
		// Queen	
		case 'Q':
		case 'q':
			pieceMovement.addAll(scanOrtho(row, col, false, colorOfPiece));
			pieceMovement.addAll(scanDiag(row, col, false, colorOfPiece));
			break;
			
		// Rook	
		case 'R':
		case 'r':
			pieceMovement.addAll(scanOrtho(row, col, false, colorOfPiece));
			break;
			
		// Bishop	
		case 'B':
		case 'b':
			pieceMovement.addAll(scanDiag(row, col, false, colorOfPiece));
			pieceMovement.addAll(scan(row, col, 0, 1, true, false, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, 0, -1, true, false, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, 1, 0, true, false, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, -1, 0, true, false, true, colorOfPiece));
			break;
			
		// Knight
		case 'N':
		case 'n':
			pieceMovement.addAll(scan(row, col, 2, 1, true, true, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, 2, -1, true, true, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, -2, 1, true, true, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, -2, -1, true, true, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, 1, 2, true, true, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, 1, -2, true, true, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, -1, 2, true, true, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, -1, -2, true, true, true, colorOfPiece));
			break;
			
		// Pawn	
		case 'P':
			pieceMovement.addAll(scan(row, col, 1, 0, true, false, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, 1, -1, true, true, false, colorOfPiece));
			pieceMovement.addAll(scan(row, col, 1, 1, true, true, false, colorOfPiece));
			break;
		case 'p':
			pieceMovement.addAll(scan(row, col, -1, 0, true, false, true, colorOfPiece));
			pieceMovement.addAll(scan(row, col, -1, -1, true, true, false, colorOfPiece));
			pieceMovement.addAll(scan(row, col, -1, 1, true, true, false, colorOfPiece));
			break;
			
		default:
			throw new Error("This piece does not exist!");
		}
		
		return pieceMovement;
	}
	
	public ArrayList<Move> generateMovements() {
		char piece;
		ArrayList<Move> moveList = new ArrayList<Move>();
		// Scan Board
		for (int row = 5; row >= 0; row--) {
			for (int col = 0; col < 5; col++) {
				piece = squares[row][col];
				if (piece == '.')
					continue;
				else if (colorOfPiece(piece) != this.turn)
					continue;
				else
					moveList.addAll(generateMovementOfPiece(row, col, piece));
			}
		}

		return moveList;
	}
	
	public String legalMovesToString(ArrayList<Move> moveList) {
		String returnValue = "";
		for(Move mov : moveList) {
			returnValue += "[" + mov.toString() + "], ";
		}
		return returnValue;
	}
	
	public void printLegalMoves() {
		System.out.print(this.legalMovesToString(this.generateMovements()));
	}
	
	public void unmove(Move mov, char[] pieces) {
		squares[mov.to.row][mov.to.col] = pieces[0];
		squares[mov.from.row][mov.from.col] = pieces[1];
		
		if (turn == 'B')
			turn = 'W';
		else {
			turn = 'B';
			round--;
		}
	}
	
	public float[] sumScores() {
		float[] returnValue = new float[2];
		float returnValueB = 0.0f;
		float returnValueW = 0.0f;
		for (int row = 5; row >= 0; row--) {
			for (int col = 0; col < 5; col++) {
				if(squares[row][col] == 'p')
					returnValueB += pawn;
				else if(squares[row][col] == 'n')
					returnValueB += knight;
				else if(squares[row][col] == 'b')
					returnValueB += bishop;
				else if(squares[row][col] == 'r')
					returnValueB += rook;
				else if(squares[row][col] == 'q')
					returnValueB += queen;
				else if(squares[row][col] == 'k')
					returnValueB += king;
				else if(squares[row][col] == 'P')
					returnValueW += pawn;
				else if(squares[row][col] == 'N')
					returnValueW += knight;
				else if(squares[row][col] == 'B')
					returnValueW += bishop;
				else if(squares[row][col] == 'R')
					returnValueW += rook;
				else if(squares[row][col] == 'Q')
					returnValueW += queen;
				else if(squares[row][col] == 'K')
					returnValueW += king;
			}
		}
		returnValue[0] = returnValueW;
		returnValue[1] = returnValueB;
		return returnValue;
	}
	
	public float compareScore(char color) {
		float[] scoreField = sumScores();
		if(color == 'W')
			return scoreField[0]-scoreField[1];
		if(color == 'B')
			return scoreField[1]-scoreField[0];
		throw new Error("Illegal Color");
	}
	
	public float compareScoreActualPlayer() {
		return this.compareScore(this.turn);
	}
	
	public char otherPlayer() {
		if(turn == 'W')
			return 'B';
		else if(turn == 'B')
			return 'W';
		else
			return turn;
	}
}
