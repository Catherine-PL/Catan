package database;

import java.io.Serializable;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.sun.javafx.runtime.SystemProperties;

public class Game implements Serializable{
	private Player thisPlayer = new Player(0);
	static Player[] players = null;
	private Board board;
	private Dice dice;
	private Player actualPlayer;
	private HashMap <Integer,Integer> colors=new HashMap<Integer,Integer>();
	
	public void initGame(List<String> pl, String name, Texture avatar){		
		board = Board.getInstance();
		dice = Dice.getInstance();
		dice.throwDice();
		System.out.println("initGame: " + pl);
		players=new Player[pl.size()];

		thisPlayer=new Player(name,avatar,0);
		for(int i=0;i<pl.size();i++){
			if(!pl.get(i).equals("Me"))
			{
				players[i]=new Player(pl.get(i),new Texture(Gdx.files.internal("avatars/playeravatar3.png")),i);

			}
			else
			{
				thisPlayer.setId(i);
				players[i]=thisPlayer;
			}
		}
		actualPlayer=players[0];				
		
		for(int i=0;i<pl.size();i++){
			colors.put(players[i].getId(), i);
		}
		
	}
	public Game()
	{
		board = Board.getInstance();
		dice = Dice.getInstance();
		dice.throwDice();
		
		
	}
	

	public Player getActualPlayer()
	{
		return actualPlayer;
	}
	public Board getBoard()
	{
		return board;
	}
	public void setBoard(Board board)
	{
		this.board = board;
	}
			
	
	public Dice getDice()
	{
		return dice;
	}
	
	public Player getThisPlayer()
	{
		return thisPlayer;
	}
	
	public HashMap<Integer,Integer> getColors()
	{
		return colors;
	}
	
	public void endTurn(){		
		int nextId = actualPlayer.getId()+1;
		if(nextId>Game.players.length-1)
			nextId = 0;
		
		for(int i=0;i<players.length;i++){
			if(players[i].getId() == nextId){
				actualPlayer = players[i];
			}
		}
				
		if((dice.getFirst()+dice.getSecond())!=7){
			Player player=this.thisPlayer;{
				System.out.println("Player: " + player.getName());
				for(Node node: player.getPlayerNodes()){
					System.out.println("--Node: " + node.getNodeNumber());
					for(Tile tile:node.getNearResources()){
						System.out.println("----tile: " + tile.getNumber());
						System.out.println(tile.getDiceNumber()==(dice.getFirst()+dice.getSecond()));
						if(tile.getDiceNumber()==(dice.getFirst()+dice.getSecond())){
							//jesli osada zwiekszy o 1, jesli miasto zwiekszy zasob o 2							
							System.out.println(tile.getType());
							
							if(tile.getType().equals("Forest"))
								player.changeResources("wood", node.getBuilding());
							if(tile.getType().equals("Hills"))
								player.changeResources("clay", node.getBuilding());
							if(tile.getType().equals("Pasture"))
								player.changeResources("sheep", node.getBuilding());
							if(tile.getType().equals("Fields"))
								player.changeResources("grain", node.getBuilding());
							if(tile.getType().equals("Mountains"))
								player.changeResources("ore", node.getBuilding());
							
						}
					}
				}
			}
		}
		else{
			//TODO budzi sie zlodziej
		}
		
		
	}
	
	public Player[] getPlayers(){
		return players;
	}
	
	public static void main(String [ ] args){
		Game game = new Game();
	
		//testy czy mog� si� odwo�a� do planszy
		for(int i=0;i<54;i++){
			System.out.print(i + " - ");
			if(!game.board.getNode(i).getNeighbours().isEmpty()){
				for (Node node : game.board.getNode(i).getNeighbours()) {
				        System.out.print(node.getNodeNumber() + " ");
				} 
			}
	        System.out.println();
		}
		//testy
		System.out.println("Obecny gracz id - " + game.actualPlayer.getId());
		game.endTurn();
		System.out.println("Kolejny gracz ma id - " + game.actualPlayer.getId());

		

	}
}
