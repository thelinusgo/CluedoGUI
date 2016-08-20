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
	
	public Player currentPlayer(){
		return this.currentPlayer;
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
		//System.out.println(currentRoll);
		return roll;
	}
	
	/**
	 * Repaints the Canvas and the JFrame whenever a move is made. This ensures that the canvas is updated appropriately whenever it is cleaned.
	 */
	private void cleanCanvas(){
		cluedoJFrame.repaint();
		cluedoCanvas.repaint();
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
		System.out.print("hello\n");
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
	 * input. This then calls askCharacters .
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
				JOptionPane.showMessageDialog(null, "You must enter Integer values only.", "GAME ERROR", JOptionPane.ERROR_MESSAGE);
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
	 * @throws InvalidMove 
	 */
	public Suggestion makeSuggestion(Player p){
		if(p == null){
			JOptionPane.showMessageDialog(null, "You must have a current player to make an accusation", "GAME ERROR" ,JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if(!p.isInRoom()){
			JOptionPane.showMessageDialog(null, "You must be in a room to make a suggestion", "GAME WARNING", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		
		JOptionPane.showMessageDialog(null, "What cards do you want to nominate?", "ACCUSATION", JOptionPane.INFORMATION_MESSAGE);
		
		Set<WeaponCard> weapons = new HashSet<>();
		Set<CharacterCard> suspects = new HashSet<>();
		
		for(Card c : p.getCards()){
			if(c instanceof WeaponCard){
				weapons.add((WeaponCard)c);
			}else if(c instanceof CharacterCard){
				suspects.add((CharacterCard)c);
			}
		}
		
		WeaponCard weapon = this.askWeapons(weapons, p);
		CharacterCard character = this.askSuspects(suspects, p);
		
		return new Suggestion(weapon, new RoomCard(p.getRoom()), character, p);
	}

	/**
	 * This makes an accusation. TODO: need to fix bug where it displays duplicate values and the accusation status is incorrect.
	 * @param p
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Accusation makeAccusation(Player p) throws CluedoGame.InvalidMove{
		if(p == null){
			JOptionPane.showMessageDialog(null, "You must have a current player to make an accusation", "GAME ERROR" ,JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		JOptionPane.showMessageDialog(null, "What cards do you want to nominate?", "ACCUSATION", JOptionPane.INFORMATION_MESSAGE);
		
		final Set<WeaponCard> weapons = new HashSet<>(Initializer.getWeaponCards());
		final Set<RoomCard> rooms = new HashSet<>(Initializer.getRoomCards());
		final Set<CharacterCard> suspects = new HashSet<>(Initializer.getCharacterCards());
		WeaponCard weapon = this.askWeapons(weapons, p);
		RoomCard room = this.askRooms(rooms, p);
		CharacterCard character = this.askSuspects(suspects, p);
		
		Card[] solutionEnvelope = Initializer.getEnvelope().getCards();
		System.out.println("Envelope:");
		for(Card c : solutionEnvelope){
			System.out.println(c.toString());
		}
		
		Accusation ac = new Accusation(weapon, room, character, p, Initializer.getEnvelope());
		if(ac.accusationStatus()){
			return ac;
		}
		p.setOut(true);
		return null;
	}
	

	
	
	/**
	 * This code allows us to select from a collection of weapons.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private WeaponCard askWeapons(Collection <WeaponCard> weaponCol, Player p) {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Please make a selection:"));
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(WeaponCard wep : weaponCol){
			model.addElement(wep.toString());
		}
		JComboBox comboBox = new JComboBox(model);
		panel.add(comboBox);

		int result = JOptionPane.showConfirmDialog(null, panel, "	1)	Choose a Weapon", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (result) {
		case JOptionPane.OK_OPTION:
			System.out.println("You selected " + comboBox.getSelectedItem());
			return new WeaponCard(new Weapon((String)comboBox.getSelectedItem()));
		default:
			return null;
		}

	} 
	
	/**
	 * This code allows us to select from a collection of Rooms.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private RoomCard askRooms(Collection<RoomCard> roomCol, Player p){
		JPanel panel = new JPanel();
		panel.add(new JLabel("Please make a selection:"));
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(RoomCard roomCard : roomCol){
		model.addElement(roomCard.toString());
		}
		JComboBox comboBox = new JComboBox(model);
		panel.add(comboBox);
		int result = JOptionPane.showConfirmDialog(null, panel, "	2)	Choose a Room", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (result) {
		case JOptionPane.OK_OPTION:
			System.out.println("You selected " + comboBox.getSelectedItem());
			return new RoomCard(new Room((String)comboBox.getSelectedItem()));
		default:
			return null;
		}
	}
	
	/**
	 * This code allows us to select from a collection of Suspects.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private CharacterCard askSuspects(Collection<CharacterCard> charCol, Player p){
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Please make a selection:"));
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(CharacterCard cc: charCol){
			model.addElement(cc.toString());
		}
		JComboBox comboBox = new JComboBox(model);
		panel.add(comboBox);
		int result = JOptionPane.showConfirmDialog(null, panel, "	3)	Choose a suspect.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (result) {
		case JOptionPane.OK_OPTION:
			System.out.println("You selected " + comboBox.getSelectedItem());
			for(CharacterCard cc : charCol){
				if(cc.toString().equals(comboBox.getSelectedItem())){
					System.out.println("they do match");
					return cc;
				}
			}

		default:
			return null;
		}
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
	 * Indicates an attempt to make an invalid move. Whenever an invalid move is thrown, it also brings up a Warning Dialog.
	 */
	public static class InvalidMove extends Exception {
		public InvalidMove(String msg) {
			super(msg);
			JOptionPane.showMessageDialog(null, msg, "GAME WARNING" ,JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.currentPlayer == null){
			//handle when the current player is null.
			JOptionPane.showMessageDialog(null, "You do not have a current active player.", "Move ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
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
										this.cleanCanvas();
									}else{
										if(t instanceof DoorTile){
											cluedoCanvas.exitRoom(currentPlayer, currentPlayers);
											this.cleanCanvas();
										}else if(t instanceof StairsTile){
											cluedoCanvas.moveToRoom(currentPlayer, currentPlayer.getRoom().getOtherRoom());
											this.cleanCanvas();
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
		this.cleanCanvas();
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