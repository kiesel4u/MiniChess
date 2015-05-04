/**
 * 
 */
package miniChessGame;

/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class State {

	char[][] squares = new char[6][5];
	int round;
	char turn;
	
	char gameStatus = '?';
	
	final float pawn = 1.0f;
	final float bishop = 3.0f;
	final float knight = 3.0f;
	final float rook = 5.0f;
	final float queen = 9.0f;
	final float king = 10000.0f;
	
	float whitePoints = 0.0f;
	float blackPoints = 0.0f;

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
		

	public char tileColor(Square square) {
		return this.tileColor(squares[square.row][square.col]);
	}
	/**
	 * returns 'B' if 'W' and backwards
	 * if not 'B' or 'W' returns onMove
	 * @return
	 */
	public char otherPlayer() {
		if(turn == 'W')
			return 'B';
		else if(turn == 'B')
			return 'W';
		else
			return turn;
	}
	
	public char[] move(Move mov) {
		char[] returnPieces = new char[2];
		if (squares[mov.from.row][mov.from.col] == '.') {
			System.out.println(this.toString() + "Move: " + mov.toString()); // TODO debug entfernen
			throw new Error("there is no tile on this square!");
		}
		if (tileColor(mov.from) != this.turn) {
			System.out.println(this.toString() + "Move: " + mov.toString()); // TODO debug entfernen
			throw new Error("only the other color ist allowed to move. CurrentColor: " + this.turn);
		}

		if(squares[mov.to.row][mov.to.col] == 'k') {
			gameStatus = 'W';
		} else if(squares[mov.to.row][mov.to.col] == 'K') {
			gameStatus = 'B';
		} else {
			gameStatus = '?';
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
			gameStatus = '=';
		}
		
		return returnPieces;
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

	public ArrayList<Move> scan(int r, int c, int dr, int dc, boolean singleStep, boolean pawnOrtho, boolean pawnDia, char color) {
		int currentRow = r;
		int currentCol = c;
		ArrayList<Move> scanAndAdd = new ArrayList<Move>();
		do
		{
			currentRow = currentRow + dr;
			currentCol = currentCol + dc;

			if (currentRow < 0 || currentCol < 0 || currentRow >= 6	|| currentCol >= 5) // out of bounds
				break;

			if(squares[currentRow][currentCol] != '.') 	// there is a piece p at x, y 
			{
				if(tileColor(new Square(currentRow, currentCol)) == color)	// the color of p is c 
					break;
				if(!pawnOrtho)	// the color of p is not c and capture is not possible
					break;
				singleStep = true;
				
			}
			else if(!pawnDia)
				break;			
			scanAndAdd.add(new Move(new Square(r, c).toString() + "-" + new Square(currentRow, currentCol).toString()));		
			
		}
		while(!singleStep);
		return scanAndAdd;

	}

	public ArrayList<Move> scanDiag(int r, int c, boolean singleStep, char color) {
		ArrayList<Move> moveList = new ArrayList<Move>();
		moveList.addAll(scan(r, c, -1, -1, singleStep, true, true, color));
		moveList.addAll(scan(r, c, -1, 1, singleStep, true, true, color));
		moveList.addAll(scan(r, c, 1, -1, singleStep, true, true, color));
		moveList.addAll(scan(r, c, 1, 1, singleStep, true, true, color));

		return moveList;
	}

	public ArrayList<Move> scanOrtho(int r, int c, boolean singleStep, char color) {
		ArrayList<Move> moveList = new ArrayList<Move>();
		moveList.addAll(scan(r, c, 0, 1, singleStep, true, true, color));
		moveList.addAll(scan(r, c, 0, -1, singleStep, true, true, color));
		moveList.addAll(scan(r, c, 1, 0, singleStep, true, true, color));
		moveList.addAll(scan(r, c, -1, 0, singleStep, true, true, color));

		return moveList;
	}
	
	public ArrayList<Move> genTileMove(int r, int c, char tile) {
		ArrayList<Move> tileMoves = new ArrayList<Move>();
		char tileColor = tileColor(tile);
		
		switch (tile) {
		case 'K':
		case 'k':
			tileMoves.addAll(scanOrtho(r, c, true, tileColor));
			tileMoves.addAll(scanDiag(r, c, true, tileColor));
			break;
		case 'Q':
		case 'q':
			tileMoves.addAll(scanOrtho(r, c, false, tileColor));
			tileMoves.addAll(scanDiag(r, c, false, tileColor));
			break;
		case 'R':
		case 'r':
			tileMoves.addAll(scanOrtho(r, c, false, tileColor));
			break;
		case 'B':
		case 'b':
			tileMoves.addAll(scanDiag(r, c, false, tileColor));
			tileMoves.addAll(scan(r, c, 0, 1, true, false, true, tileColor));
			tileMoves.addAll(scan(r, c, 0, -1, true, false, true, tileColor));
			tileMoves.addAll(scan(r, c, 1, 0, true, false, true, tileColor));
			tileMoves.addAll(scan(r, c, -1, 0, true, false, true, tileColor));
			break;
		case 'N':
		case 'n':
			tileMoves.addAll(scan(r, c, 2, 1, true, true, true, tileColor));
			tileMoves.addAll(scan(r, c, 2, -1, true, true, true, tileColor));
			tileMoves.addAll(scan(r, c, -2, 1, true, true, true, tileColor));
			tileMoves.addAll(scan(r, c, -2, -1, true, true, true, tileColor));
			tileMoves.addAll(scan(r, c, 1, 2, true, true, true, tileColor));
			tileMoves.addAll(scan(r, c, 1, -2, true, true, true, tileColor));
			tileMoves.addAll(scan(r, c, -1, 2, true, true, true, tileColor));
			tileMoves.addAll(scan(r, c, -1, -2, true, true, true, tileColor));
			break;
		case 'P':
			tileMoves.addAll(scan(r, c, 1, 0, true, false, true, tileColor));
			tileMoves.addAll(scan(r, c, 1, -1, true, true, false, tileColor));
			tileMoves.addAll(scan(r, c, 1, 1, true, true, false, tileColor));
			break;
		case 'p':
			tileMoves.addAll(scan(r, c, -1, 0, true, false, true, tileColor));
			tileMoves.addAll(scan(r, c, -1, -1, true, true, false, tileColor));
			tileMoves.addAll(scan(r, c, -1, 1, true, true, false, tileColor));
			break;
		default:
			throw new Error("This Tile does not exist");
		}
		return tileMoves;
	}
	
	public ArrayList<Move> genMoves() {
		char tile;
		ArrayList<Move> moveList = new ArrayList<Move>();
		// Scan Board
		for (int row = 5; row >= 0; row--) {
			for (int col = 0; col < 5; col++) {
				tile = squares[row][col];
				if (tile == '.')
					continue;
				else if (tileColor(tile) != this.turn)
					continue;
				else
					moveList.addAll(genTileMove(row, col, tile));
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
	
	public void printLegalMoves(Writer writer) throws IOException {
		writer.write(this.legalMovesToString(this.genMoves()));
	}

	public static void main(String args[]) throws FileNotFoundException,
			IOException {

		System.out.println(new File("otherBoardTest").getAbsoluteFile());
		State board = new State("12 W\nk.b.r\np..pp\nP.p..\n....P\n.R.PK\n.B...");
//      Writer writer = new FileWriter("output.txt");
		Writer writer = new PrintWriter(System.out);
		board.print(writer);
		board.printLegalMoves(writer);
		writer.close();
	}
}

