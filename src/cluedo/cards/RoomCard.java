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
	 * Y position of card.
	 */
	private static final int Y_ROOM_OFFSET = 57+CARD_HT;
	
	/**
	 * Constructs a new RoomCard object
	 * @param room
	 */
	public RoomCard(Room r, Image im){
		super(r, im);
	}
	
	public void draw(Graphics g, int i){
		g.drawImage(image, X_OFFSET+i*CARD_WD, Y_ROOM_OFFSET, CARD_WD, CARD_HT, null);
		g.drawRect(X_OFFSET+i*CARD_WD, Y_ROOM_OFFSET, CARD_WD, CARD_HT);
		System.out.println(super.getName());
	}
}
