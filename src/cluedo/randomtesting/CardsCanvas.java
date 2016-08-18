package cluedo.randomtesting;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Canvas used to display the cards that a current Player has.
 * @author linus
 *
 */
public class CardsCanvas extends JPanel{
	/*Fields to represent the room Cards. */
	private BufferedImage kitchenCard;
	private BufferedImage ballCard;
	private BufferedImage conservatoryCard;
	private BufferedImage billiardCard;
	private BufferedImage libraryCard;
	private BufferedImage studyCard;
	private BufferedImage hallCard;
	private BufferedImage loungeCard;
	private BufferedImage diningCard;
	
	/*Fields to represent the weapons.*/
	private BufferedImage candleCard;
	private BufferedImage daggerCard;
	private BufferedImage leadPipeCard;
	private BufferedImage revolverCard;
	private BufferedImage ropeCard;
	private BufferedImage spannerCard;
	
	/*Fields that represent the Characters */
	private BufferedImage missScarlett;
	private BufferedImage colonelMustard;
	private BufferedImage mrsWhite;
	private BufferedImage theRevGreen;
	private BufferedImage mrsPeacock;
	private BufferedImage profPlum;
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(105,505);
	}
	
	@Override
	public void paint(Graphics g){
		
		try {
			candleCard = ImageIO.read(new File("CandleStick.JPG"));
			daggerCard = ImageIO.read(new File("KNIFE.JPG"));
			leadPipeCard = ImageIO.read(new File("LeadPipe.JPG"));
			revolverCard = ImageIO.read(new File("Revolver.JPG"));
			ropeCard = ImageIO.read(new File("Rope.JPG"));
		} catch (IOException e) {
			System.err.println("One of the images is not right");
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		g.drawString("Cards in hand:", 5, 15);
		
		
	}
	
	
	
	
	
}
