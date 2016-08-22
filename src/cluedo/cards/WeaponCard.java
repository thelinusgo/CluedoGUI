package cluedo.cards;
import java.awt.Graphics;
import java.awt.Image;

import cluedo.assets.Weapon;

/**
 * This class represents a card holding a Weapon object.
 * @author Linus and Casey
 *
 */
public class WeaponCard extends Card<Weapon>{
	
	/**
	 * Construct a new weapon card, with a given weapon
	 * @param Weapon w
	 */
	public WeaponCard(Weapon w, Image im){
		super(w, im);
	}
	
	public void draw(Graphics g, int i, int Y_WEP_OFFSET, int X_OFFSET){
		g.drawImage(image, X_OFFSET+i*CARD_WD, Y_WEP_OFFSET, CARD_WD, CARD_HT, null);
		g.drawRect(X_OFFSET+i*CARD_WD, Y_WEP_OFFSET, CARD_WD, CARD_HT);
	}
}
