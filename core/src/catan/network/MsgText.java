package catan.network;

/**
 * Specified type of abstract Message class 
 * @author Sebastian
 *
 */
public class MsgText extends Message {
	
	private String content;

	MsgText(String text)
	{
		this.type = Message.Type.TEXT;
		content = text;				
	}
	
	/**
	 * 
	 * @return Received string text
	 */
	public String getContent()
	{
		return content;
	}

}
