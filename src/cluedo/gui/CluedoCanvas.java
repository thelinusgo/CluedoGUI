package cluedo.gui;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import cluedo.assets.Tile;
/**
 * Class that represents the Cluedo Canvas
 * @author linus
 *
 */
public class CluedoCanvas extends JPanel {
	
	private Tile[][] board = new Tile[24][23];
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	/**
	 * This sets the dimension of the Cluedo Canvas.
	 */
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(600,600);
	}
	
	public void paint(Graphics g){
		
	}
	
	public void drawKitchen(Graphics g){
		//g.drawRect(x, y, width, height);
	}
	
}
