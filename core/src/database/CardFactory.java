package database;

public class CardFactory {
	  //use getCard method to get object of type shape 
	   public Card getCard(String cardType){
	      if(cardType == null){
	         return null;
	      }		
	      if(cardType.equalsIgnoreCase("Year")){
	         return new Year();
	         
	      } else if(cardType.equalsIgnoreCase("Soldier")){
	         return new Soldier();
	         
	      } else if(cardType.equalsIgnoreCase("Point")){
	         return new Point();
	      }
	      else if(cardType.equalsIgnoreCase("Monopol")){
		         return new Monopol();
		      }
	      else if(cardType.equalsIgnoreCase("FreeRoads")){
		         return new FreeRoads();
		      }
	      return null;
	   }
}
