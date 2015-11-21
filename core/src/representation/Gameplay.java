package representation;


import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

import database.*;



public class Gameplay extends View implements InputProcessor
{
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
	private Texture playerAvatar;
	private Texture cubePlayer;
	
	private Texture citymenu;
	private Texture nobuildingmenu;
	private Texture villagemenu;
	
	private Texture city;
	private Texture nobuilding;
	private Texture village;
	
	private Texture roadSWNE;
	private Texture roadNWSE;
	private Texture roadSN;
	
	private Board board;
	
	//tekstury na wszystkie tile
	private ArrayList<Texture> textures = new ArrayList<Texture>();
	//tablica na miasta
	private Coordinates[] buildingsXY;
	private Coordinates[]tilesXY;
	private Integer touchedBuildingID;
	private Integer [] touchedBuildingRoads; //drogi tego wybranego budynku
	
	private Player thisPlayer;
	
	boolean selected; //zapobiega kilkukrotnemu wcisnieciu Q lub W
	
	
	public void init()
	{
		selected=false;
		thisPlayer= new Player(1);
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
		board = Board.getInstance();
		board.setNeighbours();
		initTiles();
		initBuildings();
		background = new Texture(Gdx.files.internal("ocean.png"));
		cubePlayer = new Texture(Gdx.files.internal("avatars/playeravatar1.png"));
		gameplayMenu = new Texture(Gdx.files.internal("menualfa.png"));
		playerAvatar = new Texture(Gdx.files.internal("avatars/playeravatar4.png"));
		
		city = new Texture(Gdx.files.internal("city.png"));
		nobuilding = new Texture(Gdx.files.internal("nobuilding.png"));
		village = new Texture(Gdx.files.internal("village.png"));
		citymenu = new Texture(Gdx.files.internal("citymenu.png"));
		nobuildingmenu = new Texture(Gdx.files.internal("nobuildingmenu.png"));
		villagemenu = new Texture(Gdx.files.internal("villagemenu.png"));
		
		roadSWNE = new Texture(Gdx.files.internal("roadSWNE.png"));;
		roadNWSE = new Texture(Gdx.files.internal("roadNWSE.png"));;
		roadSN = new Texture(Gdx.files.internal("roadNS.png"));;
		
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
		batch.end();
	}	
	

