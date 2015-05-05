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
		PrintWriter writer = new PrintWriter(System.out);
		
		try {
			state.print(writer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
}
