package database;

/*
 * KLASA DO USUNIECIA
 */
import java.util.ArrayList;

import database.*;
import database.Road;
public abstract class DevelopmentCard extends Card {

	protected enum DevelopType{
		YEAR,POINT,SOLDIER,ROAD,MONOPOL
	}

	
	//TO DO interakcja okineka itp, w okienku wybieram co kradne, na planszy gdzie z³odzieja postawie, co z banku wezme, gdzie wybuduje drogi
	
	public void playCard(Player player,DevelopType which) {
		// TODO Auto-generated method stub
		Tile to=null;
		Player player2=null;
		switch(which){
		case YEAR:
			//dodanie dwóch kart zasobu
			chooseResources(player);
			break;
		case POINT:
			//dodaje punkt po prostu
			player.addPoints(1);
			break;
		case SOLDIER:
			//przesuñ z³odzieja, ukradnij 1 kartê surowca innemu graczowi, dodaj 1 zo³nierza do puli
			//interakcja wybranie gdzie go przeniesc
			moveThief(to);
			//interackja wybranie kogo okraœæ
			steal(player,player2);//player2 ten od którego kradnê
			player.addSoldier();
			break;
		case ROAD:
			buildRoads(player);
			break;
		case MONOPOL:
			chooseMonopol(player);
			break;
		}
		
		
	}

	private void chooseMonopol(Player player) {
		// TODO Auto-generated method stub
		ArrayList <ResourceCard> monopol = null;
		String type;
		Player thatPlayer;
		//wybranie monopolu
		
		
		
		// dodanie kart wszystkich do vectora
		
		
		
		//
		//przekazanie kart graczowi z wektora
		while(!monopol.isEmpty()){
			player.addCard(monopol.get(0));
			monopol.remove(0);
		}
		
	}

	private void chooseResources(Player player) {
		// TODO interakcja wybranie kart
		ResourceCard card1 = null,card2 = null;
		
		//wybranie kart
		
		
		player.addCard(card1);
		player.addCard(card2);
	}

	private void buildRoads(Player player) {
		Node from = null,to = null;
		Road r1=null,r2=null;
		// TODO interakcja wybranie dróg na planszy

		
		//wybranie dróg raz
	//	r1.buildRoad(player, from, to, 1);
		
		
		//wybranie dróg drugi raz
		
		//r2.buildRoad(player, from, to, 1);

	}

	//tu przekazujemy gracza od którego kradniemy
	private void steal(Player player,Player player2) {
		//
		ResourceCard card = null;
		ArrayList <Card> cardsToSteal=player2.getCards();//pobieramy jego karty, spokojnie do rysowania bêdzie zawsze max 7
		int i=0;
		
		//tu po drodze trzeba wybraæ i
		
		card=(ResourceCard) cardsToSteal.get(i);
		//
		player.addCard(card);
	}

	//przekazuje gdzie ma przemieœciæ siê z³odziej, przy okazji sprawdzam czy gracz nie chce go daæ w to samo miejsce
	
	}

}
