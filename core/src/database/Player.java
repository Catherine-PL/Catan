package database;

import java.util.Map;
import java.util.Vector;

import catan.network.MsgText;
import database.Players;

public class Player {
	private	int id;							//metody set,get
	private	Vector <Card> cards;			//metody add,rm,get
	private	int points;						//metody get,set,check
	private Map <String,Integer> resources;	//get,change
	private	Vector <SpecialCard> specialCards;//add,rm,get
	private int soldierCount;
	
	
		//void restrictions; // to zrobiæ w tablicy?? 
	//za³ó¿my mo¿e ¿e nie braknie tych 96 kart na razie
	
	
	Player(int _id){
		id=_id;
		resources.put("clay", 0) ; 
		resources.put("grain", 0) ;
		resources.put("ore", 0) ;
		resources.put("sheep", 0) ;
		resources.put("wood", 0) ;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	//KARY SPECIALNE
	public Vector<SpecialCard> getSpecialCards() {
		return specialCards;
	}
	public void addSpecialCard( SpecialCard card) {
		this.specialCards.addElement(card);
	}
	public void rmSpecialCard( SpecialCard card){
		this.specialCards.remove(card);
	}
	
	//PUNKTY
	public int getPoints() {
		return points;
	}
	//set zmieni³em na add
	public void addPoints(int points) {
		this.points =this.points + points;
	}	
	
	//Gracz sam sprawdza i liczy swoje punkty czy wygra³ i wysy³a o tym komunikat
	public void checkPoints(Player player){
		String text="WYGRA£ player "+ player.getId() ;//alternatywa "WYGRA£ player " + players.getPlayer(player.getId()) wyœwietla nick
		//zmienne pomocnicze
		int specialCardCount=specialCards.size();
		Node itNode;
		
		
		//liczenie punktów zwyciêstwa
		//ka¿da karta specialna daje 2 pubkty, vector zwraca ich iloœæ
		player.addPoints(specialCardCount*2);
				
		//iteracja po wszystkich nodach, jeœli mój node to : dodaj wartosc pola building do moich punktów, osada 1, settlement 2
		for(int i; i<54;i++){
			itNode= Board.getInstance().getNode(i);
			if(itNode.getPlayerNumber()==player.getId()){
				player.addPoints(itNode.getBuilding());
			}
		}
		
	    if(getPoints() >= 10) 
	    {
	    	System.out.println(text); //wygra³ gracz (i jego ID do wyœwietlenia)
	    	
	    	//czeœæ zwi¹zana z propagacj¹
	    	MsgText msg=new MsgText(text);//przygotowanie obiekty MsgText do wys³ania
	    	Peer.send(msg);// tak to ma wygl¹daæ Sebastianie, chodzi mi o to czy tak bêdzie wygl¹daæ wysy³anie
	    	
	    }
	}
	
	
	//KARTY
	//zwracanie kart na rêce

	public Vector<Card> getCards() {
		return cards;
	}
	//dodanie karty na rêke
	public void addCard(Card card) {
		this.cards.addElement(card);
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
		int i=0;
		resources.put(name, (resources.get(name).intValue()+amount));
	
	}
	
}
