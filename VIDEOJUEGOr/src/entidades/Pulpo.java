package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import main.Juego;

public class Pulpo extends B2DSprite {

	public Pulpo(Body body) {
		super(body);
		
		Texture tex = Juego.res.getTexture("pulpo");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		animation.setFrames(sprites, 1 / 6f);
		
		width = sprites[0].getRegionWidth();
		height = sprites[0].getRegionHeight();
	}	

}
