package logic;

public class StartState implements State {

	   public void doAction(Context context) {
	      System.out.println("Pole mo¿e dawaæ surowce");
	      context.setState(this);	
	   }

	   public String toString(){
	      return "Start State";
	   }
	}