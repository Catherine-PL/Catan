package database;

import database.Building;
import database.Node.portType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
	//private Map<Character ,Integer> letterToNumber = new HashMap<Character, Integer>();
	public int thiefPosition=0;
	private Node[] nodes = new Node[54];
	//rivate int[][] adjencyMatrix=new int[54][54];
	private int[] letterToNumber=new int [] {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};//zamiast tego ca³ego ABCDEF
	private int [][] tileToDice=new int [19][2];
	public  ArrayList <Road> boardRoads=new ArrayList<Road> ();
	private int[][] adjencyMatrix=new int[][] {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0}};
	
	private Tile tiles[]={new Tile.Builder("Forest").build(),new Tile.Builder("Forest").build(),new Tile.Builder("Forest").build(),
			new Tile.Builder("Forest").build(),new Tile.Builder("Desert").build(),new Tile.Builder("Mountains").build(),
			new Tile.Builder("Mountains").build(),new Tile.Builder("Mountains").build(),new Tile.Builder("Fields").build(),
			new Tile.Builder("Fields").build(),new Tile.Builder("Fields").build(),new Tile.Builder("Fields").build(),new Tile.Builder("Pasture").build(),
			new Tile.Builder("Pasture").build(),new Tile.Builder("Pasture").build(),new Tile.Builder("Pasture").build(),
			new Tile.Builder("Hills").build(),new Tile.Builder("Hills").build(),new Tile.Builder("Hills").build()};
	
	
	//make the constructor private so that this class cannot be
	//instantiated
	protected Board(){
		//tabelka na cyferkê przy tile, nie wiem jak j¹ zrobiæ szyciej, na razie
		/*letterToNumber.put('A', 5);
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
		letterToNumber.put('R', 11);*/
		
		//generowanie planszy
		//przemieszanie kafli l¹du
		Collections.shuffle(Arrays.asList(tiles));
				
		//indeksowanie wierzcho³ków
		for(int i=0;i<54;i++){
			nodes[i] = new Node(i);		
		}
		
		//loadMatrix();
		setNeighbours();
		setRoadsy();
		setNoRoads();

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
	public void setNoRoads(){
		for(int i=0;i<54;i++){
			nodes[i].setNoRoads(nodes[i].getNeighbours());		
		}
	}
	public void setRoadsy(){	
		//set RoadRoad
		int k=0;
		for(int i=0;i<54;i++){
			for(Node nod:nodes[i].getNeighbours()){
				
				if(!nodes[i].hasRoadRoadTo(nod)){
					
				Road r=new Road(nodes[i],nod);
				
				boardRoads.add(r);
				
				r.setID(k);
				k++;
				nod.addRoadRoad(r);
				nodes[i].addRoadRoad(r);
				}
				
			}
		}
	}
	public void loadMatrix(){
		 //wczytanie macierzy sasiedztwa
			Scanner scanner;
			try {
				scanner = new Scanner(new File("src\\database\\adjencymatrix.txt"));
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
	
	public Tile getTile(int index){
		return tiles[index];
	}
	public Node[] getNodes() {
		return nodes;
	}
	
	public Node getNode(int i){
		return nodes[i];
	}
	
	public int getLetterToNumber(int i){
		return this.letterToNumber[i];
	}
	
	public static void main(String [ ] args) throws FileNotFoundException{
		Board board = Board.getInstance();
	/*
		//test, wypisanie s¹siadów
		for(int i=0;i<54;i++){
			System.out.print(i + " - ");
			if(!board.nodes[i].getNeighbours().isEmpty()){
				for (Node node : board.nodes[i].getNeighbours()) {
				        System.out.print(node.getNodeNumber() + " ");
				} 
			}
	        System.out.println();
  
		}
*/
	
		//testowanie Marcin
		
		Player p1=new Player(3);
		
		p1.changeResources("grain", 500);
		p1.changeResources("sheep",500);
		p1.changeResources("wood", 500);
		p1.changeResources("clay", 500);		
	
	    for(Node e: board.getNodes()){
			Building.buildSettlement(p1, e);
			System.out.print("\nNode number "+e.getNodeNumber()+"Roads");
			for(Road r:e.getRoads()){
				System.out.print(r.getID()+"  ");
			}

		}
	    System.out.print("\n"+board.boardRoads.get(0).getOwnerID()+"  "+board.boardRoads.get(0).getState()+" to2imp"+board.nodes[0].getRoads2Improve()+" to2imp"+board.nodes[0].getRoadsIdImprove());
		board.getNode(0).buildRoad(p1, 0);
		board.getNode(0).buildRoad(p1, 1);
	    System.out.print("\n"+board.boardRoads.get(0).getOwnerID()+"  "+board.boardRoads.get(0).getState()+" to2imp"+board.nodes[0].getRoads2Improve()+" to2imp"+board.nodes[0].getRoadsIdImprove());

		
	}
	
	
	//nie usuwajcie tego narazie dobra
	//st¹d sobie kopiuje kod do testów, a nie chce wrzycaæ zasmieconego main'a
	public void testMarcin(){
		
		//System.out.println("\n jest "+board.boardRoads.size()+" drog \n ");//zwroci ile jest drog

		//testowanie Marcin
		Board board = Board.getInstance();
		Player p1=new Player(3), p2=new Player(2);
		
		p1.changeResources("grain", 500);
		p1.changeResources("sheep",500);
		p1.changeResources("wood", 500);
		p1.changeResources("clay", 500);		
		/*
		for(Node e: board.getNodes()){
			System.out.print(e.getNodeNumber()+"przed"+e.getPlayerNumber());
			Building.buildSettlement(p1, e);
			System.out.println(e.getNodeNumber()+"po"+e.getPlayerNumber());
		}*/
		int i=0;
		
		/*for(Node n: board.nodes){
			System.out.println(n.getNodeNumber()+" "+n.getRoadRoad().isEmpty());
		}*/
		//new Road(p1,board.nodes[0],board.nodes[3],0);
		//new Road(p1,board.nodes[0],board.nodes[4],0);
		//board.nodes[0].getRoadRoad().  buildRoad(p1,board.nodes[0],board.nodes[3],0);
		
		
		board.nodes[0].buildRoadRoad(p1,board.nodes[0],board.nodes[3],1);
		ArrayList <Integer> dr=new ArrayList <Integer>();
		dr=board.nodes[0].getRoadsToImprove();
		for(Integer a:dr){
			System.out.println(" * "+a+" * ");
		}
		
		
		
		System.out.println("----------------------");
		for(Road r:board.nodes[0].getRoadRoad()){
			System.out.println(r.getFrom().getNodeNumber()+"----"+r.getTo().getNodeNumber()+" state"+r.getState()+"owner:"+r.getOwnerID());
		}
		System.out.println(board.nodes[0].getPlayerNumber()+"--------");
		System.out.println("X "+board.nodes[0].getRoadRoad().size());

		board.nodes[0].buildRoadRoad(p1,board.nodes[0],board.nodes[3],1);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^");

		board.nodes[0].buildRoadRoad(p1,board.nodes[0],board.nodes[4],1);
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("X "+board.nodes[0].getRoadRoad().size());

		System.out.println("----------------------");
		for(Road r:board.nodes[0].getRoadRoad()){
			System.out.println("********\n"+r.getFrom().getNodeNumber()+"----"+r.getTo().getNodeNumber()+"\n state: "+r.getState()+"owner: "+r.getOwnerID()+"\n*********");
		}
		
		System.out.println("droga z 53");
		for(Road r:board.nodes[16].getRoadRoad()){
			System.out.println("********\n"+r.getFrom().getNodeNumber()+"----"+r.getTo().getNodeNumber()+"\n state: "+r.getState()+"owner: "+r.getOwnerID()+"\n*********");
		}
		board.nodes[16].buildRoadRoad(p1,board.nodes[16],board.nodes[21],1);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("----------------------");
		for(Road r:board.nodes[16].getRoadRoad()){
			System.out.println("********\n"+r.getFrom().getNodeNumber()+"----"+r.getTo().getNodeNumber()+"\n state: "+r.getState()+"owner: "+r.getOwnerID()+"\n*********");
		}
		
		
//		System.out.println("X "+board.nodes[0].getRoadRoad().size());
		/*
		System.out.println("----------------------");
		
		System.out.println(board.nodes[0].getRoadRoad().get(0).ID+" "+board.nodes[0].getRoadRoad().get(0).getFrom());
		System.out.println(board.nodes[0].getRoadRoad().get(1).ID+" "+board.nodes[0].getRoadRoad().get(0).getFrom());
		board.nodes[0].getRoadRoad().get(1).buildRoad(p1, board.nodes[4], board.nodes[0], 0);
		board.nodes[0].getRoadRoad().get(1).buildRoad(p1, board.nodes[4], board.nodes[0], 0);
		System.out.println("----------------------");
		for(Road r:board.nodes[0].getRoadRoad()){
			System.out.println(r.getFrom().getNodeNumber()+"----"+r.getTo().getNodeNumber()+" state"+r.getState()+"owner:"+r.getOwnerID());
		}
		System.out.println("----------------------");
	
		*/
		/*
		for(Node temp:board.nodes){
			int [][]temp2=temp.getNodeRoadOwner2();
			System.out.println("*****************\n"+temp.getNodeNumber());
			for( int i=0;i<3;i++){
				System.out.print("\ndo Noda numer"+temp2[i][0]+"\t stan boardRoads "+temp2[i][1]+" wlasciciel tej boardRoads "+temp2[i][2]+"\n");
			}
		}
		*/
	}
	
	
}
