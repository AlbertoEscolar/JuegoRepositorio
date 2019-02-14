package states;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import handlers.BotonJuego;
import handlers.EstadoJuegoConfiguracion;
import main.Juego;

public class ElegirNivel extends EstadoJuego {
	
	private TextureRegion reg;
	
	private BotonJuego[][] buttons;
	
	public ElegirNivel(EstadoJuegoConfiguracion gsm) {	
		super(gsm);
		
		//Paro Musica
		Juego.res.getSound("wii").pause();
		
		reg = new TextureRegion(Juego.res.getTexture("fondoNivel"), 0, 0, 320, 240);
		
		TextureRegion buttonReg = new TextureRegion(Juego.res.getTexture("marco"), 0, 0, 24, 24);
		buttons = new BotonJuego[2][2];
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col] = new BotonJuego(buttonReg, 80 + col * 100, 200 - row * 80, cam);
				buttons[row][col].setText(row * buttons[0].length + col + 1 + "");
			}
		}
		cam.setToOrtho(false, Juego.V_WIDTH, Juego.V_HEIGHT);
	}
	
	public void handleInput() {
	}
	
	public void update(float dt) {
		handleInput();
		
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].update(dt);
				if(buttons[row][col].isClicked()) {
					Jugar.level = row * buttons[0].length + col + 1;
					Juego.res.getSound("jugandoMusic").play();
					gsm.setEstado(EstadoJuegoConfiguracion.JUGAR);
				}
			}
		}
		
	}
	
	public void render() {
		sb.setProjectionMatrix(cam.combined);
		
		sb.begin();
		sb.draw(reg, 0, 0);
		sb.end();
		
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].render(sb);
			}
		}
		
	}
	
	public void dispose() {
		// everything is in the resource manager com.neet.blockbunny.handlers.Content
	}
	
}
