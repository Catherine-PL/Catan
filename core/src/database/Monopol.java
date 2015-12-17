package database;

import database.Card.DevelopType;

public class Monopol {
	DevelopType type=DevelopType.MONOPOL;
	void playCard(Player player){
		//miejsce na aset
		//wybranie zasobu
		String resource="clay";
		int sum=0,count=0;
		for(Player p: ){
			count=p.getResources(resource);
			p.changeResources(resource, -count);
			sum=sum+count;
		}
		player.changeResources(resource,sum);
	}
}
