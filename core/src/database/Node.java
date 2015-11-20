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
	private ArrayList <Node> neighbours=new ArrayList<Node>();//referencja do s1siednich Node
	private ArrayList <Tile> nearResources=new ArrayList<Tile>(); //referencja do  kafli, potrzebne do surowców
	private ArrayList <Node> roads=new ArrayList <Node>();//przedtem by3o <Road>
	private ArrayList <Node> noRoads=new ArrayList <Node>(); 
	
	public Node(int id){
		nodeNumber = id;
	}
	
	
	public ArrayList <Tile> getNearResources(){
		return nearResources;
	}		
	//dodaj tile do wektora  nearResources
	public void addNearResources(Tile tile) {
		this.nearResources.addElement(tile);
	}
	
	public ArrayList <Node> getNeighbours(){
		return neighbours;
	}
	
	public void addNeighbour(Node node){
		neighbours.addElement(node);
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
	public void addRoad(Node to) {
		// TODO Auto-generated method stub
		this.roads.add(to);
		this.noRoads.remove(to);
	}
	public Node getRoad(int i){
		return roads.get(i);
	}
	public boolean hasRoadTo(Node to){
		return roads.contains(to);
	}
	public boolean roadsIsEmpty(){
		return roads.isEmpty();
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
	


	public ArrayList <Node> getNoRoads() {
		return noRoads;
	}
	public void addNoRoads(Node to){
		this.noRoads.add(to);
	}

//pocz1tkowo nie ma dróg do s1siadów, wiec moge je skopiowaa z tamt1d?/
	public void setNoRoads(ArrayList <Node> noRoads) {
		this.noRoads=this.roads;
	}
	
	
/*
	public HashMap <Integer,Integer> getNeighbourRoad() {
		return neighbourRoad;
	}

	public void changeNeighbourRoad(Integer neighbour,int change) {
		this.neighbourRoad.put(neighbour, change);
	}
	public ArrayList <Integer> getRoadsToImprove(){
		ArrayList <Integer> returnList=new ArrayList <Integer>();
		for(int i=0;i<this.neighbourRoad.size();)
		    if(this.neighbourRoad.)
		return returnList;
	}
	*/
	
	
}
