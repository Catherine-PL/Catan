package database;

import database.Node;
//TO DO
//zale¿ne od implementacji grafu
public class Road extends Element{
	private Node from, to;
	private int ownerID;//owner to ID gracza
	String textNB="NIe mo¿esz tu budowaæ";//no building possible
	String textNR="Masz za ma³o surowców";//no resources 
	String textNY="To pole nie jest puste";//not yours
	String textYR="Ju¿ znajduje siê tu twoja DROGA";
	String textNYN="Node FROM nie nalezy do ciebie";
	
	public Road(Player player, Node from2, Node to2, int free){//zmiena free jest potrzebna gdy zagrywamy karte rozwoju
		
		if(from2.getPlayerNumber()==player.getId()){
			
			//droga jest kontunyacja innej lub zaczyna siê w miescie/osdadzie
			if(!from2.roads.isEmpty() || from2.getBuilding()>0){
				
				//nie mozemy drugi raz postawiæ tej samej drogi
				if(!from2.hasRoadTo(to2)){
					
					//musimy miec potrzebne surowce
					if(player.getResources("clay")==1 && player.getResources("wood")==1 || free==1){
						
						//wartoœci obiektu droga
						this.from=from2;
						this.to=to2;
						this.ownerID=player.getId();
						
						
						
						from2.addRoad(to2);
						from2.changeNodeRoadOwner(to, 1,player.getId());
						from2.getNoRoads().remove(to2);
						//%%%%%%%%%%%%%%%%%%%%%%%%%
						from2.changeNodeRoadOwner2(player, to2.getNodeNumber());
						////////
						this.to=to2;
						
						to2.getNoRoads().remove(from2);
						to2.changeNodeRoadOwner(to, 1,player.getId());
						to2.addRoad(from2); 
						////////////////////////////////////////////
						to2.changeNodeRoadOwner2(player, from2.getNodeNumber());
						/////////////////////////////////////////////
						this.from=from2;
						
						if(free==0){
							player.changeResources("clay", -1);
							player.changeResources("wood", -1);
						}
					}
					else
						System.out.println(textNR);
				}
				else  
					System.out.println(textNB);
			}
			else
				System.out.println(textNB);
		}
		else
			System.out.println(textNYN);

			
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
	
}
