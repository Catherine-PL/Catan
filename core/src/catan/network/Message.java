package catan.network;

import java.io.Serializable;

public abstract class Message implements Serializable {
	
	public enum Type
	{
		DICE, THIEF, TRADE, CARD, MAP, BUDYNEK, GRACZ;
	}
	
	private Type type;
	private Object content;
	
	Message(Type t, Object obj)
	{
		type = t;
		content = obj;
	}
	
	public Type getType()
	{
		return type;
	}
	public Object getContent()
	{
		return content;
	}


}
