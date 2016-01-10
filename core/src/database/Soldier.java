package database;

public class Soldier implements Card {
	DevelopType type=DevelopType.SOLDIER;
	
	public void playCard(Player p,int to){
		Board board=Board.getInstance();
		p.addSoldier();		
		if(board.getWhoArmy()==null ){
			if(p.getSoldierCount()>=5)
				board.setWhoArmy(p);
		}
		else
		{
			if(board.getWhoArmy().getSoldierCount()<p.getSoldierCount())
				board.setWhoArmy(p);
		}
		board.moveThief(p,to);//podaj� player'a kt�ry wywo�uje kart�, int to numer tile'a (z board.tiles)		
		
		p.rmCard(this);
	}

	@Override
	public DevelopType getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public void playCard(Player p) {
		p.rmCard(this);
		// TODO Auto-generated method stub
		
	}
}
