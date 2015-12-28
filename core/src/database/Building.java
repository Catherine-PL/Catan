package database;
import database.Player;

public class Building extends Element {
	int type;
	
	/* Warto�ci zwracane przy budowaniu:
	 * 0 - uda�o si� wybudowac
	 * 1 - za ma�o surowc�w
	 * 2 - pole nie jest puste
	 * 3 - nie mo�esz tu budowa�; zasada odleglosci111
	 * 4 - znajduje si� tu twoje miasto
	 * 5 - znajduje si� tu twoja osada 
	 * 8-nie masz drogi do osady
	 */
	
	
	public static int buildSettlement(Player player,Node here)// player kto buduje, here gdzie
	{
		int state = -1;
		if(here.nodeHasOwnedRoad(player) || player.getPoints()<2 ){
			if(!here.neighboursHasBuildins()){
				if(here.getBuilding()==0){
				//sprawdza czy pole jest puste(niczyje)
						if(player.getResources("grain")>=1 && player.getResources("sheep")>=1 && player.getResources("wood")>=1 && player.getResources("clay")>=1 || player.getPoints()<2) {
							here.setBuilding(1);
							here.setPlayerNumber(player.getId());
							
							//maj�c powy�ej 2 punkt�w, osiedla s� na pewno budpwane za surowce, wi�c tylko wtedy je pobieramy
							if(player.getPoints()>=2){
							player.changeResources("grain", -1);
							player.changeResources("sheep", -1);
							player.changeResources("wood", -1);
							player.changeResources("clay", -1);
							}
							else{
								player.setFreeRoads(1);
							}
							
							player.addPort(here.getPort());
							player.addPoints(1);
							player.addPlayerNodes(here);
							state = 0;
							
							
						}
						else
							state = 1;
				
				}
				else if(here.getBuilding()==1)
					state = 5;
				else if(here.getBuilding()==2)
					state = 4;
			
			}
			else
				state = 3;
		}	
		else
			state=8;
		
		return state;						
	}

	public static int buildCity(Player player, Node here)
	{
		int state = -1;
		if(!here.neighboursHasBuildins()){
			//sprawdza czy pole jest moje i ma osade czy jest puste(niczyje)
			if(here.getPlayerNumber()==player.getId() || here.getPlayerNumber()==0){
				//
				if(here.getBuilding()==1){
					if(player.getResources("grain")>=2 && player.getResources("ore")>=3){
						here.setBuilding(2);
						player.changeResources("grain", -2);
						player.changeResources("ore", -3);
						player.addPoints(1);
						player.addPlayerNodes(here);
						state = 0;
					}
					state = 1;
				}
				
				if(here.getBuilding()==2)
					state = 4;	
				
				if(here.getBuilding()==0){
					if(player.getResources("grain")>=3 && player.getResources("sheep")>=1 && player.getResources("wood")>=1 && player.getResources("clay")>=1 && player.getResources("ore")>=3){
						here.setBuilding(2);
						here.setPlayerNumber(player.getId());
						player.changeResources("grain", -3);
						player.changeResources("sheep", -1);
						player.changeResources("wood", -1);
						player.changeResources("clay", -1);
						player.changeResources("ore", -3);
						player.addPoints(2);
						player.addPlayerNodes(here);
						state = 0;
					}
						else 
							state = 1;
				}
				
			}
			else
				state = 2;
		}
		else 
			state = 3;
		
		return state;
	}
}
