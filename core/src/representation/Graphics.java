package representation;

import java.util.ArrayList;

import representation.View.Screen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import database.*;



public class Graphics  extends ApplicationAdapter {
	
	private MainMenu mainMenu = new MainMenu();
	private NewGameMenu newGameMenu = new NewGameMenu();
	private Gameplay gameplay = new Gameplay();
	private EndGame endGame = new EndGame();
	private Credits credits = new Credits();
	private Rules rules = new Rules();
	

	@Override
	public void create () {
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
			switch(View.getView())
			{
				case MAINMENU:
					mainMenu.batch();
					//newGameMenu.init();
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
				case END:
					System.exit(0);
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
