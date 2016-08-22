package cluedo.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
/**
 * This class represents the canvas that is drawn when the game is lost.
 * It draws a Game Over image, that is displayed on a pop up window.
 * @author linus
 *
 */
public class GameOverCanvas extends Canvas {
	
	private BufferedImage img;
	private String pName;
	public GameOverCanvas(){
		try{
			this.img = ImageIO.read(new File("GameOver.jpg"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g){
		g.drawImage(img, 0, 0, 792, 656, null);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(792,656);
	}
	
	
}
