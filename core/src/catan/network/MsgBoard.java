package catan.network;

import database.Board;

public class MsgBoard extends UpdateMessage 
{

	private Board content = null;
	
	MsgBoard(Board board) {
		super(UpdateMessage.UpdateType.BOARD);
		this.content = board;
	}
	
	public Board getContent()
	{
		return content;
	}

}
