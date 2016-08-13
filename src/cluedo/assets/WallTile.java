package cluedo.assets;

import java.awt.Color;
import java.awt.Graphics;

public class WallTile extends Tile{
	public WallTile(int x, int y) {
		super(x, y);
	}

	public void draw(Graphics g){
		g.setColor(new Color(255, 92, 51));
		g.fillRect(super.xLoc, super.yLoc, super.size, super.size);
	}
}
