package catan.network;

import java.io.Serializable;

/**
 * Abstract class for specified messages, and using for sending and receiving messages.
 * @author Sebastian
 *
 */
abstract class Message implements Serializable{
			
	/**
	 * Types of messages
	 * @author Sebastian
	 *
	 */
	enum Type		
	{
		TRADE, SYSTEM, UPDATE;
	}	
	
	protected Type type;
			
	Message(Type type)
	{
		this.type = type;
	}
	Type getType()
	{
		return type;
	}
	
	
	
	
}
