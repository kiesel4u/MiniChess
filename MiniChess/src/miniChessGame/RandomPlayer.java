/**
 * 
 */
package miniChessGame;

/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */

import java.util.ArrayList;

public class RandomPlayer extends PlayerAI {

	@Override
	Move getMove(State board) {
		ArrayList<Move> moveList = board.genMoves();
		return getRandomMove(moveList);
	}
}
