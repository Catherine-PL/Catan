package catan.network;

import java.util.Set;

public class ObserverPeers extends Observer {


	ObserverPeers(Communication sub)
	{
		super(sub);		
		this.sub.add(this);
	}

	public Set<String> getPeersNames()
	{
		Set<String> connected = ((Communication)this.sub).getStatePeers();
		return connected;
	}
	
	@Override
	void update() 
	{		
		Set<String> connected = ((Communication)this.sub).getStatePeers();
		
		System.out.print("-Observer: ");
		System.out.println(connected);
		// TODO Co zrobiæ, gdy peersy siê zmieni¹
		//
	}

}
