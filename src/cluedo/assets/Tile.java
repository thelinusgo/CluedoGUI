package cluedo.assets;

public class Tile {
	
	protected int xLoc;
	protected int yLoc;
	
	protected final int size = 5;
	
	/**
	 * Construct a new Tile with the given type of tile inside.
	 * @param tile
	 */
	public Tile(int x, int y){
		this.xLoc = x;
		this.yLoc = y;
	}
}
