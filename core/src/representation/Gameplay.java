package representation;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import database.*;

public class Gameplay extends View
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
	public void init()
	{
		buildingsXY = new Coordinates[54];
		for(int i=0;i<54;i++)
		{
			buildingsXY[i] = new Coordinates(100,100);
		}

		batch = new SpriteBatch();
		board = Board.getInstance();
		board.setNeighbours();
		initTiles();
		background = new Texture(Gdx.files.internal("ocean.png"));
		cubePlayer = new Texture(Gdx.files.internal("actualplayer1.png"));
		gameplayMenu = new Texture(Gdx.files.internal("menualfa.png"));
		playerAvatar = new Texture(Gdx.files.internal("playeravatar.png"));
		city = new Texture(Gdx.files.internal("city.png"));
		nobuilding = new Texture(Gdx.files.internal("nobuilding.png"));
		village = new Texture(Gdx.files.internal("village.png"));
		roadSWNE = new Texture(Gdx.files.internal("roadSWNE.png"));;
		roadNWSE = new Texture(Gdx.files.internal("roadNWSE.png"));;
		roadSN = new Texture(Gdx.files.internal("roadNS.png"));;
		
	}
	
	public void batch()
	{
		
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
	private void batchBuildings()
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
				switch (board.getNode(counter).getBuilding())
				{
				case 0:
					batch.draw(nobuilding, cX+j*110, cY);
					break;
				case 1:
					batch.draw(village, cX+j*110, cY);
					break;
				case 2:
					batch.draw(city, cX+j*110, cY);
					break;
				}
				buildingsXY[counter].set(cX+j*110, cY);
				counter=counter+1;
			}	
		}
	}
	
	
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
				
				
				if ((x ==myX) || (y>myY))
				{
					batch.draw(roadSN, myX+10, myY);
				}
				if ((x ==myX) || (y<myY))
				{
					batch.draw(roadSN, myX+10, y);
				}
				if ((x >myX) || (y<myY))
				{
					batch.draw(roadNWSE, myX+10, y);					
				}
				if ((x <myX) || (y>myY))
				{
					batch.draw(roadNWSE, x+10, myY);					
				}
				if ((x <myX) || (y<myY))
				{
					batch.draw(roadSWNE, x+10, y);					
				}
				if ((x >myX) || (y>myY))
				{
					batch.draw(roadSWNE, myX+10, myY);					
				}
			}
			
			if ((i ==6) || (i==43))
			{
				i=i+5;
			}
			if ((i ==15) || (i==37))
			{
				i=i+6;
			}
			if (i ==26)
			{
				i=i+7;
			}
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
	}
	
	
}
