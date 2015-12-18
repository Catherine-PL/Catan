package database;

import database.Card.DevelopType;

public class Monopol {
	DevelopType type=DevelopType.MONOPOL;
	void playCard(Player player){
		//miejsce na aset
		//wybranie zasobug
		String resource="clay";
		int sum=0,count=0;
		for(Player p: Game.players){
			count=p.getResources(resource);
			p.changeResources(resource, -count);
			sum=sum+count;
		}
		player.changeResources(resource,sum);
	}
}
