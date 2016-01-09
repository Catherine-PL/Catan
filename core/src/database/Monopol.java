package database;



public class Monopol implements Card {
	
	DevelopType type=DevelopType.MONOPOL;
	
	public void playCard(Player player,String resource){
	
		int sum=0,count=0;
		for(Player p: Game.players){
			count=p.getResources(resource);
			p.changeResources(resource, -count);
			sum=sum+count;
		}
		player.changeResources(resource,sum);
		player.rmCard(this);
	}
	@Override
	public database.DevelopType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	@Override
	public void playCard(Player p) {
		p.rmCard(this);
		// TODO Auto-generated method stub
		
	}
}
