package database;

import java.util.Map;
import java.util.Vector;

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
