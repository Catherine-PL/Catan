package representation;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

import database.*;



public class Gameplay extends View implements InputProcessor
{
	public enum TradeState {
	    MAKE_OFFER, RESPOND_OFFER, CHOOSE_RESPONSE ,NOTHING}
	
	public enum SelectedKey {
	    Q,W,E,R,NOTHING}
	
	class Coordinates
	{
		private int x;
		private int y;
		Coordinates(int _x,int _y)
		{
			x=_x;
			y=_y;
		}
		int getX()
		{
			return x;
		}
		int getY()
		{
			return y;
		}
		void set(int _x,int _y)
		{
			x=_x;
			y=_y;
		}
		
	}
	
	
	private SpriteBatch batch;
	
	private Texture gameplayMenu;
	private Texture background;
	private Texture trade;
	private Texture endofturn;
	private Texture buycard;
	
	private Texture makeoffer;
	private Texture chooseoffer;
	private Texture reviewoffer;
	
	
	private Texture citymenu;
	private Texture nobuildingmenu;
	private Texture villagemenu;
	
	private Texture city;
	private Texture nobuilding;
	private Texture village;
	
	//karty
	private Texture cardyear;
	private Texture cardpoint;
	private Texture cardmonopol;
	private Texture cardfree;
	private Texture cardsoldier;
	private Texture cardlongest;
	private Texture cardbiggest;
	//menu kart
	private Texture menuresource;
	private Texture menusoldier;
	
	
	
	private Texture[] roads = new Texture[12];
	private Texture[] buildingColor = new Texture[4];
	private Texture[] acColor = new Texture[4];
	private Texture[] dice = new Texture[6];
	private int[] tradeGoods=new int[10]; 
	
	
	//tekstury na wszystkie tile
	private ArrayList<Texture> textures = new ArrayList<Texture>();
	//tablica na miasta
	private Coordinates[] buildingsXY;
	private Coordinates[]tilesXY;
	private Integer touchedBuildingID;
	private Integer [] touchedBuildingRoads; //drogi tego wybranego budynku

	SelectedKey selected; //zapobiega kilkukrotnemu wcisnieciu Q lub W lub E
	TradeState tradeState;
	BitmapFont font;
	
	
	public void init()
	{
		initRoadsTextures();
		game=new Game();
		
		
		//karty
		 cardyear=new Texture(Gdx.files.internal("gameplay/cards/year.png"));
		  cardpoint=new Texture(Gdx.files.internal("gameplay/cards/point.png"));
		  cardmonopol=new Texture(Gdx.files.internal("gameplay/cards/monopol.png"));
		  cardfree=new Texture(Gdx.files.internal("gameplay/cards/freeroads.png"));
		  cardsoldier=new Texture(Gdx.files.internal("gameplay/cards/soldier.png"));
		  cardlongest=new Texture(Gdx.files.internal("gameplay/cards/longestroad.png"));
		  cardbiggest=new Texture(Gdx.files.internal("gameplay/cards/biggestarmy.png"));
		//menu kart
		  menuresource=new Texture(Gdx.files.internal("gameplay/cards/menuresource.png"));
		  menusoldier=new Texture(Gdx.files.internal("gameplay/cards/menusoldier.png"));
		
		

		
		selected=SelectedKey.NOTHING;
		tradeState=TradeState.RESPOND_OFFER;
		touchedBuildingRoads=null;
		touchedBuildingID=null; //null gdy nic nie wcisniete
		buildingsXY = new Coordinates[54];
		for(int i=0;i<54;i++)
		{
			buildingsXY[i] = new Coordinates(100,100);
		}
		tilesXY = new Coordinates[19];
		for(int i=0;i<19;i++)
		{
			tilesXY[i] = new Coordinates(100,100);
		}
		coordinateTiles();
		batch = new SpriteBatch();
		initTiles();
		initBuildings();
		background = new Texture(Gdx.files.internal("ocean.png"));
		gameplayMenu = new Texture(Gdx.files.internal("gameplay/menugameplay.png"));
		trade = new Texture(Gdx.files.internal("gameplay/trade.png"));
		endofturn = new Texture(Gdx.files.internal("gameplay/endofturn.png"));
		buycard = new Texture(Gdx.files.internal("gameplay/buycard.png"));
		
		makeoffer =new Texture(Gdx.files.internal("gameplay/trade/offer1.png"));
		chooseoffer= new Texture(Gdx.files.internal("gameplay/trade/offer3.png"));
		reviewoffer =new Texture(Gdx.files.internal("gameplay/trade/offer2.png"));
		
		
		city = new Texture(Gdx.files.internal("gameplay/buildings/city.png"));
		nobuilding = new Texture(Gdx.files.internal("gameplay/buildings/nobuilding.png"));
		village = new Texture(Gdx.files.internal("gameplay/buildings/village.png"));
		citymenu = new Texture(Gdx.files.internal("gameplay/buildings/citymenu.png"));
		nobuildingmenu = new Texture(Gdx.files.internal("gameplay/buildings/nobuildingmenu.png"));
		villagemenu = new Texture(Gdx.files.internal("gameplay/buildings/villagemenu.png"));		
		
		coordinateTiles();
		initBuildingColors();
		initActualPlayerColors();
		initDice();
		font = new BitmapFont();
		font.getData().setScale(1.5f, 1.5f);
		//font.setColor(0, 0, 0, 1);
		
	}
	
