package database;

public class Soldier implements Card {
	DevelopType type=DevelopType.SOLDIER;
	
	public void playCard(Player p){
		Board board=Board.getInstance();
		p.addSoldier();
		
		/*
		 * Tu trzeba jakoœ wybraæ tile
		 */
		int to=0;
		board.moveThief(p,to);//podajê player'a który wywo³uje kartê, int to numer tile'a (z board.tiles)
		
	}

	@Override
	public DevelopType getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
