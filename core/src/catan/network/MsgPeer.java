package catan.network;

class MsgPeer extends SystemMessage{	
	
	private String content;
	
	MsgPeer(String nickname) {
		super(SystemMessage.SystemType.PEER);
		this.content = nickname;
	}
	
	public String getContent()
	{
		return content;
	}

	
}
