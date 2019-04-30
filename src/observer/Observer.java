package observer;

import observer.Subject;

public interface Observer {
	public void update();
	public void setSubject(Subject subject);
}
