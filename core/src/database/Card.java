package database;

public abstract class Card {
	protected enum DevelopType{
		YEAR,POINT,SOLDIER,ROAD,MONOPOL
	}
	
	DevelopType type;
	void playCard(Player p){
		
	};
	DevelopType getType(){
	return type;			
	};
}
