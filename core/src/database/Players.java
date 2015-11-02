package database;

import java.util.Map;

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
