package cluedo.assets.tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import cluedo.assets.Door;
import cluedo.assets.Player;

/**
 * Determines if player wants to move into a DoorTile.
 * @author Casey
 *
 */
public class DoorTile extends Tile{
	/**
	 * The Door object this DoorTile belongs to.
	 */
	private Door door;
	
	public DoorTile(int x, int y, Door d) {
		super(x, y);
		this.door = d;
	}
	
	/**
	 * Returns this DoorTile's door object.
	 * @return
	 */
	public Door getDoor(){
		return this.door;
	}
	
	/**
	 * Sets the player that is in this DoorTile object.
	 */
	public void setPlayer(Player p){
		super.setPlayer(p);
	}
	
	/**
	 * Draws the DoorTile.
	 */
	public void draw(Graphics g){
		if(this.player  != null){
			this.player.draw(g, xLoc, yLoc);
		}
	}
}
