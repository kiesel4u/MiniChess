/**
 * 
 */
package pawnWars;

/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
import java.util.ArrayList;
import java.util.Random;

public abstract class PlayerModel {

	private final long ITERATIVE_TIMEOUT = 6;

	public abstract Move getMove(State State);
	int counter = 0;
	float gameTime = 300.0f;

	public Move getRandomMove(ArrayList<Move> moveList) {
		Random randomGenerator = new Random();
		int randomIndex = randomGenerator.nextInt(moveList.size());
		return moveList.get(randomIndex);
	}

	public ArrayList<Move> getBestNextMoves(State State) {
		ArrayList<Move> legalMoveList = State.generateMovements();
		ArrayList<Move> returnMoveList = new ArrayList<Move>();
		char actualStateOnMove = State.turn;
		float bestScore = State.compareScore(actualStateOnMove);

		State lookupState = null;
		float moveScore = 0.0f;
		for (Move mov : legalMoveList) {
			lookupState = new State(State);
			lookupState.move(mov);
			moveScore = lookupState.compareScore(actualStateOnMove);

			if (moveScore == bestScore)
				returnMoveList.add(mov);
			if (moveScore > bestScore) {
				bestScore = moveScore;
				returnMoveList.clear();
				returnMoveList.add(mov);
			}
		}
		return returnMoveList;
	}

	public ArrayList<Move> getNegaMaxMoves(State State, int maxDepth) {
		ArrayList<Move> moveList = State.generateMovements();
		ArrayList<Move> returnMoveList = new ArrayList<Move>();
		float maxScore = -10000.0f;
		float currentScore;
		char[] savePieces = new char[2];

		for (Move move : moveList) {
			savePieces = State.move(move);
			if (State.endOfTheGame == State.otherPlayer()) { // WIN!
				returnMoveList.clear();
				returnMoveList.add(move);
				State.unmove(move, savePieces);
				return returnMoveList;
			}
			currentScore = -negaMax(State, maxDepth);

			if (currentScore > maxScore) {
				returnMoveList.clear();
				returnMoveList.add(move);
				maxScore = currentScore;
			} else if (currentScore == maxScore) {
				returnMoveList.add(move);
			}
			State.unmove(move, savePieces);
		}
		return returnMoveList;
	}

	public ArrayList<Move> getNegaMaxPruneMoves(State State, int maxDepth) {
		ArrayList<Move> moveList = State.generateMovements();
		ArrayList<Move> returnMoveList = new ArrayList<Move>();
		char[] savePieces = new char[2];
		float maxScore = -10000.0f;
		float currentScore;

		for (Move mov : moveList) {
			savePieces = State.move(mov);
			if (State.endOfTheGame == State.otherPlayer()) { // WIN!
				returnMoveList.clear();
				returnMoveList.add(mov);
				State.unmove(mov, savePieces);
				return returnMoveList;
			}
			currentScore = -negaMaxPrune(maxDepth, State, -10000000000000000000.0f, 100000000000000000000.0f);

	
			if (currentScore > maxScore) {
				returnMoveList.clear();
				returnMoveList.add(mov);
				maxScore = currentScore;
			} else if (currentScore == maxScore) {
				returnMoveList.add(mov);
			}
			State.unmove(mov, savePieces);
		}
		return returnMoveList;
	}

	public ArrayList<Move> getIiterativeDeepeningNegaMaxPruneMoves(State State) {
		ArrayList<Move> legalMoves = State.generateMovements();
		ArrayList<Move> returnMoves = new ArrayList<Move>();
		float maxScore = 10000;
		long startTime = System.currentTimeMillis();
		int d = 4;
		float alpha = -10000;
		float beta = 10000;
		char[] savePieces = new char[2];

		while (true) {
			for (Move currentMove : legalMoves) {
				if (currentMove != null) {
					savePieces = State.move(currentMove);
					if (State.endOfTheGame == State.otherPlayer()) { // Win!!!
						returnMoves.clear();
						returnMoves.add(currentMove);
						State.unmove(currentMove, savePieces);
						return returnMoves;
					}
					counter++;
					float currentScore = negamaxPruneTime(d, State, alpha, beta, startTime);
					if (currentScore == maxScore) {
						returnMoves.add(currentMove);
					} else if (currentScore < maxScore) {
						returnMoves.clear();
						returnMoves.add(currentMove);
						maxScore = currentScore;
					}
					if ((System.currentTimeMillis() - startTime) >= ((long) (ITERATIVE_TIMEOUT * 1000))) {
						System.out.println("Depth: " + d);
						counter = 0;
						State.unmove(currentMove, savePieces);
						return returnMoves;
					}
					State.unmove(currentMove, savePieces);
				}
			}
			d++;
		}
	}

