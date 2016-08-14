package cluedo.assets;

import java.awt.Color;

/**
 * Class that represents a player in the game. Contains a name.
 * @author Casey & Linus
 *
 */
public class Character {
	/**
	 * The name of this character.
	 */
	private String name;
	
	/**
	 * The room that this character object is in (if in a room).
	 */
	private Room room;
	
	/**
	 * Stores the player that this character object belongs to.
	 */
	private Player player;
	
	/**
	 * Colour of this character
	 */
	private Color color;
	
	/**
	 * Stores the start position of this character
	 */
	private Position startPos;
	
	/**
	 * Construct a new character with a given name.
	 * @param name
	 */
	public Character(String name, Color c, Position startPos){
		this.name = name;
		this.color = c;
		this.startPos = startPos;
	}
	
	/**
	 * Returns the name of the character. This will return a character piece on the board.
	 * @return
	 */
	public String getName(){
		switch(this.name){
			case "Miss Scarlett": return "M";
			case "Colonel Mustard": return "C";
			case "Mrs. White": return "W";
			case "The Reverend Green": return "G";
			case "Mrs. Peacock": return "P";
			case "Professor Plum": return "R";
		}
		return null;
	}
	
	/**
	 * Returns the colour of this character
	 * @return
	 */
	public Color getColor(){
		return this.color;
	}
	
	/**
	 * Returns the start position of this character
	 * @return
	 */
	public Position getStartPos(){
		return startPos;
	}
	
	/**
	 * Returns the full String name of this character.
	 * @return
	 */
	public String name(){
		return this.name;
	}
	
	/**
	 * Stores the room that this player is in.
	 * @param rm
	 */
	public void addRoom(Room rm){
		this.room = rm;
	}
	
	/**
	 * Returns the room that this player belongs to.
	 * @return
	 */
	public Room getRoom(){
		return this.room;
	}
	
	/**
	 * Returns the player that this character object belongs to.
	 * @return
	 */
	public Player player(){
		return this.player;
	}
	
	/**
	 * Set player that this character object belongs to.
	 * @param p
	 */
	public void setPlayer(Player p){
		this.player = p;
	}
	/**
	 * Returns a toString representation of this character.
	 */
	@Override
	public String toString(){
		return "Character: " + name;
	}
	
}
