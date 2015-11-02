package catan.network;

public class Message {
	private String destination;
	private boolean trade;
	private byte[] content;
	
	public Message(String nick, boolean type, byte[] cont)
	{
		destination = nick;
		trade = type;
		content = cont;
	}	
	public byte[] getContent()
	{
		return content;
	}
	public String getDestination()
	{
		return destination;
	}
	public boolean isTrade()
	{
		if (trade == true) return true;
		else return false;
	}

}
