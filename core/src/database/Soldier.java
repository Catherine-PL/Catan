package database;

public class Soldier implements Card {
	DevelopType type=DevelopType.SOLDIER;
	
	public void playCard(Player p){
		Board board=Board.getInstance();
		p.addSoldier();
		
		/*
		 * Tu trzeba jako� wybra� tile
		 */
		int to=0;
		board.moveThief(p,to);//podaj� player'a kt�ry wywo�uje kart�, int to numer tile'a (z board.tiles)
		
	}

	@Override
	public DevelopType getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
