package catan.network;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import catan.network.SystemMessage.SystemType;
import catan.network.TradeMessage.TradeType;
import catan.network.UpdateMessage.UpdateType;
import database.Board;
import database.Node;
import database.Tile;

class UpdateMessageFactory extends AbstractMessageFactory {

	
	UpdateMessage getUpdateMessage(UpdateType type, Object content) throws ContentException {
		switch(type)
		{
		
		case DICE:
			if (content.getClass()!=Integer.class)
				throw new ContentException();
			else
				return new MsgDice((Integer) content);
			
		case BOARD:
			if (content.getClass()!=Board.class)
				throw new ContentException();
			else
				return new MsgBoard((Board) content);
		
					
		case RESOURCES:
			if (content.getClass()!=Integer.class)
				throw new ContentException();
			else
				return new MsgResources((Integer) content);
			
		case THIEF_TARGET:
			return new MsgThiefTarget();			
			
		case THIEF_LOOT:
			if (content.getClass()!=String.class)
				throw new ContentException();
			else
				return new MsgThiefLoot((String) content);

		case END_GAME:
			return new MsgEndGame();

		case END_TURN:
			return new MsgEndTurn();
			
		case ORDER:			
			if (content.getClass()!=LinkedList.class)
				throw new ContentException();
			else
				return new MsgOrder((List)content);

					
		default:
			System.err.println("Podany zosta� niepoprawny typ wiadomosci");
			return null;
		
		}
	}	
	UpdateMessage getUpdateMessage(UpdateType type, Object content, int index) throws ContentException
	{
		
		switch(type)
		{
		
		case TILE:
			if (content.getClass()!=Tile.class)
				throw new ContentException();
			else
				return new MsgTile((Tile) content, index);
			
		case NODE:
			if (content.getClass()!=Boolean.class)
				throw new ContentException();
			else
				return new MsgNode((Boolean) content, index);
		
		case ROAD:
			if (content.getClass()!=Integer.class)
				throw new ContentException();
			else
				return new MsgRoad((Integer) content, index);
			
		default:
			System.err.println("Podany zosta� niepoprawny typ wiadomosci");
			return null;
		}
		
	}
	
	SystemMessage getSystemMessage(SystemType type, Object content) throws ContentException {
		return null;
	}
	@Override
	TradeMessage getTradeMessage(TradeType type) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	TradeMessage getTradeMessage(TradeType type, HashMap<String, Integer> content, HashMap<String, Integer> content2) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
