package database;

public class Year implements Card{
	DevelopType type=DevelopType.YEAR;
	public void playCard(Player p){
		//miejsce na aset
		//wybranie zasobu
		String resource="clay";
		p.changeResources(resource,2);
	}
	@Override
	public DevelopType getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
