package representation;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import database.*;



public class Graphics extends ApplicationAdapter {
	
	public enum View {
	    MAINMENU, NEWGAMEMENU, GAMEPLAY, ENDGAME,
	    CREDITS, RULES
	}
	
	View view;

	MainMenu mainMenu = new MainMenu();
	NewGameMenu newGameMenu = new NewGameMenu();
	Gameplay gameplay = new Gameplay();
	EndGame endGame = new EndGame();
	Credits credits = new Credits();
	Rules rules = new Rules();
	

	@Override
	public void create () {
		//TODO zmienic na main menu potem
		view = View.GAMEPLAY;
		
		mainMenu.init();
		newGameMenu.init();
		gameplay.init();
		endGame.init();
		credits.init();
		rules.init();
	}

	@Override
	public void render () 
	{
		switch(view)
		{
			case MAINMENU:
				mainMenu.batch();
		        break;
			case NEWGAMEMENU:
		        newGameMenu.batch();
		        break;
			case GAMEPLAY:
				gameplay.batch();
		        break;
			case ENDGAME:
		        endGame.batch();
		        break;
			case CREDITS:
		        credits.batch();
		        break;
			case RULES:
		        rules.batch();
		        break;
		}
	}

	
//TODO na razie mnie to nie interesuje	
	public void dispose() {
	      // dispose of all the native resources
		
	   }

	   @Override
	   public void resize(int width, int height) {
	   }

	   @Override
	   public void pause() {
	   }

	   @Override
	   public void resume() {
	   }
	}
