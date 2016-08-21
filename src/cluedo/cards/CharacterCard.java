package cluedo.cards;

import java.awt.Graphics;
import java.awt.Image;

import cluedo.assets.Character;

/**
 * This class represents a Card containing a player object.
 * @author Linus and Casey
 *
 */
public class CharacterCard extends Card<Character>{
	/**
	 * Y position of card.
	 */
	private static final int Y_CHAR_OFFSET = 350;
	
	/**
	 * Constructs a new CharacterCard object.
	 * @param itm
	 */
	public CharacterCard(Character itm, Image im) {
		super(itm, im);
	}

	public void draw(Graphics g, int i){
		g.drawImage(image, X_OFFSET+i*CARD_WD, Y_CHAR_OFFSET, CARD_WD, CARD_HT, null);
		g.drawRect(X_OFFSET+i*CARD_WD, Y_CHAR_OFFSET, CARD_WD, CARD_HT);
	}
}
