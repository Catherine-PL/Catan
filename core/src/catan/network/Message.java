package catan.network;

import java.io.Serializable;

/**
 * Abstract class for specified messages, and using for sending and receiving messages.
 * @author Sebastian
 *
 */
public abstract class Message implements Serializable {
	
	/**
	 * Types of messages
	 * @author Sebastian
	 *
	 */
	public enum Type
	{
		DICE, THIEF, TRADE, CARD, MAP, BUDYNEK, TEXT, PEER, END;
	}
	
	protected Type type;
		
	public Type getType()
	{
		return type;
	}
	
}
