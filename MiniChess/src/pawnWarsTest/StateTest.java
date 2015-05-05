/**
 * 
 */
package pawnWarsTest;

import java.io.IOException;
import java.io.PrintWriter;

import pawnWars.State;

/**
 * @author Michael Kiesel and Ralf Landwehr
 *
 */



public class StateTest {
	
	public static void main (String args[]) {
		State state = new State();
		state.toString();
		try {
			state.print(new PrintWriter(System.out));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
