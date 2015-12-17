package database;

public class FreeRoads extends Card{

	DevelopType type=DevelopType.ROAD;
	
	void playCard(Player p){
		p.setFreeRoads(2);
	}
}
