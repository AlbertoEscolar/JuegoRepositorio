package handlers;

import static handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import entidades.B2DSprite;
import handlers.Animaciones;
import handlers.B2DVars;
//import handlers.Background;
//import handlers.GameButton;
//import handlers.GameStateManager;
import main.Juego;
import states.EstadoJuego;

public class Menu extends EstadoJuego{ 
	
	private boolean debug = false;
	private BotonJuego botonJugar;
	private Fondo bg;
	private Animaciones animacion;
	//private GameButton playButton;
	
	private World mundo;
	private Box2DDebugRenderer b2dRenderer;
	
	private Array<B2DSprite> blocks;
	
	public Menu(EstadoJuegoConfiguracion gsm) {
		 super(gsm);
		
		Texture tex = Juego.res.getTexture("menu");
		bg = new Fondo(new TextureRegion(tex), cam, 1f);
		bg.setVector(-20, 0);
		
		tex = Juego.res.getTexture("robot");
		TextureRegion[] reg = new TextureRegion[4];
		for(int i = 0; i < reg.length; i++) {
			reg[i] = new TextureRegion(tex, i * 32, 0, 32, 32);
		}
		animacion = new Animaciones(reg, 1 / 12f);
		
		tex = Juego.res.getTexture("hudTitulo");
		botonJugar = new BotonJuego(new TextureRegion(tex, 0, 0, 115, 49), 170, 100, cam);
		
		cam.setToOrtho(false, Juego.V_WIDTH, Juego.V_HEIGHT);
		
		mundo = new World(new Vector2(0, -9.8f * 5), true);
		/*world = new World(new Vector2(0, 0), true);*/
		b2dRenderer = new Box2DDebugRenderer();
		
		createTitleBodies();
	}
	
	private void createTitleBodies() {
		// top platform
		BodyDef tpbdef = new BodyDef();
		tpbdef.type = BodyType.StaticBody;
		tpbdef.position.set(160 / PPM, 180 / PPM);
		Body tpbody = mundo.createBody(tpbdef);
		PolygonShape tpshape = new PolygonShape();
		tpshape.setAsBox(120 / PPM, 1 / PPM);
		FixtureDef tpfdef = new FixtureDef();
		tpfdef.shape = tpshape;
		tpfdef.filter.categoryBits = B2DVars.BIT_TOP_PLATFORM;
		tpfdef.filter.maskBits = B2DVars.BIT_TOP_BLOCK;
		tpbody.createFixture(tpfdef);
		tpshape.dispose();
		
		// bottom platform
		BodyDef bpbdef = new BodyDef();
		bpbdef.type = BodyType.StaticBody;
		bpbdef.position.set(160 / PPM, 130 / PPM);
		Body bpbody = mundo.createBody(bpbdef);
		PolygonShape bpshape = new PolygonShape();
		bpshape.setAsBox(120 / PPM, 1 / PPM);
		FixtureDef bpfdef = new FixtureDef();
		bpfdef.shape = bpshape;
		bpfdef.filter.categoryBits = B2DVars.BIT_BOTTOM_PLATFORM;
		bpfdef.filter.maskBits = B2DVars.BIT_BOTTOM_BLOCK;
		bpbody.createFixture(bpfdef);
		bpshape.dispose();
		
		Texture tex = Juego.res.getTexture("hud");
		TextureRegion[] blockSprites = new TextureRegion[3];
		for(int i = 0; i < blockSprites.length; i++) {
			blockSprites[i] = new TextureRegion(tex, 40 + i * 5, 0, 5, 5);
		}
	
		blocks = new Array<B2DSprite>();
		
		int[][] spellRun = {
			{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}
		};
		
		int[][] spellBot = {
			{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
		};
		
		// top blocks
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 29; col++) {
				BodyDef tbbdef = new BodyDef();
				tbbdef.type = BodyType.DynamicBody;
				tbbdef.fixedRotation = true;
				tbbdef.position.set((62 + col * 6 + col) / PPM, (270 - row * 6  + row) / PPM);
				Body tbbody = mundo.createBody(tbbdef);
				PolygonShape tbshape = new PolygonShape();
				tbshape.setAsBox(2f / PPM, 2f / PPM);
				FixtureDef tbfdef = new FixtureDef();
				tbfdef.shape = tbshape;
				tbfdef.filter.categoryBits = B2DVars.BIT_TOP_BLOCK;
				tbfdef.filter.maskBits = B2DVars.BIT_TOP_PLATFORM | B2DVars.BIT_TOP_BLOCK;
				tbbody.createFixture(tbfdef);
				tbshape.dispose();
	
				
				if(spellRun[row][col] == 1) {
					B2DSprite sprite = new B2DSprite(tbbody);
					sprite.setAnimation(blockSprites[MathUtils.random(2)], 0);
					blocks.add(sprite);
				}
			}
		}
		
		// bottom blocks
		for(int row = 0; row < 5; row++) {
			for(int col = 0; col < 29; col++) {
				BodyDef bbbdef = new BodyDef();
				bbbdef.type = BodyType.DynamicBody;
				bbbdef.fixedRotation = true;
				bbbdef.position.set((62 + col * 6 + col) / PPM, (270 - row * 6 + row) / PPM);
				Body bbbody = mundo.createBody(bbbdef);
				PolygonShape bbshape = new PolygonShape();
				bbshape.setAsBox(2f / PPM, 2f / PPM);
				FixtureDef bbfdef = new FixtureDef();
				bbfdef.shape = bbshape;
				bbfdef.filter.categoryBits = B2DVars.BIT_BOTTOM_BLOCK;
				bbfdef.filter.maskBits = B2DVars.BIT_BOTTOM_PLATFORM | B2DVars.BIT_BOTTOM_BLOCK;
				bbbody.createFixture(bbfdef);
				bbshape.dispose();
				if(spellBot[row][col] == 1) {
					B2DSprite sprite = new B2DSprite(bbbody);
					sprite.setAnimation(blockSprites[MathUtils.random(2)], 0);
					blocks.add(sprite);
				}
			}
		}
		
	}
	
	public void handleInput() {
		// mouse/touch input
		if(botonJugar.isClicked()) {
			//Juego.res.getSound("moneda").play();
			gsm.setEstado(EstadoJuegoConfiguracion.LEVEL_SELECT);
		}
	}
	
	public void update(float dt) {
		handleInput();
		
		mundo.step(dt / 5, 8, 3);
		
		bg.update(dt);
		animacion.update(dt);
		
		botonJugar.update(dt);
		
	}
	
	public void render() {
		sb.setProjectionMatrix(cam.combined);
		
		// draw background
		bg.render(sb);
		
		// draw button
		botonJugar.render(sb);
		
		// draw bunny
		sb.begin();
		sb.draw(animacion.getFrame(), 146, 31);
		sb.end();
		
		// debug draw box2d
		if(debug) {
			cam.setToOrtho(false, Juego.V_WIDTH / PPM, Juego.V_HEIGHT / PPM);
			b2dRenderer.render(mundo, cam.combined);
			cam.setToOrtho(false, Juego.V_WIDTH, Juego.V_HEIGHT);
		}
		
		// draw title
		for(int i = 0; i < blocks.size; i++) {
			blocks.get(i).render(sb);
		}
		
	}
	
	public void dispose() {
		// everything is in the resource manager com.neet.blockbunny.handlers.Content
	}
	
}
