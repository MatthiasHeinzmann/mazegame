package observer;

import java.util.List;
import java.util.ArrayList;
import observer.Observer;

public abstract class Subject {
	// constructors
	public Subject() {
		observers = new ArrayList<Observer>();
	}
	
	// abstract methods
	public abstract Subject clone();
	
	// public methods
	public final void notifyObserver() {
		for (Observer observer : observers)
			observer.update();
	}
	
	// protected methods
	protected final void attachObserver(Observer observer) {
		if (!observers.contains(observer))
			observers.add(observer);
	}
	
	protected final void detachObserver(Observer observer) {
		if (!observers.contains(observer))
			observers.remove(observer);
	}
	
	// members
	private List<Observer> observers;
}
