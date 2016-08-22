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
	 * Constructs a new CharacterCard object.
	 * @param itm
	 */
	public CharacterCard(Character itm, Image im) {
		super(itm, im);
	}

	public void draw(Graphics g, int i, int yOffset, int xOffset){
		g.drawImage(image, xOffset+i*CARD_WD, yOffset, CARD_WD, CARD_HT, null);
		g.drawRect(xOffset+i*CARD_WD, yOffset, CARD_WD, CARD_HT);
	}
}
