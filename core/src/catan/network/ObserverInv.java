package catan.network;

import java.util.Map;
import java.util.Set;

import catan.network.GameCommunication.InvStatus;

public class ObserverInv extends Observer {

	ObserverInv(GameCommunication subject) {
		super(subject);
		this.sub.add(this);
	}

	@Override
	void update() 
	{
		Map<String, InvStatus> invstatus = ((GameCommunication)sub).getStateInv();
				
		System.out.println("-ObserverInv: ");
		System.out.println(invstatus);
		// TODO Co zrobiæ, gdy peersy siê zmieni¹

	}

	public Map<String, InvStatus> getAllStatuses() {
		return ((GameCommunication)sub).getStateInv();
	}

}
