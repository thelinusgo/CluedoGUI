package cluedo.main;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cluedo.arguments.Accusation;
import cluedo.arguments.Suggestion;
import cluedo.assets.*;
import cluedo.assets.Character;
import cluedo.assets.tiles.DoorTile;
import cluedo.assets.tiles.StairsTile;
import cluedo.assets.tiles.Tile;
import cluedo.cards.Card;
import cluedo.cards.CharacterCard;
import cluedo.cards.RoomCard;
import cluedo.cards.WeaponCard;
import cluedo.gui.CluedoCanvas;
import cluedo.gui.CluedoJFrame;
/**
 * Creates a new instance of a Board, and runs the textClient.
 * @author Casey & Linus
 *
 */
public class CluedoGame implements MouseMotionListener, MouseListener{

	/**
	 * This boolean represents if the arguments button has been pressed.
	 */
	public boolean argsButtonPressed = false;

	/**
	 * This boolean respresents if suggestion radio box was ticked.
	 */
	public boolean isSuggestionSelection = false;

	/**
	 * This boolean respresents if move button is pressed.
	 */
	public boolean isMoveSelection = false;

	/**
	 * Initializes all of the data
	 */
	public static Initializer initializer;

	/**
	 * An instance of the CluedoCanvas.
	 */
	public static CluedoCanvas cluedoCanvas;

	/**
	 * An instance of the board
	 */
	public Tile[][] board;

	/**
	 * stores the amount of players.
	 */
	private int numPlayers = 0; 
	private static List<Player> currentPlayers; //a list of the current players.

	/**
	 * Flag for stating whether asking was a success or not.
	 */
	public boolean askSuccess; 

	/**
	 * Field that determines the choice of the player.
	 */
	private String option;

	/**
	 * Check if dice has been rolled this round.
	 */
	public boolean rolled = false;

	/**
	 * If a player has asked or not.
	 */
	private boolean hasAsked = false;

