package catan.game.desktop;

import representation.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class DesktopLauncher {
   public static void main(String[] args) {
      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
      config.title = "Catan";
      config.width = 1366;
      config.height = 768;
      new LwjglApplication(new Graphics(), config);
   }
}
