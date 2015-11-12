package logic;

public class StartState implements State {

	   public void doAction(Context context) {
	      System.out.println("Pole mo¿e dawaæ surowce");//to te¿ z plilu
	      context.setState(this);	
	   }

	   public String toString(){
	      return "Start State";
	   }
	}