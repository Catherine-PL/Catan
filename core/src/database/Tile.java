package database;

public class Tile {
	private String type;
	
	private Boolean thiefState;
	
	
	public static class Builder{
		private String type;
		private int buildings; 
		private Boolean  thiefState;
		
		public Builder(String type){
			this.type = type;
		}
		
		public Tile build() {
			return new Tile(this);
	    }
		
	}
	
	private Tile(Builder builder){
		type = builder.type;
		buildings = builder.buildings;
		thiefState = builder.thiefState;
		if (type == "Desert")
			thiefState = true;
	
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
		return buildings;
	}
	//ustaianie czy wioska,miasto czy nic
	public void setBuildings(int buildings) {
		this.buildings = buildings;
	}	
}

