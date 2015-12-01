package database;

import database.Node;
//TO DO
//zale¿ne od implementacji grafu
public class Road extends Element{
	private Node from, to;
	private int state;//0-nie wybudowana, 1-wybudowana
	private int ownerID;//owner to ID gracza
	public int ID;
	
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
		this.ownerID=0;
	//	System.out.println("Inicjalizacja drogi \t"+from.getNodeNumber()+" ---- "+to.getNodeNumber());

	}
	
	
	public int buildRoad(Player player, Node from2, Node to2, int free){//zmiena free jest potrzebna gdy zagrywamy karte rozwoju
		
		if(from2.getPlayerNumber()==player.getId() || from2.nodeHasOwnedRoad(player)){
			
			//droga jest kontunyacja innej lub zaczyna siê w miescie/osdadzie
			if(!from2.roads.isEmpty() || from2.getBuilding()>0){
				
				//nie mozemy drugi raz postawiæ tej samej drogi
				if(!from2.hasRoadTo(to2)){
					
					//musimy miec potrzebne surowce
					if(player.getResources("clay")>=1 && player.getResources("wood")>=1 || free==1){
						
						//wartoœci obiektu droga
						//this.from=from2;
						//this.to=to2;
						this.ownerID=player.getId();
						this.state=1;
						
						//inny patent
						//from2.getRoadRoad().add(this);
						//to2.getRoadRoad().add(this);
					//	System.out.println(from.getNodeNumber()+" ---- "+to.getNodeNumber());
						
						//to ze starego patentu, wolê zostawiæ na razie nim dojdzie do kolejnej refaktoryzacji
						from2.addRoad(to2);
						to2.addRoad(from2); 
						
						//ju¿ w addRoad siê wykonuje
						//from2.getNoRoads().remove(to2);
						//to2.getNoRoads().remove(from2);
						
						
						//ponie¿ej raczej do wywalenia
						/*
						from2.addRoad(to2);
						from2.changeNodeRoadOwner(to, 1,player.getId());
						from2.getNoRoads().remove(to2);
						//%%%%%%%%%%%%%%%%%%%%%%%%%
						from2.changeNodeRoadOwner2(player, to2.getNodeNumber());
						////////
						
						
						to2.getNoRoads().remove(from2);
					//	to2.changeNodeRoadOwner(to, 1,player.getId());
						to2.addRoad(from2); 
						////////////////////////////////////////////
						to2.changeNodeRoadOwner2(player, from2.getNodeNumber());
						/////////////////////////////////////////////
						
						*/
						if(free==0){
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

	
}
