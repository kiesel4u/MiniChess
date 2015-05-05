/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

public class HeuristicPlayer extends PlayerModel {

	@Override
	public Move getMove(State state) {
		return getRandomMove(getBestNextMoves(state));
	}
	
}
