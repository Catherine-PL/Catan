package catan.network;

class MsgPeer extends SystemMessage{	
	
	private String content = null;
	
	MsgPeer(String nickname) {
		super(SystemMessage.SystemType.PEER);
		this.content = nickname;
	}
	
	String getContent()
	{
		return content;
	}

	
}
