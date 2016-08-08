package cluedo.cards;

import cluedo.assets.Room;

/**
 * Abstract class that contains all of the Cards.
 * @author Casey & Linus
 * @param <E>
 *
 */
public abstract class Card<E> {
	/**
	 * The item being held by the card.
	 */
	private E item; //This represents the Item being held in the card.
	
	/**
	 * Construct a new Card with the given item inside.
	 * @param itm
	 */
	public Card(E itm){
		assert itm != null : "Item in card CANNOT be null!";
		this.item = itm;
	}
	
	/**
	 * Returns a toString representation of this card.
	 * @return
	 */
	public String toString(){
		if(item instanceof Room){
			return "Card: " + ((Room) item).stringName();
		}
		return "Card: [" + item.toString() + "]";
	}
	
	/**
	 * Returns the name of this card.
	 * @return
	 */
	public String getName(){
		return item.toString();
	}
	
	/**
	 * Returns if this card is equal to another card. This works by checking the name of the card and seeing if they match.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Card){
			if(((Card) obj).getName().equals(this.getName())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the item being currently held by this card. For example, a RoomCard's getObject() call would return a Room object.
	 * @return
	 */
	public E getObject(){
	return this.item;	
	}
}
