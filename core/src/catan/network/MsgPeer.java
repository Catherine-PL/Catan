package catan.network;

class MsgPeer extends Message {

	private String content;
	
	MsgPeer(String cont)
	{
		this.type = Message.Type.PEER;
		content = cont;
	}
	
	public String getContent()
	{
		return content;
	}
	
}
