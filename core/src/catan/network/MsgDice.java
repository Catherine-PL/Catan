package catan.network;

class MsgDice extends UpdateMessage 
{
	
	private Integer first = null;
	private Integer second = null;
	
	MsgDice(Integer first, Integer second) {
		super(UpdateMessage.UpdateType.DICE);
		this.first = first;
		this.second = second;
	}
	
	public Integer getFirst()
	{
		return first;
	}
	public Integer getSecond()
	{
		return second;
	}
	
}
