package database;

import java.util.Vector;

public class Node extends Element {
	//private Boolean city;//czy jest miasto true-tak false-nie
	//private Boolean settlement;//czy jest osada true-tak false-nie
	//lepsza chyba logika 3-wartoœciowa
	private int building; //0-nic 1-osada 2-miasto
	private int playerNumber;//ID gracza
	private int nodeNumber;//numer ID noda
	private Vector <Node> neighbours;//referencja do s¹siednich Node
	private Vector <Tile> nearResources; //referencja do  kafli, potrzebne do surowców
	
	
	
	public Vector<Tile> getNearResources(){
		return nearResources;
	}	
	//dodaj tile do wektora  nearResources
	public void addNearResources(Tile tile) {
		this.nearResources.addElement(tile);
	}
	
	public Boolean canBuild(){
		
		
		return false;
	}
	
	
	//sprawdzanie czy Node ma
	/*
	public Boolean hasNeighbours(){
		if()
			return false;
		else
			return true;		
	}
	
	public Boolean getCity() {
		return city;
	}
	
	public void buildCity() {
		this.city = true;
	}
	
	public Boolean getSettlement() {
		return settlement;
	}
	
	public void buildSettlement() {
		this.settlement = true;
	}
	*/
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	
	
}
