/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */
package pawnWarsTest;
import java.io.*;

import pawnWars.*;

public class OnlinePlayOffer {

	public static void main(String[] args) throws IOException {
			
			State state = new State();
			
			PlayerModel player = new HumanPlayer();
			
			Move saveMove;
			
			Client client = new Client("imcs-wurzburg.svcs.cs.pdx.edu", "80", "Pawn", "pawnwars");
			
			System.out.println("Select Player:");
			System.out.println("(1) RandomPlayer");
			System.out.println("(2) HeuristicPlayer");
			System.out.println("(3) NegaMaxPlayer");
			System.out.println("(4) IterativeDeepeningNegaMaxPlayer");
			System.out.println("(5) HumanPlayer");
			
			BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
			boolean selected;
			String choice="";
			
			do
			{
				try {
					choice = buffRead.readLine();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				selected= true;
				
				switch(Integer.parseInt(choice)){
				case 1:
					player= new RandomPlayer();
					break;
				case 2:
					player= new HeuristicPlayer();
					break;
				case 3:
					player= new NegaMaxPlayer();
					break;
				case 4:
					player= new IterativeDeepeningNegaMaxPlayer();
					break;
				case 5:
					break;
				 default: 
					 System.out.println("No option selected, try again!");
					 selected=false;
                 break;
				}
				
			}while(!selected);
			
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
