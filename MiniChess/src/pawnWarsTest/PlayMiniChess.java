/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWarsTest;

import pawnWars.*;

public class PlayMiniChess {
public static void main(String[] args) {
		
		State state= new State ();
		
		PlayerModel whitePlayer = new RandomPlayer();
		PlayerModel blackPlayer = new RandomPlayer();
		
		System.out.println("A new game starts ...");
		
		while(state.endOfTheGame == '?') {
			state.print();
			System.out.println("legal Movements for this turn are:");
			state.printLegalMoves();
			System.out.println("\n");
			if(state.turn == 'W') {
				Move movement = whitePlayer.getMove(state);
				System.out.println("White moves [" + movement.toString() + "]");
				state.move(movement);
				continue;
			} else if(state.turn == 'B') {
				Move movement = blackPlayer.getMove(state);
				System.out.println("Black moves [" + movement.toString() + "]");
				state.move(movement);
				continue;
			} else {
				throw new Error("the variable state.turn has wrong content");
			}
		}
		
		state.print();
		if (state.endOfTheGame == '=') {
			System.out.println("DRAW");
		} else {
			System.out.println("Player " + state.endOfTheGame + " wins");
		}
	}

}
