package catan.network;

class Receiver implements Runnable {

	String nickname;
	Peer peer;
	Communication com;
	
	
	
	Receiver(String nickname, Peer peer, Communication communication)
	{
		this.nickname = nickname;
		this.peer = peer;
		this.com = communication;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// odczytuje co przyszlo
		// sprawdza co dokldanie
		// odpala funkcje z communication, ktora jest synchronizowana
		/*
		 * robimy tyle tych watkow demonow ile mamy peersow
		 * jezeli jakis nowy dojdzie to i dla niego rowniez
		 * podczas gry potrzebujemy tylko te watki z ktorymi gramy
		 * 
		 * */
	}

}
