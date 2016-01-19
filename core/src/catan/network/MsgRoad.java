package catan.network;

public class MsgRoad extends UpdateMessage {
	
	private Integer content = null;
	private int content2;
	
	MsgRoad(Integer node, int road) {
		super(UpdateMessage.UpdateType.ROAD);
		this.content = node;
		this.content2 = road;
	}
	
	public Integer getContent()
	{
		return content;
	}
	public Integer getContent2()
	{
		return content2;
	}
	

}
