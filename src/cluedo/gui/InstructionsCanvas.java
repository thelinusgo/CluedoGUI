package cluedo.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class InstructionsCanvas extends Canvas {
	
	private BufferedImage img;
	
	public InstructionsCanvas(){
		try{
			this.img = ImageIO.read(new File("instructions.jpg"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	

	@Override
	public void paint(Graphics g){
		g.drawImage(img, 0, 0, 708, 567, null);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(695, 557);
	}
	
	
}
