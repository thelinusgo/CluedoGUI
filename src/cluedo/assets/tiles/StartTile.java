package cluedo.assets.tiles;

import java.awt.Color;
import java.awt.Graphics;

import cluedo.assets.Character;
import cluedo.assets.Player;

public class StartTile extends Tile{

	private Character c;
	
	public StartTile(int x, int y, Character c) {
		super(x, y);
		this.c = c;
	}
	
	public void setPlayer(Player p){
		super.setPlayer(p);
	}

	public void draw(Graphics g){
		g.setColor(c.getColor());
		g.fillRect(xLoc, yLoc, TILESIZE, TILESIZE);
		g.setColor(Color.black);
		g.drawRect(xLoc, yLoc, TILESIZE, TILESIZE);
		if(super.player != null){
			super.player.draw(g, xLoc, yLoc);
		}
	}
}
