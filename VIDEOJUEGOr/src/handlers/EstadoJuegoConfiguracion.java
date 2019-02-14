package handlers;

import java.io.PushbackInputStream;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import main.Juego;
import states.ElegirNivel;
import states.EstadoJuego;
import states.Jugar;

public class EstadoJuegoConfiguracion {

	private Juego juego;
	
	private java.util.Stack<EstadoJuego> estadosJuego;
	
	public static final int JUGAR = 388031654;
	public static final int MENU = 83774392;
	public static final int LEVEL_SELECT = -9238732;
	
	public EstadoJuegoConfiguracion(Juego juego) {
		this.juego = juego;
		estadosJuego = new java.util.Stack<EstadoJuego>();
		pushState(MENU);
	}
	
	public Juego juego() {
		return juego;
	}
	
	public void update(float dt) {
		estadosJuego.peek().update(dt);
	}
	
	public void render() {
		estadosJuego.peek().render();
	}
	
	private EstadoJuego getEstado(int estado) {
		if(estado == MENU) return new Menu(this);
		if (estado == JUGAR) return new Jugar(this);
		if(estado == LEVEL_SELECT) return new ElegirNivel(this);
		return null;
	}
	
	public void setEstado(int estado) {
		popEstado();
		pushState(estado);
	}

	public void pushState(int estado) {
		estadosJuego.push(getEstado(estado));
	}
	
	public void popEstado() {
		EstadoJuego g = estadosJuego.pop();
		g.dispose();
	}
	
}
