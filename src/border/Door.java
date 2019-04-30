package border;

import java.util.Scanner;
import border.Border;
import observer.RiddleSubject;
import observer.RiddleObserver;

public class Door extends RiddleObserver implements Border {
	public Door(RiddleSubject subject) {
		super(subject);
		this.locked = true;
	}
	
	private Door(boolean locked) {
		this.locked = locked;
	}
	
	// public methods
	public boolean isUnlocked() {
		return !locked;
	}
	
	public boolean unlock(Scanner input) {
		if (super.isSolved()) 
			locked = false;
		else {
			locked = !super.tryToSolve(input);
			if (locked) 
				System.out.println("door still locked.\n");
			else
				System.out.println("door unlocked.\n");
		}
		
		return !locked;
	}
	
	public Door clone() {
		return new Door(!this.isUnlocked());
	}
	
	public String toString() {
		String output = "";
		if (locked)
			output = "locked ";
		else
			output = "unlocked ";
		
		return output + "door with riddle of " + super.getRiddleClass();
	}

	// members
	private boolean locked;
}
