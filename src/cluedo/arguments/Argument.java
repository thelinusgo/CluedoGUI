package cluedo.arguments;

import cluedo.assets.Player;
import cluedo.cards.*;

/**
 * Class that represents arguments - that is, either a suggestion or an accusation in Cluedo.
 * 
 * @author Casey & Linus
 *
 */
public class Argument {
	private WeaponCard weapon;
	private RoomCard roomCard;
	private CharacterCard character;
	private Player currentPlayer;
	
	/**
	 * Constructs an argument object, consisting of a weapon, a room and a character
	 * @param w
	 * @param r
	 * @param c
	 */
	public Argument(WeaponCard w, RoomCard r, CharacterCard c, Player p){
		this.weapon = w;
		this.roomCard = r;
		this.character = c;
		this.currentPlayer = p;
	}
	
	/**
	 * gets the WeaponCard object.
	 * @return
	 */
	public WeaponCard getWeaponCard() {
		return weapon;
	}

	/**
	 * gets the roomCard object.
	 * @return
	 */
	public RoomCard getRoomCard() {
		return roomCard;
	}
	
	/**
	 * gets the characterCard object.
	 * @return
	 */
	public CharacterCard getCharacterCard() {
		return character;
	}
	/**
	 * Returns the current player that this argument object is associated with.
	 * @return
	 */
	public Player getCurrentPlayer(){
		return this.currentPlayer;
	}
}
