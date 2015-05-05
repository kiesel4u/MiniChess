/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWars;

public class NegaMaxPlayer extends PlayerModel {

	private int maxDepth;
	
	public NegaMaxPlayer() {
		maxDepth = 4;
	}
	
	public NegaMaxPlayer(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	
	@Override
	public Move getMove(State state) {
		return getRandomMove(getNegaMaxPruneMoves(state, maxDepth));
	}

}