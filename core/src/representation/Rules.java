package representation;

import java.util.ArrayList;

import representation.View.Screen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import database.*;



public class Rules extends View implements InputProcessor
{
	private SpriteBatch batch;
	
	private Texture background;
	private Texture returnbutton;
	
	public void init()
	{
		batch= new SpriteBatch();
		background = new Texture(Gdx.files.internal("rulesback.png"));
		returnbutton = new Texture(Gdx.files.internal("return.png"));
	}
	
	public void batch()
	{
		Gdx.input.setInputProcessor(this);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(returnbutton, 455, 100);	
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
			if ((X>455) && (X<455+421) &&(Y>120) && (Y<130+61))
			{
				setView(Screen.MAINMENU);
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
