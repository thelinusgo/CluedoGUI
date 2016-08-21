package cluedo.assets.tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import cluedo.assets.Player;
import cluedo.assets.Room;

public class RoomTile extends Tile{
	private String direction = null;
	private String direction2 = null;
	private Room room = null;

	public RoomTile(int x, int y, String dir, String dir2, Room r){
		super(x, y);
		this.direction = dir;
		this.direction2 = dir2;
		this.room = r;
	}

	public void setPlayer(Player p){
		super.setPlayer(p);
	}
	
	public Room getRoom(){
		return this.room;
	}

	public void draw(Graphics g){
		if(this.player != null){
			this.player.draw(g, xLoc, yLoc);
		}
	}
	
	public String getDir(){
		return this.direction;
	}
	
	public String getDir2(){
		return this.direction2;
	}
}
