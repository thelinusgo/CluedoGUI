package cluedo.assets.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cluedo.assets.Player;
/**
 * Determines if player wants to move into a Tile.
 * @author Casey
 *
 */
public class Tile{
	
	/**
	 * x and y location of the Tile on the canvas
	 */
	protected int xLoc;
	protected int yLoc;
	
	/**
	 * x and y location of the Tile in the 2D array of Tiles
	 */
	public int x;
	public int y;
	
	private Color color;
	
	/**
	 * Stores the player in this Tile.
	 */
	protected Player player;
	
	public static final int TILESIZE = 21;
	
	/**
	 * Construct a new Tile with the given type of tile inside.
	 * @param tile
	 */
	public Tile(int x, int y){
		this.xLoc = 38+x*TILESIZE;
		this.yLoc = 25+y*TILESIZE;
		this.x = x;
		this.y = y;
		color = new Color(222, 192, 70);
	}
	
	/**
	 * Draws the Tile.
	 * @param g
	 */
	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(xLoc, yLoc, TILESIZE, TILESIZE);
		g.setColor(Color.black);
		g.drawRect(xLoc, yLoc, TILESIZE, TILESIZE);
		
		if(this.player != null){
			this.player.draw(g, xLoc, yLoc);
		}
	}
	
	public void setColor(Color c){
		this.color = c;
	}

	/**
	 * Sets the player.
	 * @param p
	 */
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	/**
	 * Checks if the point is within this Tile.
	 * @param p
	 * @return
	 */
	public boolean contains(Point p){
		if(this.xLoc <= p.x && this.xLoc + TILESIZE > p.x && this.yLoc <= p.y && this.yLoc + TILESIZE > p.y){
			return true;
		}
		return false;
	}

	/**
	 * Returns the player's name.
	 * @return
	 */
	public String player() {
		return this.player.getName();
	}



}
