package database;

public class Soldier {
	void playCard(Player p){
		Board board=Board.getInstance();
		p.addSoldier();
		
		/*
		 * Tu trzeba jakoœ wybraæ tile
		 */
		int to=0;
		board.moveThief(p,to);//podajê player'a który wywo³uje kartê, int to numer tile'a (z board.tiles)
		
	}
}
