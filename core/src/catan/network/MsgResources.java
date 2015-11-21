package catan.network;

public class MsgResources extends UpdateMessage 
{
	
	private Integer content = null;
	
	MsgResources(Integer result) {
		super(UpdateMessage.UpdateType.RESOURCES);
		this.content = result;
	}
	
	public Integer getContent()
	{
		return content;
	}

}
