package cluedo.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import cluedo.assets.*;
import cluedo.assets.Character;
import cluedo.cards.Card;
import cluedo.cards.CharacterCard;
import cluedo.cards.Envelope;
import cluedo.cards.RoomCard;
import cluedo.cards.WeaponCard;
/**
 * Class that represents the Board. Contains fields and methods regarding setting up the board.
 * @author Casey & Linus
 *
 */
public class Initializer {
	/**Lists that hold components of the board */
	private static Room[] rooms = new Room[9];
	private static Weapon[] weapons = new Weapon[6];
	private static List<Card> cards = new ArrayList<>();
	private static Character[] characters = new Character[6];
	public static List<String> characterNames = new ArrayList<>();
	private static Player[] players = new Player[6];
	private static Envelope envelope = new Envelope();
	private static RoomCard[] roomCards = new RoomCard[9];
	private static WeaponCard[] weaponCards = new WeaponCard[6];
	private static CharacterCard[] characterCards = new CharacterCard[6];

	/** This helps generating a random shuffle for the lists */
	private long seed = System.nanoTime();

	/*Initialise Rooms NB: not all rooms have weapons.  */
	public Room kitchen = new Room(CluedoGame.kitchenCard, "Kitchen", 0, 1, 6, 6, true);
	public Room diningrm = new Room(CluedoGame.diningCard, "Dining Room", 0, 9, 8, 7, false);
	public Room ballRm = new Room(CluedoGame.ballCard, "Ball Room", 8, 1, 8, 7, false);
	public Room conservatory = new Room(CluedoGame.conservatoryCard, "Conservatory", 18, 1, 7, 5, true);
	public Room billRm = new Room(CluedoGame.billiardCard, "Billiard Room", 19, 8, 6, 5, false);
	public Room lib = new Room(CluedoGame.libraryCard, "Library", 18, 14, 7, 5, false);
	public Room study = new Room(CluedoGame.studyCard, "Study", 17, 21, 8, 4, true);
	public Room hall = new Room(CluedoGame.hallCard, "Hall", 9, 18, 6, 7, false);
	public Room lounge = new Room(CluedoGame.loungeCard, "Lounge", 0, 19, 7, 6, true);

	/**
	 * Construct a new Board
	 */
	public Initializer(){
		initializeWeapons();
		initializeRooms();
		initializeCharacters();
		fillList();
		initializeEnvelope();
	}
	
	/**
	 * Returns the envelope object.
	 * @return
	 */
	public static Envelope getEnvelope(){
		return envelope;
	}

	/**
	 * Initializes the weapons list.
	 */
	private void initializeWeapons(){
		/*Fill the arraylist with weapons*/
		weapons[0] = new Weapon(CluedoGame.candleCard, "Candlestick");
		weapons[1] = new Weapon(CluedoGame.daggerCard, "Dagger");
		weapons[2] = new Weapon(CluedoGame.leadPipeCard, "Lead Pipe");
		weapons[3] = new Weapon(CluedoGame.revolverCard, "Revolver");
		weapons[4] = new Weapon(CluedoGame.ropeCard, "Rope");
		weapons[5] = new Weapon(CluedoGame.spannerCard, "Spanner");

		/*Shuffle it so that a weapon will be in a random room each time. */
		Collections.shuffle(Arrays.asList(weapons), new Random(seed)); //shuffle it
	}

	/**
	 * Initializes the rooms list.
	 */
	private void initializeRooms(){
		/*Add rooms to rooms list*/
		rooms[0] = kitchen;
		rooms[1] = diningrm;
		rooms[2] = ballRm;
		rooms[3] = conservatory;
		rooms[4] = billRm;
		rooms[5] = lib;
		rooms[6] = study;
		rooms[7] = hall;
		rooms[8] = lounge;

		/*Set connecting rooms*/
		kitchen.setRoom(study);
		conservatory.setRoom(lounge);
		study.setRoom(kitchen);
		lounge.setRoom(conservatory);
		Collections.shuffle(Arrays.asList(rooms)); //shuffle it

		for(int i = 0; i < weapons.length; i++){
			Room r = rooms[i];
			Weapon w = weapons[i];
			r.addWeapon(w);
			w.addRoom(r);
		}

		Collections.shuffle(Arrays.asList(weapons), new Random(seed));
	}

	/**
	 * Initializes the characters list.
	 */
	private void initializeCharacters(){
		/*Fill the ArrayList with people.. */
		characters[0] = new Character(CluedoGame.missScarlett, "Miss Scarlet", new Color(255, 77, 77), new Position(7, 24));
		characters[1] = new Character(CluedoGame.colonelMustard, "Colonel Mustard", new Color(255, 255, 77), new Position(0, 17));
		characters[2] = new Character(CluedoGame.mrsWhite, "Mrs. White", Color.white, new Position(9, 0));
		characters[3] = new Character(CluedoGame.theRevGreen, "The Reverend Green", new Color(0, 204, 0), new Position(14, 0));
		characters[4] = new Character(CluedoGame.mrsPeacock, "Mrs. Peacock", new Color(153, 0, 204), new Position(23, 6));
		characters[5] = new Character(CluedoGame.profPlum, "Professor Plum", new Color(0, 102, 204), new Position(23, 19));
	
		/*Allows a List of the characters Names to be used/modified */
		for(Character c : characters){
			characterNames.add(c.name());
		}
	}

