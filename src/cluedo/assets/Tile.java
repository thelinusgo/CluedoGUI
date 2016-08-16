package cluedo.assets;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {
	
	protected int xLoc;
	protected int yLoc;
	
	public static final int TILESIZE = 21;
	
	/**
	 * Construct a new Tile with the given type of tile inside.
	 * @param tile
	 */
	public Tile(int x, int y){
		this.xLoc = 38+x*TILESIZE;
		this.yLoc = 25+y*TILESIZE;
	}
	
	
	
	public void draw(Graphics g){
		g.setColor(new Color(222, 192, 70));
		g.fillRect(xLoc, yLoc, TILESIZE, TILESIZE);
		g.setColor(Color.black);
		g.drawRect(xLoc, yLoc, TILESIZE, TILESIZE);
	}
}
