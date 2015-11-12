package representation;

public abstract class View {

	public enum Screen {
	    MAINMENU, NEWGAMEMENU, GAMEPLAY, ENDGAME,
	    CREDITS, RULES,END}
	
	private static Screen view = Screen.GAMEPLAY;
	final static int screensizeX=1366; 
	final static int screensizeY=768; 
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
	
}
