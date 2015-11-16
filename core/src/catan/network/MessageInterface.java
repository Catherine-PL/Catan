package catan.network;

public interface MessageInterface<SubType, Content> {
		
	public Content getContent();
	public Message.Type getType();
	public SubType getSubType();
	

}
