package catan.network;

/**
 * Specified type of abstract Message class
 * @author Sebastian
 *
 */
public class MsgDice extends Message {
		
	private int content;
	
	MsgDice(int cont)
	{
		this.type = Message.Type.DICE;
		content = cont;
	}
	
	/**
	 * 
	 * @return Received roll of the dice
	 */
	public int getContent()
	{
		return content;
	}
	
}
