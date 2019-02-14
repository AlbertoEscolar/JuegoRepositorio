package handlers;

public class B2DVars {

	//Pixeles por metro medida que usa el gdx
	public static final float PPM = 100;
	
	
	//Categorias de los elementos en bits
	public static final short BIT_PLAYER = 2;
	public static final short BIT_BLACK = 4;
	public static final short BIT_GREEN = 8;
	public static final short BIT_ORANGE = 16;
	public static final short BIT_MONEDA = 32;
	public static final short BIT_MINA = 64;
	public static final short BIT_CANGREJO = 128;
	public static final short BIT_PULPO = 256;
	
	public static final short BIT_TOP_BLOCK = 2;
	public static final short BIT_BOTTOM_BLOCK = 4;
	public static final short BIT_TOP_PLATFORM = 8;
	public static final short BIT_BOTTOM_PLATFORM = 16;
	
	/**
	 * Como serian las categorias segun sus bits asignados
	 * 
	 * Para cualquier elemento por defecto
	 * 0000 0000 0000 0001
	 * El suelo
	 * 0000 0000 0000 0010
	 * La caja
	 * 0000 0000 0000 0100
	 */
}
