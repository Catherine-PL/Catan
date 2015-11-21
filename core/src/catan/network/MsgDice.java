package catan.network;

public class MsgDice extends UpdateMessage 
{
	
	private Integer content = null;
	
	MsgDice(Integer result) {
		super(UpdateMessage.UpdateType.DICE);
		this.content = result;
	}
	
	public Integer getContent()
	{
		return content;
	}
	
}
