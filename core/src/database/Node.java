package database;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	
	private HashMap <Node,HashMap<Integer,Integer>> nodeRoadOwner=new HashMap <Node,HashMap<Integer,Integer> >();
	
	private HashMap <Integer,Tile> intResources=new HashMap<Integer,Tile>();
	ArrayList <Node> roads=new ArrayList <Node>();//przedtem by3o <Road>
	private ArrayList <Node> noRoads=new ArrayList <Node>(); 
	
	public Node(int id){
		nodeNumber = id;
	}
	
	
	public ArrayList <Tile> getNearResources(){
		return nearResources;
	}		
	//dodaj tile do wektora  nearResources
	public void addNearResources(Tile tile) {
		this.nearResources.add(tile);
	}
	
	public ArrayList <Node> getNeighbours(){
		return neighbours;
	}
	
	public void addNeighbour(Node node){
		neighbours.add(node);
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
	//zwraca nam, Tile z którego bêdziemy chcieli pobraæ surowiec
	public Tile getIntResource(int number) {
		return intResources.get(number);
	}
	
	public void setIntResources() {
		Board board=Board.getInstance();
		for(Tile temp: this.nearResources){
			
		}
			
		this.intResources.put(temp, );
	}
	*/
	
	//Kasi whisList
	
	public HashMap <Node,HashMap<Integer,Integer>> getNodeRoadOwner(){
		return nodeRoadOwner;
	}
	public void changeNodeRoadOwner(Node node,int roadd, int owner){
		HashMap<Integer,Integer> temp=new HashMap<Integer,Integer>();
		temp.put(roadd, owner);
		this.nodeRoadOwner.put(node,temp);
	}
	
	
	public Integer [] getRoadsToImprove(Node node){
		Integer[] withoutRoad={0,0,0};
		
		if(node.getNoRoads().isEmpty())
			return null;
		else{
			ArrayList <Node> noRoads=new ArrayList<Node>();
			noRoads=node.getNoRoads();
			int i=0;
			//for(int i=0; i<node.getNoRoads().size();i++){
			//	withoutRoad[i]= noRoads.get(i);				
			for(Node temp: noRoads){				
				withoutRoad[i]=temp.getNodeNumber();
				i++;
				}
			}
			return withoutRoad;
		}
	public ArrayList<Integer> getRoadsToImprove2(Node node){
		ArrayList<Integer> withoutRoad=new ArrayList<Integer>();
		
		if(node.getNoRoads().isEmpty())
			return null;
		else{
			ArrayList <Node> noRoads=new ArrayList<Node>();
			noRoads=node.getNoRoads();
						
			for(Node temp: noRoads){				
				withoutRoad.add(temp.getNodeNumber());
				}
			}
			return withoutRoad;
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
