package observer;

import java.util.Scanner;
import observer.RiddleSubject;
import observer.Subject;
import riddle.Riddle;

public abstract class RiddleObserver implements Observer {
	// constructor
	public RiddleObserver() {}
	
	/**
	 * creates a new RiddleObserver with member "subject" set to the parameter "subject".<p>
	 * appends "this" RiddleObserver to the member "subject".
	 * 
	 * @param RiddleSubject to which the member "subject" is set.
	 */
	public RiddleObserver(RiddleSubject subject) {
		this.subject = subject;
		this.riddle = this.subject.getRiddle();
		this.subject.attachObserver(this);
	}
	
	// public methods
	public final void update() {
		riddle = subject.getRiddle();
	}
	
	/**
	 * Sets RiddleSubject member to some specific RiddleSubject. <p>
	 * Attaches this to the new RiddleSubject.
	 * Detaches this from the current RiddleSubject.
	 *
	 * @param  anotherSubject RiddleSubject to which RiddleSubject member is set.
	 */
	public final void setSubject(Subject anotherSubject) {
		if (anotherSubject instanceof RiddleSubject) {
			if (subject != null)
				subject.detachObserver(this);
			
			subject = (RiddleSubject) anotherSubject;
			riddle = subject.getRiddle();
			subject.attachObserver(this);
		}
	}
	
	public final boolean tryToSolve(Scanner input) {
		return riddle.tryToSolve(input);
	}

	public final boolean isSolved() {
		return riddle.isSolved();
	}
	
	public final String getRiddleClass() {
		return riddle.getClass().toString();
	}
	
	// members
	private RiddleSubject subject;
	private Riddle riddle;
}
