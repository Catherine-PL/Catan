package database;

import database.Node;
//TO DO
//zale¿ne od implementacji grafu
public class Road extends Element{
	private Node from, to;
	private int state;//0-nie wybudowana, 1-wybudowana
	private int ownerID;//owner to ID gracza
	private int ID;
	
	/* Wartoœci zwracane przy budowaniu:
	 * 0 - uda³o siê 
	 * 1 - Masz za ma³o surowców
	 * 2 - Droga ju¿ istnieje
	 * 3 - nie mo¿esz tu budowaæ
	 * 4 - Node FROM nie nalezy do ciebie
	 */
	
	public Road(Node from2,Node to2){
		this.from=from2;
		this.to=to2;
		this.state=-1;
		this.ownerID=-1;
	//	System.out.println("Inicjalizacja drogi \t"+from.getNodeNumber()+" ---- "+to.getNodeNumber());

	}
	
	
	public int buildRoad(Player player, Node from2, Node to2){//zmiena free jest potrzebna gdy zagrywamy karte rozwoju
		
		if(from2.getPlayerNumber()==player.getId() || from2.nodeHasOwnedRoad(player)){
			
			//droga jest kontunyacja innej lub zaczyna siê w miescie/osdadzie
			if(!from2.getImprovedRoads().isEmpty() || from2.getBuilding()>0){
				
				//nie mozemy drugi raz postawiæ tej samej drogi
				if(!from2.hasRoadTo(to2)){
					
					//musimy miec potrzebne surowce
					if(player.getResources("clay")>=1 && player.getResources("wood")>=1 || player.getFreeRoads()>0){
						
						//wartoœci obiektu droga
						this.ownerID=player.getId();
						this.state=1;
												
						from2.addImprovedRoads(to2);
						to2.addImprovedRoads(from2);
						
						//
						player.setLongestRoadDistance(player.getLongestRoadDistance()+1);
						//dopisaæ tu sprawdzanie nad³u¿szej drogi
						if(Board.getInstance().getWhoRoad().equals(null) && player.getLongestRoadDistance()>=5){
							Board.getInstance().setWhoRoad(player);
						}else{
							if(Board.getInstance().getWhoRoad().getLongestRoadDistance()<player.getLongestRoadDistance())
								Board.getInstance().setWhoRoad(player);
						}
							
						
						if(player.getFreeRoads()>0){
							player.setFreeRoads(player.getFreeRoads()-1);
							
						}
						else{
							player.changeResources("clay", -1);
							player.changeResources("wood", -1);
						}
						
						return 0;
					}
					else
						return 1;
				}
				else  
					return 2;
			}
			else
				return 3;
		}
		else
			return 4;

			
	}
				
		
	
	public Node getTo(){
		return to;
	}
	public Node getFrom(){
		return from;
	}
	public int getOwnerID() {
		return ownerID;
	}
	public int getState() {
		return state;
	}


	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	
	
}
