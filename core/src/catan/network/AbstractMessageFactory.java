package catan.network;

import java.util.HashMap;

import catan.network.UpdateMessage.UpdateType;
import database.Node;
import database.Tile;

class ContentException extends Exception{}

	
/**
 * Class needed to create messages
 * @author Sebastian
 *
 */
public abstract class AbstractMessageFactory {
	
	abstract SystemMessage getSystemMessage(SystemMessage.SystemType type, Object content) throws ContentException;

	abstract TradeMessage getTradeMessage(TradeMessage.TradeType type);
	abstract TradeMessage getTradeMessage(TradeMessage.TradeType type, HashMap<String, Integer> content, HashMap<String, Integer> content2);
	
	abstract UpdateMessage getUpdateMessage(UpdateMessage.UpdateType type, Object content) throws ContentException;
	abstract UpdateMessage getUpdateMessage(UpdateType type, Object content, int index) throws ContentException;
		
	
}
