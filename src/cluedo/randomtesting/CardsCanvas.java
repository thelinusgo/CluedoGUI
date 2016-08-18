package cluedo.randomtesting;

import java.awt.Dimension;
import java.awt.Font;
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
	
	/**
	 * The Width of a Card Image.
	 */
	private static final int CARD_WD = 90;
	/**
	 * The Height of a Card Image.
	 */
	private static final int CARD_HT = 125;
	
	/*Offset for the Weapon Card positions. Provides x-y starting position for drawing them. */
	private static final int X_OFFSET = 5; 
	private static final int Y_WEP_OFFSET = 37;
	private static final int Y_ROOM_OFFSET = 57+CARD_HT;
	private static final int Y_CHAR_OFFSET = 330;
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(640,552);
	}
	
	@Override
	public void paint(Graphics g){
		
		try {
			//load in the weapon images.
			candleCard = ImageIO.read(new File("CandleStick.JPG"));
			daggerCard = ImageIO.read(new File("Knife.JPG"));
			leadPipeCard = ImageIO.read(new File("LeadPipe.JPG"));
			revolverCard = ImageIO.read(new File("Revolver.JPG"));
			ropeCard = ImageIO.read(new File("Rope.JPG"));
			spannerCard = ImageIO.read(new File("Wrench.JPG"));
			//the rooms...
			kitchenCard = ImageIO.read(new File("Kitchen.JPG"));
			ballCard = ImageIO.read(new File("Ballroom.JPG"));
			libraryCard = ImageIO.read(new File("Library.JPG"));
			studyCard = ImageIO.read(new File("Study.JPG"));
			hallCard = ImageIO.read(new File("Hall.JPG"));
			loungeCard = ImageIO.read(new File("Lounge.JPG"));
			diningCard = ImageIO.read(new File("DiningRoom.JPG"));
			//the character cards.
			missScarlett = ImageIO.read(new File("MissScarlet.JPG"));
			colonelMustard = ImageIO.read(new File("ColonelMustard.JPG"));
			mrsWhite = ImageIO.read(new File("MrsWhite.JPG"));
			theRevGreen = ImageIO.read(new File("MrGreen.JPG"));
			mrsPeacock = ImageIO.read(new File("MrsPeacock.JPG"));
			profPlum = ImageIO.read(new File("ProfessorPlum.JPG"));

		} catch (IOException e) {
			System.err.println("One of the images is not right");
			e.printStackTrace();
		}
		
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		g.drawString("Cards in hand:", 5, 15);
		g.drawString("Weapons:", 5, 27);
		
		/*Drawing out the Weapon Cards */
		g.drawImage(candleCard, X_OFFSET, Y_WEP_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(daggerCard, X_OFFSET+CARD_WD, Y_WEP_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(leadPipeCard, X_OFFSET+(2*CARD_WD), Y_WEP_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(revolverCard, X_OFFSET+(3*CARD_WD), Y_WEP_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(ropeCard, X_OFFSET+(4*CARD_WD), Y_WEP_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(spannerCard, X_OFFSET+(5*CARD_WD), Y_WEP_OFFSET, CARD_WD, CARD_HT, null);

		g.drawString("Rooms:", 5, 52+CARD_HT);
		
		
		/*Drawing out the Room Cards */
		g.drawImage(kitchenCard, X_OFFSET, Y_ROOM_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(ballCard, X_OFFSET+CARD_WD, Y_ROOM_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(libraryCard, X_OFFSET+(2*CARD_WD), Y_ROOM_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(studyCard, X_OFFSET+(3*CARD_WD), Y_ROOM_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(hallCard, X_OFFSET+(4*CARD_WD), Y_ROOM_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(loungeCard, X_OFFSET+(5*CARD_WD), Y_ROOM_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(diningCard, X_OFFSET+(6*CARD_WD), Y_ROOM_OFFSET, CARD_WD, CARD_HT, null);
		
		g.drawString("Characters: " , 5, (3*CARD_HT)-35);
		System.out.println(3*CARD_HT-35);
		g.drawImage(missScarlett, X_OFFSET, Y_CHAR_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(colonelMustard, X_OFFSET+CARD_WD, Y_CHAR_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(mrsWhite, X_OFFSET+(2*CARD_WD), Y_CHAR_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(theRevGreen, X_OFFSET+(3*CARD_WD), Y_CHAR_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(mrsPeacock,  X_OFFSET+(4*CARD_WD), Y_CHAR_OFFSET, CARD_WD, CARD_HT, null);
		g.drawImage(profPlum, X_OFFSET+(5*CARD_WD), Y_CHAR_OFFSET, CARD_WD, CARD_HT, null);

	}
	
	
	
	
	
}
