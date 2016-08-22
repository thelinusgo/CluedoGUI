package cluedo.main;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
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
import cluedo.gui.CardsCanvas;
import cluedo.gui.CluedoCanvas;
import cluedo.gui.Sound;
import tests.CluedoTests;
/**
 * The heart of the Cluedo Game.
 * @author Casey & Linus
 *
 */
public class CluedoGameController implements MouseListener{
	//Setup Card Images
	/*Fields to represent the room Cards. */
	public static BufferedImage kitchenCard;
	public static BufferedImage ballCard;
	public static BufferedImage conservatoryCard;
	public static BufferedImage billiardCard;
	public static BufferedImage libraryCard;
	public static BufferedImage studyCard;
	public static BufferedImage hallCard;
	public static BufferedImage loungeCard;
	public static BufferedImage diningCard;

	/*Fields to represent the weapons.*/
	public static BufferedImage candleCard;
	public static BufferedImage daggerCard;
	public static BufferedImage leadPipeCard;
	public static BufferedImage revolverCard;
	public static BufferedImage ropeCard;
	public static BufferedImage spannerCard;

	/*Fields that represent the Characters */
	public static BufferedImage missScarlett;
	public static BufferedImage colonelMustard;
	public static BufferedImage mrsWhite;
	public static BufferedImage theRevGreen;
	public static BufferedImage mrsPeacock;
	public static BufferedImage profPlum;

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
	 * An instance of the CardsCanvas.
	 */
	public CardsCanvas cardsCanvas;

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
	public int currentRoll = 0;

	/**
	 * The current player of the round.
	 */
	private static Player currentPlayer; //the current player of the round.

	/** If player has made a move*/
	public boolean moveMade = false;

	/**
	 * Checks if any button has been pressed
	 */
	public boolean btnPressed = false;

	/**
	 * Stores the cards of players who have been kicked out of the game because they made an incorrect accusation.
	 */
	private static List<Card> showCards = new ArrayList<>();

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
	private CluedoView cluedoJFrame;
	
	/**
	 * For playing music.
	 */
	private static Sound sound;

