package database;

public class Soldier {
	void playCard(Player p){
		Board board=Board.getInstance();
		p.addSoldier();
		int to=0;
		/*
		 * Tu trzeba jakoœ wybraæ tile
		 */
		
		board.moveThief(p,to);//podajê player'a który wywo³uje kartê, int to numer tile'a (z board.tiles)
		
	}
}
