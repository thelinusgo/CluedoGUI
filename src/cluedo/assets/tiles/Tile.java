package cluedo.assets.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cluedo.assets.Player;

public class Tile implements MouseListener{
	
	public int xLoc;
	public int yLoc;
	private Player player;
	private Color color = new Color(222, 192, 70); 
	
	public static final int TILESIZE = 21;
	
	/**
	 * Construct a new Tile with the given type of tile inside.
	 * @param tile
	 */
	public Tile(int x, int y){
		this.xLoc = 38+x*TILESIZE;
		this.yLoc = 25+y*TILESIZE;
	}
	
	public void setColor(Color c){
		this.color = c;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(xLoc, yLoc, TILESIZE, TILESIZE);
		g.setColor(Color.black);
		g.drawRect(xLoc, yLoc, TILESIZE, TILESIZE);
		
		if(this.player != null){
			g.setColor(this.player.getCharacter().getColor());
			g.drawOval(xLoc, yLoc, TILESIZE, TILESIZE);
		}
	}

	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public boolean contains(Point p){
		if(this.xLoc <= p.x && this.xLoc + TILESIZE > p.x && this.yLoc <= p.y && this.yLoc + TILESIZE > p.y){
			return true;
		}
		return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		int r = this.getColor().getRed();
		int g = this.getColor().getGreen();
		int b = this.getColor().getBlue();
		this.setColor(new Color(r, g, b, 50));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		int r = this.getColor().getRed();
		int g = this.getColor().getGreen();
		int b = this.getColor().getBlue();
		this.setColor(new Color(r, g, b, 1));
	}



}
