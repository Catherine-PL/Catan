package representation;

import java.util.ArrayList;

import representation.View.Screen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

import database.*;



public class NewGameMenu  extends View implements InputProcessor
{
	//private Player realPlayer;
	
	private SpriteBatch batch;
	
	private StringBuilder nametext;
	private String namestring;
	private Texture background;
	private Texture returnbutton;
	private Texture avatar;
	private Texture arrowl;
	private Texture arrowr;
	private Texture allusers;
	private Texture guestlist;
	private Texture name;
	private Texture invite;	
	private ArrayList<Texture> textures = new ArrayList<Texture>();
	private int avatarTextureID;
	BitmapFont font;
	boolean accepted;
	boolean inputname;
	
	public void init()
	{
		nametext=new StringBuilder(12);
		nametext=nametext.append("YOUR NAME");
		font = new BitmapFont();
		batch= new SpriteBatch();
		background = new Texture(Gdx.files.internal("newgameback.png"));
		returnbutton = new Texture(Gdx.files.internal("return.png"));
		arrowr = new Texture(Gdx.files.internal("arrowr.png"));
		arrowl = new Texture(Gdx.files.internal("arrowl.png"));
		allusers=new Texture(Gdx.files.internal("alluserstext.png"));
		guestlist=new Texture(Gdx.files.internal("guestlisttext.png"));
		name = new Texture(Gdx.files.internal("name.png"));
		invite = new Texture(Gdx.files.internal("invite.png"));
		initTextures();
		avatar=textures.get(0);
	}
	
	public void batch()
	{
		Gdx.input.setInputProcessor(this);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(returnbutton, 600, 100);	
		batch.draw(avatar, 90, 310);	
		//namestring=nametext.toString();
		namestring=nametext.toString();
		if(namestring.length()>12) namestring=nametext.substring(0,13);
		font.draw(batch, namestring, 190, 260);	
		
		if(accepted==false)
		{
			font.draw(batch, "to start or stop writing your name click LPM on the field below", 60, 300);	
			batch.draw(name, 95, 220);
			batch.draw(name, 95, 150);	
			batch.draw(arrowr, 390, 450);	
			batch.draw(arrowl, 60, 450);
		}
		else
		{
			batch.draw(allusers, 650, 650);
			batch.draw(guestlist, 1010, 650);
			batch.draw(invite, 1080, 220);	
			//TODO
			//batch users lists
		}
		batch.end();
	}	
	
	private void initTextures()
	{
		textures.add(new Texture(Gdx.files.internal("avatars/playeravatar.png")));
		textures.add(new Texture(Gdx.files.internal("avatars/playeravatar1.png")));
		textures.add(new Texture(Gdx.files.internal("avatars/playeravatar2.png")));
		textures.add(new Texture(Gdx.files.internal("avatars/playeravatar3.png")));
		textures.add(new Texture(Gdx.files.internal("avatars/playeravatar4.png")));
		
	}
	
	
	//overrides
    @Override
    public boolean keyDown(int keycode) {
    	if(accepted==false)
    	{
    		if (inputname==true)
        	{
    			if ((keycode==Keys.BACKSPACE ) && (nametext.length()>0)) nametext.deleteCharAt(nametext.length()-1);
    			else if ((keycode==Keys.SPACE ) && (nametext.length()>0)) nametext.append(" ");
    			else if(keycode!=Keys.BACKSPACE) nametext.append(Input.Keys.toString(keycode));
    			
        	}
    		else
    		{
        		if(Gdx.input.isKeyPressed(Keys.E))
            	{
            		avatarTextureID=(avatarTextureID+1)% textures.size();
            		avatar = textures.get(avatarTextureID);
            				
            	}
        		else if(Gdx.input.isKeyPressed(Keys.Q))
            	{
            		avatarTextureID=avatarTextureID-1;
            		if(avatarTextureID==-1) avatarTextureID=textures.size()-1;
            		avatar = textures.get(avatarTextureID);
            	}
    		}
    	}

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
    		int X= screenX;
			int Y=screensizeY - screenY;
			if ((X>600) && (X<600+200) &&(Y>120) && (Y<130+31))
			{
				setView(Screen.MAINMENU);
			}
			if(accepted==false)
			{
				if ((X>95) && (X<95+300) &&(Y>240) && (Y<240+60))
				{
					if(inputname==true)
					{
						inputname=false;
					}
					else 
						inputname=true;
				}
				
				if ((X>95) && (X<95+300) &&(Y>170) && (Y<170+60))
				{
					accepted= true;
				}		
			}
			else
			{
				//invite
				if ((X>1060) && (X<1060+130) &&(Y>235) && (Y<235+45))
				{
					//TODO
					accepted=false;
				}
				//all users
				
				//guest list
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