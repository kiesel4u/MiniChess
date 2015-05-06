/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWarsTest;
import java.io.IOException;

import pawnWars.*;

public class OnlinePlayer {

	public static void main(String[] args) throws IOException {
			
			State state = new State();
			
			PlayerModel player = new IterativeDeepeningNegaMaxPlayer();
			
			Move saveMove;
			
			Client client = new Client("imcs-wurzburg.svcs.cs.pdx.edu", "80", "Pawn", "pawnwars");
			char c = client.offer('?');
			
			System.out.println("A new game starts ...");
			
			while(state.endOfTheGame == '?') {
				if((c == 'W' && state.turn == 'W') || (c == 'B' && state.turn == 'B')) {
					saveMove = player.getMove(state);
					client.sendMove(saveMove.toString());
					state.move(saveMove);
				}

				else if((c == 'W' && state.turn == 'B') || (c == 'B' && state.turn == 'W'))
					state.move(new Move(client.getMove()));
				
			}
			
			System.out.println("Player " + state.endOfTheGame + " wins");
			
	}

}
