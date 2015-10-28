package logic;

import database.Player;

public class Logic {
	
public void won(Player gracz){
    if( gracz.points >= 10) 
    {
    	System.out.println("WYGRA£ GRACZ " ++ toUpperCase(gracz.nazwa)); //
    	//wyœlij wiadomoœæ send coœ tam
    }
}
public void buildRoad(Player gracz, edge krawedz){
	
	if(glina==1 && drewno==1){
		if()
	}
	
	
}
public void buildSettlement(Player gracz,node punkt)
{
	//zliczenie surowcow z kart albo z bazy danych wziêcie, co lepsze	
	if(zboze==2 && owca==1 && drewno==1 && glina==1) 
		if(punkt.moznaBudowac)
			punkt.space=Settlement;
		else System.out.println("NIe mo¿esz tu budowaæ");
	else
		System.out.println("Masz za ma³o surowców");
}

public void buildCity(Player gracz)
{
	//zliczenie surowcow z kart albo z bazy danych wziêcie, co lepsze	
	if(zboze==2 && ruda==3 ) 
		if(node.space==Settlement || zboze==3 && owca==1 && drewno==1 && glina==1 && ruda==3 && punkt.moznaBudowac)
				node.space=City
		else System.out.println("NIe mo¿esz tu budowaæ");
	else
		System.out.println("Masz za ma³o surowców");
}

}
