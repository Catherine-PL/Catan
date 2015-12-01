package database;

public class Game {
	private Player[] players = new Player[4];
	private Board board;
	private Dice dice;
	
	public Game(){
		board = Board.getInstance();
		dice = Dice.getInstance();
		
		for(int i=0;i<4;i++){
			players[i] = new Player(i);
		}
		
		
		
	}
	
	/* szablon funkcji - mo¿e byæ wywo³ywana, gdy zostanie wciœniêty jakiœ przycisk EndTurn 
	 * przyjmuje gracza, zwraca id kolejnego gracza */
	public int endTurn(Player player){
		int id;
		
		id = player.getId()+1;
		if(id>3)
			id = 0;
		return id;
	}
	
	public Player[] getPlayers(){
		return players;
	}
	
	public static void main(String [ ] args){
		Game game = new Game();
	
		//testy czy mogê siê odwo³aæ do planszy
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
		System.out.println("Obecny gracz id - " + game.players[0].getId());
		System.out.println("Kolejny gracz ma id - " + game.endTurn(game.players[0]));

		

	}
}
