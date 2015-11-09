package database;

import java.util.Map;


//musimy chyba mie� singleton tu i static, bo b�dzie tylko jedna klasa przechowuj� mape gracz-nick
public class Players {
	private Map<Integer,String> players;

	public Map<Integer,String> getPlayers() {
		return players;
	}
	
	public String getPlayer(int id) {
		return players.get(id);
	}
	
	public void addPlayer(int id, String name) {
		players.put(id, name);
	}

	
}
