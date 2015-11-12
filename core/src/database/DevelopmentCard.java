package database;
import java.util.Vector;

import database.*;

public abstract class DevelopmentCard extends Card {

	protected enum DevelopType{
		YEAR,POINT,SOLDIER,ROAD,MONOPOL
	}

	
	//TO DO interakcja okineka itp, w okienku wybieram co kradne, na planszy gdzie z�odzieja postawie, co z banku wezme, gdzie wybuduje drogi
	
	public void playCard(Player player,DevelopType which) {
		// TODO Auto-generated method stub
		
		switch(which){
		case YEAR:
			//dodanie dw�ch kart zasobu
			chooseResources(player);
			break;
		case POINT:
			//dodaje punkt po prostu
			player.addPoints(1);
			break;
		case SOLDIER:
			//przesu� z�odzieja, ukradnij 1 kart� surowca innemu graczowi, dodaj 1 zo�nierza do puli
			moveThief();
			steal(player);
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
		Vector <ResourceCard> monopol;
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
		ResourceCard card1,card2;
		
		
		
		
		player.addCard(card1);
		player.addCard(card2);
	}

	private void buildRoads(Player player) {
		Node from,to;
		// TODO interakcja wybranie dr�g na planszy

		
		//
		Road(player,from,to,1);
		Road(player,from,to,1);

	}

	private void steal(Player player) {
		// TODO interakcja wybranie kogo okra�� i wyb�r karty
		ResourceCard card;
		
		
		//
		player.addCard(card);
	}

	private void moveThief() {
		// TODO interakcja wybranie taila na thiefa
		Tile from,to;
		//1 wybierz gdzie, nie mo�na w to samo miejsce
		
		
		
		//2
		from.changeThiefState();
		to.changeThiefState();
		
	}

}
