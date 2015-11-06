package representation;


import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import database.*;

public class Gameplay  {
	SpriteBatch batch;
	
	
	Texture gameplayMenu;
	Texture background;
	Texture playerAvatar;
	Texture cubePlayer;
	Board board;
	//tekstury na wszystkie tile
	ArrayList<Texture> textures = new ArrayList<Texture>();

	public void init()
	{
		batch = new SpriteBatch();
		board = Board.getInstance();
		initTiles();
		background = new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/ocean.png"));
		cubePlayer = new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/actualplayer1.png"));
		gameplayMenu = new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/menualfa.png"));
		playerAvatar = new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/playeravatar.png"));
	}
	
	public void batch()
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//menu i tło w czasie rozgrywki
		batchMenuGameplay();
		//plansza - kafelki
		batchTiles();
		//TODO plansza - miasta
		
		//TODO plansza - drogi
		
		batch.end();
	}	
	

	private void initTiles()
	{
		for (int k =0; k<19; k++)
		{
			Tile t = board.getTile(k);
			
			if ("Hills".equals(t.getType() )) 
			{
			    textures.add(new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/tiles/tileHills.png")));
			}
			else if ("Forest".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/tiles/tileForest.png")));
			}
			else if ("Mountains".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/tiles/tileMountains.png")));
			}
			else if ("Fields".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/tiles/tileFields.png")));
			}
			else if ("Pasture".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/tiles/tilePasture.png")));
			}
			else if ("Desert".equals(t.getType() )) 
			{
				 textures.add(new Texture(Gdx.files.absolute("/Zainstalowane/Eclipse/Catan-core/assets/tiles/tileDesert.png")));
			}
		}
	}
	
	private void batchTiles()
	{
		int cX = 628; // x position of upper center tile
		int cY = 560; // y position of upper center tile
		int tileX=110; 
		int tileY=127;

		batch.draw(textures.get(0), cX, cY);
		batch.draw(textures.get(1), cX-tileX+1, cY);
		batch.draw(textures.get(2), cX+tileX-1, cY);
		//2rząd
		batch.draw(textures.get(3), cX-tileX/2+1, cY-tileY+tileY/4+2);
		batch.draw(textures.get(4), cX+tileX/2-1, cY-tileY+tileY/4+2);
		batch.draw(textures.get(5), cX-tileX/2+2-tileX, cY-tileY+tileY/4+2);
		batch.draw(textures.get(6), cX+tileX/2-2+tileX, cY-tileY+tileY/4+2);
		//3rząd
		batch.draw(textures.get(7), cX, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(8), cX-tileX+1, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(9), cX+tileX-1, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(10), cX-2*tileX+2, cY-2*tileY+2*tileY/4+2);
		batch.draw(textures.get(11), cX+2*tileX-2, cY-2*tileY+2*tileY/4+2);
		//4rząd 
		batch.draw(textures.get(12), cX-tileX/2+1, cY-3*tileY+3*tileY/4+3);
		batch.draw(textures.get(13), cX+tileX/2-1, cY-3*tileY+3*tileY/4+3);
		batch.draw(textures.get(14), cX-tileX/2+2-tileX, cY-3*tileY+3*tileY/4+3);
		batch.draw(textures.get(15), cX+tileX/2-2+tileX, cY-3*tileY+3*tileY/4+3);
		//5rząd
		batch.draw(textures.get(16), cX, cY-4*tileY+4*tileY/4+4);
		batch.draw(textures.get(17), cX-tileX+1, cY-4*tileY+4*tileY/4+4);
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
