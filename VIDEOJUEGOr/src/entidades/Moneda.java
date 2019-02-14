package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import main.Juego;

public class Moneda extends B2DSprite{
	
	public Moneda(Body body) {
		
		super(body);
		
		Texture tex = Juego.res.getTexture("monedas");
		
		TextureRegion[] sprites = TextureRegion.split(tex, 18, 17)[0];
		
		setAnimation(sprites, 1 / 6f);
	}

}
