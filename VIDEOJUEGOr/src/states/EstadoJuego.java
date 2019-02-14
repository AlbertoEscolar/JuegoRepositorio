package states;

import org.ietf.jgss.GSSManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import handlers.EstadoJuegoConfiguracion;
import main.Juego;

public abstract class EstadoJuego {
	
	protected EstadoJuegoConfiguracion gsm;
	protected Juego juego;
	
	protected SpriteBatch sb;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudCam;

	protected EstadoJuego(EstadoJuegoConfiguracion gsm) {
		this.gsm = gsm;
		juego = gsm.juego();
		sb = juego.getSproteBatch();
		cam = juego.getCamera();
		hudCam = juego.getHUDCamera();
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
	
	

}
