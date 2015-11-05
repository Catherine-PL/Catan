package database;

public class Tile {
	private String type;
	private int buildings; //??0-nic 1-osada 2-miasto ???
	private Boolean  thiefState;
	
	
	public static class Builder(){
		private String type;
		private int buildings; //??0-nic 1-osada 2-miasto ???
		private Boolean  thiefState;
		
		public Builder(String type, int buildings, Boolean thiefState){
			this.type = type;
			this.buildings = buildings;
			this.thiefState = thiefState;
		}
		
		public Tile build() {
			return new Tile(this);
	    }
		
	}
	
	private Tile(Builder builder){
		type = builder.type;
		buildings = builder.buildings;
		thiefState = builder.thiefState;
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

