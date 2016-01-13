package catan.network;

import java.util.Map;
import java.util.Set;

import catan.network.GameCommunication.InvStatus;
import representation.View;

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
		//ObserverInv updatuje listê zaproszeñ w View z reprezentacji:
		// TODO Gdy przyjdzie nowe zaproszenie, dodaj nazwy niewpisanych nadawców do listy nadawców w View
		
	}

	public Map<String, InvStatus> getAllStatuses() {
		return ((GameCommunication)sub).getStateInv();
	}

}
