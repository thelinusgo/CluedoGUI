package cluedo.assets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RoomTile extends Tile{
	private String direction = null;
	private String direction2 = null;
	private Player player = null;

	public RoomTile(int x, int y, String dir, String dir2){
		super(x, y);
		this.direction = dir;
		this.direction2 = dir2;
	}

	public void setPlayer(Player p){
		this.player = p;
	}

	public void draw(Graphics g){
		g.setColor(new Color(255, 255, 153));
		g.fillRect(super.xLoc, super.yLoc, super.TILESIZE, super.TILESIZE);
		g.setColor(Color.black);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		if(this.direction != null){
			switch(this.direction){
			case "right":
				g.drawLine(super.xLoc+TILESIZE, super.yLoc, super.xLoc+TILESIZE, super.yLoc+TILESIZE);
				break;
			case "left":
				g.drawLine(super.xLoc, super.yLoc, super.xLoc, super.yLoc+TILESIZE);
				break;
			case "top":
				g.drawLine(super.xLoc, super.yLoc, super.xLoc+TILESIZE, super.yLoc);
				break;
			case "bottom":
				g.drawLine(super.xLoc, super.yLoc+TILESIZE, super.xLoc+TILESIZE, super.yLoc+TILESIZE);
				break;
			}
		}
		if(this.direction2 != null){
			switch(this.direction2){
			case "right":
				g.drawLine(super.xLoc+TILESIZE, super.yLoc, super.xLoc+TILESIZE, super.yLoc+TILESIZE);
				break;
			case "left":
				g.drawLine(super.xLoc, super.yLoc, super.xLoc, super.yLoc+TILESIZE);
				break;
			case "top":
				g.drawLine(super.xLoc, super.yLoc, super.xLoc+TILESIZE, super.yLoc);
				break;
			case "bottom":
				g.drawLine(super.xLoc, super.yLoc+TILESIZE, super.xLoc+TILESIZE, super.yLoc+TILESIZE);
				break;
			}
		}
		g2.setStroke(new BasicStroke(0));
		if(this.player != null){
			g.drawOval(super.xLoc, super.yLoc, super.TILESIZE, super.TILESIZE);
		}
	}
}
