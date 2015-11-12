package database;

public class SpecialCard extends Card {
	private String type;
	@Override
	public void playCard() {
		// TODO Auto-generated method stub
		
	}
	SpecialCard(int i){
		String army="Largest army",road="Longest road";
		if(i==1) setType(army);
		if(i==2) setType(road);
	}
	public void setType(String type){
		this.type=type;
	}

}
