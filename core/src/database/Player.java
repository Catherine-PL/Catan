package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

//import network.MsgText;
//import network.*;
import database.Players;

public class Player {
	private	int id;							//metody set,get
	private String name=null;
	private int avatar;
	private	ArrayList<Card> cards=new ArrayList <Card>();			//metody add,rm,get
	private	int points;						//metody get,set,check
	private HashMap <String,Integer> resources=new HashMap <String,Integer>();	//get,change
	private	ArrayList <SpecialCard> specialCards=new ArrayList <SpecialCard>();//add,rm,get
	private int soldierCount;
	
	
		//void restrictions; // to zrobi� w tablicy?? 
	//za��my mo�e �e nie braknie tych 96 kart na razie
	
	
	public Player(int _id){
		id=_id;
		resources.put("clay", 0) ; 
		resources.put("grain", 0) ;
		resources.put("ore", 0) ;
		resources.put("sheep", 0) ;
		resources.put("wood", 0) ;
		soldierCount=0;
		points=0;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public int getAvatar(){
		return avatar;
	}
	public void setAvatar(int avatar){
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
	
	//Gracz sam sprawdza i liczy swoje punkty czy wygra� i wysy�a o tym komunikat
	public void checkPoints(Player player){
		String text="WYGRA� player "+ player.getId() ;//alternatywa "WYGRA� player " + players.getPlayer(player.getId()) wy�wietla nick
		//zmienne pomocnicze
		int specialCardCount=specialCards.size();
		Node itNode;
		
		
		//liczenie punkt�w zwyci�stwa
		//ka�da karta specialna daje 2 pubkty, vector zwraca ich ilo��
		player.addPoints(specialCardCount*2);
				
		//iteracja po wszystkich nodach, je�li m�j node to : dodaj wartosc pola building do moich punkt�w, osada 1, settlement 2
		for(int i=0; i<54;i++){
			itNode= Board.getInstance().getNode(i);
			if(itNode.getPlayerNumber()==player.getId()){
				player.addPoints(itNode.getBuilding());
			}
		}
		
	    if(getPoints() >= 10) 
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
		int i=0;
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
	
}
