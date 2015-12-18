package catan.network;

class MsgResources extends UpdateMessage 
{
	
	private Integer content = null;
	
	MsgResources(Integer result) {
		super(UpdateMessage.UpdateType.RESOURCES);
		this.content = result;
	}
	
	Integer getContent()
	{
		return content;
	}

}
