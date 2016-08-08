package cluedo.cards;

import cluedo.assets.Room;

/**
 * This class represents a Card holding a Room object.
 * @author Linus
 *
 */
public class RoomCard extends Card<Room>{
	/**
	 * Constructs a new RoomCard object
	 * @param room
	 */
	public RoomCard(Room r){
		super(r);
	}
}
