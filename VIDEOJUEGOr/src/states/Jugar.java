package states;

import static handlers.B2DVars.PPM;
import javax.swing.Box;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import handlers.B2DVars;
import handlers.EstadoJuegoConfiguracion;
import handlers.Fondo;
import handlers.MiEntrada;
import handlers.MisContactosListener;
import main.Juego;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import entidades.B2DSprite;
import entidades.Cangrejo;
import entidades.HUD;
import entidades.Jugador;
import entidades.JugadorSalta;
import entidades.Mina;
import entidades.Moneda;
import entidades.Pulpo;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Jugar extends EstadoJuego{
	
		private boolean debug = false;
	
		private World mundo;
		private Box2DDebugRenderer b2dr;
		private B2DSprite b2dsprite;
		Body body;
		
		private OrthographicCamera b2dCam;
		private MisContactosListener cl;
		private TiledMap tileMap;
		private int tileMapWidth;
		private int tileMapHeight;
		private int tileSize;
		private OrthogonalTiledMapRenderer tmRenderer;
		
		private Fondo[] fondos;
		
		private Jugador jugador;
		private JugadorSalta jugadorS;
		private Array<Cangrejo> cangrejos;
		private Array<Pulpo> pulpos;
		private Array<Moneda> monedas;
		private Array<Mina> minas;
		private TextureRegion[] sprites;

		public boolean estaSaltando = false;
	
		private HUD hud;
		
		public static int level;
		
		public Jugar(EstadoJuegoConfiguracion gsm) {
				
		super(gsm);
	
		//Iniciar las cosas de box2d
		mundo = new World(new Vector2(0, -9.81f), true);
		cl = new MisContactosListener();
		mundo.setContactListener(cl);
		b2dr = new Box2DDebugRenderer();
		//Crear Jugador
		crearJugador();
		//Crear Tiles
		crearTiles();
		
		//Crear Monedas
		crearMonedas();
		
		//Crear Minas
		crearMinas();
		
		//Crear Cangrejo
		crearCangrejo();
		
		//Crear Pulpo
		crearPulpo();
		
		// create backgrounds
		Texture bgs = Juego.res.getTexture("fondoJuegoDentro");
		TextureRegion fondo = new TextureRegion(bgs, 0, 0, 320, 240);
		TextureRegion tuberias = new TextureRegion(bgs, 0, 0, 320, 240);
		fondos = new Fondo[2];
		fondos[0] = new Fondo(fondo, cam, 0f);
	    fondos[1] = new Fondo(tuberias, cam, 0.1f);
		
	    //Iniciamos la camara box2d
	    b2dCam = new OrthographicCamera();
	    b2dCam.setToOrtho(false, Juego.V_WIDTH / PPM, Juego.V_HEIGHT / PPM);
		
	    //Iniciar HUD
	    hud = new HUD(jugador);
	    ///////////////////////////////////////////////////////
	}
	
	@Override
	public void handleInput() {
		//Salto de jugador
		if (MiEntrada.isPressed(MiEntrada.BUTTON1)) {
			jugadorSalta(); 
			estaSaltando = true;
			 
		} else {
			estaSaltando = false;
		}
		//Cambio el color del bloque
		if (MiEntrada.isPressed(MiEntrada.BUTTON2)) {
			cambiarBloques();
		}
		/*
		if (MiEntrada.isPressed(MiEntrada.BUTTON1)) {
			System.out.println("Z presionada");
		}
		if (MiEntrada.isDown(MiEntrada.BUTTON2)) {
			System.out.println("X mantenida");
		}*/
	}

	private void jugadorSalta() {
		if(cl.puedeSaltar()) {
			jugador.getBody().setLinearVelocity(jugador.getBody().getLinearVelocity().x, (float) 0.5);
			
			if (jugador.getNumMonedas() >= 3) {
				jugador.getBody().applyForceToCenter(0, 250, true);
			}else {
				jugador.getBody().applyForceToCenter(0, 200, true);
			}
			Juego.res.getSound("salta").play();	
		}
	}
	
	@Override
	public void update(float dt) {
		//checkea  input
		handleInput();
		//Actualiza box2d
		mundo.step(Juego.STEP, 6, 2);
		//Borrar monedas
		Array<Body> bodies = cl.getCuerposBorrar();
		
		for (int i = 0; i < bodies.size; i++) {
			Body b = bodies.get(i);
			monedas.removeValue((Moneda) b.getUserData(), true);
			mundo.destroyBody(b);
			jugador.collectMoneda();
			Juego.res.getSound("moneda").play();
		}
		bodies.clear();
		
		jugador.update(dt);
		
		//Comprobar que el jugador gana
		if (jugador.getBody().getPosition().x * PPM > tileMapWidth * tileSize) {
			Juego.res.getSound("elegirNivel").play();
			gsm.setEstado(EstadoJuegoConfiguracion.LEVEL_SELECT);
			Juego.res.getSound("jugandoMusic").stop();
		}
		
		//Comprueba que el jugador muere
			if(jugador.getBody().getPosition().y < 0) {
				Juego.res.getSound("muere").play();
				gsm.setEstado(EstadoJuegoConfiguracion.MENU);
				Juego.res.getSound("wii").play();
				Juego.res.getSound("jugandoMusic").stop();
			}
			if(jugador.getBody().getLinearVelocity().x < 0.001f) {
				Juego.res.getSound("muere").play();
				gsm.setEstado(EstadoJuegoConfiguracion.MENU);
				Juego.res.getSound("wii").play();
				Juego.res.getSound("jugandoMusic").stop();
			}
			if(cl.isJugadorMuerto()) {
				Juego.res.getSound("muere").play();
				gsm.setEstado(EstadoJuegoConfiguracion.MENU);
				Juego.res.getSound("wii").play();
				Juego.res.getSound("jugandoMusic").stop();
			}
		
		//Actualizar monedas	
		for (int i =0; i<monedas.size ; i++) {
			monedas.get(i).update(dt);
		}
		
		// Actualizar minas
		for(int i = 0; i < minas.size; i++) {
			minas.get(i).update(dt);
		}
		
		//Actualizar cangrejos
		for (int i = 0; i < cangrejos.size ; i++) {
			cangrejos.get(i).update(dt);
		}
		
		//Actualizar pulpos
		for (int i = 0; i < pulpos.size ; i++) {
			pulpos.get(i).update(dt);
		}
	
	}

	@Override
	public void render() {
		//Pantalla limpia
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//Hacer que la camara siga a el jugador
		cam.position.set(jugador.getPosition().x *PPM+ Juego.V_WIDTH / 4,
				Juego.V_HEIGHT / 2, 0);
		cam.update();
		
		//Dibujar Fondo
		// draw bgs
		sb.setProjectionMatrix(hudCam.combined);
		for(int i = 0; i < fondos.length; i++) {
			fondos[i].render(sb);
		}
		
		//Cargar mapa
		tmRenderer.setView(cam);
		tmRenderer.render();
		
		//Dibujar jugador
		sb.setProjectionMatrix(cam.combined);
		jugador.render(sb);
		
		//Dibujar Monedas
		for (int i =0; i<monedas.size ; i++) {
			monedas.get(i).render(sb);
		}
		//Dibujar Minas
		for(int i = 0; i < minas.size; i++) {
			minas.get(i).render(sb);
		}
		
		//Dibujar Cangrejo
		for(int i = 0; i < cangrejos.size; i++) {
			cangrejos.get(i).render(sb);
		}
		
		//Dibujar Pulpo
		for(int i = 0; i < pulpos.size; i++) {
			pulpos.get(i).render(sb);
		}
		
		//Dibujar HUD
		sb.setProjectionMatrix(hudCam.combined);
		hud.render(sb);
		
		//dibujar box2d
		if (debug) {
		b2dr.render(mundo, b2dCam.combined);
		}
	}

	@Override
	public void dispose() {		
	}
	
	private void crearJugador() {
	    PolygonShape shape = new PolygonShape();
	    //Crear jugador
		BodyDef bdef = new BodyDef();
	    bdef.position.set(32*3 / PPM, 32*3 / PPM);
	    bdef.type = BodyType.DynamicBody;
	    bdef.linearVelocity.set(1, 0);
	    //Crear cuerpo de bodydef
	    Body body = mundo.createBody(bdef);

	    shape.setAsBox(12 / PPM, 16 / PPM);
	    
	    FixtureDef fdef = new FixtureDef();
	    fdef.shape = shape;
	    fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
	    fdef.filter.maskBits = B2DVars.BIT_BLACK | B2DVars.BIT_MONEDA | B2DVars.BIT_MINA 
	    		| B2DVars.BIT_CANGREJO | B2DVars.BIT_PULPO;
	    //rebote
	    //fdef.restitution=1F;
	    body.createFixture(fdef).setUserData("jugador");

	    //Crear sensor de los pies
	    shape.setAsBox(12 / PPM, 2 / PPM, new Vector2(0, -15 / PPM), 0);
	    fdef.shape = shape;
	    fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
	    fdef.filter.maskBits = B2DVars.BIT_BLACK;
	    fdef.isSensor = true;
	    body.createFixture(fdef).setUserData("pies");
	    
	    //Crear Jugador
	    jugador = new Jugador(body, this);
	    
	    body.setUserData(jugador);	 
	}
	
	private void crearCangrejo() {
		cangrejos = new Array<Cangrejo>();
		
		MapLayer ml = tileMap.getLayers().get("cangrejos");
		if(ml == null) return;
		
		for(MapObject mo : ml.getObjects()) {
			BodyDef cdef = new BodyDef();
			cdef.type = BodyType.StaticBody;
			cdef.fixedRotation = true;
			float x = (float) mo.getProperties().get("x") / PPM;
			float y = (float) mo.getProperties().get("y") / PPM;
			cdef.position.set(x, y);
			Body body = mundo.createBody(cdef);
			FixtureDef cfdef = new FixtureDef();
			PolygonShape cshape = new PolygonShape();
			cshape.setAsBox(8 / PPM, 8 / PPM);
			cfdef.shape = cshape;
			cfdef.isSensor = true;
			cfdef.filter.categoryBits = B2DVars.BIT_CANGREJO;
			cfdef.filter.maskBits = B2DVars.BIT_PLAYER;
			body.createFixture(cfdef).setUserData("cangrejo");
			
			//Crea cangrejo
			Cangrejo c = new Cangrejo(body);
			
			body.setUserData(c);
			cangrejos.add(c);
			cshape.dispose();
		}
	}
	
	private void crearPulpo() {
		pulpos = new Array<Pulpo>();
		
		MapLayer ml = tileMap.getLayers().get("pulpos");
		if(ml == null) return;
		
		for(MapObject mo : ml.getObjects()) {
			BodyDef cdef = new BodyDef();
			cdef.type = BodyType.StaticBody;
			float x = (float) mo.getProperties().get("x") / PPM;
			float y = (float) mo.getProperties().get("y") / PPM;
			cdef.position.set(x, y);
			Body body = mundo.createBody(cdef);
			FixtureDef cfdef = new FixtureDef();
			CircleShape cshape = new CircleShape();
			cshape.setRadius(5 / PPM);
			cfdef.shape = cshape;
			cdef.gravityScale=0;
			cfdef.isSensor = true;
			cfdef.filter.categoryBits = B2DVars.BIT_PULPO;
			cfdef.filter.maskBits = B2DVars.BIT_PLAYER;
			body.createFixture(cfdef).setUserData("pulpo");
			//Crea pulpos
			Pulpo c = new Pulpo(body);
			body.setUserData(c);
			pulpos.add(c);
			cshape.dispose();
		}
	}
	
	private void crearTiles() {
		 //cargar el mapa de tiled
		try {
			tileMap = new TmxMapLoader().load("res/maps/level" + level + ".tmx");
		}
		catch(Exception e) {
			System.out.println("Cannot find file: res/maps/level" + level + ".tmx");
			Gdx.app.exit();
		}
	    
	    tmRenderer = new OrthogonalTiledMapRenderer(tileMap);
	    tileMapWidth = (int) tileMap.getProperties().get("width");
		tileMapHeight = (int) tileMap.getProperties().get("height");
		tileSize = (int) tileMap.getProperties().get("tilewidth");
		tmRenderer = new OrthogonalTiledMapRenderer(tileMap);
	    
	    TiledMapTileLayer layer;
	    
	    //Obtiene la capa que quiero de mi mapa
	     layer = (TiledMapTileLayer) tileMap.getLayers().get("negro");
	     crearLayer(layer, B2DVars.BIT_BLACK);
	   
	     layer = (TiledMapTileLayer) tileMap.getLayers().get("verde");
	     crearLayer(layer, B2DVars.BIT_GREEN);
	     
	     layer = (TiledMapTileLayer) tileMap.getLayers().get("naranja");
	     crearLayer(layer, B2DVars.BIT_ORANGE);
	}
	
	/**
	 * Iniciar las minas en el b2dbox
	 */
	private void crearMinas() {
		minas = new Array<Mina>();
		
		MapLayer ml = tileMap.getLayers().get("minas");
		if(ml == null) return;
		
		for(MapObject mo : ml.getObjects()) {
			BodyDef cdef = new BodyDef();
			cdef.type = BodyType.StaticBody;
			float x = (float) mo.getProperties().get("x") / PPM;
			float y = (float) mo.getProperties().get("y") / PPM;
			cdef.position.set(x, y);
			Body body = mundo.createBody(cdef);
			FixtureDef cfdef = new FixtureDef();
			CircleShape cshape = new CircleShape();
			cshape.setRadius(5 / PPM);
			cfdef.shape = cshape;
			cfdef.isSensor = true;
			cfdef.filter.categoryBits = B2DVars.BIT_MINA;
			cfdef.filter.maskBits = B2DVars.BIT_PLAYER;
			body.createFixture(cfdef).setUserData("mina");
			Mina s = new Mina(body);
			body.setUserData(s);
			minas.add(s);
			cshape.dispose();
		}
	}	
	
	private void crearLayer(TiledMapTileLayer layer, short bits) {
		float ts = layer.getTileWidth();
		
		//recorre todas las celdas
		for(int row = 0; row < layer.getHeight(); row++) {
			for(int col = 0; col < layer.getWidth(); col++) {
				
				// obtiene celda
				Cell cell = layer.getCell(col, row);
				
				// ¿hay celda?
				if(cell == null) continue;
				if(cell.getTile() == null) continue;
				
				//crear body de la celda
				BodyDef bdef = new BodyDef();
				bdef.type = BodyType.StaticBody;
				bdef.position.set((col + 0.5f) * ts / PPM, (row + 0.5f) * ts / PPM);
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[3];
				v[0] = new Vector2(-ts / 2 / PPM, -ts / 2 / PPM);
				v[1] = new Vector2(-ts / 2 / PPM, ts / 2 / PPM);
				v[2] = new Vector2(ts / 2 / PPM, ts / 2 / PPM);
				cs.createChain(v);
				FixtureDef fd = new FixtureDef();
				fd.friction = 0;
				fd.shape = cs;
				fd.isSensor = false;
				fd.filter.categoryBits = bits;
				fd.filter.maskBits = B2DVars.BIT_PLAYER;
				mundo.createBody(bdef).createFixture(fd);
				cs.dispose();
	    	}
	    } 
	}
	
	private void crearMonedas() {
		monedas = new Array<Moneda>();
		
		MapLayer layer = tileMap.getLayers().get("monedas");
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
			
		if(layer == null) return;
		
		for (MapObject mo : layer.getObjects()) {
			bdef.type = BodyType.StaticBody;
			
			float x =  mo.getProperties().get("x", Float.class) /PPM;
			float y =  mo.getProperties().get("y", Float.class) / PPM;
			
			bdef.position.set(x, y);
			
			CircleShape cshape = new CircleShape();
			cshape.setRadius(8 / PPM);
			
			fdef.shape = cshape;
			//Obligatorio para cosas que se recojen
			fdef.isSensor = true;
			fdef.filter.categoryBits = B2DVars.BIT_MONEDA;
			fdef.filter.maskBits = B2DVars.BIT_PLAYER;
			
			Body body = mundo.createBody(bdef);
			body.createFixture(fdef).setUserData("moneda");
			
			Moneda m = new Moneda(body);
			monedas.add(m);
			
			body.setUserData(m);
			cshape.dispose();
		}
	}
	
	private void cambiarBloques() {
		Filter filter = jugador.getBody().getFixtureList().first().getFilterData();
		
		short bits = filter.maskBits;
		
		//Cambiar siguiente color
		//Negro -> Verde -> Naranja
		if ((bits & B2DVars.BIT_BLACK) != 0) {
			bits = B2DVars.BIT_GREEN;
		}
		else if ((bits & B2DVars.BIT_GREEN) != 0) {
			bits = B2DVars.BIT_ORANGE;
		}
		else if ((bits & B2DVars.BIT_ORANGE) != 0) {
			bits = B2DVars.BIT_BLACK;
		}
		
		// set player foot mask bits
		filter.maskBits = bits;
		jugador.getBody().getFixtureList().get(1).setFilterData(filter);
		
		// set player mask bits
		bits |= B2DVars.BIT_MONEDA | B2DVars.BIT_MINA | B2DVars.BIT_CANGREJO | B2DVars.BIT_PULPO;
		filter.maskBits = bits;
		jugador.getBody().getFixtureList().get(0).setFilterData(filter);	
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

}
