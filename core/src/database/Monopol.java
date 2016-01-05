package database;



public class Monopol implements Card {
	DevelopType type=DevelopType.MONOPOL;
	public void playCard(Player player){
		//miejsce na aset
		//wybranie zasobu
		String resource="clay";
		int sum=0,count=0;
		for(Player p: Game.players){
			count=p.getResources(resource);
			p.changeResources(resource, -count);
			sum=sum+count;
		}
		player.changeResources(resource,sum);
	}
	@Override
	public database.DevelopType getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
