package catan.network;

import database.Tile;

class MsgTile extends UpdateMessage 
{
	
	private Tile content = null;
	private int index;
	
	MsgTile(Tile tile, int index) {
		super(UpdateMessage.UpdateType.TILE);
		this.content = tile;
		this.index = index;
	}
	
	Tile getContent()
	{
		return content;
	}
	int getIndex()
	{
		return index;
	}
}
