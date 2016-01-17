package representation;

import java.util.LinkedList;
import java.util.List;

import catan.network.CatanNetwork;
import database.Game;

public abstract class View {

	public enum Screen {
	    MAINMENU, NEWGAMEMENU, GAMEPLAY, ENDGAME,
	    CREDITS, RULES,END}
	
	private static Screen view = Screen.MAINMENU;
	final static int screensizeX=1366; 
	final static int screensizeY=768; 
	private static CatanNetwork network;
	public static Game game;
	
	public static List<String> invFrom = new LinkedList<String>();
	
	
	
	public void init(){}
	public void batch(){}
	
	public static Screen setView(Screen _view)
	{
		view = _view;
		return view;
	}
	
	public static Screen getView()
	{
		return view;
	}
	public static CatanNetwork getNetwork() {
		return network;
	}
	public void setNetwork(CatanNetwork network) {
		this.network = network;
	}
	
}
