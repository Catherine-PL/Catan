package catan.network;

import java.util.Set;

public class ObserverPeers extends Observer {

	ObserverPeers(Communication sub)
	{
		super(sub);		
		this.sub.add(this);
	}
	
	@Override
	void update() 
	{		
		Set<String> connected = ((Communication)this.sub).getStatePeers();
		
		System.out.print("-Observer: ");
		System.out.println(connected);
		// TODO Co zrobiæ, gdy peersy siê zmieni¹

	}

	public Set<String> getPeersNames() {
		// TODO Auto-generated method stub
		return ((Communication)this.sub).getStatePeers();
	}

}
