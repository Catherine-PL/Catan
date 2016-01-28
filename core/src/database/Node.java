package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;//nawet nie wiem co to 



public class Node extends Element {
	
	public enum portType{
		NORMAL,THREE,GRAIN,SHEEP,WOOD,ORE,CLAY
	}
	private int building; //0-nic 1-osada 2-miasto
	private int playerNumber;//ID gracza
	private Player player;
	private int nodeNumber;//numer ID noda
	private portType port;
	private ArrayList <Node> neighbours=new ArrayList<Node>();//referencja do s1siednich Node
	private ArrayList <Tile> nearResources=new ArrayList<Tile>(); //referencja do  kafli, potrzebne do surowc�w
	private ArrayList <Road> roads=new ArrayList<Road> ();
	
	private HashMap <Integer,Tile> intResources=new HashMap<Integer,Tile>();//zastanowi� si� czy to w og�le potrzebne	
	private ArrayList <Node> improvedRoads=new ArrayList <Node>();//zastanowi� si� czy to w og�le potrzebne
	private ArrayList <Node> noRoads=new ArrayList <Node>(); //zastanowi� si� czy to w og�le potrzebne
	
	//konstruktor
	public Node(int id){
		nodeNumber = id;
		port=portType.NORMAL;
		building=0;
		
	}
	
	
	//#####Tile
	public ArrayList <Tile> getNearResources(){
		return nearResources;
	}		
	//dodaj tile do wektora  nearResources
	public void addNearResources(Tile tile) {
		this.nearResources.add(tile);
	}
	
	//#####neighbours
	public ArrayList <Node> getNeighbours(){
		return neighbours;
	}
	
	public void addNeighbour(Node node){
		neighbours.add(node);
	}
	
	//sprawdza czy s�siednie nody s� zabudowane
	public boolean twoSpaceRule3(){
		
		for(Road r:this.roads){
			if(r.getFrom().building!=0 && this.nodeNumber!=r.getFrom().nodeNumber || r.getTo().building!=0 && this.nodeNumber!=r.getTo().nodeNumber){
				return false;
			}
		}
		return true;
		
	}
	//####buildings
	public int getBuilding(){
		return this.building;
	}
	public void setBuilding(int build){
		this.building=build;
	}
	
	///###player
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	///####NOde
	public int getNodeNumber() {
		return nodeNumber;
	}
	
	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}
	
	///####Roads
	public void addImprovedRoads(Node to) {	
		this.improvedRoads.add(to);
		this.noRoads.remove(to);
	}
	
	public Node getImprovedRoads(int i){
		return improvedRoads.get(i);
	}
	//na hasImprovedRoadTo
	public boolean hasRoadTo(Node to){
		return improvedRoads.contains(to);
	}
	public boolean hasRoadRoadTo(Node to){
		Boolean have=false;
		
		for(Road r: this.getRoads())
		{
			if(r.getFrom().getNodeNumber()==to.getNodeNumber() || r.getTo().getNodeNumber()==to.getNodeNumber())
				have=true;
		}
		
		return have;
	}
	
	


	public ArrayList <Node> getImprovedRoads() {
		return improvedRoads;
	}
	public ArrayList <Node> getNoRoads() {
		return noRoads;
	}
	public void addNoRoads(Node to){
		this.noRoads.add(to);
	}

//pocz1tkowo nie ma dr�g do s1siad�w, wiec moge je skopiowaa z tamt1d?/
	public void setNoRoads(ArrayList <Node> noRoads) {
		this.noRoads=noRoads;
	}

	
	//Kasi whisList
	
	public ArrayList<Road> getRoads2Improve(){
		ArrayList<Road> withoutRoad=new ArrayList<Road>();
		for(Road r: this.getRoads()){
			if(r.getState()<0)
				withoutRoad.add(r);
		}
		return withoutRoad;
	}
	
	public ArrayList<Integer> getRoadsIdImprove(){
		ArrayList<Integer> withoutRoad=new ArrayList<Integer>();
		for(Road r: this.getRoads()){
			if(r.getState()<0)
				withoutRoad.add(r.getID());
		}
		return withoutRoad;
	}
	///przemy�le� czy potrzebne
	public ArrayList<Integer> getNodesFromRoadsImprove(){
		ArrayList<Integer> withoutRoad=new ArrayList<Integer>();
		
		if(this.getNoRoads().isEmpty())
			return null;
		else{
			ArrayList <Node> noRoads=new ArrayList<Node>();
			noRoads=this.getNoRoads();
						
			for(Node temp: noRoads){				
				withoutRoad.add(temp.getNodeNumber());
				}
			}
			return withoutRoad;
		}

//na razie zostaje, lepiej jak zrobie metode wywylywana przez player, ktory przeiteruje
	public HashMap <Integer,Tile> getIntResources() {
		return intResources;
	}
	public void setIntResources(HashMap <Integer,Tile> intResources) {
		this.intResources = intResources;
	}


	public ArrayList <Road> getRoads() {
		return roads;
	}


	public void setRoads(ArrayList <Road> roadRoad) {
		this.roads = roadRoad;
	}	
	public void addRoadRoad(Road r){
		this.roads.add(r);
	}
	//zwraca szukany Road albo null
	public Road getRoadNode(Node to2){
		for(Road r: this.roads)
		if(to2.nodeNumber==r.getTo().getNodeNumber() || to2.nodeNumber==r.getFrom().getNodeNumber())
			return r;
		
		return null;
	}
	public Road getRoadInt(int toInt){
		for(Road r: this.roads)
		if(toInt==r.getTo().getNodeNumber() || toInt==r.getFrom().getNodeNumber())
			return r;
		
		return null;
	}
	//wywo�anie node.nodeHasOwnedRoad(gracz)
	//i to sprawdzi czy do tego Noda wchodz� jakie� drogi podanego gracza
	public Boolean nodeHasOwnedRoad(Player player){
		for(Road r:this.roads){
			if(r.getOwnerID()==player.getId()){
				return true;
			}
		}
		
		return false;
	}
	
	/* Warto�ci zwracane przy budowaniu:
	 * 0 - uda�o si� 
	 * 1 - Masz za ma�o surowc�w
	 * 2 - Droga ju� istnieje
	 * 3 - nie mo�esz tu budowa�
	 * 4 - Node FROM nie nalezy do ciebie
	 * 5 - Nie ma drogi z From2 do To2
	 * 6	- Nie wykona� si� zaden if 
	 * 10- nie ma takiej drogi przy tym node
	 */
	
	public int buildRoad(Player player, int id){
		System.out.println("Node.buildRoad trying to build a road");
		Road r=Board.getInstance().boardRoads.get(id);
		if(this.getRoads().contains(r))
		{
			r.buildRoad(player,r.getTo(),r.getFrom());
			r.buildRoad(player,r.getFrom(),r.getTo());
			System.out.println("Road r updated");
			return 0;
			//return r.buildRoad(player,r.getFrom(),r.getTo());
		}
		//return r.buildRoad(player,r.getFrom(),r.getTo());
		else
			System.out.println("Road not updated");
			return 10;		
	}
	
	
	
//###porty
	public portType getPort() {
		return port;
	}

	public void setPort(portType port) {
		this.port=port;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
