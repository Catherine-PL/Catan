package representation;

import java.io.IOException;
import java.nio.channels.NetworkChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import representation.View.Screen;
import catan.network.*;
import catan.network.GameCommunication.InvStatus;
import catan.network.SystemMessage.SystemType;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

import database.*;



public class NewGameMenu  extends View implements InputProcessor
{
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
	private Texture start;	
	private Texture invitation;
	
	private ArrayList<Texture> textures = new ArrayList<Texture>();
	private int avatarTextureID;
	BitmapFont font;
	boolean accepted;
	boolean invited;
	boolean inputname;
	private Map<Integer,String> peers=new HashMap<Integer,String>();
	private Map<Integer,String> guests=new HashMap<Integer,String>();
	
	
	private boolean hasInvitation;
	
	
	public void init()
	{
		
		start = new Texture(Gdx.files.internal("startgameaborttext.png"));
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
		invitation=new Texture(Gdx.files.internal("invitation.png"));
		name = new Texture(Gdx.files.internal("name.png"));
		invite = new Texture(Gdx.files.internal("invite.png"));
		initTextures();
		avatar=textures.get(0);
		
		//networking side
		//TODO na razie na sztywno ju¿ w liœcie zaproszonyc
		
		
		
		
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
		//if(invited==true) System.out.println("aaaaa");


		
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
			
			
			if(invited==false) 
			{
				batch.draw(invite, 1080, 220);	
			}
			else
			{
				batch.draw(start, 0,0);	
			}
			Set<String> peersnames = getNetwork().peersObservers.get(0).getPeersNames();
			
			int peersStringY=620;
			if(peersnames.size()>0)
			{
				peers.clear();
				for(String s: peersnames)
				{
					//¿eby potem da³o siê ³atwo wyci¹gn¹æ Stringa na podstawie touchdown
					if (!guests.containsValue(s))
					{
						peers.put((620-peersStringY)/30,s);
						font.draw(batch, s, 690,peersStringY);
						peersStringY=peersStringY-30;
					}
				}
			}
			//guests
			peersStringY=620;
			if(guests.get(0)!=null)
			{
				for(String s:guests.values() )
				{
					//¿eby potem da³o siê ³atwo wyci¹gn¹æ Stringa na podstawie touchdown
					//peers.put((620-peersStringY)/30,s);
					font.draw(batch, s,1060,peersStringY);
					peersStringY=peersStringY-30;
				}
				
			}
			
			Map<String, InvStatus> invitedmap = ((ObserverInv)getNetwork().invObservers.get(0)).getAllStatuses();			
			if (invitedmap.size()>0)
			{
				guests.clear();
				
				peersStringY=620;
				for(String s: invitedmap.keySet())
				{
					//¿eby potem da³o siê ³atwo wyci¹gn¹æ Stringa na podstawie touchdown
					guests.put( (620-peersStringY)/30,s);
					switch (invitedmap.get(s))
					{
					case WAIT:
						font.setColor(Color.YELLOW);
						font.draw(batch, s, 1060,peersStringY);	
						font.setColor(Color.WHITE);
						break;
					case ACCEPTED:
						font.setColor(Color.GREEN);
						font.draw(batch, s, 1060,peersStringY);	
						font.setColor(Color.WHITE);
						break;
					case REJECTED:
						font.setColor(Color.RED);
						font.draw(batch, s, 1060,peersStringY);	
						font.setColor(Color.WHITE);
						break;
					
					}
					peersStringY=peersStringY-30;
				}
			}
			
			
			
			
			//obs³uga zaproszenia od kogoœ
			//TODO jakoœ rozs¹dniej. Observator???????
			if(hasInvitation==false) 
			{
				if (!View.invFrom.isEmpty()) hasInvitation=true; 
			}
			if(hasInvitation==true)
			{
				//TODO wyswietl okno
				batch.draw(invitation, 0, 0);	
				font.draw(batch, View.invFrom.get(0), 650,400);	
				//od kogo zaproszenie i Q reject A accept
				//i dodaæ klikanie do tego

			}	
			
						
			if(((ObserverStart)getNetwork().invObservers.get(1)).getStateGame())
			{
				//TODO
				game = new Game(getNetwork().getQueue(),namestring);
				View.setView(Screen.GAMEPLAY);
				
				
			}
			
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
    	//bycie zaproszonym
    	if(hasInvitation==true) 
		{
    		if(Gdx.input.isKeyPressed(Keys.Q))
    		{
    			getNetwork().sendInvitationAnswer(View.invFrom.get(0), SystemType.REJECT);
    			
    			//View.invFrom.remove(0);
    			View.invFrom.remove(0);
    			hasInvitation=false;
    		}
    		if(Gdx.input.isKeyPressed(Keys.A))
    		{
    			getNetwork().sendInvitationAnswer(View.invFrom.get(0), SystemType.ACCEPT);
    			//View.invFrom.remove(0);
    			View.invFrom.remove(0);
    			hasInvitation=false;
    		}
			
			
			
			
			return false;
		}
    	
    	
    	
    	
    	
    	
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

    
	public boolean touchList(int X, int Y, int Xth, int Yth)
	{

		if(Xth==690) //click on peers
		{
			for(int i=0;i<peers.size();i++)
			{
				if((X>Xth) && (X<Xth+200) && (Y>(Yth-i*30)) && (Y<(Yth-i*30+20)))
				{
					if(peers.get(i)!=null)
					{
						guests.put(guests.size(),peers.get(i));
						//TODO !!!!!! remove from list in network
						//TODO usunac z listy peers w network, po usunieciu z guest dodaæ do peers w network
						peers.remove(i);
						for(int j=i+1;j<peers.size();j++)
						{
							peers.put(j-1, peers.get(j));
							peers.remove(j);
						}
						return true;
					}
					
				}
			}	
		}
		if(Xth==1060) //click on guests
		{
			for(int i=0;i<guests.size();i++)
			{
				if((X>Xth) && (X<Xth+200) && (Y>(Yth-i*30)) && (Y<(Yth-i*30+20)))
				{
					if(guests.get(i)!=null)
					{
						peers.put(peers.size(),guests.get(i));
						//TODO ??? jakiœ b³¹d jest :v
						//TODO usunac z listy peers w network, po usunieciu z guest dodaæ do peers w network
						guests.remove(i);
						for(int j=i+1;j<guests.size();j++)
						{
							guests.put(j-1, guests.get(j));
							guests.remove(j);
						}
						return true;
					}
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
    		//jak sie jest zaproszonym to nie mo¿na nic klikaæ!
			if(hasInvitation==true) return false;

    		int X= screenX;
			int Y=screensizeY - screenY;

			if ((touchList(X,Y,690,620)) || (touchList(X,Y,1060,620)) )
			{
				return true;
			}
			
			if ((X>600) && (X<600+200) &&(Y>120) && (Y<130+31))
			{
				
				setView(Screen.MAINMENU);
			}
			if(accepted==false )
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
					namestring=nametext.toString();
					//TODO zmienic imie gracza u gracza!!!
					
					//TODO wys³aæ zmienione imiê
					
					//network initialization
					setNetwork(new CatanNetwork(namestring));
					getNetwork().readAddresses();
					try {
						getNetwork().initNetwork();
					} catch (IOException e) {
						// TODO Auto-generated catch block - IO exception - b³¹d przy tworzeniu serverportu
						e.printStackTrace();
					}
					
					
					//TODO usun¹æ potem - na razie dodaje sama siebie	
					//Communication.sleep(5000);
					//TODO usunac invitedpeers bo to samo mam w guests
					//invitedpeers.add(namestring);
					//TODO do razu przejscie do Gameplay
					//getNetwork().invite(invitedpeers);
					
					//Communication.sleep(5000);
					
					//getNetwork().start();
					
					
					
				}		
			}
			else if (invited==false)
			{
				//invite
				if ((X>1060) && (X<1060+130) &&(Y>235) && (Y<235+45))
				{
					List <String> toinvite = new LinkedList<String>(); 
					for(int i=0;i<guests.size();i++)
					{
						if (guests.get(i)!=null) 	toinvite.add(guests.get(i));;
					}
					if(toinvite.size()>0) getNetwork().invite(toinvite);						
					invited=true;
					return true;					
				}				
			}
			
			
		    if (invited==true)
			{
				if ((X>1000) && (X<1195) &&(Y>235) && (Y<270))
				{
					// Rzucamy koscia, wynik do start, jezeli jest ok ludi to start return true.
					Dice.getInstance().throwDice();
					if(getNetwork().start())
						View.setView(Screen.GAMEPLAY);
					
					
					//TODO 
					//obsluga Start Game
					//TODO from GAme dice
					//Communication.sleep(1000);
					//getNetwork().sendInvitationAnswer("YOUR NAME", SystemType.ACCEPT);
					
					
					
					//System.out.println(getNetwork().start(5));
					//TODO tylko tymaczasowo pomi¿sze
					
				 	//View.setView(Screen.GAMEPLAY);
				}
				else if ((X>1097) && (X<1195) &&(Y>screensizeY-612) && (Y<screensizeY-577))
				{
					//TODO 
					//obsluga Abort
					//getNetwork().
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
