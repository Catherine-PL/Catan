package logic;

public class StopState implements State {

	   public void doAction(Context context) {
	      System.out.println("Pole zablokowane. Nie daje surowców");
	      context.setState(this);	
	   }

	   public String toString(){
	      return "Stop State";
	   }
	}