package database;


import database.Node.portType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


//import network.MsgText;
//import network.*;


public class Player {
	private	int id;							//metody set,get
	private String name=null;
	private Texture avatar=null;
	private	ArrayList<Card> cards=new ArrayList <Card>();			//metody add,rm,get
	private	int points;						//metody get,set,check
	private HashMap <String,Integer> resources=new HashMap <String,Integer>();	//get,change
	private	ArrayList <SpecialCard> specialCards=new ArrayList <SpecialCard>();//add,rm,get
	private int soldierCount;
	private int freeRoads;
	private ArrayList<Node> playerNodes=new ArrayList<Node>();
	// jakiœ set private ArrayList <portType> ports=new ArrayList<portType>;
	private EnumSet<portType> ports=EnumSet.of(portType.NORMAL);//ka¿dy taki i tak ma, a jakoœ zainicjowaæ trzeba
	
	
		//void restrictions; // to zrobiæ w tablicy?? 
	//za³ó¿my mo¿e ¿e nie braknie tych 96 kart na razie
	
	
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
		soldierCount=0;
		points=0;
		freeRoads=0;//ka¿dy ma dwie drogi za darmo na start
		
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
		this.addPoints(2);
		//dodanie karty to dostanie 2 punktów zwyciêstwa
	}
	public void rmSpecialCard(SpecialCard card){
		this.specialCards.remove(card);
		this.addPoints(-2);
		//stracenie karty to strata 2 punktów zwyciêstwa
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

	public ArrayList<Card> getCards() {
		return cards;
	}
	//dodanie karty na rêke
	public void addCard(Card card) {
		this.cards.add(card);
	}
	//usuniêcie karty z rêki, potrzebne przy zagrywaniu
	public void rmCard(Card card){
		this.cards.remove(card);
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

	/*
	 * 0- udalo sie
	 * 1- nie ma takiego portu
	 * 2- nie masz takiego portu
	 * 3- nie masz tylu surowców
	 * 4- nie ma takiego zasobu
	 * 
	 * 6- nie mozesz zamieniæ tego surowaca w tym porcie
	 * port- port w którym chcesz dokonaæ wymiany
	 * resource- nazwa zasobu który chcesz zameniæ
	 */
	public int dealBank(portType port, String resource,int quanity,String want){
		int playerGet;
		
		//sprawdza czy poda³eœ dobr¹ nazwê zasobu
		if(resource!="wood" || resource!="grain" || resource!="sheep" || resource!="ore" || resource!="clay")
			return 4;
		
		//zwraca b³¹d jesli nie masz tylu surowców które chcesz wymieniæ
		if(this.getResources(resource)<quanity)
			return 3;						
			
		switch (port){ 
			case NORMAL: {
				if(this.getPorts().contains(portType.NORMAL)){
					playerGet=quanity/4;
					this.changeResources(resource, -quanity);
					this.changeResources(want, playerGet);
					return 0;
				}
				else
					return 2;				
			}
			case THREE: {
				if(this.getPorts().contains(portType.THREE)){
					playerGet=quanity/3;
					this.changeResources(resource, -quanity);
					this.changeResources(want, playerGet);
					return 0;
				}
				else
					return 2;

			}
			case CLAY: {
				if(resource=="clay")
					if(this.getPorts().contains(portType.CLAY)){
						playerGet=quanity/2;
						this.changeResources(resource, -quanity);
						this.changeResources(want, playerGet);
						return 0;
					}
					else
						return 2;
				else
					return 6;
		
			}
			case GRAIN: {
				if(resource=="grain")
					if(this.getPorts().contains(portType.GRAIN)){
						playerGet=quanity/2;
						this.changeResources(resource, -quanity);
						this.changeResources(want, playerGet);
						return 0;
					}
					else
						return 2;
				else
					return 6;
			
			}
			case WOOD: {
				if(resource=="wood")
					if(this.getPorts().contains(portType.WOOD)){
						playerGet=quanity/2;
						this.changeResources(resource, -quanity);
						this.changeResources(want, playerGet);
						return 0;
					}
					else
						return 2;
				else
					return 6;
				
			}
			case ORE: {
				if(resource=="ore")
					if(this.getPorts().contains(portType.ORE)){
						playerGet=quanity/2;
						this.changeResources(resource, -quanity);
						this.changeResources(want, playerGet);
						return 0;
					}
					else
						return 2;
				else
					return 6;
			
			}
			case SHEEP: {
				if(resource=="sheep")
					if(this.getPorts().contains(portType.SHEEP)){
						playerGet=quanity/2;
						this.changeResources(resource, -quanity);
						this.changeResources(want, playerGet);
						return 0;
					}
					else
						return 2;
				else
					return 6;
				
			}
			default:{
				return 1;
			}
			
		  }//switch(port)

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
	
}
