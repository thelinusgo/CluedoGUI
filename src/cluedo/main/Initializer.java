package cluedo.main;

import java.awt.Color;
import java.util.ArrayList;
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
	private static List<Room> rooms = new ArrayList<>();
	private static List<Weapon> weapons = new ArrayList<>();
	private static List<Card> cards = new ArrayList<>();
	private static Character[] characters = new Character[6];
	public static List<String> characterNames = new ArrayList<>();
	private static Player[] players = new Player[6];
	private static Envelope envelope = new Envelope();
	private static List<RoomCard> roomCards = new ArrayList<>();
	private static List<WeaponCard> weaponCards = new ArrayList<>();
	private static List<CharacterCard> characterCards = new ArrayList<>();

	/** This helps generating a random shuffle for the lists */
	private long seed = System.nanoTime();

	/*Initialise Rooms NB: not all rooms have weapons.  */
	public Room kitchen = new Room("Kitchen", 0, 1, 6, 6, true);
	public Room diningrm = new Room("Dining Room", 0, 9, 8, 7, false);
	public Room ballRm = new Room("Ball Room", 8, 1, 8, 7, false);
	public Room conservatory = new Room("Conservatory", 18, 1, 7, 5, true);
	public Room billRm = new Room("Billiard Room", 19, 8, 6, 5, false);
	public Room lib = new Room("Library", 18, 14, 7, 5, false);
	public Room study = new Room("Study", 17, 21, 8, 4, true);
	public Room hall = new Room("Hall", 9, 18, 6, 7, false);
	public Room lounge = new Room("Lounge", 0, 19, 7, 6, true);

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
		weapons.add(new Weapon("Candlestick"));
		weapons.add(new Weapon("Dagger"));
		weapons.add(new Weapon("Lead Pipe"));
		weapons.add(new Weapon("Revolver"));
		weapons.add(new Weapon("Rope"));
		weapons.add(new Weapon("Spanner"));

		/*Shuffle it so that a weapon will be in a random room each time. */
		Collections.shuffle(weapons, new Random(seed)); //shuffle it
	}

	/**
	 * Initializes the rooms list.
	 */
	private void initializeRooms(){
		/*Add rooms to rooms list*/
		rooms.add(kitchen);
		rooms.add(diningrm);
		rooms.add(ballRm);
		rooms.add(conservatory);
		rooms.add(billRm);
		rooms.add(lib);
		rooms.add(study);
		rooms.add(hall);
		rooms.add(lounge);

		/*Set connecting rooms*/
		kitchen.setRoom(study);
		conservatory.setRoom(lounge);
		study.setRoom(kitchen);
		lounge.setRoom(conservatory);
		Collections.shuffle(rooms); //shuffle it

		for(int i = 0; i < weapons.size(); i++){
			Room r = rooms.get(i);
			Weapon w = weapons.get(i);
			r.addWeapon(w);
			w.addRoom(r);
		}

		Collections.shuffle(weapons, new Random(seed));
	}

	/**
	 * Initializes the characters list.
	 */
	private void initializeCharacters(){
		/*Fill the ArrayList with people.. */
		characters[0] = new Character("Miss Scarlet", new Color(255, 77, 77), new Position(7, 24));
		characters[1] = new Character("Colonel Mustard", new Color(255, 255, 77), new Position(0, 17));
		characters[2] = new Character("Mrs. White", Color.white, new Position(9, 0));
		characters[3] = new Character("The Reverend Green", new Color(0, 204, 0), new Position(14, 0));
		characters[4] = new Character("Mrs. Peacock", new Color(153, 0, 204), new Position(23, 6));
		characters[5] = new Character("Professor Plum", new Color(0, 102, 204), new Position(23, 19));
	
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
			cards.add(new RoomCard(r));
		}

		/*Fill the cards arrayList with Weapon Cards */
		for(Weapon w : weapons){
			cards.add(new WeaponCard(w));
		}

		/*Fill the cards ArrayList with Player Cards */
		for(Character p : characters){
			cards.add(new CharacterCard(p));
		}

		for(Card c : cards){
			if(c instanceof RoomCard){
				roomCards.add((RoomCard) c);
			}else if(c instanceof WeaponCard){
				weaponCards.add((WeaponCard) c);
			}else if(c instanceof CharacterCard){
				characterCards.add((CharacterCard) c);
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
		Collections.shuffle(rooms, new Random(seed));
		for(int i = 0; i < characters.length; i++){
			Character c = characters[i];
			if(c.player() == null){
				Player p = new Player("none");
				c.setPlayer(p);
				p.setCharacter(c);
				Room rm = rooms.get(i);
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
		Collections.shuffle(roomCards, new Random(seed)); 
		Collections.shuffle(weaponCards, new Random(seed)); 
		Collections.shuffle(characterCards, new Random(seed)); 
		for(int i = 0, j = 0; j < roomCards.size(); i++, j++){
			if(i == currentPlayers.size()){
				i = 0;
			}
			Player currentPlayer = currentPlayers.get(i);
			currentPlayer.addCard(roomCards.get(j));
		}
		for(int i = 0, j = 0; j < weaponCards.size(); i++, j++){
			if(i == currentPlayers.size()){
				i = 0;
			}
			Player currentPlayer = currentPlayers.get(i);
			currentPlayer.addCard(weaponCards.get(j));
		}
		for(int i = 0, j = 0; j < characterCards.size(); i++, j++){
			if(i == currentPlayers.size()){
				i = 0;
			}
			Player currentPlayer = currentPlayers.get(i);
			currentPlayer.addCard(characterCards.get(j));
		}
	}

	/**
	 * Returns the list of RoomCards
	 * @return
	 */
	public static List<RoomCard> getRoomCards(){
		return Initializer.roomCards;
	}

	/**
	 * Returns the list of WeaponCards
	 * @return
	 */
	public static List<WeaponCard> getWeaponCards(){
		return Initializer.weaponCards;
	}

	/**
	 * Returns the list of CharacterCards
	 * @return
	 */
	public static List<CharacterCard> getCharacterCards(){
		return Initializer.characterCards;
	}

	/**
	 * Returns the rooms
	 * @return
	 */
	public static List<Room> getRooms(){
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
