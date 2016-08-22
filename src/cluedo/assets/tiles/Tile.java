package cluedo.assets.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cluedo.assets.Player;

public class Tile{
	
	protected int xLoc;
	protected int yLoc;
	public int x;
	public int y;
	protected Player player;
	private Color color;
	
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
		this.color = new Color(222, 192, 70);
	}
	
	public void setColor(Color c){
		this.color = c;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(xLoc, yLoc, TILESIZE, TILESIZE);
		g.setColor(Color.black);
		g.drawRect(xLoc, yLoc, TILESIZE, TILESIZE);
		
		if(this.player != null){
			this.player.draw(g, xLoc, yLoc);
		}
	}

	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public boolean contains(Point p){
		if(this.xLoc <= p.x && this.xLoc + TILESIZE > p.x && this.yLoc <= p.y && this.yLoc + TILESIZE > p.y){
			return true;
		}
		return false;
	}

	public String player() {
		return this.player.getName();
	}



}
