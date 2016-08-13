package cluedo.assets;

import java.awt.Color;
import java.awt.Graphics;

public class DoorTile extends Tile{
	public DoorTile(int x, int y) {
		super(x, y);
	}
	
	public void draw(Graphics g){
		g.setColor(new Color(0, 179, 89));
		g.fillRect(super.xLoc, super.yLoc, super.size, super.size);
	}
}