	public void batch()
	{
		Gdx.input.setInputProcessor(this);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batchMenuGameplay();
		batchTiles();
		batchRoads();
		batchBuildings();
		batchTrade();
		batchCards();
		batch.end();
		batchTouchedBuildingRoads();
	}	
	

	private void initTiles()
	{
		for (int k =0; k<19; k++)
		{
			Tile t = game.getBoard().getTile(k);
			//Tile t = board.getTile(k);
			
			if ("Hills".equals(t.getType() )) 
			{
				textures.add(new Texture(Gdx.files.internal("gameplay/tiles/tileHills.png")));
			}
			else if ("Forest".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.internal("gameplay/tiles/tileForest.png")));
			}
			else if ("Mountains".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.internal("gameplay/tiles/tileMountains.png")));
			}
			else if ("Fields".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.internal("gameplay/tiles/tileFields.png")));
			}
			else if ("Pasture".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.internal("gameplay/tiles/tilePasture.png")));
			}
			else if ("Desert".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.internal("gameplay/tiles/tileDesert.png")));
			}
		}
	}
	
	private void initBuildings()
	{
		int cX=560;
		int cY=700;
		int counter=0;
		//number of cities in the row
		int row=3;
		//12 rows
		for (int i =0;i<12;i++)
		{
			cY=cY-30;
			if ((i % 2==0) && (i!=0))
			{
				cY=cY-33;
			}
			if ((i ==1) || (i==3) || (i==5))
			{
				cX=cX-55;
				row=row+1;
			}
			if ((i ==7) || (i==9) || (i==11))
			{
				cX=cX+55;
				row=row-1;
			}
			
			for(int j = 0; j<row;j++)
			{
				buildingsXY[counter].set(cX+j*110, cY);
				counter=counter+1;
			}	
		}
	}
	
	private void initDice()
	{
		dice[0]=new Texture(Gdx.files.internal("gameplay/dice1.png"));
		dice[1]=new Texture(Gdx.files.internal("gameplay/dice2.png"));
		dice[2]=new Texture(Gdx.files.internal("gameplay/dice3.png"));
		dice[3]=new Texture(Gdx.files.internal("gameplay/dice4.png"));
		dice[4]=new Texture(Gdx.files.internal("gameplay/dice5.png"));
		dice[5]=new Texture(Gdx.files.internal("gameplay/dice6.png"));
	}
	
	
	
	private void initBuildingColors()
	{
		buildingColor[0]=new Texture(Gdx.files.internal("gameplay/buildings/bred.png"));
		buildingColor[1]=new Texture(Gdx.files.internal("gameplay/buildings/bblue.png"));
		buildingColor[2]=new Texture(Gdx.files.internal("gameplay/buildings/byellow.png"));
		buildingColor[3]=new Texture(Gdx.files.internal("gameplay/buildings/borange.png"));
	}
	
	private void initActualPlayerColors()
	{
		acColor[0]=new Texture(Gdx.files.internal("gameplay/apbackred.png"));
		acColor[1]=new Texture(Gdx.files.internal("gameplay/apbackblue.png"));
		acColor[2]=new Texture(Gdx.files.internal("gameplay/apbackyellow.png"));
		acColor[3]=new Texture(Gdx.files.internal("gameplay/apbackorange.png"));
	}
	
	private void initRoadsTextures()
	{
		roads[0]=new Texture(Gdx.files.internal("gameplay/roads/roadNS.png"));
		roads[1]=new Texture(Gdx.files.internal("gameplay/roads/roadNS1.png"));
		roads[2]=new Texture(Gdx.files.internal("gameplay/roads/roadNS2.png"));
		roads[3]=new Texture(Gdx.files.internal("gameplay/roads/roadNS3.png"));
		roads[4]=new Texture(Gdx.files.internal("gameplay/roads/roadNWSE.png"));
		roads[5]=new Texture(Gdx.files.internal("gameplay/roads/roadNWSE1.png"));
		roads[6]=new Texture(Gdx.files.internal("gameplay/roads/roadNWSE2.png"));
		roads[7]=new Texture(Gdx.files.internal("gameplay/roads/roadNWSE3.png"));
		roads[8]=new Texture(Gdx.files.internal("gameplay/roads/roadSWNE.png"));
		roads[9]=new Texture(Gdx.files.internal("gameplay/roads/roadSWNE1.png"));
		roads[10]=new Texture(Gdx.files.internal("gameplay/roads/roadSWNE2.png"));
		roads[11]=new Texture(Gdx.files.internal("gameplay/roads/roadSWNE3.png")); 
		
	}
	
	
	private void batchCards()
	{
		if (game.getThisPlayer().getBiggestArmy()==true)
		{
			batch.draw(cardbiggest ,0,0);
		}
		if (game.getThisPlayer().getLongestRoad() ==true)
		{
			batch.draw(cardlongest,0,0);
		}
		
		
		ArrayList<Card> cards= game.getThisPlayer().getCards();
		for (Card c : cards)
		{
			c.getType();
			switch (c.getType())
			{
			case MONOPOL:
				batch.draw(cardmonopol,0,0);
				break;
			case POINT:
				batch.draw(cardpoint,0,0);
				break;
			case ROAD:
				batch.draw(cardfree,0,0);
				break;
			case SOLDIER:
				batch.draw(cardsoldier,0,0);
				break;
			case YEAR:
				batch.draw(cardyear,0,0);
				break;
			default:
				break;
			}
		}
		batch.draw(makeoffer,0,screensizeY-150);
	}
	
	
	
	private void batchTrade()
	{
		if (tradeState==TradeState.MAKE_OFFER)
		{
			batch.draw(makeoffer,0,screensizeY-150);
		}
		
		if (tradeState==TradeState.CHOOSE_RESPONSE)
		{
			//TODO
			batch.draw(chooseoffer,0,screensizeY-197);
		}
		
		if (tradeState==TradeState.RESPOND_OFFER)
		{
			//TODO
			batch.draw(reviewoffer,0,screensizeY-173);
			
			//wyswietlanie liczb
			for(int i=0;i<5;i++)
			{
				font.draw(batch, Integer.toString(tradeGoods[i]), 50+i*43,screensizeY-88);
			}
			for(int i=5;i<10;i++)
			{
				font.draw(batch, Integer.toString(tradeGoods[i]), 50+(i-5)*43,screensizeY-123);
			}
			
		}
		//wyswietlanie liczb
				if (tradeState==TradeState.MAKE_OFFER || tradeState==TradeState.CHOOSE_RESPONSE)
				{
					
					for(int i=0;i<5;i++)
					{
						font.draw(batch, Integer.toString(tradeGoods[i]), 50+i*43,screensizeY-65);
					}
					for(int i=5;i<10;i++)
					{
						font.draw(batch, Integer.toString(tradeGoods[i]), 50+(i-5)*43,screensizeY-100);
					}
					
				}
		
	}
	
	private void batchBuildings()
	{
		int buildingindex;
		int X;
		int Y;
		for(int j = 0; j<54;j++)
		{
			X=buildingsXY[j].getX();
			Y=buildingsXY[j].getY();
			buildingindex=game.getBoard().getNode(j).getBuilding();
			if(touchedBuildingID!=null)
			{
				if(touchedBuildingID==j) buildingindex=buildingindex+3;
			}
		
			switch (buildingindex)
			{
			case 0:
				batch.draw(nobuilding,X,Y);
				break;
			case 1:
				batch.draw(buildingColor[game.getBoard().getNode(j).getPlayerNumber()],X,Y);
				batch.draw(village,X,Y);
				break;
			case 2:
				batch.draw(buildingColor[game.getBoard().getNode(j).getPlayerNumber()],X,Y);
				batch.draw(city,X,Y);
				break;
			//nobuildingmenu
			case 3:
				batch.draw(nobuildingmenu,X,Y);
				break;
			//villagemenu
			case 4:
				batch.draw(buildingColor[game.getBoard().getNode(j).getPlayerNumber()],X,Y);
				batch.draw(villagemenu,X,Y);
				break;
			//citymenu
			case 5:
				batch.draw(buildingColor[game.getBoard().getNode(j).getPlayerNumber()],X,Y);
				batch.draw(citymenu,X,Y);
				break;
			}
		}		
	}	
			
				
	private void batchTouchedBuildingRoads()
	{
		if(selected==SelectedKey.E)
		{
			batch.begin();
			Node neighbour;
			for(int m=0; m<game.getBoard().getNode(touchedBuildingID).getNoRoads().size();m++)
			{
				neighbour = game.getBoard().getNode(touchedBuildingID).getNoRoads().get(m);				
				font.draw(batch, " " + (m+1),  buildingsXY[neighbour.getNodeNumber()].getX()  , buildingsXY[neighbour.getNodeNumber()].getY()+20 );
			}
			batch.end();
		}
	}
	private void batchRoads()
	{
		int x=0;
		int y=0;
		int myX=0;
		int myY=0;
		int color=-1;
		
		for (int i =0;i<54;i++)
		{				
			myX=buildingsXY[i].getX();
			myY=buildingsXY[i].getY();
			for (Road n : game.getBoard().getNode(i).getRoads())
			{
				if(n.getOwnerID()!=-1)
				{	
					color=game.getColors().get(n.getOwnerID());
					if (n.getTo().getNodeNumber()!=i )
					{
						x=buildingsXY[n.getTo().getNodeNumber()].getX();
						y=buildingsXY[n.getTo().getNodeNumber()].getY();
					}
					else
					{
						x=buildingsXY[n.getFrom().getNodeNumber()].getX();
						y=buildingsXY[n.getFrom().getNodeNumber()].getY();
					}	
					
					if ((x ==myX) && (y>myY))
					{
						batch.draw(roads[0+color], myX+10, myY+10);
					}
					else if ((x ==myX) && (y<myY))
					{
						batch.draw(roads[0+color], myX+10, y+10);
					}
					else if ((x >myX) && (y<myY))
					{
						batch.draw(roads[4+color], myX+10, y+10);			
					}
					else if ((x <myX) && (y>myY))
					{
						batch.draw(roads[4+color], x+10, myY+10);					
					}
					else if ((x <myX) && (y<myY))
					{
						batch.draw(roads[8+color], x+10, y+10);					
					}
					else if ((x >myX) && (y>myY))
					{
						batch.draw(roads[8+color], myX+10, myY+10);					
					}
				}
			}
			
		}
		
		
		
		
		
	}
	
	
	
	private void coordinateTiles()
	{
		int cX = 628; // x position of upper center tile
		int cY = 560; // y position of upper center tile
		int tileX=110; 
		int tileY=127;

		tilesXY[0].set(cX+tileX-1, cY);
		tilesXY[1].set(cX, cY);
		tilesXY[2].set(cX-tileX+1, cY);
		tilesXY[3].set(cX-tileX/2+2-tileX, cY-tileY+tileY/4+2);
		tilesXY[4].set(cX-tileX/2+1, cY-tileY+tileY/4+2);
		tilesXY[5].set(cX+tileX/2-1, cY-tileY+tileY/4+2);
		tilesXY[6].set(cX+tileX/2-2+tileX, cY-tileY+tileY/4+2);
		tilesXY[7].set(cX-2*tileX+2, cY-2*tileY+2*tileY/4+2);
		tilesXY[8].set(cX-tileX+1, cY-2*tileY+2*tileY/4+2);
		tilesXY[9].set(cX, cY-2*tileY+2*tileY/4+2);
		tilesXY[10].set(cX+tileX-1, cY-2*tileY+2*tileY/4+2);
		tilesXY[11].set( cX+2*tileX-2, cY-2*tileY+2*tileY/4+2);
		tilesXY[12].set(cX-tileX/2+2-tileX, cY-3*tileY+3*tileY/4+3);
		tilesXY[13].set(cX-tileX/2+1, cY-3*tileY+3*tileY/4+3);
		tilesXY[14].set(cX+tileX/2-1, cY-3*tileY+3*tileY/4+3);
		tilesXY[15].set(cX+tileX/2-2+tileX, cY-3*tileY+3*tileY/4+3);
		tilesXY[16].set(cX-tileX+1, cY-4*tileY+4*tileY/4+4);
		tilesXY[17].set(cX, cY-4*tileY+4*tileY/4+4);
		tilesXY[18].set( cX+tileX-1, cY-4*tileY+4*tileY/4+4);
	
		for(int i=0;i<19;i++)
		{
			tilesXY[i].set(tilesXY[i].getX()+55,tilesXY[i].getY()+64);
		}
	}
	
	private void batchTiles()
	{
		int cX = 628; // x position of upper center tile
		int cY = 560; // y position of upper center tile
		int tileX=110; 
		int tileY=127;

		batch.draw(textures.get(1), cX, cY);
		batch.draw(textures.get(2), cX-tileX+1, cY);
		batch.draw(textures.get(0), cX+tileX-1, cY);
		//2rz�d
		batch.draw(textures.get(4), cX-tileX/2+1, cY-tileY+tileY/4+2);
		batch.draw(textures.get(5), cX+tileX/2-1, cY-tileY+tileY/4+2);
		batch.draw(textures.get(3), cX-tileX/2+2-tileX, cY-tileY+tileY/4+2);
		batch.draw(textures.get(6), cX+tileX/2-2+tileX, cY-tileY+tileY/4+2);
		//3rz�d
		batch.draw(textures.get(9), cX, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(8), cX-tileX+1, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(10), cX+tileX-1, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(7), cX-2*tileX+2, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(11), cX+2*tileX-2, cY-2*tileY+2*tileY/4+2);
		//4rz�d 
		batch.draw(textures.get(13), cX-tileX/2+1, cY-3*tileY+3*tileY/4+3);
		batch.draw(textures.get(14), cX+tileX/2-1, cY-3*tileY+3*tileY/4+3);
		batch.draw(textures.get(12), cX-tileX/2+2-tileX, cY-3*tileY+3*tileY/4+3);
		batch.draw(textures.get(15), cX+tileX/2-2+tileX, cY-3*tileY+3*tileY/4+3);
		//5rz�d
		batch.draw(textures.get(17), cX, cY-4*tileY+4*tileY/4+4);
		batch.draw(textures.get(16), cX-tileX+1, cY-4*tileY+4*tileY/4+4);
		batch.draw(textures.get(18), cX+tileX-1, cY-4*tileY+4*tileY/4+4);
		
		for(int i=0;i<19;i++)
		{
			font.draw(batch, Integer.toString(game.getBoard().getTile(i).getDiceNumber()), tilesXY[i].getX(),tilesXY[i].getY());
		}
		
	}
	
	private void batchMenuGameplay()
	{
		//ocean
		batch.draw(background,0,0);		
		//ten gracz
		batch.draw(game.getThisPlayer().getAvatar(),0,0);
		//wyswietlenie paska na zasoby, opcje,miasta,drogi i punkty
		batch.draw(gameplayMenu,0,0);
		//wyswietlenie postaci gracza kt�ry aktualnie gra
		batch.draw(game.getActualPlayer().getAvatar(),1100,500, 200, (float) 1.24*200);
		batch.draw(acColor[game.getActualPlayer().getId()],1080,450);
		batch.draw(dice[game.getDice().getFirst()-1],1120,510);
		batch.draw(dice[game.getDice().getSecond()-1],1200,510);
		
		
		//imie aktualnego gracza
		font.draw(batch, " "+game.getActualPlayer().getName(), 1160,500);
		//koniec kolejki
		batch.draw(endofturn,screensizeX/2-180 ,110);
		//kup karte
		batch.draw(buycard,0,6);
		//handluj
		batch.draw(trade,screensizeX/2-30,110);
		//liczby od surowc�w
		int X=170;
		int Y=82;
		font.draw(batch, " "+game.getThisPlayer().getResources("clay"), X,Y);
		font.draw(batch, " "+game.getThisPlayer().getResources("grain"), X+120,Y);
		font.draw(batch, " "+game.getThisPlayer().getResources("ore"), X+2*120,Y);
		font.draw(batch, " "+game.getThisPlayer().getResources("sheep"), X+3*118,Y);
		font.draw(batch, " "+game.getThisPlayer().getResources("wood"), X+4*120,Y);
		font.draw(batch, " "+game.getThisPlayer().getPoints(), X+4*120+65,Y);	
		
	}
	
	

