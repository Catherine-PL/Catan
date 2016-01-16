package catan.network;

import java.util.Set;

class MsgStartGame extends SystemMessage 
{

//	private Set<String> content;
	
	MsgStartGame() 
	{
		super(SystemType.START_GAME);
		//this.content = addresses;
	}
	
//	Set<String> getContent()
//	{
		//return content;
	//}

}
