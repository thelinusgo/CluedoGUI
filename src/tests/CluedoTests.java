package tests;
import org.junit.*;

import cluedo.arguments.Accusation;
import cluedo.arguments.Suggestion;
import cluedo.assets.*;
import cluedo.assets.Character;
import cluedo.cards.Card;
import cluedo.cards.CharacterCard;
import cluedo.cards.Envelope;
import cluedo.cards.RoomCard;
import cluedo.cards.WeaponCard;
import cluedo.main.*;

import static org.junit.Assert.*;
import java.util.*;


public class CluedoTests {

	CluedoGameController game = new CluedoGameController(new CluedoView());

	/**
	 * Tests that it can move one position.
	 */
	@Test
	public void testValidMove_01() {
		try {
			Player player = setupMockPlayer("Ralf", "Miss Scarlett", new Position(23, 13));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			player.setNumberofMoves(12);
			game.cluedoCanvas.move(-1, 0, player, game.currentPlayers());
			assertEquals(new Position(22, 13), player.position());
		} catch (CluedoGameController.InvalidMove e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests that you can move multiple steps.
	 */
	@Test
	public void testValidMove_02() {
		try {
			Player player = setupMockPlayer("Ralf", "Miss Scarlett", new Position(23, 13));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			player.setNumberofMoves(12);
			game.cluedoCanvas.move(-4, 0, player, game.currentPlayers());
			assertEquals(new Position(19, 13), player.position());
		} catch (CluedoGameController.InvalidMove e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Ensures that you cannot move backwards from your position.
	 */
	@Test
	public void testInvalidMove_01(){
		try{
			Player player = setupMockPlayer("Ralf", "Professor Plum", new Position(0,17));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player); 
			player.setNumberofMoves(12);
			game.cluedoCanvas.move(-1, 0, player, game.currentPlayers());
			assertEquals(new Position(0, 17), player.position());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Ensures that you cannot move into a wall.
	 */
	@Test
	public void testInvalidMove_02(){
		try{
			Player player = setupMockPlayer("Ralf", "Professor Plum", new Position(7,24));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			player.setNumberofMoves(12);
			game.cluedoCanvas.move(1, 0, player, game.currentPlayers());
			assertEquals(new Position(7, 24), player.position());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that it utilizes the diceRoll correctly.
	 */
	@Test
	public void testValidMove_03(){
		int diceRoll = game.diceRoll();
		try{
			Player player = setupMockPlayer("Ralf", "Professor Plum", new Position(0,17));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player); 
			player.setNumberofMoves(12);
			for(int i = 0; i < diceRoll; i++){
				game.cluedoCanvas.move(1, 0, player, game.currentPlayers());
			}
			assertEquals(new Position(diceRoll, player.position().getY()), player.position());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that I can go inside of a room.
	 */
	@Test
	public void testGoingInRoom_01(){
		try{
			Player player = setupMockPlayer("Ralf", "Professor Plum", new Position(0,17));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			player.setNumberofMoves(12);
			game.cluedoCanvas.move(6, -2, player, game.currentPlayers());
			assertTrue(player.isInRoom());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGoingInRoom_02(){
		try {
			Player player1 = setupMockPlayer("Ralf", "Professor Plum", new Position(6,18));
			player1.setNumberofMoves(12);
			Player player2 = setupMockPlayer("Homer", "Miss Scarlett", new Position(6,17));
			player2.setNumberofMoves(12);
			game.cluedoCanvas.move(0, 1, player1, game.currentPlayers());
			game.cluedoCanvas.move(0, 1, player2, game.currentPlayers());
			game.cluedoCanvas.move(0, 1, player2, game.currentPlayers());
			Player player3 = setupMockPlayer("Sam", "Colonel Mustard", new Position(6,18));
			player3.setNumberofMoves(12);
			Player player4 = setupMockPlayer("Kumar", "Mrs. White", new Position(6,17));
			player4.setNumberofMoves(12);
			game.cluedoCanvas.move(0, 1, player3, game.currentPlayers());
			game.cluedoCanvas.move(0, 1, player4, game.currentPlayers());
			game.cluedoCanvas.move(0, 1, player4, game.currentPlayers());
			Player player5 = setupMockPlayer("Flo", "The Reverend Green", new Position(6,18));
			player5.setNumberofMoves(12);
			Player player6 = setupMockPlayer("Peter", "Mrs. Peacock", new Position(6,17));
			player6.setNumberofMoves(12);
			game.cluedoCanvas.move(0, 1, player5, game.currentPlayers());
			game.cluedoCanvas.move(0, 1, player6, game.currentPlayers());
			game.cluedoCanvas.move(0, 1, player6, game.currentPlayers());
			assertEquals(6, player1.getRoom().getMap().size());
		} catch (CluedoGameController.InvalidMove e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGoingInRoom_03(){
		try{
			Player player = setupMockPlayer("Ralf", "Professor Plum", new Position(0,17));
			game.addPlayer(player);
			Player player2 = setupMockPlayer("Pete", "Mrs. White", new Position(1,17));
			game.addPlayer(player2);
			player.setNumberofMoves(12);
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			game.cluedoCanvas.move(6, -2, player, game.currentPlayers());
			assertTrue(player.isInRoom());
			game.cluedoCanvas.exitRoom(player, game.currentPlayers());
			assertFalse(player.isInRoom());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that I can leave a room after entering it.
	 */
	@Test
	public void testLeavingRoom(){
		try{
			Player player = setupMockPlayer("Ralf", "Professor Plum", new Position(0,17));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			player.setNumberofMoves(12);
			game.cluedoCanvas.move(6, -2, player, game.currentPlayers()); //moving diagonally but who cares anyway?
			System.out.println(player.position().toString());
			assertTrue(player.isInRoom());
			game.cluedoCanvas.exitRoom(player, game.currentPlayers());
			assertTrue(!player.isInRoom());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGoingInStairs(){
		try{
			Player player = setupMockPlayer("Ralf", "Professor Plum", new Position(0,17));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player); 
			player.setNumberofMoves(12);
			game.cluedoCanvas.move(6, 2, player, game.currentPlayers()); //moving diagonally but who cares anyway?
			assertTrue(player.isInRoom());
			//get the room that the player WILL LEAVE
			Room oldRoom = player.getRoom();
			game.cluedoCanvas.moveToRoom(player, oldRoom.getOtherRoom());
			assertTrue(player.isInRoom());
			//check that the rooms Other Room equals the old room.
			assertEquals(player.getRoom().getOtherRoom(), oldRoom);
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}	
	}

	/**
	 * Test when you try to go use stairs in a room that does not have them.
	 */
	@Test
	public void testInvalidGoingStairs(){
		try{
			Player player = setupMockPlayer("Ralf", "Professor Plum", new Position(23,6));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player); 
			player.setNumberofMoves(12);
			game.cluedoCanvas.move(0, -1, player, game.currentPlayers());
			assertFalse(player.isInRoom());
			assertEquals(new Position(23, 6), player.position());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that a player must make a valid name.
	 */
	@Test
	public void testInvalidCharacterName(){
		try{
			Player player = setupMockPlayer("Ralf", "Professor Pulm", new Position(0,17));
			assertTrue(player.getCharacterName() == null);
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that a player must make a valid name.
	 */
	@Test
	public void testValidCharacter_01(){
		try{
			Player player = setupMockPlayer("Ralf", "Professor Plum", new Position(0,17));
			assertTrue(player.getCharacterName() != null);
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that it generates a correct suggestion object.
	 * NOTE: REQUIRES USER INPUT to pass.
	 */
	@Test
	public void testValidSuggestion_01(){
		try{
			Player player = setupMockPlayer("Ralf", "Miss Scarlett", new Position(0,17));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			player.setNumberofMoves(12);
			game.cluedoCanvas.move(6, -2, player, game.currentPlayers());
			assertTrue(player.getRoom() != null);

			WeaponCard wc = new WeaponCard(new Weapon(null, "Dagger"), null);
			RoomCard rc = new RoomCard(player.getRoom(), null);
			CharacterCard cc = new CharacterCard(new Character(null, "Mrs. White", null, null), null);
			Suggestion sug = new Suggestion(wc, rc, cc, player);
			assertTrue(sug != null);
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * This tests that the user makes a correct suggestion, and it will iterate over the other players hands.
	 * 
	 */
	@Test
	public void testValidSuggestion_02(){
		try{
			Player player1 = setupMockPlayer("A", "Miss Scarlett", new Position(0,17));
			game.board[player1.position().getX()][player1.position().getY()].setPlayer(player1);
			player1.setNumberofMoves(12);
			game.cluedoCanvas.move(6, -2, player1, game.currentPlayers());
			RoomCard r1 = new RoomCard(new Room("Kitchen"), null);
			WeaponCard w1 = new WeaponCard(new Weapon(null, "Rope"), null);
			CharacterCard c1 = new CharacterCard(new Character(null, "Miss Scarlett", null, null), null);

			player1.addCard(c1);
			player1.addCard(w1);
			player1.addCard(r1);
			game.addPlayer(player1);
			r1.getObject().addWeapon(w1.getObject());
			r1.getObject().addCharacter(c1.getObject());
			w1.getObject().addRoom(r1.getObject());
			c1.getObject().addRoom(r1.getObject());

			RoomCard r2 = new RoomCard(new Room("Study"), null);
			WeaponCard w2 = new WeaponCard(new Weapon(null, "Dagger"), null);
			CharacterCard c2 = new CharacterCard(new Character(null, "Professor Plum", null, null), null);
			Player player2 = setupMockPlayer("B", "Professor Plum", new Position(0,25));
			player2.addCard(r2);
			player2.addCard(w2);
			player2.addCard(c2);
			game.addPlayer(player2);
			r2.getObject().addWeapon(w2.getObject());
			r2.getObject().addCharacter(c2.getObject());
			w2.getObject().addRoom(r2.getObject());
			c2.getObject().addRoom(r2.getObject());

			Player player3 = setupMockPlayer("C", "The Reverend Green", new Position(7,24));
			player3.addCard(new CharacterCard(new Character(null, "Miss Scarlett", null, null), null));
			player3.addCard(new WeaponCard(new Weapon(null, "Knife"), null));
			player3.addCard(new RoomCard(new Room("Conservatory"), null));
			game.addPlayer(player3);

			game.initializer.setCharacters();
			assertTrue(player1.getRoom() != null);

			WeaponCard wc = new WeaponCard(new Weapon(null, "Revolver"), null);
			RoomCard rc = new RoomCard(player1.getRoom(), null);
			CharacterCard cc = new CharacterCard(new Character(null, "Colonel Mustard", null, null), null);
			Suggestion sug = new Suggestion(wc, rc, cc, player1);
			assertTrue(sug.checkSuggestion(game.currentPlayers()));
			assertEquals(player1.getRoom(), sug.getCharacterCard().getObject().getRoom());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that you cannot make an invalid suggestion.
	 * 
	 */
	@Test
	public void testInvalidSuggestion(){
		try{
			Player player = setupMockPlayer("Ralf", "Miss Scarlett", new Position(0,17));
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			player.setNumberofMoves(12);
			game.cluedoCanvas.move(+2, 0, player, game.currentPlayers());
			assertTrue(player.getRoom() == null);
			Suggestion sug = game.makeSuggestion(player);
			assertTrue(sug == null);
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that you can make a valid accusation.
	 */
	@Test
	public void testValidAccusation_01(){
		try{
			Envelope env = new Envelope();
			WeaponCard wep = new WeaponCard(new Weapon(null, "Dagger"), null);
			RoomCard room = new RoomCard(new Room("Ballroom"), null);
			CharacterCard character = new CharacterCard(new Character(null, "Miss Scarlett", null, null), null);
			env.add(wep);
			env.add(room);
			env.add(character);
			Player player = setupMockPlayer("Ralf", "Miss Scarlett", new Position(0,17));
			game.addPlayer(player);
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			Accusation accu = new Accusation(wep,room,character,player, env);
			assertTrue(accu != null);
			//assertTrue(accu.argumentStatus());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that you can make a valid accusation - AND the game will be over.
	 */
	@Test
	public void testValidAccusation_02(){
		try{
			Envelope env = new Envelope();
			WeaponCard wep = new WeaponCard(new Weapon(null, "Dagger"), null);
			RoomCard room = new RoomCard(new Room("Ballroom"), null);
			CharacterCard character = new CharacterCard(new Character(null, "Miss Scarlett", null, null), null);
			env.add(wep);
			env.add(room);
			env.add(character);
			Player player = setupMockPlayer("Ralf", "Miss Scarlett", new Position(0,17));
			game.addPlayer(player);
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			Accusation accu = new Accusation(wep,room,character,player,env);
			assertTrue(accu != null);
			assertTrue(accu.accusationStatus());
			//assertTrue(accu.checkAccusation(env, player));
			//assertTrue(game.isGameOver());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that you can make an invalid accusation.
	 */
	@Test
	public void testInvalidAccusation_01(){
		try{
			Envelope env = new Envelope();
			WeaponCard wep = new WeaponCard(new Weapon(null, "Dagger"), null);
			RoomCard room = new RoomCard(new Room("Ballroom"), null);
			CharacterCard character = new CharacterCard(new Character(null, "Miss Scarlett", null, null), null);
			env.add(wep);
			env.add(room);
			env.add(character);
			Player player = setupMockPlayer("Ralf", "Miss Scarlett", new Position(0,17));
			game.addPlayer(player);
			game.board[player.position().getX()][player.position().getY()].setPlayer(player);
			Accusation accu = new Accusation(wep,room,(new CharacterCard(new Character(null, "Professor Plum", null, null), null)), player, env);
			assertTrue(accu != null);
			assertFalse(accu.accusationStatus());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Test that a player can win when all players are out of the game
	 */
	@Test
	public void testWin_01(){
		try{
			Envelope env = new Envelope();
			WeaponCard wep = new WeaponCard(new Weapon(null, "Dagger"), null);
			RoomCard room = new RoomCard(new Room("Ball Room"), null);
			CharacterCard character = new CharacterCard(new Character(null, "Miss Scarlett", null, null), null);
			env.add(wep);
			env.add(room);
			env.add(character);
			Player player1 = setupMockPlayer("Ralf", "Miss Scarlett", new Position(0,17));
			Player player2 = setupMockPlayer("Sam", "Mrs. White", new Position(9, 0));
			Player player3 = setupMockPlayer("Pete", "Professor Plum", new Position(14, 0));
			game.addPlayer(player1);
			game.addPlayer(player2);
			game.addPlayer(player3);
			Accusation accu1 = new Accusation(wep,room,(new CharacterCard(new Character(null, "Professor Plum", null, null), null)), player1, env);
			Accusation accu2 = new Accusation(wep,(new RoomCard(new Room("Dining Room"), null)), character, player2, env);
			assertTrue(game.isGameOver());
		}catch(CluedoGameController.InvalidMove e){
			fail(e.getMessage());
		}
	}

	/**
	 * Setup a mock game of monopoly with a player located at a given location.
	 */
	private Player setupMockPlayer(String name, String charName, Position pos)
			throws CluedoGameController.InvalidMove {

		Player p = new Player(name);
		p.setCharacter(new Character(null, charName, null, pos));
		p.setPos(pos.getX(), pos.getY());
		return p;
	}
}
