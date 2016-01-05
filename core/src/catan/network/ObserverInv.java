package catan.network;

import java.util.Map;
import java.util.Set;

import catan.network.GameCommunication.InvStatus;

public class ObserverInv extends Observer {

	ObserverInv(GameCommunication subject) {
		super(subject);
		this.sub.add(this);
	}

	public Map<String, InvStatus> getAllStatuses()
	{
		Map<String, InvStatus> invstatus = ((GameCommunication)sub).getStateInv();
		return invstatus;
	}
	
	@Override
	void update() 
	{
		Map<String, InvStatus> invstatus = ((GameCommunication)sub).getStateInv();
				
		System.out.println("-ObserverInv: ");
		System.out.println(invstatus);
		// TODO Co zrobiæ, gdy peersy siê zmieni¹

	}

}
