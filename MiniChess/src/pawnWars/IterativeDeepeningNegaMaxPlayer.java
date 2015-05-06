/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

public class IterativeDeepeningNegaMaxPlayer extends PlayerModel {

	
	@Override
	public Move getMove(State state) {
		return getRandomMove(getIterativeDeepeningNegaMaxPruneMoves(state));
	}

}