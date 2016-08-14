package cluedo.assets;

import java.awt.Graphics;

public class StartTile extends Tile{

	private Character c;
	public StartTile(int x, int y, Character c) {
		super(x, y);
		this.c = c;
	}

	public void draw(Graphics g){
		g.setColor(c.getColor());
		g.fillRect(xLoc, yLoc, size, size);
	}
}
