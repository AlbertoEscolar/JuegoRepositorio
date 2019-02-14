package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import main.Juego;
import states.Jugar;

public class JugadorSalta extends B2DSprite{

	private int numMonedas;
	private int totalMonedas;
	
	public JugadorSalta(Body body, Jugar jugar) {
		super(body);
		
		Texture tex = Juego.res.getTexture("saltar");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
	
		setAnimation(sprites, 1 / 4f);
		
		width = sprites[0].getRegionWidth();
		height = sprites[0].getRegionHeight();
	}
	
	public void collectMoneda() {
		numMonedas++;
	}
	
	public int getNumMonedas() {
		return numMonedas;
	}
	
	public void setTotalMonedas(int i) {
		totalMonedas = i;
	}
	
	public int getTotalMonedas() {
		return totalMonedas;
	}
	
}
