package database;

/*
 * KLASA DO USUNIECIA

 */
import java.util.ArrayList;
import java.util.HashMap;

import database.*;
public class DevelopmentCard{
		
	//TO DO interakcja okineka itp, w okienku wybieram co kradne, na planszy gdzie z³odzieja postawie, co z banku wezme, gdzie wybuduje drogi
	
	public void playCard(DevelopType which,Player player, String resource) {
		// TODO Auto-generated method stub
		//Board board=Board.getInstance();
		switch(which){
		case YEAR:
			player.changeResources(resource,2);
			player.rmCard(DevelopType.YEAR);
			break;
		case MONOPOL:
			int sum=0,count=0;
			for(Player p: Game.players){
				count=p.getResources(resource);
				p.changeResources(resource, -count);
				sum=sum+count;
			}
			player.changeResources(resource,sum);
			player.rmCard(DevelopType.MONOPOL);			
			break;
			
			
		case ROAD:
			player.setFreeRoads(2);
			player.rmCard(DevelopType.ROAD);
			break;
		case POINT:
			player.addPoints(1);
			player.rmCard(DevelopType.POINT);
			break;	
		}
	}
		public void playCard(DevelopType which,Player player, int where) {
			// TODO Auto-generated method stub
			Board board=Board.getInstance();
				
			if(which==DevelopType.SOLDIER){
				player.addSoldier();		
				if(board.getWhoArmy()==null ){
					if(player.getSoldierCount()>=5)
						board.setWhoArmy(player);
				}
				else
				{
					if(board.getWhoArmy().getSoldierCount()<player.getSoldierCount())
						board.setWhoArmy(player);
				}
				board.moveThief(player,where);//podajê player'a który wywo³uje kartê, int to numer tile'a (z board.tiles)		
				
				player.rmCard(DevelopType.SOLDIER);
			}
			
			
			
			
		}
				

		

}
