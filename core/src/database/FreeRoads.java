package database;

public class FreeRoads implements Card{

	DevelopType type=DevelopType.ROAD;
	
	public void playCard(Player p){
		p.setFreeRoads(2);
		p.rmCard(this);
	}

	@Override
	public DevelopType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
}
