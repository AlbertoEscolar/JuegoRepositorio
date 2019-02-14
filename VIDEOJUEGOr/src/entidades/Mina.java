package entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import main.Juego;

public class Mina extends B2DSprite {
	
	public Mina(Body body) {
		
		super(body);
		
		Texture tex = Juego.res.getTexture("minas");
		TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		animation.setFrames(sprites, 1 / 12f);
		
		width = sprites[0].getRegionWidth();
		height = sprites[0].getRegionHeight();
	}
	
}
