/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWarsTest;

import pawnWars.*;

public class PlayMiniChess {
public static void main(String[] args) {
		
		State state= new State ();
		
		PlayerAI whitePlayer = new RandomPlayer();
		PlayerAI blackPlayer = new RandomPlayer();
		
		System.out.println("A new game starts ...");
		
		while(state.endOfTheGame == '?') {
			System.out.println(state.toString());
			state.print();
			if(state.turn == 'W') {
				System.out.println("White moves ...");
				state.move(whitePlayer.getMove(state));
				continue;
			} else if(state.turn == 'B') {
				System.out.println("Black moves ...");
				state.move(blackPlayer.getMove(state));
				continue;
			} else {
				throw new Error("the variable board.onMove has wrong content");
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
