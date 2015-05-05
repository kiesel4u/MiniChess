/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HumanPlayer extends PlayerModel {

	BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public Move getMove(State state) {

		ArrayList<Move> legalMoves = state.generateMovements();
		String input = "";
		Move nextMove = null;
		boolean irregularMoveFlag = true;

		System.out.print("Type your Move: ");

		do {
			try {
				input = buffRead.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				nextMove = new Move(input);
			} catch (Error e) {
				System.out.print("\nThis is not a regular move! Type a new one: ");
				continue;
			}

			for (Move mov : legalMoves) {
				if (mov.toString().equals(nextMove.toString())) {
					irregularMoveFlag = false;
					break;
				}
			}
			if (irregularMoveFlag) {
				System.out.print("\nThis move is not allowed! Type a new one: ");
			}

		} while (irregularMoveFlag);

		return nextMove;
	}

}