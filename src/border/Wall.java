package border;

import java.util.Scanner;

import observer.RiddleObserver;
import observer.RiddleSubject;

public class Wall extends RiddleObserver implements Border {
	// constructors
	public Wall() {}
	
	/**
	 * creates a Wall with member "subject" set to the parameter "subject".<p>
	 * only uses super constructor to RiddleObserver.
	 * 
	 * @param RiddleSubject to which the member "subject" is set.
	 */
	public Wall(RiddleSubject subject) {
		super(subject);
	}

	// public methods
	public boolean isUnlocked() {
		return false;
	}
	
	public boolean unlock(Scanner input) {
		System.out.println("wall can't be unlocked");
		super.tryToSolve(input);
		
		return false;
	}
	
	public Border clone() {
		return new Wall();
	}
	
	public String toString() {
		return "wall with riddle of " + super.getRiddleClass();
	}
}
