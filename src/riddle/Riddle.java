package riddle;

import java.util.Scanner;

public interface Riddle extends Cloneable {
	public boolean tryToSolve(Scanner input);
	public boolean isSolved();
	
	/**
	 * creates a new riddle (cloned riddle) which is the same as "this" riddle.
	 * 
	 * @return a cloned riddle
	 */
	public Riddle clone();
}
