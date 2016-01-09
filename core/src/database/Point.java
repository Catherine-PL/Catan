package database;
import database.Player;

public class Point implements Card {
DevelopType type=DevelopType.POINT;

public void playCard(Player p){
		p.addPoints(1);
		p.rmCard(this);
	}



@Override
public DevelopType getType() {
	// TODO Auto-generated method stub
	return this.type;
};
}
