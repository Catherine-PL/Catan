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
	private int originX1;
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
		originX=505;
		originX1=523;		
		originY1=90;
		originY=originY1+30;
		
	}
	
	public void batch()
	{
		Gdx.input.setInputProcessor(this);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(newgame, originX, originY1);		
		batch.draw(credits, originX1, originY1);	
		batch.draw(rules, originX1, originY1);	
		batch.draw(exit, originX1, originY1);	
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
    		int X=screenX;
			int Y=screensizeY - screenY;
				if((X>(originX1))&&(X<(originX1+321))&& (Y>originY)&& (Y<(originY+247)))
				{		
		    	//new game
			    	if (Y>(originY+184))
			    	{
			    		setView(Screen.NEWGAMEMENU);
			    		return false;
			    	}
			    	//rules
			    	else if ((Y<(originY+165))&&(Y>(originY+125)))
			    	{
			    		setView(Screen.RULES);
			    		return false;
			    	}
			    	//credits
			    	else if ((Y>(originY+67))&&(Y<(originY+105)))
			    	{
			    		setView(Screen.CREDITS);
			    		return false;
			    	}
			    	//exit
			    	else if (Y<(originY+42))
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
	

