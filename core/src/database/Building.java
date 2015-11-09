package database;

public class Building extends Element {
	int type;
	
	
	
	public void buildSettlement(Player player,Element point)
	{
		//zliczenie surowcow z kart albo z bazy danych wziêcie, co lepsze	
		if(grain==2 && sheep==1 && wood==1 && clay==1) 
			if(point.moznaBudowac)
				point.space=Settlement;
			else System.out.println("NIe mo¿esz tu budowaæ");
		else
			System.out.println("Masz za ma³o surowców");
	}

	public void buildCity(Player player)
	{
		//zliczenie surowcow z kart albo z bazy danych wziêcie, co lepsze	
		if(player grain==2 && ore==3 ) 
			if(node.space==Settlement || grain==3 && sheep==1 && wood==1 && clay==1 && ore==3)
					node.space=City
			else System.out.println("NIe mo¿esz tu budowaæ");
		else
			System.out.println("Masz za ma³o surowców");
	}
	
	
}
