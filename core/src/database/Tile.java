package database;

import java.util.ArrayList;

public class Tile {
	private String type;
	private Boolean thiefState;
	
	
	private int diceNumber=-1; 			
	private ArrayList<Player> tileplayer=new ArrayList<Player>();
	private int number; 

	public static class Builder{
		private String type;
		private Boolean  thiefState=false;
		
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
	
	public int getDiceNumber() {
		return diceNumber;
	}

	public void setDiceNumber(int diceNumber) {
		this.diceNumber = diceNumber;
	}

	public ArrayList<Player> getTileplayer() {
		return tileplayer;
	}

	public void addTileplayer(Player p) {
		this.tileplayer.add(p);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}

