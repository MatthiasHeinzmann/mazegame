package riddle;

import java.util.Scanner;

public class NoRiddle implements Riddle {
	public Riddle clone() {
		return this;
	}

	public boolean tryToSolve(Scanner input) {
		return true;
	}

	public boolean isSolved() {
		return true;
	}

}
