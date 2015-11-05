package database;


import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class Board {
	//reprezntacje grafu musimy wybraæ
	//public graph 
	private static Board instance=new Board();
	public Map<Integer, Tile> numberToTileID;//stare <Integer,Integer>
	private Map<Character ,Integer> letterToNumber;
	public int thiefPosition=0;
	private Tile tiles[]={new Tile.Builder("Forest",0,false).build(),new Tile.Builder("Forest",0,false).build(),new Tile.Builder("Forest",0,false).build(),
			new Tile.Builder("Forest",0,false).build(),new Tile.Builder("Desert",0,false).build(),new Tile.Builder("Mountains",0,false).buil(),
			new Tile.Builder("Mountains",0,false).build(),new Tile.Builder("Mountains",0,false).build(),new Tile.Builder("Fields",0,false).build(),
			new Tile.Builder("Fields",0,false).build(),new Tile.Builder("Fields",0,false).build(),new Tile.Builder("Fields",0,false).build(),new Tile.Builder("Pasture",0,false).build(),
			new Tile.Builder("Pasture",0,false).build(),new Tile.Builder("Pasture",0,false).build(),new Tile.Builder("Pasture",0,false).build()};
	
	
	//make the constructor private so that this class cannot be
	//instantiated
	private Board(){
		//tabelka na cyferkê przy tile, nie wiem jak j¹ zrobiæ szyciej, na razie
		letterToNumber.put('A', 5);
		letterToNumber.put('B', 2);
		letterToNumber.put('C', 6);
		letterToNumber.put('D', 3);
		letterToNumber.put('E', 8);
		letterToNumber.put('F', 10);
		letterToNumber.put('G', 9);
		letterToNumber.put('H', 12);
		letterToNumber.put('I', 11);
		letterToNumber.put('J', 4);
		letterToNumber.put('K', 8);
		letterToNumber.put('L', 10);
		letterToNumber.put('M', 9);
		letterToNumber.put('N', 4);
		letterToNumber.put('O', 5);
		letterToNumber.put('P', 6);
		letterToNumber.put('Q', 3);
		letterToNumber.put('R', 11);

		//generowanie planszy
		//przemieszanie kafli l¹du
		Collections.shuffle(Arrays.asList(tiles));
		for(int i=0;i<19;i++){
			numberToTileID.put(i, getTile(i));
		}
		
		
	}
	public static Board getInstance(){
		return instance;
	}
	
		
	Tile getTile(int index){
		return tiles[index];
	}
	
	
}
