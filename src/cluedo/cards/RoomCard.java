package cluedo.cards;

import java.awt.Graphics;
import java.awt.Image;

import cluedo.assets.Room;

/**
 * This class represents a Card holding a Room object.
 * @author Linus and Casey
 *
 */
public class RoomCard extends Card<Room>{
	
	/**
	 * Constructs a new RoomCard object
	 * @param room
	 */
	public RoomCard(Room r, Image im){
		super(r, im);
	}
	
	public void draw(Graphics g, int i, int yOffset, int xOffset){
		g.drawImage(image, xOffset+i*CARD_WD, yOffset, CARD_WD, CARD_HT, null);
		g.drawRect(xOffset+i*CARD_WD, yOffset, CARD_WD, CARD_HT);
	}
}
