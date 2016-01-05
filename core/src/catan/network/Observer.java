package catan.network;

public abstract class Observer {

	protected Subject sub;
	
	Observer(Subject subject)
	{
		this.sub = subject;
	}
		
	abstract void update();

}
