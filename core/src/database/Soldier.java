package database;

public class Soldier {
	void playCard(Player p){
		Board board=Board.getInstance();
		p.addSoldier();
		int to=0;
		/*
		 * Tu trzeba jako� wybra� tile
		 */
		
		board.moveThief(p,to);//podaj� player'a kt�ry wywo�uje kart�, int to numer tile'a (z board.tiles)
		
	}
}
