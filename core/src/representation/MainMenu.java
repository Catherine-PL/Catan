package representation;

import java.util.ArrayList;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

import database.*;



public class MainMenu extends View implements InputProcessor
{
	private SpriteBatch batch;
	
	private Texture background;
	private Texture newgame;
	private Texture credits;
	private Texture rules;
	private Texture exit;
	private int originX;
	private int originY1;
	private int originY;
	
	
	public void init()
	{
		background = new Texture(Gdx.files.internal("mainmenu/back.png"));
		newgame = new Texture(Gdx.files.internal("mainmenu/newgame.png"));
		credits = new Texture(Gdx.files.internal("mainmenu/credits.png"));
		rules = new Texture(Gdx.files.internal("mainmenu/rules.png"));
		exit = new Texture(Gdx.files.internal("mainmenu/exit.png"));
		batch= new SpriteBatch();
		originX=455;
		originY1=100;
		originY=originY1+30;
		
	}
	
	public void batch()
	{
		Gdx.input.setInputProcessor(this);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(newgame, originX, originY1);		
		batch.draw(credits, originX, originY1);	
		batch.draw(rules, originX, originY1);	
		batch.draw(exit, originX, originY1);	
		batch.end();
	}	
	
	
	//overrides

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	if(button == Buttons.LEFT){
    		int X=screensizeX - screenX;
			int Y=screensizeY - screenY;
				if((X>(originX+32))&&(X<(originX+421))&& (Y>originY)&& (Y<(originY+342)))
				{		
		    	//new game
			    	if (Y>(originY+260))
			    	{
			    		setView(Screen.NEWGAMEMENU);
			    		return false;
			    	}
			    	//rules
			    	else if ((Y<(originY+237))&&(Y>(originY+172)))
			    	{
			    		setView(Screen.RULES);
			    		return false;
			    	}
			    	//credits
			    	else if ((Y>(originY+85))&&(Y<(originY+149)))
			    	{
			    		setView(Screen.CREDITS);
			    		return false;
			    	}
			    	//exit
			    	else if (Y<(originY+61))
			    	{
			    		setView(Screen.END);
			    		return false;
			    	}
				}
    		
    	}
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
	

