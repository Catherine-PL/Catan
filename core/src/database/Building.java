package database;
import database.Player;

public class Building extends Element {
	int type;
	static String textNB="NIe mo¿esz tu budowaæ";//no building possible
	static String textNR="Masz za ma³o surowców";//no resources 
	static String textNY="To pole nie jest puste";//not yours
	static String textYC="Ju¿ znajduje siê tu twoje miasto";
	
	public static void buildSettlement(Player player,Node here)// player kto buduje, here gdzie
	{
		if(!here.neighboursHasBuildins()){
			//sprawdza czy pole jest puste(niczyje)
			if(here.getPlayerNumber()==0){
				if(player.getResources("grain")>=1 && player.getResources("sheep")>=1 && player.getResources("wood")>=1 && player.getResources("clay")>=1) {
					here.setBuilding(1);
					here.setPlayerNumber(player.getId());
					player.changeResources("grain", -1);
					player.changeResources("sheep", -1);
					player.changeResources("wood", -1);
					player.changeResources("clay", -1);
				}
				else
					System.out.println(textNR);
			}
			else
				System.out.println(textNY);
		}
		else 
			System.out.println(textNB);
				
			
	}

	public static void buildCity(Player player, Node here)
	{
				
		if(!here.neighboursHasBuildins()){
			//sprawdza czy pole jest moje i ma osade czy jest puste(niczyje)
			if(here.getPlayerNumber()==player.getId() || here.getPlayerNumber()==0){
				//
				if(here.getBuilding()==1){
					if(player.getResources("grain")>=2 && player.getResources("ore")>=3){
						here.setBuilding(2);
						player.changeResources("grain", -2);
						player.changeResources("ore", -3);				
					}
					else System.out.println(textNR);
				}
				
				
				if(here.getBuilding()==0){
					if(player.getResources("grain")>=3 && player.getResources("sheep")>=1 && player.getResources("wood")>=1 && player.getResources("clay")>=1 && player.getResources("ore")>=3){
						here.setBuilding(2);
						here.setPlayerNumber(player.getId());
						player.changeResources("grain", -3);
						player.changeResources("sheep", -1);
						player.changeResources("wood", -1);
						player.changeResources("clay", -1);
						player.changeResources("ore", -3);
					}
						else 
							System.out.println(textNR);
				}
				
				if(here.getBuilding()==2)
					System.out.println(textYC);
					
			}
			else
				System.out.println(textNY);
		}
		else 
			System.out.println(textNB);
		
	}
	
	
}
