package handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class MiEntradaProcesador extends InputAdapter {

	public boolean mouseMoved(int x, int y) {
		MiEntrada.x = x;
		MiEntrada.y = y;
		return true;
	}
	
	public boolean touchDragged(int x, int y, int pointer) {
		MiEntrada.x = x;
		MiEntrada.y = y;
		MiEntrada.down = true;
		return true;
	}
	
	public boolean touchDown(int x, int y, int pointer, int button) {
		MiEntrada.x = x;
		MiEntrada.y = y;
		MiEntrada.down = true;
		return true;
	}
	
	public boolean touchUp(int x, int y, int pointer, int button) {
		MiEntrada.x = x;
		MiEntrada.y = y;
		MiEntrada.down = false;
		return true;
	}
	
	public boolean keyDown(int k) {
		if(k == Keys.Z) MiEntrada.setKey(MiEntrada.BUTTON1, true);
		if(k == Keys.X) MiEntrada.setKey(MiEntrada.BUTTON2, true); 
		return true;
	}
	
	public boolean keyUp(int k) {
		if(k == Keys.Z) MiEntrada.setKey(MiEntrada.BUTTON1, false);
		if(k == Keys.X) MiEntrada.setKey(MiEntrada.BUTTON2, false); 
		return true;
	}
	
	
}
