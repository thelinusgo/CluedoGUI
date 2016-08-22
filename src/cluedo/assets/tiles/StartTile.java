package cluedo.assets.tiles;

import java.awt.Color;
import java.awt.Graphics;

import cluedo.assets.Character;
import cluedo.assets.Player;
/**
 * Determines the player's start tile.
 * @author Casey
 *
 */
public class StartTile extends Tile{
	/**
	 * Stores the character that this start tile belongs to.
	 */
	private Character c;
	
	public StartTile(int x, int y, Character c) {
		super(x, y);
		this.c = c;
	}
	
	/**
	 * Sets the player of this Tile.
	 */
	public void setPlayer(Player p){
		super.setPlayer(p);
	}

	/**
	 * Draws the Tile.
	 */
	public void draw(Graphics g){
		g.setColor(c.getColor());
		g.fillRect(xLoc, yLoc, TILESIZE, TILESIZE);
		g.setColor(Color.black);
		g.drawRect(xLoc, yLoc, TILESIZE, TILESIZE);
		if(super.player != null){
			super.player.draw(g, xLoc, yLoc);
		}
	}
	
	/**
	 * Returns the colour of this Tile.
	 */
	public Color getColor(){
		return c.getColor();
	}
}
