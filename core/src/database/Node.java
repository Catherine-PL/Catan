package database;

import java.util.Vector;
import java.util.function.Consumer;


public class Node extends Element {
	
	//private Boolean city;//czy jest miasto true-tak false-nie
	//private Boolean settlement;//czy jest osada true-tak false-nie
	//lepsza chyba logika 3-wartoœciowa
	private int building; //0-nic 1-osada 2-miasto
	private int playerNumber;//ID gracza
	private int nodeNumber;//numer ID noda
	private Vector <Node> neighbours;//referencja do s¹siednich Node
	private Vector <Tile> nearResources; //referencja do  kafli, potrzebne do surowców
	public Vector <Road> roads;
	
	public Node(int id){
		nodeNumber = id;
	}
	
	
	public Vector <Tile> getNearResources(){
		return nearResources;
	}	
	//dodaj tile do wektora  nearResources
	public void addNearResources(Tile tile) {
		this.nearResources.addElement(tile);
	}
	
	public Vector <Node> getNeighbours(){
		return neighbours;
	}
	
	public void addNeighbour(Node node){
		this.neighbours.addElement(node);
	}
	
	//sprawdza czy s¹siednie nody s¹ zabudowane
	public Boolean neighboursHasBuildins(){
		
		Boolean has=false;
		
		for(int i=0;i<neighbours.size();i++)
		{
			if(neighbours.get(i).getBuilding()>0){
				has=true;
				break;
			}
		}
		return has;
	
	}
	
	public int getBuilding(){
		return building;
	}
	public void setBuilding(int build){
		building=build;
	}
	
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	/*
	public void buildRoad(Node to) {
		// TODO Auto-generated method stub
		roads.addElement(to);
	}
	public boolean hasNoRoadTo(Node to) {
	Boolean has=false;
		
		for(int i=0;i<roads.size();i++)
		{
			//pobieram z vectora ROAD'a, a z niego pobieram NODA to i sprawdzam czy zgadza siê z moim Nodem to
			if(roads.get(i).getTo().equals(to)){
				has=true;
				break;
			}
		}
		return has;
	}
	*/
	public int getNodeNumber() {
		return nodeNumber;
	}
	
	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}
	
	
}
