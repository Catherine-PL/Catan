package catan.network;

public class MsgPlayer implements Message {

	private Message.Type type = Message.Type.GRACZ;
	private Object content = null; 
	
	public MsgPlayer(Object content)
	{
		this.content = content;
	}
	
	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getContent() {
		// TODO Auto-generated method stub
		return null;
	}

}
