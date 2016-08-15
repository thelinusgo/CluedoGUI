package cluedo.assets;

import java.awt.Color;
import java.awt.Graphics;

public class StairsTile extends Tile{
	public StairsTile(int x, int y){
		super(x, y);
	}

	public void draw(Graphics g){
		g.setColor(new Color(255, 153, 0));
		g.fillRect(super.xLoc, super.yLoc, super.TILESIZE, super.TILESIZE);
		g.setColor(Color.black);
		g.drawRect(xLoc, yLoc, TILESIZE, TILESIZE);
	}
}
