package database;

public class Tile {
	private String type;
	private Boolean thiefState;
	
	public static class Builder{
		private String type;
		private Boolean  thiefState;
		private int number; 
		
		public Builder(String type){
			this.type = type;
		}
		
		public Tile build() {
			return new Tile(this);
	    }

	
	}
	
	private Tile(Builder builder){
		type = builder.type;
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

}

