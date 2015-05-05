/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

import java.util.ArrayList;

public class RandomPlayer extends PlayerAI {

	@Override
	Move getMove(State state) {
		ArrayList<Move> moveList = state.generateMovements();
		return getRandomMove(moveList);
	}
}
