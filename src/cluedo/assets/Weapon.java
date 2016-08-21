package cluedo.assets;

import java.awt.Image;

/**
 * Class that represents a Weapon.
 * A weapon belongs to a room.
 * @author Casey & Linus
 *
 */
public class Weapon{
	/**
	 * The name of the weapon.
	 */
	private String weaponName;
	
	/**
	 * The image of this weapon's card
	 */
	private Image image;
	
	/**
	 * The room that this weapon is in.
	 */
	private Room room;
	
	/**
	 * Create a new weapon. The string name cannot be null.
	 * @param w
	 */
	public Weapon(Image im, String w){
		assert w!=null: "weapon name cannot be null";
		this.weaponName = w;
		this.image = im;
	}
	
	/**
	 * Returns the image of this weapon's card.
	 * @return
	 */
	public Image getImage(){
		return this.image;
	}
	
	/**
	 * Returns the weaponName of this weapon.
	 * @return
	 */
	public String weaponName(){return this.weaponName;}
	
	/**
	 * Stores the room that this weapon is in.
	 * @param rm
	 */
	public void addRoom(Room rm){
		this.room = rm;
	}
	/**
	 * Returns the room that this weapon is in.
	 * @return
	 */
	public Room getRoom(){
		return this.room;
	}
	/**
	 * Returns the toString representation of this weapon - and the room it is currently in.
	 */
	@Override
	public String toString(){
		return "Weapon: " + this.weaponName + " Room: " + this.getRoom();
	}
}
