package main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Escritorio {

	public static void main(String[] args) {
		
		LwjglApplicationConfiguration cfg = 
		new LwjglApplicationConfiguration();
		
		cfg.title = Juego.TITULO;
		cfg.width = Juego.V_WIDTH * Juego.SCALE;
		cfg.height = Juego.V_HEIGHT * Juego.SCALE;
		
		new LwjglApplication(new Juego(), cfg);	
	}
}
