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
	// jaki� set private ArrayList <portType> ports=new ArrayList<portType>;
	private EnumSet<portType> ports=EnumSet.of(portType.NORMAL);//ka�dy taki i tak ma, a jako� zainicjowa� trzeba
	
	
		//void restrictions; // to zrobi� w tablicy?? 
	//za��my mo�e �e nie braknie tych 96 kart na razie
	
	
	public Player(int _id){
		
		id=_id;
		//TODO na razie bo nie ma jeszcze przepisania avatar�w i imion wybranych w menu
		setName("Kasia");
		setAvatar(new Texture(Gdx.files.internal("avatars/playeravatar3.png")));
		resources.put("clay", 10) ; 
		resources.put("grain", 10) ;
		resources.put("ore", 10) ;
		resources.put("sheep", 10) ;
		resources.put("wood", 10) ;
		soldierCount=0;
		points=0;
		freeRoads=0;//ka�dy ma dwie drogi za darmo na start
		
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
		//dodanie karty to dostanie 2 punkt�w zwyci�stwa
	}
	public void rmSpecialCard(SpecialCard card){
		this.specialCards.remove(card);
		this.addPoints(-2);
		//stracenie karty to strata 2 punkt�w zwyci�stwa
	}
	
	//PUNKTY
	public int getPoints() {
		return points;
	}

	public void addPoints(int points) {
		this.points =this.points + points;
	}	
	
	//Gracz sam sprawdza i liczy swoje punkty czy wygra� i wysy�a o tym komunikat
	public void checkPoints(Player player){
		String text="WYGRA� player "+ player.getId() ;//alternatywa "WYGRA� player " + players.getPlayer(player.getId()) wy�wietla nick
		
	    if(player.getPoints() >= 10) 
	    {
	    	System.out.println(text); //wygra� gracz (i jego ID do wy�wietlenia)
	    	
	    	//cze�� zwi�zana z propagacj�
	    	//zakomentowane bo wywa�a�o i error na razie
	    //	MsgText msg=new MsgText(text);//przygotowanie obiekty MsgText do wys�ania
	      //  Peer.send(msg);// tak to ma wygl�da� Sebastianie, chodzi mi o to czy tak b�dzie wygl�da� wysy�anie
	    	
	    }
	}
	
	
	//KARTY
	//zwracanie kart na r�ce

	public ArrayList<Card> getCards() {
		return cards;
	}
	//dodanie karty na r�ke
	public void addCard(Card card) {
		this.cards.add(card);
	}
	//usuni�cie karty z r�ki, potrzebne przy zagrywaniu
	public void rmCard(Card card){
		this.cards.remove(card);
	}
	//zmiena m�wi�ca nam ile mamy �o�nierzy 
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
	 * 3- nie masz tylu surowc�w
	 * 4- nie ma takiego zasobu
	 * 
	 * 6- nie mozesz zamieni� tego surowaca w tym porcie
	 * port- port w kt�rym chcesz dokona� wymiany
	 * resource- nazwa zasobu kt�ry chcesz zameni�
	 */
	public int dealBank(portType port, String resource,int quanity,String want){
		int playerGet;
		
		//sprawdza czy poda�e� dobr� nazw� zasobu
		if(resource!="wood" || resource!="grain" || resource!="sheep" || resource!="ore" || resource!="clay")
			return 4;
		
		//zwraca b��d jesli nie masz tylu surowc�w kt�re chcesz wymieni�
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
