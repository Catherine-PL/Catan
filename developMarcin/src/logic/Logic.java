package logic;

import database.Element;
import database.Player;
import database.Players;
//toDO
//nazwy zmiennych na angielskie
//ka�dy tekst ma by� wczytywany z pliku


public class Logic {
	
private Players players;	
	
public void won(Player player){
    if( player.getPoints() >= 10) 
    {
    	System.out.println("WYGRA� player " + players.getPlayer(player.getId())); //to z pliku, bierzesz. ka�dy tekst w jednym pliku
    	//wy�lij wiadomo�� send co� tam
    }
}
/*//do zmiany
public void buildRoad(Player player, Edge edge){
	
	if(clay==1 && wood==1){
		if()
	}
	}
	*/
// do znalezienia i wzi�cia z b


public void buildSettlement(Player player,Element point)
{
	//zliczenie surowcow z kart albo z bazy danych wzi�cie, co lepsze	
	if(grain==2 && sheep==1 && wood==1 && clay==1) 
		if(point.moznaBudowac)
			point.space=Settlement;
		else System.out.println("NIe mo�esz tu budowa�");
	else
		System.out.println("Masz za ma�o surowc�w");
}

public void buildCity(Player player)
{
	//zliczenie surowcow z kart albo z bazy danych wzi�cie, co lepsze	
	if(grain==2 && ore==3 ) 
		if(node.space==Settlement || grain==3 && sheep==1 && wood==1 && clay==1 && ore==3 && point.moznaBudowac)
				node.space=City
		else System.out.println("NIe mo�esz tu budowa�");
	else
		System.out.println("Masz za ma�o surowc�w");
}

}
