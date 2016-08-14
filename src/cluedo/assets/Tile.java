package cluedo.assets;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {
	
	protected int xLoc;
	protected int yLoc;
	
	protected static final int TILESIZE = 2;
	
	/**
	 * Construct a new Tile with the given type of tile inside.
	 * @param tile
	 */
	public Tile(int x, int y){
		this.xLoc = x;
		this.yLoc = y;
	}
	
	public void draw(Graphics g){
		g.setColor(new Color(153, 102, 255));
		g.fillRect(xLoc, yLoc, TILESIZE, TILESIZE);
	}
}
