package cluedo.assets;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {
	
	protected int xLoc;
	protected int yLoc;
	
	protected static final int TILESIZE = 20;
	
	/**
	 * Construct a new Tile with the given type of tile inside.
	 * @param tile
	 */
	public Tile(int x, int y){
		this.xLoc = x*TILESIZE;
		this.yLoc = y*TILESIZE;
	}
	
	public void draw(Graphics g){
		g.setColor(new Color(204, 153, 255));
		g.fillRect(xLoc, yLoc, TILESIZE, TILESIZE);
	}
}
