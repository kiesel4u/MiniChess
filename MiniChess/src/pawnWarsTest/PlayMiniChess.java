/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWarsTest;

import java.io.IOException;
import pawnWars.*;
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
			System.out.println("possible Players: R=RandomPlayer, H=HeuristicPlayer, " +
					"N=NegaMaxPlayer, I=IiterativeDeepeningNegaMaxPlayer, " +
					"HM=HumanPlayer");
			System.out.println("possible Game Modes: RvR, HvH, NvN, IvI, HMvHM, RvH, HvR," +
					" HMvR, HMvH, HvHM, NvR, RvN, NvH, HvN, NvHM, HMvN, IvR, RvI, IvH, HvI," +
					" IvN, NvI, IvHM, HMvI;\n");
			
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
			
			if (gameMode.equalsIgnoreCase("NvN")) {
				whitePlayer = new NegaMaxPlayer();
				blackPlayer = new NegaMaxPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("IvI")) {
				whitePlayer = new IterativeDeepeningNegaMaxPlayer();
				blackPlayer = new IterativeDeepeningNegaMaxPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HMvHM")) {
				whitePlayer = new HumanPlayer();
				blackPlayer = new HumanPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("RvH")) {
				whitePlayer = new RandomPlayer();
				blackPlayer = new HeuristicPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HvR")) {
				whitePlayer = new HeuristicPlayer();
				blackPlayer = new RandomPlayer();
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
			
			if (gameMode.equalsIgnoreCase("NvR")) {
				whitePlayer = new NegaMaxPlayer();
				blackPlayer = new RandomPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("RvN")) {
				whitePlayer = new RandomPlayer();
				blackPlayer = new NegaMaxPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("NvH")) {
				whitePlayer = new NegaMaxPlayer();
				blackPlayer = new HeuristicPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HvN")) {
				whitePlayer = new HeuristicPlayer();
				blackPlayer = new NegaMaxPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("NvHM")) {
				whitePlayer = new NegaMaxPlayer();
				blackPlayer = new HumanPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HMvN")) {
				whitePlayer = new HumanPlayer();
				blackPlayer = new NegaMaxPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("IvR")) {
				whitePlayer = new IterativeDeepeningNegaMaxPlayer();
				blackPlayer = new RandomPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("RvI")) {
				whitePlayer = new RandomPlayer();
				blackPlayer = new IterativeDeepeningNegaMaxPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("IvH")) {
				whitePlayer = new IterativeDeepeningNegaMaxPlayer();
				blackPlayer = new HeuristicPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HvI")) {
				whitePlayer = new HeuristicPlayer();
				blackPlayer = new IterativeDeepeningNegaMaxPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("IvN")) {
				whitePlayer = new IterativeDeepeningNegaMaxPlayer();
				blackPlayer = new NegaMaxPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("NvI")) {
				whitePlayer = new NegaMaxPlayer();
				blackPlayer = new IterativeDeepeningNegaMaxPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("IvHM")) {
				whitePlayer = new IterativeDeepeningNegaMaxPlayer();
				blackPlayer = new HumanPlayer();
				gameModeSelected = true;
			}
			
			if (gameMode.equalsIgnoreCase("HMvI")) {
				whitePlayer = new HumanPlayer();
				blackPlayer = new IterativeDeepeningNegaMaxPlayer();
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
