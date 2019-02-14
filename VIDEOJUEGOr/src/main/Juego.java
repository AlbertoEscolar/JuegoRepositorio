package main;

import javax.swing.text.AbstractDocument.Content;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import handlers.Contenido;
import handlers.EstadoJuegoConfiguracion;
import handlers.MiEntrada;
import handlers.MiEntradaProcesador;

public class Juego implements ApplicationListener{

	public static final String TITULO = "RunnBot";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	
	public static final float STEP = 1 /60f;
	private float accum;
	
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	
	public SpriteBatch getSproteBatch() {
		return sb;
	}
	public OrthographicCamera getCamera() {
		return cam;
	}
	public OrthographicCamera getHUDCamera() {
		return hudCam;
	}
	
	private EstadoJuegoConfiguracion gsm;
	
	public static Contenido res;
	
	
	@Override
	public void create() {
		Texture.setEnforcePotImages(false);
		
		Gdx.input.setInputProcessor(new MiEntradaProcesador());
		
		//Importanto el sprites
		res = new Contenido();
		res.loadTexture("res/images/RobotitoNuevo.png", "robot");
		res.loadTexture("res/images/monedas.png", "monedas");
		res.loadTexture("res/images/bloquesHUD.png", "hud");
		res.loadTexture("res/images/tituloHUD.png", "hudTitulo");
		res.loadTexture("res/images/minas.png", "minas");
		res.loadTexture("res/images/menuFisk.png", "menu");
		res.loadTexture("res/images/BG1.png", "fondoNivel");
		res.loadTexture("res/images/Marco.png", "marco");
		res.loadTexture("res/images/Numeros.png", "numeros");
		res.loadTexture("res/images/BG3.png", "fondoJuegoDentro");
		res.loadTexture("res/images/saltoentero.png", "saltar");
		res.loadTexture("res/images/cangrejosprite.png", "cangrejo");
		res.loadTexture("res/images/enemigo2Entero.png", "pulpo");
		
		res.loadSound("res/sfx/levelselect.wav", "elegirNivel");
		res.loadSound("res/sfx/moneda.wav", "moneda");
		res.loadSound("res/sfx/muere.wav", "muere");
		res.loadSound("res/sfx/salto.wav", "salta");
		res.loadSound("res/music/wii.wav", "wii");
		res.getSound("wii").setLooping(0, true);
		res.getSound("wii").play();
		
		res.loadSound("res/sfx/musicaJugando.wav", "jugandoMusic");
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		sb = new SpriteBatch();
		gsm = new EstadoJuegoConfiguracion(this);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void render() {
		
		Gdx.graphics.setTitle(TITULO + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
		
		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
			MiEntrada.update();
		}
		sb.setProjectionMatrix(hudCam.combined);
		sb.begin();
		//sb.draw(res.getTexture("robot"), 0, 0);
		sb.end();
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	
}
