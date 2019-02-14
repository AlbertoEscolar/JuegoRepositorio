package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import main.Juego;

public class Cangrejo extends B2DSprite {

	public Cangrejo(Body body) {
		super(body);
		
		Texture tex = Juego.res.getTexture("cangrejo");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 14)[0];
		animation.setFrames(sprites, 1 / 6f);
		
		width = sprites[0].getRegionWidth();
		height = sprites[0].getRegionHeight();
	}	

}
