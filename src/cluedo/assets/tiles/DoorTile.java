package cluedo.assets.tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import cluedo.assets.Door;
import cluedo.assets.Player;

public class DoorTile extends Tile{
	private Door door;
	private String direction;
	
	public DoorTile(int x, int y, Door d, String dir) {
		super(x, y);
		this.door = d;
		this.direction = dir;
	}
	
	public Door getDoor(){
		return this.door;
	}
	
	public void setPlayer(Player p){
		super.setPlayer(p);
	}
	
	public void draw(Graphics g){
		if(this.player  != null){
			this.player.draw(g, xLoc, yLoc);
		}
	}
}
