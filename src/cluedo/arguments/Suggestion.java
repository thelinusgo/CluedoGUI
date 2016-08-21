package cluedo.arguments;

import cluedo.cards.Card;
import cluedo.cards.CharacterCard;
import cluedo.cards.RoomCard;
import cluedo.cards.WeaponCard;
import cluedo.main.CluedoGame;
import cluedo.assets.*;
import cluedo.assets.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a suggestion. It is a subtype of an argument.
 * @author Linus
 *
 */
public class Suggestion extends Argument {
	/**
	 * Boolean flag to check if the weapon is correct
	 */
	private boolean wpcorrect = false;
	/**
	 * Boolean flag to check if the character is correct
	 */
	private boolean charcorrect = false;

	/**
	 * Construct a new suggestion.
	 * @param w
	 * @param r
	 * @param c
	 * @param p
	 */
	public Suggestion(WeaponCard w, RoomCard r, CharacterCard c, Player p) {
		super(w, r, c, p);
	}

	/**
	 * This checks if the suggestion is Correct by iterating over the list of players, and their hand.
	 * @param currentPlayers
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean checkSuggestion(List<Player> currentPlayers){
		if(currentPlayers == null) throw new IllegalArgumentException("List of players cannot be null");
		checkCards(currentPlayers);
		boolean correct = false;
		RoomCard roomCard = super.getRoomCard();
		CharacterCard cc = super.getCharacterCard();
		WeaponCard wp = super.getWeaponCard();

		if(!charcorrect){
			Room r1 = roomCard.getObject();
			Room r2 = cc.getObject().getRoom();
			Character c1 = cc.getObject();
			Character c2 = r1.getCharacter();
			c1.addRoom(r1);
			if(c2 != null){
				c2.addRoom(r2);
			}
			if(r2 != null){
				r2.addCharacter(c2);
			}
			r1.addCharacter(c1);
			for(Player player : currentPlayers){
				if(!player.equals(super.getCurrentPlayer())){
					if(player.getCharacter().equals(cc.getObject())){
						CluedoGame.cluedoCanvas.moveToRoom(player, roomCard.getObject());
					}
				}
			}
			correct = true;
		}else if(!wpcorrect){
			Room r1 = roomCard.getObject();

			//TODO: casey, I believe there is a major bug in here.
			Room r2 = wp.getObject().getRoom();
			Weapon w1 = wp.getObject();
			Weapon w2 = r1.getWeapon();
			w1.addRoom(r1);
			if(w2 !=  null){
				w2.addRoom(r2);
			}
			r1.addWeapon(w1);
			r2.addWeapon(w2);
			correct = true;
		}
		return correct;
	}


	/**
	 * Iterates over all of the players and checks if they have one of the matching suggestion cards.
	 * @param players
	 */
	public void checkCards(List<Player> players){
		List<Card> playerHand;
		wpcorrect = false;
		charcorrect = false;
		for(Player p : players){
				playerHand = p.getCards();
				for(Card card : playerHand){
					if(card.equals(super.getCharacterCard())){
						System.out.println(card.toString());
						charcorrect = true;
					}else if(card.equals(super.getWeaponCard())){
						System.out.println(card.toString());
						wpcorrect = true;
					}
				}
		}
	}
}
