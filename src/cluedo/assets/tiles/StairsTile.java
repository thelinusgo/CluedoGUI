package cluedo.assets.tiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import cluedo.assets.Player;

public class StairsTile extends Tile{
	private String direction = null;
	private String direction2 = null;
	
	public StairsTile(int x, int y, String dir, String dir2){
		super(x, y);
		this.direction = dir;
		this.direction2 = dir2;
	}
	
	public void setPlayer(Player p){
		super.setPlayer(p);
	}
	
	public void draw(Graphics g){
		if(this.player != null){
			this.player.draw(g, xLoc, yLoc);
		}
	}
}
