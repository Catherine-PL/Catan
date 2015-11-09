package database;

import java.util.Map;


//musimy chyba mieæ singleton tu i static, bo bêdzie tylko jedna klasa przechowuj¹ mape gracz-nick
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
