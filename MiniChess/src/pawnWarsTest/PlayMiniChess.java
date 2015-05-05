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
			if(state.turn == 'W') {
				System.out.println("White moves [" + whitePlayer.getMove(state).toString() + "]");
				state.move(whitePlayer.getMove(state));
				continue;
			} else if(state.turn == 'B') {
				System.out.println("Black moves [" + blackPlayer.getMove(state).toString() + "]");
				state.move(blackPlayer.getMove(state));
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
