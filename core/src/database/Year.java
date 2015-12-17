package database;

public class Year extends Card{
	DevelopType type=DevelopType.YEAR;
	void playCard(Player p){
		//miejsce na aset
		//wybranie zasobu
		String resource="clay";
		p.changeResources(resource,2);
	}
}
