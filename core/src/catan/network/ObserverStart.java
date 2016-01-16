package catan.network;

import java.util.Map;

import catan.network.GameCommunication.InvStatus;

public class ObserverStart extends Observer {
	
	private boolean inGame;

	ObserverStart(GameCommunication subject) {
		super(subject);
		this.sub.add(this);
	}

	@Override
	void update() {
		inGame = true;
	}

	
	public boolean getStateGame() {
		return inGame;
	}

}
