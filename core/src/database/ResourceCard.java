package database;

/*
 * DO USUNIECIA
 */
public class ResourceCard extends Card {
	private String type;
	private final int value=1;
	
	ResourceCard(String type){
		this.setType(type);
	}
	@Override
	
	public void playCard() {
		// TODO Auto-generated method stub
	}
	public int getValue(){
		return this.value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
