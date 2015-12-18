package database;

public abstract class Card {
	String type;
	protected enum DevelopType{
		YEAR,POINT,SOLDIER,ROAD,MONOPOL
	}
	void playCard(){
		
	};
	String getType(){
	return type;			
	};
}
