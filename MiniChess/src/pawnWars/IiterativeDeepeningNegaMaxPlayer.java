/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

public class IiterativeDeepeningNegaMaxPlayer extends PlayerModel {

	
	@Override
	public Move getMove(State state) {
		return getRandomMove(getIiterativeDeepeningNegaMaxPruneMoves(state));
	}

}