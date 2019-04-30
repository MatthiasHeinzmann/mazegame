package observer;

import riddle.Riddle;

public class RiddleSubject extends Subject implements Cloneable {
	// constructors
	public RiddleSubject(Riddle riddle) {
		super();
		this.riddle = riddle;
	}
	
	// public methods
	/**
	 * clones a riddle subject by creating a new riddle subject
	 * of which the riddle is given by a cloned version of "this" riddle.
	 * 
	 * {@link riddle.Riddle#clone() clone} 
	 * @return new riddle subject with cloned riddle.
	 */
	public RiddleSubject clone() {
		return new RiddleSubject(riddle.clone());
	}
	
	public final Riddle getRiddle() {
		return riddle;
	}
	
	public final void setRiddle(Riddle riddle) {
		this.riddle = riddle;
		super.notifyObserver();
	}
	
	// members
	private Riddle riddle;
}
