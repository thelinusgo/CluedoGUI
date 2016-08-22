package cluedo.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class GameOverCanvas extends Canvas {
	
	private BufferedImage img;
	
	public GameOverCanvas(){
		try{
			this.img = ImageIO.read(new File("GameOver.jpg"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g){
		g.drawImage(img, 0, 0, 528, 437, null);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(528,437);
	}
	
	
}
