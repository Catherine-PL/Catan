package catan.network;

import database.Tile;

public class MsgTile extends UpdateMessage 
{
	
	private Tile content = null;
	private int index;
	
	MsgTile(Tile tile, int index) {
		super(UpdateMessage.UpdateType.TILE);
		this.content = tile;
		this.index = index;
	}
	
	public Tile getContent()
	{
		return content;
	}
	public int getIndex()
	{
		return index;
	}
}