	/**
	 * List of sequencial numbers. This allows a random number to be chosen.
	 */
	private List<Integer> singleDie = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6));

	/**
	 * This rolls the dice, obtains the first element in the array.
	 */
	private int currentRoll = 0;

	/**
	 * The current player of the round.
	 */
	private Player currentPlayer; //the current player of the round.

	/** If player has made a move*/
	public boolean moveMade = false;

	/**
	 * Checks if any button has been pressed
	 */
	public boolean btnPressed = false;

	/**
	 * Stores the cards of players who have been kicked out of the game because they made an incorrect accusation.
	 */
	private List<List<Card>> showCards = new ArrayList<>();

	/**Stores player's previous option*/
	private String prevOption = "";

	/**
	 * Current accusation object.
	 */
	private Accusation accusation = null;

	/**
	 * Stores the index of currentPlayer
	 */
	public int index = 0;

	/**
	 * Store JFrame
	 */
	private CluedoJFrame cluedoJFrame;

	/**
	 * Construct a new instance of the cluedo game. Initialize the fields.
	 */
	public CluedoGame(CluedoJFrame cluedoJF){
		currentPlayers = new ArrayList<Player>();
		initializer = new Initializer();
		cluedoCanvas = new CluedoCanvas();
		board = cluedoCanvas.board;
		cluedoJFrame = cluedoJF;
	}

	/**
	 * This sets the option field to a value.
	 * @param val
	 */
	public void setOption(String val){
		this.option = val;
	}

	/**
	 * Gets the list of current Players
	 * @return
	 */
	public static List<Player> currentPlayers(){
		return currentPlayers;
	}
	/**
	 * Returns the number of players that are playing.
	 * @return
	 */
	public int numPlayers(){
		return this.numPlayers;
	}


	/**
	 * Add new player to current players.
	 * @param player
	 */
	public void addPlayer(Player player){
		currentPlayers.add(player);
	}

	/**
	 * Returns a random value between 1-6
	 * @return
	 */
	public int diceRoll(){
		Collections.shuffle(singleDie);
		int roll = singleDie.get(0);
		currentRoll += roll;
		System.out.println(currentRoll);
		return roll;
	}

	/**
	 * Initialize the current players - give them random cards
	 */
	public void initialSetup(){
		if(currentPlayers.size() == 0){
			System.out.println("current players list is empty");
			return; //silently fail, do nothing if there are no currentPlayers.
		}
		initializer.distributeCards(currentPlayers); //distributes the cards out to the players.
		cluedoCanvas.setPlayerPosition(currentPlayers);
		currentPlayer = currentPlayers.get(index);
		cluedoJFrame.currentPlayerText.setText(currentPlayer.getName() + "\r\n");
		System.out.print("hello");
		cluedoCanvas.paint(cluedoCanvas.getGraphics());
		System.out.println("Finished drawing characters");
	}


	/**
	 * Returns all of the currentPlayers, for use in a JTextBox or a JList.
	 */
	public String getPlayerAndCharacterText(){
		StringBuilder sb = new StringBuilder();
		int index = 1;
		for(Player player : currentPlayers){
			String value = index + ": " + player.getName() + "\n->"+ player.getCharacter().name() + "\n";
			sb.append(value);
			++index;
		}
		return sb.toString();
	}

	/*START OF SPECIAL METHODS*/
	///////////////////////////
	/**
	 * Determines whether the user has inputed an integer.
	 * 
	 * @param input
	 * @return
	 */
	private boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Asks the players for their name, and their preferred character.. Brings up a JOptionPane to request user
	 * input.
	 * 
	 */
	public String askPlayers() {
		String numPlayers = "";
		boolean correctInput = false, isInteger = false;

		while (!correctInput && !isInteger) {
			numPlayers = (String) (JOptionPane.showInputDialog(null, "How many players would you like?"));
			if (isInteger(numPlayers)) {
				if (Integer.parseInt(numPlayers) > 6 || Integer.parseInt(numPlayers) < 3) {
					JOptionPane.showMessageDialog(null, "Number of players has to be in between 3-6 (a number)",
							"Incorrect Input", JOptionPane.ERROR_MESSAGE);
				} else {
					correctInput = true;
					isInteger = true;
				}
			} else {
				JOptionPane.showMessageDialog(null, "You must enter Integer values only.");
			}
		}
		return askCharacters(Integer.parseInt(numPlayers));
	}

	/**
	 * Asks for the number of Players.
	 * @param numPlayers
	 */
	private String askCharacters(int numPlayers){
		String singleName = "";
		Player playerInProgress = null;
		for (int i = 0; i != (numPlayers); ++i) {
			singleName = (JOptionPane.showInputDialog(null, "Please enter Player " + (i + 1) + "'s name."));
			playerInProgress = new Player(singleName);		
			grabCharacters(initializer.getCharacters(), playerInProgress);
			addPlayer(playerInProgress);
		}
		this.askSuccess = true;
		this.initialSetup();
		return this.getPlayerAndCharacterText();
	}

	/**
	 * This code allows us to select from a list of characters, given a player.
	 */
	public void grabCharacters(Character[] characters, Player p) {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Please make a selection:"));
		@SuppressWarnings("rawtypes")
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(Character c : characters){
			if(c.player() == null){
				model.addElement(c.name());
			}
		}
		JComboBox comboBox = new JComboBox(model);
		panel.add(comboBox);

		int result = JOptionPane.showConfirmDialog(null, panel, "Choose a Character", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (result) {
		case JOptionPane.OK_OPTION:
			System.out.println("You selected " + comboBox.getSelectedItem());
			for(Character c : characters){
				if(c.name().equals(comboBox.getSelectedItem())){
					p.setCharacter(c);
					c.setPlayer(p);
				}else{
					continue;
				}
			}
		}
	} 

	//////////////////////////
	/*END OF SPECIAL METHODS*/


	/**
	 * Runs the game - only if asking players was successful.
	 * @throws InvalidMove 
	 */
	/*public void runGame() throws InvalidMove{
		if(askSuccess){
			while(!isGameOver()){
				for(int i = 0; i < currentPlayers.size(); i++){
					moveMade = false;
					currentPlayer = currentPlayers.get(i);
					cluedoJFrame.currentPlayerText.setText(currentPlayer.getName() + "\r\n");
					if(!currentPlayer.out()){
						while(!moveMade){
							if(btnPressed){
								if(!this.isMoveSelection){
									doOption(option, currentPlayer);
								}
								if(moveMade){
									//board.drawBoard();
								}
							}
							if(gameStarted){
								break;
							}
						}
					}
					if(isGameOver()){
						System.out.println("Congratulations!! Player " + currentPlayer.getName() + " won!");
						System.out.println("Game is over.");
						printEnvelope();
						return;
					}
				}
			}
		}
	}*/

	/**
	 * This method performs a given option, based off the users input.
	 * m is move, c is current cards, d is show previous player cards, a is accusation and s is for suggestion.
	 * @param option
	 * @param currentPlayer
	 * @throws InvalidMove
	 */
	public void doOption() throws InvalidMove{
		switch(option){
		case "c":
			System.out.println("Your current cards.");
			for(Card c : currentPlayer.getCards()){
				System.out.println(c.toString());
			}
			break;
		case "d":
			if(!showCards.isEmpty()){
				System.out.println("Show all previous players' cards");
				for(List<Card> lc : showCards){
					for(Card c : lc){
						System.out.println(c.toString());
					}
				}
			}else{
				System.out.println("No cards to show.");
			}
			break;
		case "a":
			System.out.println("Player " + currentPlayer.getName() + " wishes to make an accusation.");
			accusation = makeAccusation(currentPlayer);
			if(accusation == null){
				System.out.println("The accusation pieces did not match."); 
				System.out.println("You can no longer make a move.");
				showCards.add(currentPlayer.getCards());
			}
			moveMade = true;
			break;
		case "s":

			System.out.println("Player " + currentPlayer.getName() + " wishes to make an suggestion.");
			Suggestion sugg = makeSuggestion(currentPlayer);

			if(sugg == null){
				break;
			}

			if(sugg.checkSuggestion(currentPlayers)){
				System.out.println("At least one extra card was found");
			}else{
				System.out.println("no extra cards were found");
			}
			prevOption = "s";
			break;
		}
	}

	/**
	 * This makes an accusation.
	 * @param p
	 * @return
	 */
	public Suggestion makeSuggestion(Player p){
		/*if(!p.isInRoom()){
			System.err.println("ERROR: Sorry, you must be in a room to make a suggestion.");
			return null;
		}
		System.out.println("-----------SUGGESTION!-------------");
		System.out.println("What cards do you want to nominate?");
		System.out.println("----------------------------------");
		System.out.println("AVAILABLE CARDS:");
		//the objects for creating a suggestion.
		//TODO: needs to be based on room he's in..
		WeaponCard weapon = null;
		CharacterCard character = null;
		RoomCard room = new RoomCard(p.getRoom());
		int indexChoice;

		List<WeaponCard> weapons = Initializer.getWeaponCards();
		List<CharacterCard> suspects = Initializer.getCharacterCards();

		System.out.println("Instructions: Enter index of the item you want to nominate.");
		for(int i = 0; i < 2;){
			if(i == 0){
				System.out.println("Step 1) Choose from available weapons: ");
				int index = 0;
				for(WeaponCard ww : weapons){
					System.out.println(index + " "  + ww.toString());				
					index++;
				}
				indexChoice = TextClient.askIndex(weapons);
				weapon = (WeaponCard) weapons.get(indexChoice);
				i++;
			}
			else if(i == 1){
				System.out.println("Step 2) Choose from available Suspects: ");
				int index = 0;
				for(CharacterCard cc : suspects){
					System.out.println(index + " "  + cc.toString());
					index++;
				}
				indexChoice = TextClient.askIndex(weapons);
				character = (CharacterCard) suspects.get(indexChoice);
				i++;
			}
		}
		System.out.println("----------------------------------");
		System.out.println(" CONFIRMED Suggestion Pieces:     ");
		System.out.println(" weapon: " + weapon);
		System.out.println(" character: " + character);
		System.out.println(" room: " + room);
		System.out.println("----------------------------------");
		return new Suggestion(weapon, room, character, p);*/
		return null;
	}

	/**
	 * This makes an accusation.
	 * @param p
	 * @return
	 */
	public Accusation makeAccusation(Player p){
		/*System.out.println("-----------ACCUSATION!-------------");
		System.out.println("What cards do you want to nominate?");
		System.out.println("----------------------------------");
		System.out.println("AVAILABLE CARDS:");

		//the objects for creating a suggestion.
		WeaponCard weapon = null;
		CharacterCard character = null;
		RoomCard room = null;
		int indexChoice = -1;

		//sublists containing cards of a certain category.
		List<WeaponCard> weapons = Initializer.getWeaponCards();
		List<RoomCard> rooms = Initializer.getRoomCards();
		List<CharacterCard> suspects = Initializer.getCharacterCards();
		System.out.println("Instructions: Enter index of the item you want to nominate.");
		for(int i = 0; i < 3;){
			if(i == 0){
				System.out.println("Step 1) Choose from available weapons: ");
				int index = 0;
				for(WeaponCard ww : weapons){
					System.out.println(index + " "  + ww.toString());				
					index++;
				}
				indexChoice = TextClient.askIndex(weapons);
				weapon = (WeaponCard) weapons.get(indexChoice);
				i++;
			}else if(i == 1){
				System.out.println("Step 2) Choose from available Rooms: ");
				int index = 0;
				for(RoomCard rr : rooms){
					System.out.println(index + " "  + rr.toString());
					index++;
				}
				indexChoice = TextClient.askIndex(rooms);
				room = (RoomCard) rooms.get(indexChoice);
				i++;
			}else{
				System.out.println("Step 3) Choose from available Suspects: ");
				int index = 0;
				for(CharacterCard cc : suspects){
					System.out.println(index + " "  + cc.toString());
					index++;
				}
				indexChoice = TextClient.askIndex(suspects);
				character = (CharacterCard) suspects.get(indexChoice);
				i++;
			}
		}

		Card[] env = Initializer.getEnvelope().getCards();

		int count = 0;
		for(Card card : env){
			if(card instanceof WeaponCard){
				if(card.equals(weapon)){
					count++;
				}
			}else if(card instanceof CharacterCard){
				if(card.equals(character)){
					count++;
				}
			}else if(card instanceof RoomCard){
				if(card.equals(room)){
					count++;
				}
			}
		}
		System.out.println("----------------------------------");
		System.out.println(" CONFIRMED Accusation Pieces:     ");
		System.out.println(" weapon: " + weapon);
		System.out.println(" character: " + character);
		System.out.println(" room: " + room);
		System.out.println("----------------------------------");
		Accusation ac = new Accusation(weapon, room, character, p,  Initializer.getEnvelope());
		if(ac.accusationStatus()){
			return ac;
		}*/
		return null;
	}

	/**
	 * This checks if the game over. Returns true if so, returns false otherwise.
	 * @return
	 */
	public boolean isGameOver(){
		if(accusation != null && accusation.accusationStatus()){
			return true;
		}else{
			int count = currentPlayers.size();
			for(Player p : currentPlayers){
				if(p.out()){
					count--;
				}
			}
			if(count == 1){
				return true;
			}
		}
		return false;
	}

	/**
	 * This prints out the contents of the envelope.
	 */
	public void printEnvelope(){
		System.out.println("The envelope consisted of these cards: ");
		for(Card c : initializer.getEnvelope().getCards()){
			System.out.println(c.toString());
		}
	}

	/**
	 * Indicates an attempt to make an invalid move.
	 */
	public static class InvalidMove extends Exception {
		public InvalidMove(String msg) {
			super(msg);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentPlayer.coordinatesTaken().clear();
		if(this.isMoveSelection && rolled){
			currentPlayer.setNumberofMoves(currentRoll);
			if(currentPlayer.numberofMoves() > 0){
				try {
					if(currentPlayer.numberofMoves() > 0){
						for(int x = 0; x < board.length; x++){
							for(int y = 0; y < board.length; y++){
								Tile t = board[x][y];
								if(t.contains(e.getPoint())){
									if(!currentPlayer.isInRoom()){
										cluedoCanvas.move(t.x-currentPlayer.position().getX(), t.y-currentPlayer.position().getY(), currentPlayer, currentPlayers);
									}else{
										if(t instanceof DoorTile){
											cluedoCanvas.exitRoom(currentPlayer, currentPlayers);
										}else if(t instanceof StairsTile){
											cluedoCanvas.moveToRoom(currentPlayer, currentPlayer.getRoom().getOtherRoom());
										}
									}
									return;
								}
							}
						}
					}
				}catch (InvalidMove e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	public void reset() {
		currentRoll = 0;
		this.argsButtonPressed = false;
		this.isMoveSelection = false;
		this.isSuggestionSelection = false;
		this.rolled = false;
		moveMade = false;
		btnPressed = false;
		accusation = null;
		option = "";
		prevOption = "";
		if(!currentPlayers.isEmpty()){
			index++;
			if(index >= currentPlayers().size()){
				index = 0;
			}
			currentPlayer = currentPlayers.get(index);
			cluedoJFrame.currentPlayerText.setText(currentPlayer.getName() + "\r\n");
		}
	}

	public void resetAll() {
		currentPlayers = new ArrayList<Player>();
		initializer = new Initializer();
		numPlayers = 0; 
		askSuccess = false;
		hasAsked = false;
		currentPlayer = null; //the current player of the round.
		showCards = new ArrayList<>();
		index = 0;
		this.reset();
	}
}