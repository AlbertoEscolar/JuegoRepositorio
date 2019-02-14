package handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MisContactosListener implements ContactListener{

	private int numeroDePisadas;
	private Array<Body> cuerposBorrar;
	private boolean jugadorMuerto;
	
	 public MisContactosListener() {
		 super();
		cuerposBorrar = new Array<Body>();
	}
	
	//Cuando dos texturas empiezan a colisionar
	@Override
	public void beginContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if (fa.getUserData() != null && fa.getUserData().equals("pies")) {
			numeroDePisadas++;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("pies")) {
			numeroDePisadas++;
		}
		//Borrar Moneda
		if (fa.getUserData() != null && fa.getUserData().equals("moneda")) {
			cuerposBorrar.add(fa.getBody());
		}
		
		if (fb.getUserData() != null && fb.getUserData().equals("moneda")) {
			cuerposBorrar.add(fb.getBody());
		}
		if(fa.getUserData() != null && fa.getUserData().equals("mina")) {
			jugadorMuerto = true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("mina")) {
			jugadorMuerto = true;
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("cangrejo")) {
			jugadorMuerto = true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("cangrejo")) {
			jugadorMuerto = true;
		}
		
		if(fa.getUserData() != null && fa.getUserData().equals("pulpo")) {
			jugadorMuerto = true;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("pulpo")) {
			jugadorMuerto = true;
		}
		//System.out.println(fa.getUserData() + " , " + fb.getUserData());
		
	}

	//Cuando dos texturas ya no colisionan
	@Override
	public void endContact(Contact c) {
		
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		if (fa == null || fb == null) return;
		
		
		if (fa.getUserData() != null && fa.getUserData().equals("pies")) {
			numeroDePisadas--;
		}
		if (fb.getUserData() != null && fb.getUserData().equals("pies")) {
			numeroDePisadas--;
		}
	}
	
	public boolean puedeSaltar() {
		return numeroDePisadas > 0; }

	public Array<Body> getCuerposBorrar() {
		return cuerposBorrar;
	}

	public void setCuerposBorrar(Array<Body> cuerposBorrar) {
		this.cuerposBorrar = cuerposBorrar;
	}	
	
	public boolean isJugadorMuerto() {
		return jugadorMuerto; 
	}
	
	public void preSolve(Contact c, Manifold m) {}
	public void postSolve(Contact c, ContactImpulse ci) {}
	
}
