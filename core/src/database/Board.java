package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class Board {
	private Map<Character ,Integer> letterToNumber;
	public int thiefPosition=0;
	private Node[] nodes = new Node[54];
	private int[][] adjencyMatrix=new int[54][54];//54 wierzcho�ki

	
	private Tile tiles[]={new Tile.Builder("Forest").build(),new Tile.Builder("Forest").build(),new Tile.Builder("Forest").build(),
			new Tile.Builder("Forest").build(),new Tile.Builder("Desert").build(),new Tile.Builder("Mountains").build(),
			new Tile.Builder("Mountains").build(),new Tile.Builder("Mountains").build(),new Tile.Builder("Fields").build(),
			new Tile.Builder("Fields").build(),new Tile.Builder("Fields").build(),new Tile.Builder("Fields").build(),new Tile.Builder("Pasture").build(),
			new Tile.Builder("Pasture").build(),new Tile.Builder("Pasture").build(),new Tile.Builder("Pasture").build()};
	
	
	//make the constructor private so that this class cannot be
	//instantiated
	protected Board(){
		//tabelka na cyferk� przy tile, nie wiem jak j� zrobi� szyciej, na razie
	/*	letterToNumber.put('A', 5);
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
*/
		//generowanie planszy
		//przemieszanie kafli l�du
		Collections.shuffle(Arrays.asList(tiles));
		
		//indeksowanie wierzcho�k�w
		for(int i=0;i<54;i++){
			nodes[i] = new Node(i);		
		}

	}
	
	private static class BoardHolder { 
	    private static final Board instance = new Board();
	}

	public static Board getInstance() {
		return BoardHolder.instance;
	}
	
	
	public void setNeighbours(){
		for(int i=0;i<54;i++){
    		for(int j=0;j<54;j++){
    			if(adjencyMatrix[i][j] == 1){
    				nodes[i].addNeighbour(nodes[j]);
    				nodes[j].addNeighbour(nodes[i]);
    		}
		}
	}
	}
	
	public void loadMatrix(){
		 //wczytanie macierzy sasiedztwa
			Scanner scanner;
			try {
				scanner = new Scanner(new File("adjencymatrix.txt"));
				for(int i=0;i<54;i++){
		    		for(int j=0;j<54;j++){
		    			if(scanner.hasNextInt())
		    			    adjencyMatrix[i][j] = scanner.nextInt();
		    		}
			    }
				scanner.close();
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	Tile getTile(int index){
		return tiles[index];
	}
	public Node[] getNodes() {
		return nodes;
	}
	
	public Node getNode(int i){
		return nodes[i];
	}
	
	public static void main(String [ ] args) throws FileNotFoundException{
		Board board = Board.getInstance();
		board.loadMatrix();
		board.setNeighbours();
	
		//test, wypisanie s�siad�w
		for(int i=0;i<54;i++){
			System.out.print(i + " - ");
			if(!board.nodes[i].getNeighbours().isEmpty()){
				for (Node node : board.nodes[i].getNeighbours()) {
				        System.out.print(node.getNodeNumber() + " ");
				} 
			}
	        System.out.println();
		}
		
	}
	
	
}
