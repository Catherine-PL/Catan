package database;

public class Tile {
	private String type;
	private int Buildings; //??0-nic 1-osada 2-miasto ???
	private Boolean  thiefState;
	
	
	Tile(String type){
		this.setType(type);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getThiefState() {
		return thiefState;
	}
	public void changeThiefState() {
		
		this.thiefState=!(this.thiefState);
	}
	public int getBuildings() {
		return Buildings;
	}
	//ustaianie czy wioska,miasto czy nic
	public void setBuildings(int buildings) {
		Buildings = buildings;
	}
	
}