	/**
	 * returns the worst Score for the player who is in State.onMove
	 * 
	 * @param State
	 * @param maxDepth
	 * @return
	 */
	public float negaMax(State State, int maxDepth) {
		float scoreValue = 0.0f;
		float returnScore = 10000.0f;
		char[] savePieces = new char[2];

		if (maxDepth <= 0)
			return State.compareScoreActualPlayer();

		ArrayList<Move> legalMoves = State.generateMovements();

		for (Move mov : legalMoves) {
			savePieces = State.move(mov);

			if (State.endOfTheGame == State.otherPlayer()) {
				scoreValue = 10000.0f;
			} else if (State.endOfTheGame == '=') {
				scoreValue = 0.0f;
			} else if (State.endOfTheGame == '?') {
				scoreValue = negaMax(State, maxDepth - 1);
			} else {
				scoreValue = -10000.0f;
			}
			returnScore = Math.min(returnScore, scoreValue);
			State.unmove(mov, savePieces);
		}
		return -returnScore;
	}

	public float negaMaxPrune(int depth, State State, float alpha, float beta) {
		if (depth <= 0) {
			return State.compareScoreActualPlayer();
		}
		float alpha2 = alpha;
		float s = -10000;
		boolean isEqual = false;
		char[] savePieces = new char[2];

		ArrayList<Move> alLegalMoves = State.generateMovements();
		for (Move currentMove : alLegalMoves) {
			savePieces = State.move(currentMove);

			if (State.endOfTheGame == State.turn) {
				s = 1000;
			} else if (State.endOfTheGame == '=') {
				s = 0;
			} else if (State.endOfTheGame == '?') {
				s = (-negaMaxPrune(depth - 1, State, -beta, -alpha));
			} else {
				throw new Error("captured own king");
			}

			if (s >= beta) {
				State.unmove(currentMove, savePieces);
				return s;
			}
			if (s == alpha) {
				isEqual = true;
			}
			if (s > alpha) {
				alpha = s;
				isEqual = false;
			}
			State.unmove(currentMove, savePieces);
		}

		if (alpha2 < alpha)
			return alpha;
		else if (alpha2 == alpha && isEqual)
			return alpha;
		else
			return -10000;
	}

	public float negamaxPruneTime(int depth, State State, float alpha, float beta, long startTime) {
		counter++;
		if (counter >= 1000 && (System.currentTimeMillis() - startTime) >= ((long) (ITERATIVE_TIMEOUT * 1000))) {
			return State.compareScoreActualPlayer();
		}

		if (depth <= 0) {
			return State.compareScoreActualPlayer();
		}
		float alpha2 = alpha;
		float s = -10000;
		boolean isEqual = false;
		char[] savePieces = new char[2];

		ArrayList<Move> alLegalMoves = State.generateMovements();
		for (Move currentMove : alLegalMoves) {
			savePieces = State.move(currentMove);

			if (State.endOfTheGame == State.otherPlayer()) {
				s = 1000;
			} else if (State.endOfTheGame == '=') {
				s = 0;
			} else if (State.endOfTheGame == '?') {
				s = (-negamaxPruneTime(depth - 1, State, -beta, -alpha, startTime));
			} else {
				s = -1000;
			}

			if (s >= beta) {
				State.unmove(currentMove, savePieces);
				return s;
			}
			if (s == alpha) {
				isEqual = true;
			}
			if (s > alpha) {
				alpha = s;
				isEqual = false;
			}
			State.unmove(currentMove, savePieces);
		}

		if (alpha2 < alpha)
			return alpha;
		else if (alpha2 == alpha && isEqual)
			return alpha;
		else
			return -10000;
	}

	public boolean timeOver(float timeLimit, float startTime, float currentTime) {
		if (currentTime - startTime > timeLimit)
			return true;
		return false;
	}
}