	/**
	 * Put all cards in cards list.
	 */
	private void fillList(){
		/*Fill the cards arrayList with Room Cards */
		for(Room r : rooms){
			cards.add(new RoomCard(r, r.getImage()));
		}

		/*Fill the cards arrayList with Weapon Cards */
		for(Weapon w : weapons){
			cards.add(new WeaponCard(w, w.getImage()));
		}

		/*Fill the cards ArrayList with Player Cards */
		for(Character c : characters){
			cards.add(new CharacterCard(c, c.getImage()));
		}
		
		int index_R = 0;
		int index_W = 0;
		int index_C = 0;
		for(int i = 0 ; i < cards.size(); i++){
			Card c = cards.get(i);
			if(c instanceof RoomCard){
				roomCards[index_R] = (RoomCard) c;
				index_R++;
			}else if(c instanceof WeaponCard){
				weaponCards[index_W] = (WeaponCard) c;
				index_W++;
			}else if(c instanceof CharacterCard){
				characterCards[index_C] = (CharacterCard) c;
				index_C++;
			}
		}

		Collections.shuffle(cards, new Random(seed)); 
	}

	/**
	 * Initializes the envelope list.
	 */
	private void initializeEnvelope(){

		RoomCard roomCard = null;
		CharacterCard characterCard = null;
		WeaponCard weaponCard = null;

		for(Card c : cards){
			if(roomCard == null){
				if(c instanceof RoomCard){
					roomCard = (RoomCard) c;
				}
			}else if(weaponCard == null){
				if(c instanceof WeaponCard){
					weaponCard = (WeaponCard) c;
				}
			}else if(characterCard == null){
				if(c instanceof CharacterCard){
					characterCard = (CharacterCard) c;
				}
			}

			if(roomCard != null && characterCard != null && weaponCard != null){
				break;
			}
		}
		envelope.add(weaponCard);
		envelope.add(characterCard);
		envelope.add(roomCard);

		/*Finally, remove these cards from their arrayList */
		cards.remove(roomCard);
		cards.remove(weaponCard);
		cards.remove(characterCard);
	}

	/**
	 * Store character in room and room in character.
	 */
	public void setCharacters(){
		Collections.shuffle(Arrays.asList(rooms), new Random(seed));
		for(int i = 0; i < characters.length; i++){
			Character c = characters[i];
			if(c.player() == null){
				Player p = new Player("none");
				c.setPlayer(p);
				p.setCharacter(c);
				Room rm = rooms[i];
				rm.addCharacter(c);
				c.addRoom(rm);
				CluedoGame.cluedoCanvas.moveToRoom(p, rm);
			}
		}
	}

	/**
	 * Distribute cards to players.
	 * @param currentPlayers
	 */
	public void distributeCards(List<Player> currentPlayers){
		Collections.shuffle(Arrays.asList(roomCards), new Random(seed)); 
		Collections.shuffle(Arrays.asList(weaponCards), new Random(seed)); 
		Collections.shuffle(Arrays.asList(characterCards), new Random(seed)); 
		for(int i = 0, j = 0; j < roomCards.length; i++, j++){
			if(i == currentPlayers.size()){
				i = 0;
			}
			Player currentPlayer = currentPlayers.get(i);
			currentPlayer.addCard(roomCards[j]);
		}
		for(int i = 0, j = 0; j < weaponCards.length; i++, j++){
			if(i == currentPlayers.size()){
				i = 0;
			}
			Player currentPlayer = currentPlayers.get(i);
			currentPlayer.addCard(weaponCards[j]);
		}
		for(int i = 0, j = 0; j < characterCards.length; i++, j++){
			if(i == currentPlayers.size()){
				i = 0;
			}
			Player currentPlayer = currentPlayers.get(i);
			currentPlayer.addCard(characterCards[j]);
		}
		System.out.println(roomCards.length+weaponCards.length+characterCards.length);
	}

	/**
	 * Returns the list of RoomCards
	 * @return
	 */
	public static RoomCard[] getRoomCards(){
		return Initializer.roomCards;
	}

	/**
	 * Returns the list of WeaponCards
	 * @return
	 */
	public static WeaponCard[] getWeaponCards(){
		return Initializer.weaponCards;
	}

	/**
	 * Returns the list of CharacterCards
	 * @return
	 */
	public static CharacterCard[] getCharacterCards(){
		return Initializer.characterCards;
	}

	/**
	 * Returns the rooms
	 * @return
	 */
	public static Room[] getRooms(){
		return Initializer.rooms;
	}
	
	/**
	 * Returns the list of characters
	 * @return
	 */
	public static Character[] getCharacters(){
		return characters;
	}
}
