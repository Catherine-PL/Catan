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
	
	private ArrayList <Road> roadRoad=new ArrayList<Road> ();
	
	//do wywalenia
	//private HashMap <Node,HashMap<Integer,Integer>> nodeRoadOwner=new HashMap <Node,HashMap<Integer,Integer> >();
	//do wywalenia
	//private int [][]nodeRoadOwner2=new int[][]{{-1,0,0},{-1,0,0},{-1,0,0}};
	
	
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
		this.noRoads=noRoads;
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
	
	//public HashMap <Node,HashMap<Integer,Integer>> getNodeRoadOwner(){
	//	return nodeRoadOwner;
	//}
//	public void changeNodeRoadOwner(Node node,int roadd, int owner){
//		HashMap<Integer,Integer> temp=new HashMap<Integer,Integer>();
//		
//		temp.put(roadd, owner);
//		
//		this.nodeRoadOwner.put(node,temp);
//	}
	
	
	public Integer [] getRoadsToImprove(){
		Integer[] withoutRoad={-1,-1,-1};
		
		if(this.getNoRoads().isEmpty())
			return null;
		else{
			ArrayList <Node> noRoads=new ArrayList<Node>();
			noRoads=this.getNoRoads();
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
	public ArrayList<Integer> getRoadsToImprove2(){
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


	public HashMap <Integer,Tile> getIntResources() {
		return intResources;
	}


	public void setIntResources(HashMap <Integer,Tile> intResources) {
		this.intResources = intResources;
	}


	
	
	//public int [][] getNodeRoadOwner2() {
//		return nodeRoadOwner2;
//	}
//	public void initializeNodeRoadOwner2(){
//		for(Node temp: this.neighbours){
////			int k=(temp.getNodeNumber()>=0) ? temp.getNodeNumber() : (-1);
//			//this.setNodeRoadOwner2(temp.getNodeNumber(), 0, 0);
//			this.setNodeRoadOwner2(k, 0, 0);
	//		
//		}
/*	}
	public void changeNodeRoadOwner2(Player player,int node){
		int newRoadOwner=player.getId();
		int i=0;
		for(Node temp:this.neighbours){
			if(temp.nodeNumber==node)
				break;
			else
				i++;
		}
		this.nodeRoadOwner2[i][2]=newRoadOwner;
		this.nodeRoadOwner2[i][1]=1; 
	
	}
		
*/
	/*
	public void setNodeRoadOwner2(int node,int roadState,int roadOwner) {
		int i=0;
		for(Node temp:this.neighbours){
			if(temp.nodeNumber!=node && i<3)
				i++;
			else
				break;
		}
		this.nodeRoadOwner2[i][0]=node;
		this.nodeRoadOwner2[i][1]=roadState;
		this.nodeRoadOwner2[i][2]=roadOwner;

	}
*/

	public ArrayList <Road> getRoadRoad() {
		return roadRoad;
	}


	public void setRoadRoad(ArrayList <Road> roadRoad) {
		this.roadRoad = roadRoad;
	}	
	
	//zwraca szukany Road albo null
	public Road getRoadNode(Node to2){
		for(Road r: this.roadRoad)
		if(to2.nodeNumber==r.getTo().getNodeNumber() || to2.nodeNumber==r.getFrom().getNodeNumber())
			return r;
		
		return null;
	}
	public Road getRoadInt(int toInt){
		for(Road r: this.roadRoad)
		if(toInt==r.getTo().getNodeNumber() || toInt==r.getFrom().getNodeNumber())
			return r;
		
		return null;
	}
	//wywo³anie node.nodeHasOwnedRoad(gracz)
	//i to sprawdzi czy do tego Noda wchodz¹ jakieœ drogi podanego gracza
	public Boolean nodeHasOwnedRoad(Player player){
		for(Road r:this.roadRoad){
			if(r.getOwnerID()==player.getId()){
				return true;
			}
		}
		
		return false;
	}
	
	/* Wartoœci zwracane przy budowaniu:
	 * 0 - uda³o siê 
	 * 1 - Masz za ma³o surowców
	 * 2 - Droga ju¿ istnieje
	 * 3 - nie mo¿esz tu budowaæ
	 * 4 - Node FROM nie nalezy do ciebie
	 * 5 - Nie ma drogi z From2 do To2
	 * 6	- Nie wykona³ siê zaden if 
	 */
	public int buildRoadRoad(Player player, Node from2, Node to2, int free){
		//from2.roadRoad.
		int k=-1;
		for(Road r: from2.roadRoad){
			//System.out.println("wlasciwosci drogi"+r.ID+" \nfrom"+r.getFrom().getNodeNumber()+"\n to"+r.getTo().getNodeNumber());
			//System.out.println("to2="+to2.getNodeNumber()+"owner "+to2.getPlayerNumber());
			//System.out.println("from2="+from2.getNodeNumber()+"owner"+from2.getPlayerNumber());
			//System.out.println("R To="+r.getTo().getNodeNumber());
			//System.out.println("R From="+r.getFrom().getNodeNumber());
			
			if(to2.getNodeNumber()==r.getTo().getNodeNumber())
		//	if(r.getTo().getNodeNumber()==to2.getNodeNumber())
			{
			//System.out.println("**");
				
				k=r.buildRoad(player,  from2,to2, free);
				//System.out.print(r.getState());
		//		System.out.println("If"+k+"\n"+r.getOwnerID()+from2.getPlayerNumber()+" lol "+to2.getPlayerNumber());

			//	System.out.println("IF*****"+k);
				
				break;
			}
			else// if(r.getTo().getNodeNumber()==from2.getNodeNumber()){
				if(to2.getNodeNumber()==r.getFrom().getNodeNumber()){

				
				k=r.buildRoad(player,from2,to2, free);
				//System.out.print(r.getState());
				//System.out.println("If"+k+"\n"+r.getOwnerID()+from2.getPlayerNumber()+" lol "+to2.getPlayerNumber());
			//	System.out.println("\n");
				//System.out.println(" IF ELSE*****"+k);

				break;
			}
			else
				k=6;//Nie wykona³ siê zaden if
				//System.out.println("**Nie wykona³ siê zaden if");
		
		}
		return k;
	
	}
	public void addRoadRoad(Road r){
		this.roadRoad.add(r);
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
