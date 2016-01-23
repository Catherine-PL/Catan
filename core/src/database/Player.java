package database;


import database.Node.portType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


//import network.MsgText;
//import network.*;
import database.Players;

public class Player implements Serializable{
	private	int id;							//metody set,get
	private String name=null;
	private transient Texture avatar=null;
	//private	ArrayList<DevelopType> cards=new ArrayList <DevelopType>();			//metody add,rm,get
	private HashMap <DevelopType,Integer> developCards= new HashMap <DevelopType,Integer>();
	private	int points;						//metody get,set,check
	private HashMap <String,Integer> resources=new HashMap <String,Integer>();	//get,change
	private	ArrayList <SpecialCard> specialCards=new ArrayList <SpecialCard>();//add,rm,get
	private int soldierCount;
	private boolean biggestArmy;
	private int longestRoadDistance;
	private boolean longestRoad;
	private int freeRoads;
	private ArrayList<Node> playerNodes=new ArrayList<Node>();
	// jakiœ set private ArrayList <portType> ports=new ArrayList<portType>;
	private EnumSet<portType> ports=EnumSet.of(portType.NORMAL);//ka¿dy taki i tak ma, a jakoœ zainicjowaæ trzeba
	
	
	
	public Player(String name, Texture avatar, int _id)
	{

		id=_id;
		setName(name);
		setAvatar(avatar);
		resources.put("clay", 10) ; 
		resources.put("grain", 10) ;
		resources.put("ore", 10) ;
		resources.put("sheep", 10) ;
		resources.put("wood", 10) ;
		developCards.put(DevelopType.MONOPOL, 0);
		developCards.put(DevelopType.POINT, 0);
		developCards.put(DevelopType.ROAD, 0);
		developCards.put(DevelopType.SOLDIER, 0);
		developCards.put(DevelopType.YEAR, 0);

		
		soldierCount=0;
		longestRoadDistance=0;
		points=0;
		freeRoads=0;
		
	}

	
	
	public Player(int _id){
		
		id=_id;
		//TODO na razie bo nie ma jeszcze przepisania avatarów i imion wybranych w menu
		setName("Kasia");
		setAvatar(new Texture(Gdx.files.internal("avatars/playeravatar3.png")));
		resources.put("clay", 10) ; 
		resources.put("grain", 10) ;
		resources.put("ore", 10) ;
		resources.put("sheep", 10) ;
		resources.put("wood", 10) ;
		developCards.put(DevelopType.MONOPOL, 0);
		developCards.put(DevelopType.POINT, 0);
		developCards.put(DevelopType.ROAD, 0);
		developCards.put(DevelopType.SOLDIER, 0);
		developCards.put(DevelopType.YEAR, 0);

		
		soldierCount=0;
		longestRoadDistance=0;
		points=0;
		freeRoads=0;
		
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public Texture getAvatar(){
		return avatar;
	}
	public void setAvatar(Texture avatar){
		this.avatar=avatar;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	//KARY SPECIALNE
	public ArrayList<SpecialCard> getSpecialCards() {
		return specialCards;
	}
	public void addSpecialCard( SpecialCard card) {
		this.specialCards.add(card);
	}
	public void rmSpecialCard(SpecialCard card){
		this.specialCards.remove(card);
	}
	
	//PUNKTY
	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points =this.points + points;
	}	
	
	//Gracz sam sprawdza i liczy swoje punkty czy wygra³ i wysy³a o tym komunikat
	public void checkPoints(Player player){
		String text="WYGRA£ player "+ player.getId() ;//alternatywa "WYGRA£ player " + players.getPlayer(player.getId()) wyœwietla nick
	
		
		
	    if(player.getPoints() >= 10) 
	    {
	    	System.out.println(text); //wygra³ gracz (i jego ID do wyœwietlenia)
	    	
	    	//czeœæ zwi¹zana z propagacj¹
	    	//zakomentowane bo wywa³a³o i error na razie
	    //	MsgText msg=new MsgText(text);//przygotowanie obiekty MsgText do wys³ania
	      //  Peer.send(msg);// tak to ma wygl¹daæ Sebastianie, chodzi mi o to czy tak bêdzie wygl¹daæ wysy³anie
	    	
	    }
	}
	
	
	//KARTY
	//zwracanie kart na rêce

	public ArrayList<DevelopType> getCards() {
		ArrayList <DevelopType> nowe=new ArrayList<DevelopType>();
		if( this.developCards.get(DevelopType.MONOPOL)>0)
			nowe.add(DevelopType.MONOPOL);
		if( this.developCards.get(DevelopType.SOLDIER)>0)
			nowe.add(DevelopType.SOLDIER);
		if( this.developCards.get(DevelopType.YEAR)>0)
			nowe.add(DevelopType.YEAR);
		if( this.developCards.get(DevelopType.ROAD)>0)
			nowe.add(DevelopType.ROAD);
		if( this.developCards.get(DevelopType.POINT)>0)
			nowe.add(DevelopType.POINT);
		
		return nowe;
		
	}
	//zmiena mówi¹ca nam ile mamy ¿o³nierzy 
	public int getSoldierCount(){
		return soldierCount;
	}
	public void addSoldier(){
		this.soldierCount=this.soldierCount+1;
	}
	
	//ZASOBY
	public Integer getResources(String resource) {
		return resources.get(resource);
	}
	//zmienia wartosc danego surowca o amount. 
	public void changeResources(String name, int amount) {
		resources.put(name, (resources.get(name).intValue()+amount));
	
	}
	
	
	public static void main(String [ ] args){
		Player p1=new Player(1);
		System.out.println("WELCOME IN PLAYER CLASS");
		System.out.println(p1.getPoints());

		p1.addPoints(5);
		System.out.println(p1.getId());
		System.out.println(p1.getPoints());
		
	}
	//HANDEL
	public int dealBank(){
		return 0;
	}
	
	
	//PORTY

	public EnumSet<portType> getPorts() {
		return ports;
	}

	public void addPort(portType ports) {
		this.ports.add(ports);
	}

	public int getFreeRoads() {
		return freeRoads;
	}

	public void setFreeRoads(int freeRoads) {
		this.freeRoads = freeRoads;
	}

	public ArrayList<Node> getPlayerNodes() {
		return playerNodes;
	}

	public void addPlayerNodes(Node node) {
		if(!this.playerNodes.contains(node))
		this.playerNodes.add(node);
	}

	public boolean getBiggestArmy() {
		return biggestArmy;
	}

	public void setBiggestArmy(boolean biggestArmy) {
		this.biggestArmy = biggestArmy;
	}

	public boolean getLongestRoad() {
		return longestRoad;
	}

	public void setLongestRoad(boolean longestRoad) {
		this.longestRoad = longestRoad;
	}

	public int getLongestRoadDistance() {
		return longestRoadDistance;
	}

	public void setLongestRoadDistance(int longestRoadDistance) {
		this.longestRoadDistance = longestRoadDistance;
	}

	public void rmCard(DevelopType type) {
		// TODO Auto-generated method stub
		this.developCards.put(type,(developCards.get(type).intValue()-1));		
	}
	public void addCard(DevelopType type) {
		this.developCards.put(type,(developCards.get(type).intValue()+1));	
	}
	
}
