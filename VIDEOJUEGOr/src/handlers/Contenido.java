package handlers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.openal.Wav.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Contenido {
	
	private HashMap<String, Texture> textures;
	private HashMap<String, Sound> sounds;
	
	
	public Contenido() {
		textures = new HashMap<String, Texture>();
		sounds = new HashMap<String, Sound>();
	}
	
	
	public void loadTexture(String path, String key) {
		Texture tex = new Texture(Gdx.files.internal(path));
		textures.put(key,  tex);
	}
	
	public Texture getTexture(String key) {
		return textures.get(key);
	}
	
	public void disposeTexture(String key) {
		Texture tex = textures.get(key);
		if (tex !=null) {
			tex.dispose();
		}	
	}
	
	public void loadSound(String path) {
		int slashIndex = path.lastIndexOf('/');
		String key;
		if(slashIndex == -1) {
			key = path.substring(0, path.lastIndexOf('.'));
		}
		else {
			key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
		}
		loadSound(path, key);
	}
	
	public void loadSound(String path, String key) {
		Sound sound = (Sound) Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(key, sound);
	}
	
	public Sound getSound(String key) {
		return sounds.get(key);
	}
	
	public void removeSound(String key) {
		Sound sound = sounds.get(key);
		if(sound != null) {
			sounds.remove(key);
			sound.dispose();
		}
	}

}
