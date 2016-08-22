package cluedo.assets.tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import cluedo.assets.Player;
import cluedo.assets.Room;
/**
 * Determines if player wants to move into a RoomTile.
 * @author Casey
 *
 */
public class RoomTile extends Tile{
	/**
	 * Stores the wall direction of this tile
	 */
	private String direction = null;
	private String direction2 = null;
	
	/**
	 * Stores the Room object that this RoomTile belongs to.
	 */
	private Room room = null;

	public RoomTile(int x, int y, String dir, String dir2, Room r){
		super(x, y);
		this.direction = dir;
		this.direction2 = dir2;
		this.room = r;
	}

	/**
	 * Sets the player that is in this RoomTile
	 */
	public void setPlayer(Player p){
		super.setPlayer(p);
	}
	
	/**
	 * Returns Room object of this RoomTile.
	 * @return
	 */
	public Room getRoom(){
		return this.room;
	}

	/**
	 * Draws the Tile.
	 */
	public void draw(Graphics g){
		if(this.player != null){
			this.player.draw(g, xLoc, yLoc);
		}
	}
	
	/**
	 * Returns the direction of the wall.
	 * @return
	 */
	public String getDir(){
		return this.direction;
	}
	
	/**
	 * Returns the direction of the wall.
	 * @return
	 */
	public String getDir2(){
		return this.direction2;
	}
}
