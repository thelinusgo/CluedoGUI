package cluedo.cards;
import cluedo.assets.Weapon;

/**
 * This class represents a card holding a Weapon object.
 * @author Linus
 *
 */
public class WeaponCard extends Card<Weapon>{
	/**
	 * Construct a new weapon card, with a given weapon
	 * @param Weapon w
	 */
	public WeaponCard(Weapon w){
		super(w);
	}
}
