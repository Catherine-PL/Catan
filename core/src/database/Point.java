package database;
import database.Player;

public class Point extends Card {
DevelopType type=DevelopType.POINT;

void playCard(Player p){
		p.addPoints(1);
	};
}