	/**
	 * Construct a new instance of the cluedo game. Initialize the fields.
	 */
	
	
	public static Player getTheCurrentPlayer(){
		return currentPlayer;
	}
	
	
	
	
	public CluedoGameController(CluedoView cluedoJF){
		try {
			//load in the weapon images.
			candleCard = ImageIO.read(new File("CandleStick.JPG"));
			daggerCard = ImageIO.read(new File("Knife.JPG"));
			leadPipeCard = ImageIO.read(new File("LeadPipe.JPG"));
			revolverCard = ImageIO.read(new File("Revolver.JPG"));
			ropeCard = ImageIO.read(new File("Rope.JPG"));
			spannerCard = ImageIO.read(new File("Wrench.JPG"));
			//the rooms...
			kitchenCard = ImageIO.read(new File("Kitchen.JPG"));
			ballCard = ImageIO.read(new File("Ballroom.JPG"));
			libraryCard = ImageIO.read(new File("Library.JPG"));
			studyCard = ImageIO.read(new File("Study.JPG"));
			hallCard = ImageIO.read(new File("Hall.JPG"));
			loungeCard = ImageIO.read(new File("Lounge.JPG"));
			diningCard = ImageIO.read(new File("DiningRoom.JPG"));
			conservatoryCard = ImageIO.read(new File("Conservatory.JPG"));
			billiardCard = ImageIO.read(new File("BilliardRoom.JPG"));
			//the character cards.
			missScarlett = ImageIO.read(new File("MissScarlet.JPG"));
			colonelMustard = ImageIO.read(new File("ColonelMustard.JPG"));
			mrsWhite = ImageIO.read(new File("MrsWhite.JPG"));
			theRevGreen = ImageIO.read(new File("MrGreen.JPG"));
			mrsPeacock = ImageIO.read(new File("MrsPeacock.JPG"));
			profPlum = ImageIO.read(new File("ProfessorPlum.JPG"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentPlayers = new ArrayList<Player>();
		initializer = new Initializer();
		cluedoCanvas = new CluedoCanvas();
		board = cluedoCanvas.board();
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
		return roll;
	}

	/**
	 * Repaints all canvases whenever this is called. This ensures that the canvas is updated appropriately whenever it is cleaned.
	 */
	public void cleanCanvas(){
		cluedoJFrame.repaint();
		cluedoCanvas.repaint();
	}

	/**
	 * Initialize the current players - give them random cards
	 */
	public void initialSetup(){
		if(currentPlayers.size() == 0){
			return; //silently fail, do nothing if there are no currentPlayers.
		}
		initializer.distributeCards(currentPlayers); //distributes the cards out to the players.
		cluedoCanvas.setPlayerPosition(currentPlayers);
		initializer.setCharacters();
		currentPlayer = currentPlayers.get(index);
		cluedoJFrame.currentPlayerText.setText(currentPlayer.getName() + "\r\n");
		cluedoJFrame.setPlayerColor(currentPlayer);
		cluedoCanvas.paint(cluedoCanvas.getGraphics());
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
			numPlayers = (String) (JOptionPane.showInputDialog(null, "How many players would you like?", JOptionPane.OK_OPTION));
			
		
			
			
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

		int result = JOptionPane.showConfirmDialog(null, panel, "Choose a Character", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (result) {
		case JOptionPane.OK_OPTION:
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
	 * This makes an suggestion, by prompting the user for information..
	 * @param p
	 * @return
	 * @throws InvalidMove 
	 */
	public static Suggestion makeSuggestion(Player p){
		if(p == null){
			JOptionPane.showMessageDialog(null, "You must have a current player to make an accusation", "GAME ERROR" ,JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if(!p.isInRoom()){
			JOptionPane.showMessageDialog(null, "You must be in a room to make a suggestion", "GAME WARNING", JOptionPane.WARNING_MESSAGE);
			return null;
		}

		JOptionPane.showMessageDialog(null, "What cards do you want to nominate?", "SUGGESTION", JOptionPane.INFORMATION_MESSAGE);

		WeaponCard weapon = askWeapons(Initializer.getWeaponCards(), p);
		CharacterCard character = askSuspects(Initializer.getCharacterCards(), p);
		RoomCard room = findRoom(p.getRoom());
		
		Suggestion sugg = new Suggestion(weapon, room, character, p);
		
		if(sugg.checkSuggestion(currentPlayers())){
			JOptionPane.showMessageDialog(null, "At least one extra card was found", "NOTICE", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, "No extra cards were found.", "WARNING", JOptionPane.WARNING_MESSAGE);
		}
		return sugg;
		
	}

	/**
	 * Finds the roomCard with given room object.
	 * @param r
	 * @return
	 */
	private static RoomCard findRoom(Room r){
		for(RoomCard rc : initializer.getRoomCards()){
			if(rc.getObject().equals(r)){
				return rc;
			}
		}
		return null;
	}

	/**
	 * This makes an accusation. TODO: need to fix bug where it displays duplicate values and the accusation status is incorrect.
	 * @param p
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Accusation makeAccusation(Player p) throws CluedoGameController.InvalidMove{
		if(p == null){
			JOptionPane.showMessageDialog(null, "You must have a current player to make an accusation", "GAME ERROR" ,JOptionPane.ERROR_MESSAGE);
			return null;
		}

		JOptionPane.showMessageDialog(null, "What cards do you want to nominate?", "ACCUSATION", JOptionPane.INFORMATION_MESSAGE);

		WeaponCard weapon = askWeapons(Initializer.getWeaponCards(), p);
		RoomCard room = askRooms(Initializer.getRoomCards(), p);
		CharacterCard character = askSuspects(Initializer.getCharacterCards(), p);

		Card[] solutionEnvelope = Initializer.getEnvelope().getCards();
		
		Accusation ac = new Accusation(weapon, room, character, p, Initializer.getEnvelope());
		if(ac.accusationStatus()){
			return ac;
		}
		p.setOut(true);
		for(Card c : p.getCards()){
			getShowCards().add(c);
		}
		return null;
	}




	/**
	 * This code allows us to select from a collection of weapons.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static WeaponCard askWeapons(WeaponCard[] weaponCol, Player p) {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Please make a selection:"));
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(WeaponCard wep : weaponCol){
			model.addElement(wep.getObject().weaponName());
		}
		JComboBox comboBox = new JComboBox(model);
		panel.add(comboBox);

		int result = JOptionPane.showConfirmDialog(null, panel, "	1)	Choose a Weapon", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (result) {
		case JOptionPane.OK_OPTION:
			return findWeaponCard(weaponCol, ((String)comboBox.getSelectedItem()));
		default:
			return null;
		}
	}

	/**
	 * Finds the card that the player selected given the name of the card.
	 * @param weaponCol
	 * @param name - name of card
	 * @return
	 */
	private static WeaponCard findWeaponCard(WeaponCard[] weaponCol, String name){
		for(WeaponCard wc : weaponCol){
			if(wc.getObject().weaponName().equals(name)){
				return wc;
			}
		}
		return null;
	}

	/**
	 * This code allows us to select from a collection of Rooms.
	 * @param roomCard collection
	 * @param currentPlayer p
	 * @return RoomCard
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static RoomCard askRooms(RoomCard[] roomCol, Player p){
		JPanel panel = new JPanel();
		panel.add(new JLabel("Please make a selection:"));
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(RoomCard roomCard : roomCol){
			model.addElement(roomCard.getName());
		}
		JComboBox comboBox = new JComboBox(model);
		panel.add(comboBox);
		int result = JOptionPane.showConfirmDialog(null, panel, "	2)	Choose a Room", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (result) {
		case JOptionPane.OK_OPTION:
			return findRoomCard(roomCol, ((String)comboBox.getSelectedItem()));
		default:
			return null;
		}
	}

	/**
	 * Finds the card that the player selected given the name of the card.
	 * @param roomCol
	 * @param name - name of card
	 * @return
	 */
	private static RoomCard findRoomCard(RoomCard[] roomCol, String name){
		for(RoomCard rc : roomCol){
			if(rc.getName().equals(name)){
				return rc;
			}
		}
		return null;
	}

	/**
	 * This code allows us to select from a collection of Suspects.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static CharacterCard askSuspects(CharacterCard[] charCol, Player p){

		JPanel panel = new JPanel();
		panel.add(new JLabel("Please make a selection:"));
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for(CharacterCard cc: charCol){
			model.addElement(cc.getObject().name());
		}
		JComboBox comboBox = new JComboBox(model);
		panel.add(comboBox);
		int result = JOptionPane.showConfirmDialog(null, panel, "	3)	Choose a suspect.", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE);
		switch (result) {
		case JOptionPane.OK_OPTION:
			for(CharacterCard cc : charCol){
				if(cc.getObject().name().equals(comboBox.getSelectedItem())){
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

	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.currentPlayer == null){
			//handle when the current player is null.
			JOptionPane.showMessageDialog(null, "You do not have a current active player.", "Move ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(this.isMoveSelection && rolled && !moveMade){
			if(currentPlayer.numberofMoves() > 0){
				try {
					if(currentPlayer.numberofMoves() > 0){
						for(int x = 0; x < board.length; x++){
							for(int y = 0; y < board[0].length; y++){
								Tile t = board[x][y];
								if(t.contains(e.getPoint())){
									if(!currentPlayer.isInRoom()){
										cluedoCanvas.move(t.x-currentPlayer.position().getX(), t.y-currentPlayer.position().getY(), currentPlayer, currentPlayers);
										this.cleanCanvas();
										if(currentPlayer.isInRoom()){
											moveMade = true;
										}
									}else{
										if(t instanceof DoorTile){
											cluedoCanvas.exitRoom(currentPlayer, currentPlayers);
											this.cleanCanvas();
											moveMade = true;
										}else if(t instanceof StairsTile){
											cluedoCanvas.moveToRoom(currentPlayer, currentPlayer.getRoom().getOtherRoom());
											this.cleanCanvas();
											moveMade = true;
										}
									}
									return;
								}
							}
						}
					}else {
						moveMade = true;
					}
				}catch (InvalidMove e1) {
					e1.printStackTrace();
				}
			}
		}
		this.cleanCanvas();
	}

	/*
	 * Unused MouseListener methods
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}

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
		if(!currentPlayers.isEmpty()){
			index++;
			if(index >= currentPlayers().size()){
				index = 0;
			}
			currentPlayer = currentPlayers.get(index);
			cluedoJFrame.currentPlayerText.setText(currentPlayer.getName() + "\r\n");
			cluedoJFrame.setPlayerColor(currentPlayer);
		}
	}

	public void resetAll() {
		currentPlayers = new ArrayList<Player>();
		numPlayers = 0; 
		askSuccess = false;
		hasAsked = false;
		currentPlayer = null; //the current player of the round.
		showCards = new ArrayList<>();
		index = 0;
		this.reset();
	}

	/**
	 * Returns the previous players' cards if they are out of the game.
	 * @return the showCards
	 */
	public static List<Card> getShowCards() {
		return showCards;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sound = new Sound();
					sound.music();
					CluedoView theFrame = new CluedoView();
					theFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Sound getSound(){
		return sound;
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
}