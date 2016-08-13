package cluedo.assets;

import java.awt.Color;
import java.awt.Graphics;

public class RoomWallTile extends Tile{
	public RoomWallTile(int x, int y) {
		super(x, y);
	}

	public void draw(Graphics g){
		g.setColor(Color.black);
		g.fillRect(super.xLoc, super.yLoc, super.size, 1);
	}
}