//overrides

@Override
public boolean keyDown(int keycode) {
	//wcisniecie QWER
	if(touchedBuildingID!=null)
	{
		//to do wyboru drogi.
		if(selected==SelectedKey.E)
		{
			int noRoadsSize =(game.getBoard().getNode(touchedBuildingID)).getRoadsIdImprove().size();
			 if(Gdx.input.isKeyPressed(Keys.NUM_1 ))
			 {
				 if(noRoadsSize>=1 )  game.getBoard().getNode(touchedBuildingID).buildRoad(game.getThisPlayer(),game.getBoard().getNode(touchedBuildingID).getRoadsIdImprove().get(0));
				 selected=SelectedKey.E;
			 }
			 if(Gdx.input.isKeyPressed(Keys.NUM_2 ))
			 {
				 if(noRoadsSize>=2 ) game.getBoard().getNode(touchedBuildingID).buildRoad(game.getThisPlayer(),game.getBoard().getNode(touchedBuildingID).getRoadsIdImprove().get(1));
				 selected=SelectedKey.E;
			 }
			 if(Gdx.input.isKeyPressed(Keys.NUM_3 ))
			 {
				 if(noRoadsSize>=3 )  game.getBoard().getNode(touchedBuildingID).buildRoad(game.getThisPlayer(),game.getBoard().getNode(touchedBuildingID).getRoadsIdImprove().get(2));
				 selected=SelectedKey.E;
			 }
		}
		
		
		
		if(selected==SelectedKey.NOTHING)
		{	
			 if(Gdx.input.isKeyPressed(Keys.Q)) //build settlement
			 {
				 int build=0;
				 selected=SelectedKey.Q;
				 build =Building.buildSettlement(game.getThisPlayer(), game.getBoard().getNode(touchedBuildingID));
				 if(build>0) { nobuildingmenu = new Texture(Gdx.files.internal("gameplay/buildings/nobuildingnocity.png"));}
				 else 
				 {
					 citymenu = new Texture(Gdx.files.internal("gameplay/buildings/citymenu.png"));
					 nobuildingmenu = new Texture(Gdx.files.internal("gameplay/buildings/nobuildingmenu.png"));
					 villagemenu = new Texture(Gdx.files.internal("gameplay/buildings/villagemenu.png"));
					 touchedBuildingID=null;
					 touchedBuildingRoads=null;
					 selected=SelectedKey.NOTHING;
		
				 }
			 }
			
			
			
			 if(Gdx.input.isKeyPressed(Keys.W)) //build city
			 {
				 int build=0;
				 selected=SelectedKey.W;
				 build =Building.buildCity(game.getThisPlayer(), game.getBoard().getNode(touchedBuildingID));
				 if(build>0) 
				 { 
					 if(game.getBoard().getNode(touchedBuildingID).getBuilding()==0) nobuildingmenu = new Texture(Gdx.files.internal("gameplay/buildings/nobuildingnocity.png"));
					 if(game.getBoard().getNode(touchedBuildingID).getBuilding()==1) villagemenu = new Texture(Gdx.files.internal("gameplay/buildings/villagenocity.png"));
				 }
				 else 
				{
					 citymenu = new Texture(Gdx.files.internal("gameplay/buildings/citymenu.png"));
					 nobuildingmenu = new Texture(Gdx.files.internal("gameplay/buildings/nobuildingmenu.png"));
					 villagemenu = new Texture(Gdx.files.internal("gameplay/buildings/villagemenu.png"));
					 touchedBuildingID=null;
					 touchedBuildingRoads=null;
					 selected=SelectedKey.NOTHING;
		
				}
			 }
			 if(Gdx.input.isKeyPressed(Keys.E))//build a road
			 {
				 selected=SelectedKey.E;
				 citymenu = new Texture(Gdx.files.internal("gameplay/buildings/cityroad.png"));
				 nobuildingmenu = new Texture(Gdx.files.internal("gameplay/buildings/nobuildingroad.png"));
				 villagemenu = new Texture(Gdx.files.internal("gameplay/buildings/villageroad.png"));
	
			 }
		}
		 if(Gdx.input.isKeyPressed(Keys.R))//zamknij menu
		 {
			 citymenu = new Texture(Gdx.files.internal("gameplay/buildings/citymenu.png"));
			 nobuildingmenu = new Texture(Gdx.files.internal("gameplay/buildings/nobuildingmenu.png"));
			 villagemenu = new Texture(Gdx.files.internal("gameplay/buildings/villagemenu.png"));
			 touchedBuildingID=null;
			 touchedBuildingRoads=null;
			 selected=SelectedKey.NOTHING;
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
		//TODO doda� else if bo po co  ma si� sprawdza� wszystko jak ju� klikni�cie by�o
		int X=screenX;
		int Y=screensizeY - screenY;
	
		buildingTouch(X,Y);
		//endofturn
		if (X>510 && Y<screensizeY-610 && X<670 && Y>screensizeY-635 )
		{
			//TODO
			game.endTurn();
		}
		//trade
		tradeTouch(X,Y);
		buycardTouch(X,Y);
		cardsTouch(X,Y);
		
	}
    return false;
}


private void cardsTouch(int X, int Y)
{
	if ( Y<screensizeY-623)
	{
		//TODO nie umiem wywo�a� metod kart
		//Point
		if (X>771 &&  X<856 )
		{
			//Point.playCard(game.getThisPlayer());
		}
		//Year
		else if (X>856 &&  X<940 )
		{
			
		}
		//FreeRoads
		else if (X>940 &&  X<1026 )
		{
			
		}
		//Monopol
		else if (X>1026 &&  X<1111 )
		{
			
		}
		//Soldier
		else if (X>1111 &&  X<1197 )
		{
			
		}
		
	}
}





private void buycardTouch(int X, int Y)
{
	if (X>330 && Y<screensizeY-638 && X<477 && Y>screensizeY-664 )
	{
		//TODO odkomentowa� jak b�dzie dzia�a� 
		//game.getBoard().buyCard(game.getThisPlayer());
	}
}



private void tradeTouch(int X, int Y)
{

	if (tradeState==TradeState.NOTHING)
	{
		if (X>705 && Y<screensizeY-610 && X<780 && Y>screensizeY-635 )
		{
			tradeState=TradeState.MAKE_OFFER;
		}
	}
	
	if (tradeState==TradeState.MAKE_OFFER)
	{
		for(int i=0;i<5;i++)
		{
			if (X>50+i*43 && Y<screensizeY-45 && X<65+i*43 && Y>screensizeY-65 )
			{
				tradeGoods[i]=tradeGoods[i]+1;
			}
		}
		for(int i=5;i<10;i++)
		{
			if (X>50+(i-5)*43 && Y<screensizeY-85 && X<65+(i-5)*43 && Y>screensizeY-105 )
			{
				tradeGoods[i]=tradeGoods[i]+1;
			}
		}
		
		
		
		if (X>35 && Y<screensizeY-111 && X<80 && Y>screensizeY-125 )
		{
			for(int i=0;i<10;i++)
			{
				tradeGoods[i]=0;
			}
			tradeState=TradeState.NOTHING;
		}
		
		if (X>210 && Y<screensizeY-111 && X<245 && Y>screensizeY-125 )
		{
			//przejdz do menu oczekiwania na oferty
			tradeState=TradeState.CHOOSE_RESPONSE;
			//wyslij wiadomosc do innych uzytkownik�w z ofert�
			//TODO
		}
	}
	
	if (tradeState==TradeState.CHOOSE_RESPONSE)
	{
		//TODO
		if (X>705 && Y<screensizeY-610 && X<780 && Y>screensizeY-635 )
		{
			tradeState=TradeState.MAKE_OFFER;
		}
	}
	
	if (tradeState==TradeState.RESPOND_OFFER)
	{
		//TODO
		if (X>705 && Y<screensizeY-610 && X<780 && Y>screensizeY-635 )
		{
			tradeState=TradeState.MAKE_OFFER;
		}
	}
	
	
	
}

private void buildingTouch(int x, int y)
{
	
	if(touchedBuildingID==null)
	{
		int myX;
		int myY;
		for (int i =0;i<54;i++)
		{	
			myX=buildingsXY[i].getX()+20;
			myY=buildingsXY[i].getY()+20;  
			if(Math.sqrt(Math.pow( Math.abs((double)(x-myX)),2)+Math.pow( Math.abs((double)(y-myY)),2))<=25)
			{
				touchedBuildingID=Integer.valueOf(i);
				break;
			}	
		}
	}
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







