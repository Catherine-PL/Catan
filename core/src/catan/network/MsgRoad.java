package catan.network;

public class MsgRoad extends UpdateMessage {
	
	private Integer content = null;
	
	MsgRoad(Integer result) {
		super(UpdateMessage.UpdateType.ROAD);
		this.content = result;
	}
	
	public Integer getContent()
	{
		return content;
	}
	

}
