package cluedo.assets.tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import cluedo.assets.Player;
/**
 * Determines if player wants to move into a StairsTile.
 * @author Casey
 *
 */
public class StairsTile extends Tile{
	public StairsTile(int x, int y){
		super(x, y);
	}
	
	/**
	 * Sets the player in this Tile.
	 */
	public void setPlayer(Player p){
		super.setPlayer(p);
	}
	
	/**
	 * Draws the Tile.
	 */
	public void draw(Graphics g){
		if(this.player != null){
			this.player.draw(g, xLoc, yLoc);
		}
	}
}
