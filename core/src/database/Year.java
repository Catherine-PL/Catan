package database;

public class Year implements Card{
	DevelopType type=DevelopType.YEAR;
	public void playCard(Player p,String resource){
		p.changeResources(resource,2);
		p.rmCard(this);
	}
	@Override
	public DevelopType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	@Override
	public void playCard(Player p) {
		p.rmCard(this);
		// TODO Auto-generated method stub
		
	}
}
