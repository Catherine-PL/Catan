package database;

import java.util.Map;


//musimy chyba mie� singleton tu i static, bo b�dzie tylko jedna klasa przechowuj� mape gracz-nick
public class Players {
	private Map<Integer,String> players;//ID-NICK
	//private Map<Integer,String> playersIP;//ID-IP odebra�oby odpowiedzialno�c playerIP
	
	public Map<Integer,String> getPlayers() {
		return players;
	}
	
	public String getPlayer(int id) {
		return players.get(id);
	}
	
	public void addPlayer(int id, String name) {
		players.put(id, name);
	}
	
	
/*
	public Map<Integer,String> getPlayersIP() {
		return playersIP;
	}

	public String getPlayerIP(int id) {
		return playersIP.get(id);
	}	
	public void addPlayersIP(int id,String address) {
		this.playersIP.put(id, address);
	}

*/	
}
