package cluedo.assets.tiles;

import java.awt.Color;
import java.awt.Graphics;

public class SolutionTile extends Tile{

	public SolutionTile(int x, int y) {
		super(x, y);
	}

	public void draw(Graphics g){
		g.setColor(new Color(51, 51, 255,0));
		g.fillRect(super.xLoc, super.yLoc, super.TILESIZE, super.TILESIZE);
	}
}
