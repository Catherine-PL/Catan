package database;

import java.util.Map;
import java.util.Vector;
import database.Players;

public class Player {
	private	int id;
	private	Vector <Card> cards;
	private	int points;
	private Map <String,Integer> resources;
	private	Vector <SpecialCard> specialCards;
		//void restrictions; // to zrobiæ w tablicy??
	
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
	
	public Vector<SpecialCard> getSpecialCards() {
		return specialCards;
	}
	public void addSpecialCard(Vector<SpecialCard> specialCards, SpecialCard card) {
		this.specialCards.addElement(card);
	}
	public void rmSpecialCard(Vector<SpecialCard> specialCards, SpecialCard card){
		this.specialCards.remove(card);
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	//Gracz sam sprawdza czy wygra³ i wysy³a o tym komunikat
	public void checkPoints(Player player){
	    if(getPoints() >= 10) 
	    {
	    	//System.out.println("WYGRA£ player " + players.getPlayer(player.getId())); //wygra³ gracz (i jego NICK do wyœwietlenia)
	    	System.out.println("WYGRA£ player " + player.getId()); //wygra³ gracz (i jego ID do wyœwietlenia)

	    	//wyœlij wiadomoœæ send coœ tam
	    }
	}
	
	public Vector<Card> getCards() {
		return cards;
	}
	public void addCard(Vector<Card> cards, Card card) {
		this.cards.addElement(card);
	}
	public Integer getResources(String resource) {
		return resources.get(resource);
	}
	//zmienia wartosc danego surowca o amount. 
	public void changeResources(String name, int amount) {
		resources.put(name, (resources.get(name).intValue()+amount));
	}
}
