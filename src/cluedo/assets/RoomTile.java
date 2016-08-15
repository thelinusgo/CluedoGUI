package cluedo.assets;

import java.awt.Color;
import java.awt.Graphics;

public class RoomTile extends Tile{
	public RoomTile(int x, int y){
		super(x, y);
	}

	public void draw(Graphics g){
		g.setColor(new Color(255, 255, 153));
		g.fillRect(super.xLoc, super.yLoc, super.TILESIZE, super.TILESIZE);
		
	}
}
