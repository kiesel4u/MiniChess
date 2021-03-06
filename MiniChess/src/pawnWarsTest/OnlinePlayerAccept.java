package pawnWarsTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pawnWars.*;

public class OnlinePlayerAccept {

	public static void main(String[] args) throws IOException {
		State state = new State();
		
		PlayerModel player = new IterativeDeepeningNegaMaxPlayer();
		
		Move saveMove;
		
		Client client = new Client("imcs.svcs.cs.pdx.edu", "3589", "Pawn", "pawnwars");
	
		System.out.print("Offer: ");
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
		String choice="";
		
		try {
			choice = buffRead.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		char c = client.accept(choice, '?');
		
		
		System.out.println("A new game starts ...");
		
		while(state.endOfTheGame == '?') {
			if((c == 'W' && state.turn == 'W') || (c == 'B' && state.turn == 'B')) {
				saveMove = player.getMove(state);
				client.sendMove(saveMove.toString());
				state.move(saveMove);
			}

			else if((c == 'W' && state.turn == 'B') || (c == 'B' && state.turn == 'W'))
				try {
					state.move(new Move(client.getMove()));
				} catch (Exception e) {
					//Catches NullPointerException, if game got interrupted.
					//Do nothing at all(!), because no more moves are expected.
				}
		}
		state.print();
		System.out.println("Player " + state.endOfTheGame + " wins");
	}

}
