package catan.network;

public class CatanMessageHandler extends GameMessageHandler {

	private Board board;
	private HashMap<String, Integer> give;
	private HashMap<String, Integer> get;
	private int myNumber;
	private int inQueue = 0;		// ilosc ludzi przede mna w grze
	private int msgdice = 0;
	
	MessageHandler(Board board)
	{
		this.board = board;
	}
	
	/* SystemMessage */
	
	/*
	synchronized void handleMsgInvitation(Peer peer)													// trzeba to jakos zgrac, wybor:  accept, reject 
	{
		String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());			
		
								// ten fragment tylko wystepuje w testowaniu lokalnym
		if(inGame == true && !peer.socketIn.getInetAddress().getHostAddress().equals("127.0.0.1"))		// jezeli dostalem obce zaproszenie 
		{
			Message msg = null;
			try {					
				msg = system.getSystemMessage(SystemType.REJECT, null);
			} catch (ContentException e) {
				e.printStackTrace();
			}
			
			try {
				sendTo(nick, msg);
			} catch (IOException e) {
				System.err.println("MsgInvitation error, problem with sendTo");
				e.printStackTrace();
			}
			return ;
		}
		
		
		
		System.out.println("Invitation to a game from: " +  nick);
		
		// Tutaj musi nastapic wybor accepet albo reject wyslanie widomosci				<-----			
		
		Message msg = null;
		try {
			msg = system.getSystemMessage(SystemType.ACCEPT, null);
			inGame = true;
		} catch (ContentException e) {
			e.printStackTrace();
		}
		
		try {
			sendTo(nick, msg);
		} catch (IOException e) {
			System.err.println("MsgInvitation error, problem with sendTo");
			e.printStackTrace();
		}
		
	}
	synchronized void handleMsgAccept(Peer peer)					// dodac zmienna, ze j
	{
		String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());
		System.out.println("Player: " +  nick + " - Accepted");
		
		invPlayers.remove(nick);
		invPlayers.put(nick, InvStatus.ACCEPTED);
		
		System.out.println(invPlayers);
		
	}
	synchronized void handleMsgReject(Peer peer)
	{
		String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());
		System.out.println("Player: " +  nick + " - Rejected");
		
		invPlayers.remove(nick);
		invPlayers.put(nick, InvStatus.REJECTED);
		
		System.out.println(invPlayers);
	}
	synchronized void handleMsgStartGame(Peer peer)				// dodac metode startu gry jako takiej, ustawiæ zmienna inGame czy cos
	{
		System.out.println("The game starting ...");
		System.out.println("Sending result of my dice (creating a chain)");
		myNumber = 5;
		try {
			Message ms = update.getUpdateMessage(UpdateType.DICE, myNumber);
			sendToAll(ms);
		} catch (ContentException e) { 
			e.printStackTrace();
		}
		
		
	
	}
	synchronized void handleMsgAbandon(Peer peer)				// 
	{
		System.out.println("The game has been abandoned ...");
		inGame = false;
	}
	synchronized void handleMsgEndTurn(Peer peer)
	{
		String nick = ipToNick.get(peer.socketOut.getInetAddress().getHostAddress());
		System.out.println("Player: " + nick + " has finished turn.");
	}
	synchronized void handleMsgEndGame(Peer peer)
	{
		String nick = ipToNick.get(peer.socketOut.getInetAddress().getHostAddress());
		System.out.println("Player: " + nick + " has won the game.");
	}
	
	/* UpdateMessage */
	/*
	synchronized void handleMsgDice(Peer peer, MsgDice msg)
	{			
		String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());
		System.out.print("From: " + nick);
		System.out.println(" -- Dice result:" + msg.getContent());
		
		if(msgdice < invPlayers.size()-1)									// tylko do ustalenia kolejnosci
		{
			msgdice++;
			invPlayers.remove(nick);
			invPlayers.put(nick, InvStatus.WAIT);							// jezeli wszyscy sa wait, to kolejnosc ustalona
			
			if(msg.getContent() > myNumber)
			{
				inQueue++;
			}else if(msg.getContent() == myNumber)
			{
				if(nickname.compareToIgnoreCase(nick) > 0)
				{
					inQueue++;
				}
			}
		}
	}
	synchronized void handleMsgBoard(MsgBoard msg)			// podmienic board tam gdzie jest ona przechowywana
	{
		System.out.println("Board actualization ...");
		//this.board = msg.getContent();
	}	
	synchronized void handleMsgNode(MsgNode msg)			// problem z aktualizacja
	{
		System.out.println("Node actualization ...");
		
	//	Node n = msg.getContent();			
	//	int i = msg.getIndex();
	//	System.out.println("Node: " + n + " on index: " + i);
	//	board.setNode(n, i);
	//	System.out.println(n.getNodeNumber() + ", " + board.getNode(i));
		
	}
	synchronized void handleMsgTile(MsgTile msg)
	{
		System.out.println("Tail actualization ...");
		
	//	Tile t = msg.getContent();
	//	int i = msg.getIndex();
	//	board.setTile(t, i);
		
		
	}
	synchronized void handleMsgResources(Peer peer, MsgResources msg)
	{
		System.out.println("Resources number actualization...");
		
		int n = msg.getContent();			
		System.out.println("Player: " + ipToNick.get(peer.socketIn.getInetAddress().getHostAddress()) + ", number of resources : " + n);
		
	}
	
	/* TradeMessage */
	/*
	synchronized void handleMsgOffert(Peer peer, MsgOffert msg)								// podobnie jak z zaproszeniami 
	{			
		String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());
		System.out.println();
		System.out.println("--Offert from: " + nick);
		
		get = msg.getGive();
		give = msg.getGet();
		//HashMap<String, Integer> icanget = msg.getGive();
		//HashMap<String, Integer> ihavetogive = msg.getGet();						
		
		System.out.println("What he wants: " + give);
		System.out.println("What i would get: " + get);
		System.out.println();
		
		// Sprawdzenie czy mogê siê zgodziæ. Jeœli nie to wysy³am od razu no.
		//Message ms = trade.getTradeMessage(TradeType.NO);
		Message ms = trade.getTradeMessage(TradeType.YES);
		
		try {
			sendTo(nick, ms);
		} catch (IOException e) {
			System.err.println("Utracono polaczenie z: " + nick);
			disconnected(nick);				
		}
		
	}
	synchronized void handleMsgYes(Peer peer, MsgYes msg)
	{
		String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());
		System.out.println("Player: " + nick + " has accepted your offert");
				
		invPlayers.remove(nick);
		invPlayers.put(nick, InvStatus.ACCEPTED);
		
		System.out.println("Players" + invPlayers);			
	}
	synchronized void handleMsgNo(Peer peer, MsgNo msg)
	{
		String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());			
		System.out.println("Player " + nick + " has rejected your offert");			
				
		invPlayers.remove(nick);
		invPlayers.put(nick, InvStatus.REJECTED);
		System.out.println(invPlayers);
		
	}
	synchronized void handleMsgDeal(Peer peer, MsgDeal msg)
	{
		String nick = ipToNick.get(peer.socketIn.getInetAddress().getHostAddress());			
		System.out.println("Deal with: " + nick);	
		
		//	aktualizacja surowcow gracza. Dodajac surowce z get, Odejmujac surowce z give	
	}
	synchronized void handleMsgEndTrade(MsgEndTrade msg)
	{			
		Set<String> s = invPlayers.keySet();
		for(String nick : s)
		{
			invPlayers.remove(nick);
			invPlayers.put(nick, InvStatus.WAIT);
		}
	}
	
}						
*/
	
}