	private void initTiles()
	{
		for (int k =0; k<19; k++)
		{
			Tile t = board.getTile(k);
			
			if ("Hills".equals(t.getType() )) 
			{
				textures.add(new Texture(Gdx.files.internal("tiles/tileHills.png")));
			    //textures.add(new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/tiles/tileHills.png")));
			}
			else if ("Forest".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.internal("tiles/tileForest.png")));
			}
			else if ("Mountains".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.internal("tiles/tileMountains.png")));
			}
			else if ("Fields".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.internal("tiles/tileFields.png")));
			}
			else if ("Pasture".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.internal("tiles/tilePasture.png")));
			}
			else if ("Desert".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.internal("tiles/tileDesert.png")));
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
	
	private void batchBuildings()
	{
		int buildingindex;
		int X;
		int Y;
		for(int j = 0; j<54;j++)
		{
			X=buildingsXY[j].getX();
			Y=buildingsXY[j].getY();
			buildingindex=board.getNode(j).getBuilding();
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
				batch.draw(village,X,Y);
				break;
			case 2:
				batch.draw(city,X,Y);
				break;
			//nobuildingmenu
			case 3:
				batch.draw(nobuildingmenu,X,Y);
				break;
			//villagemenu
			case 4:
				batch.draw(villagemenu,X,Y);
				break;
			//citymenu
			case 5:
				batch.draw(citymenu,X,Y);
				break;
			}
		}		
}	
			
				
	private void batchTouchedBuildingRoads()
	{
		//TODO
		//wysietlanie odpowiednich numerow jezeli zostanie zaznaczone menu dróg
	}
	
	//TODO 
	private void batchRoads()
	{
		int x=0;
		int y=0;
		int myX=0;
		int myY=0;
		
		for (int i =0;i<54;i++)
		{				
			myX=buildingsXY[i].getX();
			myY=buildingsXY[i].getY();
			for (Node n : board.getNode(i).getNeighbours())
			{
				x=buildingsXY[n.getNodeNumber()].getX();
				y=buildingsXY[n.getNodeNumber()].getY();
				
				
				if ((x ==myX) && (y>myY))
				{
					batch.draw(roadSN, myX+10, myY+10);
				}
				else if ((x ==myX) && (y<myY))
				{
					batch.draw(roadSN, myX+10, y+10);
				}
				else if ((x >myX) && (y<myY))
				{
					batch.draw(roadNWSE, myX+10, y+10);					
				}
				else if ((x <myX) && (y>myY))
				{
					batch.draw(roadNWSE, x+10, myY+10);					
				}
				else if ((x <myX) && (y<myY))
				{
					batch.draw(roadSWNE, x+10, y+10);					
				}
				else if ((x >myX) && (y>myY))
				{
					batch.draw(roadSWNE, myX+10, myY+10);					
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
		//2rz¹d
		batch.draw(textures.get(4), cX-tileX/2+1, cY-tileY+tileY/4+2);
		batch.draw(textures.get(5), cX+tileX/2-1, cY-tileY+tileY/4+2);
		batch.draw(textures.get(3), cX-tileX/2+2-tileX, cY-tileY+tileY/4+2);
		batch.draw(textures.get(6), cX+tileX/2-2+tileX, cY-tileY+tileY/4+2);
		//3rz¹d
		batch.draw(textures.get(9), cX, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(8), cX-tileX+1, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(10), cX+tileX-1, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(7), cX-2*tileX+2, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(11), cX+2*tileX-2, cY-2*tileY+2*tileY/4+2);
		//4rz¹d 
		batch.draw(textures.get(13), cX-tileX/2+1, cY-3*tileY+3*tileY/4+3);
		batch.draw(textures.get(14), cX+tileX/2-1, cY-3*tileY+3*tileY/4+3);
		batch.draw(textures.get(12), cX-tileX/2+2-tileX, cY-3*tileY+3*tileY/4+3);
		batch.draw(textures.get(15), cX+tileX/2-2+tileX, cY-3*tileY+3*tileY/4+3);
		//5rz¹d
		batch.draw(textures.get(17), cX, cY-4*tileY+4*tileY/4+4);
		batch.draw(textures.get(16), cX-tileX+1, cY-4*tileY+4*tileY/4+4);
		batch.draw(textures.get(18), cX+tileX-1, cY-4*tileY+4*tileY/4+4);
		
	}
	
	private void batchMenuGameplay()
	{
		//ocean
		batch.draw(background,0,0);		
		//gracz
		batch.draw(playerAvatar,0,0);
		//wyswietlenie paska na zasoby, opcje,miasta,drogi i punkty
		batch.draw(gameplayMenu,0,0);
		//wyswietlenie postaci gracza który aktualnie gra
		batch.draw(cubePlayer,1050,400);
		//wyswietlanie menu miasta jezeli miasto wcisniete
	}
	
	






//overrides

@Override
public boolean keyDown(int keycode) {
	//wcisniecie QWE
	if(touchedBuildingID!=null)
	{
		if(selected==false)
		{	
			 if(Gdx.input.isKeyPressed(Keys.Q)) //build settlement
			 {
				 int build=0;
				 selected=true;
				 build =Building.buildSettlement(thisPlayer, board.getNode(touchedBuildingID));
				 if(build==1) { nobuildingmenu = new Texture(Gdx.files.internal("nobuildingroad.png"));}
				 if(build==2) { nobuildingmenu = new Texture(Gdx.files.internal("nobuildingroad.png"));}
				 if(build==3) { nobuildingmenu = new Texture(Gdx.files.internal("nobuildingroad.png"));}
				 if(build==4) { nobuildingmenu = new Texture(Gdx.files.internal("nobuildingroad.png"));}
				 if(build==5) { nobuildingmenu = new Texture(Gdx.files.internal("nobuildingroad.png"));}				 
				 //TODO metoda budujaca w ifie zeby wyswietlic info o tym, ze nie mamy wystarczajaco surowcow
				 //		 zamiana tekstury nastepuje przez za³adowanie innego pliku do zmiennej nobuildingmenu/viilagemeny/citymenu
			 }
			
			
			
			 if(Gdx.input.isKeyPressed(Keys.W)) //build city
			 {
				 int build=0;
				 selected=true;
				 build =Building.buildCity(thisPlayer, board.getNode(touchedBuildingID));
				 if(build==1) { nobuildingmenu = new Texture(Gdx.files.internal("nobuildingroad.png"));}
				 if(build==2) { nobuildingmenu = new Texture(Gdx.files.internal("nobuildingroad.png"));}
				 if(build==3) { nobuildingmenu = new Texture(Gdx.files.internal("nobuildingroad.png"));}
				 if(build==4) { nobuildingmenu = new Texture(Gdx.files.internal("nobuildingroad.png"));}
				 
				 //TODO metoda budujaca w ifie zeby wyswietlic info o tym, ze nie mamy wystarczajaco surowcow
				 //		 zamiana tekstury nastepuje przez za³adowanie innego pliku do zmiennej nobuildingmenu/viilagemeny/citymenu
			 }
			 if(Gdx.input.isKeyPressed(Keys.E))//build a road
			 {
				 selected=true;
				 //TODO
				 //touchedBuildingRoads=;
				 
				 //		zamiana tekstury nastepuje przez za³adowanie innego pliku do zmiennej nobuildingmenu/viilagemeny/citymenu
				 citymenu = new Texture(Gdx.files.internal("cityroad.png"));
				 nobuildingmenu = new Texture(Gdx.files.internal("nobuildingroad.png"));
				 villagemenu = new Texture(Gdx.files.internal("villageroad.png"));
				 // jak bêd¹ wyœwietlane numery drog? bo sa one uzaleznione od sasiadow
			 }
		}
		 if(Gdx.input.isKeyPressed(Keys.R))//zamknij menu
		 {
			 //zamykanie okien tylko  poprzez wcisniecie E, nigdy automatycznie - rozwiazuje problem nullowania ID
			 
			 
			 citymenu = new Texture(Gdx.files.internal("citymenu.png"));
			 nobuildingmenu = new Texture(Gdx.files.internal("nobuildingmenu.png"));
			 villagemenu = new Texture(Gdx.files.internal("villagemenu.png"));
			 touchedBuildingID=null;
			 touchedBuildingRoads=null;
			 selected=false;
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
		int X=screenX;
		int Y=screensizeY - screenY;
	
		buildingTouch(X,Y);
		//TODO ktos wciska na menu? inne opcje 
		//menu
	
	}
    return false;
}

private void buildingTouch(int x, int y)
{
	
	if(touchedBuildingID==null)
	{
		int myX;
		int myY;
		for (int i =0;i<54;i++)
		{	
			myX=buildingsXY[i].getX()+10;
			myY=buildingsXY[i].getY()+10;  
			if(Math.sqrt(Math.pow( (double)(x-myX),2)+Math.pow( (double)(y-myY),2))<=30)
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







