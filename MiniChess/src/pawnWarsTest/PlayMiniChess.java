/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWarsTest;

import java.io.IOException;

import pawnWars.HeuristicPlayer;
import pawnWars.HumanPlayer;
import pawnWars.Move;
import pawnWars.PlayerModel;
import pawnWars.RandomPlayer;
import pawnWars.State;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PlayMiniChess {
	public static void main(String[] args) {
		
		State state = new State ();
		String gameMode = "";
		boolean gameModeSelected;
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
		
		PlayerModel whitePlayer = null;
		PlayerModel blackPlayer = null;
		
		do {
			System.out.print("Choose Game Mode: ");
			try {
				gameMode = buffRead.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			gameModeSelected = false;
			
			if (gameMode.equalsIgnoreCase("RvR")) {
				whitePlayer = new RandomPlayer();
				blackPlayer = new RandomPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HvH")) {
				whitePlayer = new HeuristicPlayer();
				blackPlayer = new HeuristicPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HMvHM")) {
				whitePlayer = new HumanPlayer();
				blackPlayer = new HumanPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HvR")) {
				whitePlayer = new HeuristicPlayer();
				blackPlayer = new RandomPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("RvH")) {
				whitePlayer = new RandomPlayer();
				blackPlayer = new HeuristicPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HMvH")) {
				whitePlayer = new HumanPlayer();
				blackPlayer = new HeuristicPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HvHM")) {
				whitePlayer = new HeuristicPlayer();
				blackPlayer = new HumanPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HMvR")) {
				whitePlayer = new HumanPlayer();
				blackPlayer = new RandomPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("RvHM")) {
				whitePlayer = new RandomPlayer();
				blackPlayer = new HumanPlayer();
				gameModeSelected = true;
			}
			
			System.out.println("\n");

		} while (!gameModeSelected);
		
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
