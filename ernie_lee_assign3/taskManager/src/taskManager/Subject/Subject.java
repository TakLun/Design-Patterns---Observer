package Subject;

import Observer.Observer;

public interface Subject{

	public void registerObserver(Observer o);
	public void removeObserer(Observer o);
	public void notifyObservers();
	
}
