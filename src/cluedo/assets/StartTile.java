package cluedo.assets;

import java.awt.Color;
import java.awt.Graphics;

public class StartTile extends Tile{

	private Character c;
	private Player player = null;
	
	public StartTile(int x, int y, Character c) {
		super(x, y);
		this.c = c;
	}
	
	public void setPlayer(Player p){
		this.player = p;
	}

	public void draw(Graphics g){
		g.setColor(c.getColor());
		g.fillRect(xLoc, yLoc, TILESIZE, TILESIZE);
		g.setColor(Color.black);
		g.drawRect(xLoc, yLoc, TILESIZE, TILESIZE);
		
		if(this.player != null){
			g.setColor(this.player .getCharacter().getColor());
			g.drawOval(super.xLoc, super.yLoc, super.TILESIZE, super.TILESIZE);
		}
	}
}
