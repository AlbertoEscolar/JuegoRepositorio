package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import handlers.B2DVars;
import main.Juego;

public class HUD {

	private Jugador jugador;
	
	private TextureRegion container;
	private TextureRegion[] blocks;
	private TextureRegion moneda;
	private TextureRegion[] fuente;
	
	public HUD(Jugador jugador) {
		this.jugador = jugador;

		
		Texture texMarco = Juego.res.getTexture("marco");
		Texture texMoneda = Juego.res.getTexture("monedas");
		Texture tex = Juego.res.getTexture("hud");
		Texture texNumeros = Juego.res.getTexture("numeros");
		
		container = new TextureRegion(texMarco, 0, 0, 24, 24);
		
		blocks = new TextureRegion[3];
		
			for ( int i = 0; i < blocks.length; i++) {
				blocks[i] = new TextureRegion(tex, 0 + i * 19, 0, 19, 19);
			}	
			moneda = new TextureRegion(texMoneda, 19, 0, 18, 18);
			
			fuente = new TextureRegion[11];
			for(int i = 0; i < 6; i++) {
				fuente[i] = new TextureRegion(texNumeros, 1 + i * 9, 1, 9, 9);
			}
			for(int i = 0; i < 5; i++) {
				fuente[i+ 6] = new TextureRegion(texNumeros, 1 + i * 9, 10, 9, 9);
			}	
	}
	
	public void render(SpriteBatch sb) {
		
		short bits = jugador.getBody().getFixtureList().first().getFilterData().maskBits;
		
		sb.begin();
		
		// dibujar Marco
		sb.draw(container, 38, 197);
		
		
		if ((bits & B2DVars.BIT_BLACK) != 0) {
			sb.draw(blocks[0], 40, 200);
		}
		if ((bits & B2DVars.BIT_GREEN) != 0) {
			sb.draw(blocks[1], 40, 200);
		}
		if ((bits & B2DVars.BIT_ORANGE) != 0) {
			sb.draw(blocks[2], 40, 200);
		}
		
		// Dibujar Moneda HUD
		sb.draw(moneda, 100, 208);
				
		// Dibujar la cuenta de monedas
		dibujarString(sb, jugador.getNumMonedas() +"", 142, 211);
		
		sb.end();	
	}
	
	private void dibujarString(SpriteBatch sb, String s, float x, float y) {
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == '/') c = 10;
			else if(c >= '0' && c <= '9') c -= '0';
			else continue;
			sb.draw(fuente[c], x + i * 9, y);
		}
	}
	
	
}
