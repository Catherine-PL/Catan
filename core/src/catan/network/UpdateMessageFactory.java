package catan.network;

import catan.network.SystemMessage.SystemType;
import catan.network.TradeMessage.TradeType;
import catan.network.UpdateMessage.UpdateType;
import database.Board;
import database.Node;
import database.Tile;

public class UpdateMessageFactory extends AbstractMessageFactory {

	
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
			
		default:
			System.out.println("Podany zosta³ niepoprawny typ wiadomosci");
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
			if (content.getClass()!=Node.class)
				throw new ContentException();
			else
				return new MsgNode((Node) content, index);
		
		default:
			System.out.println("Podany zosta³ niepoprawny typ wiadomosci");
			return null;
		}
		
	}
	
	SystemMessage getSystemMessage(SystemType type, Object content) throws ContentException {
		return null;
	}
	TradeMessage getTradeMessage(TradeType type, Object content) throws ContentException {
		return null;
	}

	

}
