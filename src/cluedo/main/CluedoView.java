package cluedo.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cluedo.assets.Player;
import cluedo.assets.Position;
import cluedo.cards.Card;
import cluedo.gui.CardsJFrame;
import cluedo.gui.DiceCanvas;
import cluedo.gui.Sound;
import cluedo.arguments.Accusation;
import cluedo.arguments.Suggestion;
import cluedo.assets.Character;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import java.awt.Canvas;
import java.awt.Color;

/**
 * This class represents the JFrame for the Cluedo Game. This contains all of
 * the menu contexts, the buttons and the layout for the GUI of the game. (This
 * is the View in the MVC design pattern)
 * 
 * @author linus + casey
 *
 */
public class CluedoView extends JFrame {

	/* The left panel of the Class. */
	private JPanel contentPane;

	/* Holds the menuBar. */
	private JMenuBar menuBar;

	/* Menu Items */
	private JMenuItem mnFile;
	private JMenuItem mntmStartGame;
	private JMenuItem mntmExitGame;
	private JMenuItem mntmGameInstructions;
	private JMenuItem mntmAboutCluedogui;
	private JMenu mnHelp;
	/* Holds an instance of the game.. */
	private static CluedoGameController game;
	private JButton btnEndTurn;
	private JButton btnMakeMove;
	private JButton btnRollDice;
	private JButton btnDisplayHand;
	private JButton btnMakeSuggestion;
	private JButton btnMakeAccusation;
	/* Various text panes and text Fields. */
	private JTextPane current_players_pane;
	public JTextField currentPlayerText; // this is where one would set the

	private JPanel leftPanel;

	/* Represents the player Colors */
	private ImageIcon green = new ImageIcon("green.png");
	private ImageIcon white = new ImageIcon("white.png");
	private ImageIcon yellow = new ImageIcon("yellow.png");
	private ImageIcon red = new ImageIcon("red.png");
	private ImageIcon purple = new ImageIcon("purple.png");
	private ImageIcon blue = new ImageIcon("blue.png");
	private ImageIcon no_player = new ImageIcon("no_player.png");

	private CardsJFrame cardsframe;

	/**
	 * The dice canvas - where the dice ImageIcons are drawn.
	 */
	private DiceCanvas dicecanvas = new DiceCanvas();
	/*
	 * 
	 * The canvas representing the pop up window, for drawing the players hand.
	 * 
	 * private CardsCanvas cardcanvas = new CardsCanvas();
	 *//**
		 * The JFrame for the cardvancas.
		 *//*
		 * private CardsFrame cardsframe;
		 */
	private JLabel playerColor;
	private JButton btnShowPreviousplayersCards;
	private JRadioButton rdbtnMuteSound;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public CluedoView() {
		super("Canus Studios Present: The Game of Cluedo");
		game = new CluedoGameController(this);// create a new instance of the
												// game.
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				int value = JOptionPane.showConfirmDialog(null, "Do you want to exit this Game?", "Confirmation",
						JOptionPane.YES_NO_OPTION);
				if (value == 0)
					System.exit(0);
			}

		});
		setBounds(100, 100, 775, 700);
		this.setResizable(false);
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		/**
		 * Initialize the player color icons.
		 */

		/* Stuff that goes under the File Tab */
		mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);

		mntmStartGame = new JMenuItem("Start Game");
		mnFile.add(mntmStartGame);

		mntmExitGame = new JMenuItem("Exit Program");
		mnFile.add(mntmExitGame);

		/* Stuff that goes into the HELP tab */
		mnHelp = new JMenu("Help");
		mnHelp.setMnemonic('H');
		menuBar.add(mnHelp);

		mntmGameInstructions = new JMenuItem("Game Instructions");
		mnHelp.add(mntmGameInstructions);

		mntmAboutCluedogui = new JMenuItem("About CluedoGUI");
		mnHelp.add(mntmAboutCluedogui);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// create JPanel
		leftPanel = new JPanel();
		leftPanel.setToolTipText("The current player.");
		contentPane.add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new MigLayout("", "[113px]", "[23px][::50px][][][][][][][][][45px][][][][][][grow]"));

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JLabel currentPlyrLabel = new JLabel("Current Player:");
		currentPlyrLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		leftPanel.add(currentPlyrLabel, "cell 0 0");

		currentPlayerText = new JTextField();
		currentPlayerText.setToolTipText("This is the current player, and his color on the board.");
		currentPlayerText.setText("null\r\n");
		currentPlayerText.setEditable(false);
		leftPanel.add(currentPlayerText, "flowx,cell 0 1 1 2");
		currentPlayerText.setColumns(10);

		// Button for when a move is made.
		btnMakeMove = new JButton("Make Move");
		btnMakeMove.setToolTipText("Press this to make a move.");
		leftPanel.add(btnMakeMove, "cell 0 3,growx");

		// Button for when a turn has ended.
		btnEndTurn = new JButton("End Turn");
		btnEndTurn.setToolTipText("Press this to end your turn.");
		leftPanel.add(btnEndTurn, "cell 0 4 1 2,growx");

		// Button to display the current players hand.
		btnDisplayHand = new JButton("Display your Hand");
		btnDisplayHand.setToolTipText("Press this to show what cards in hand you have.");
		btnDisplayHand.setHorizontalAlignment(SwingConstants.RIGHT);
		leftPanel.add(btnDisplayHand, "cell 0 6,growx");

		btnShowPreviousplayersCards = new JButton("Previous Players Cards");
		btnShowPreviousplayersCards.setToolTipText("Press this to show previous players cards.");
		leftPanel.add(btnShowPreviousplayersCards, "cell 0 7");

		// Button to roll the dice.
		btnRollDice = new JButton("Roll the Dice");
		btnRollDice.setToolTipText("Press this to roll the die.");
		leftPanel.add(btnRollDice, "cell 0 8,growx");

		leftPanel.add(dicecanvas, "cell 0 9,alignx center,aligny top");

		rdbtnMuteSound = new JRadioButton("Mute Sound");
		leftPanel.add(rdbtnMuteSound, "cell 0 10");

		btnMakeSuggestion = new JButton("Make Suggestion");
		btnMakeSuggestion.setToolTipText("Press this button to make a Suggestion.");
		leftPanel.add(btnMakeSuggestion, "cell 0 11,growx");

		btnMakeAccusation = new JButton("Make Accusation");
		btnMakeAccusation.setToolTipText("Press this button to make an accusation.");
		leftPanel.add(btnMakeAccusation, "cell 0 12,growx");

		JLabel lblListOfPlayers = new JLabel("List of available players: ");
		leftPanel.add(lblListOfPlayers, "cell 0 13");

		playerColor = new JLabel(no_player);
		leftPanel.add(playerColor, "cell 0 1");
		current_players_pane = new JTextPane();
		current_players_pane.setToolTipText("The list of available players.");
		current_players_pane.setEditable(false);
		leftPanel.add(current_players_pane, "cell 0 14,grow");

		contentPane.add(game.cluedoCanvas, BorderLayout.CENTER);

		// this sets up the action listeners.
		this.setupActionListeners();
	}

	/**
	 * This sets the players current color.
	 * 
	 * @param p
	 */
	public void setPlayerColor(Player p) {
		if (p == null) {
			playerColor.setIcon(no_player);
			return;
		}
		String value = p.getCharacter().name();

		switch (value) {
		case "Miss Scarlet":
			playerColor.setIcon(red);
			game.cleanCanvas();
			break;
		case "Colonel Mustard":
			playerColor.setIcon(yellow);
			game.cleanCanvas();
			break;
		case "Mrs. White":
			playerColor.setIcon(white);
			game.cleanCanvas();
			break;
		case "The Reverend Green":
			playerColor.setIcon(green);
			game.cleanCanvas();
			break;
		case "Mrs. Peacock":
			playerColor.setIcon(purple);
			game.cleanCanvas();
			break;
		case "Professor Plum":
			playerColor.setIcon(blue);
			game.cleanCanvas();
			break;
		default:
			playerColor.setIcon(no_player);
			game.cleanCanvas();
			break;
		}
	}

	/**
	 * This method sets up and initializes the action listeners. If you want to
	 * add an action listener for a Swing component, you would do it here.
	 */
	private void setupActionListeners() {
		/***************************
		 * START OF ACTION/MOUSE LISTENER STUFF
		 ***************************/
		game.cluedoCanvas.addMouseListener(game);

		rdbtnMuteSound.addActionListener(e -> {
			if (rdbtnMuteSound.isSelected())
				game.getSound().stopMusic();
			else
				game.getSound().startMusic();
		});

		btnMakeSuggestion.addActionListener(e -> {
			Suggestion sug = game.makeSuggestion(game.currentPlayer());
			if (sug == null) {
				System.err.println("Why is the suggestion null? Look at me (line 336)");
				return;
			}

		});

		btnMakeAccusation.addActionListener(e -> {
			try {
				Accusation status = game.makeAccusation(game.currentPlayer());
				if (status == null) {
					JOptionPane.showMessageDialog(null, "The accusation was incorrect.", "ACCUSATION INCORRECT",
							JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, "The current player has been removed from the game, and will be prevented from moving.", "GAME Notice." ,JOptionPane.ERROR_MESSAGE);
					game.currentPlayer().setOut(true);
					game.currentPlayers().remove(game.currentPlayer());
					game.reset();
					dicecanvas.setDiceOne(0);
					dicecanvas.setDiceTwo(0);
					leftPanel.repaint();		
				} else {
					JOptionPane.showMessageDialog(null, "The accusation was Correct. Nice work!", "ACCUSATION CORRECT",
							JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(null, game.currentPlayer().getName() + " has won the game.",
							"Congratulations", JOptionPane.INFORMATION_MESSAGE);
					btnEndTurn.setEnabled(false);
					btnMakeMove.setEnabled(false);
					btnRollDice.setEnabled(false);
					btnMakeSuggestion.setEnabled(false);
					btnMakeAccusation.setEnabled(false);
					btnShowPreviousplayersCards.setEnabled(false);
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		btnEndTurn.addActionListener(e -> {
			if(!game.isGameOver()){
				game.reset();
				dicecanvas.setDiceOne(0);
				dicecanvas.setDiceTwo(0);
				leftPanel.repaint();
			}else{
				
			}
		});

		mntmStartGame.addActionListener(e -> {
			game.resetAll();
			this.setPlayerColor(game.currentPlayer());
			current_players_pane.setText(game.askPlayers());
		});

		mntmExitGame.addActionListener(e -> {
			int value = JOptionPane.showConfirmDialog(null, "Do you want to exit this Game?", "Confirmation",
					JOptionPane.YES_NO_OPTION);
			if (value == 0)
				System.exit(0);
		});

		mntmAboutCluedogui.addActionListener(e -> JOptionPane.showMessageDialog(null,
				"This game was created by Casey Huang and Linus Go for their SWEN 222 Project. \n (c) 2016 All rights reserved."));

		btnDisplayHand.addActionListener(e -> {
			if (game.currentPlayer() == null) {
				JOptionPane.showMessageDialog(null, "Cannot check the hand of a non-existant player!", "Game ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			cardsframe = new CardsJFrame(game.currentPlayer().getCards());
			cardsframe.setVisible(true);
		});
		
		btnShowPreviousplayersCards.addActionListener(e -> {
			if (game.getShowCards().isEmpty()) {
				JOptionPane.showMessageDialog(null, "No previous players' cards to show", "Game ERROR",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			cardsframe = new CardsJFrame(game.getShowCards());
			cardsframe.setVisible(true);
		});

		btnRollDice.addActionListener(e -> {
			if (game.moveMade) {
				return;
			}

			if (game.currentPlayer() == null) {
				JOptionPane.showMessageDialog(null, "You must have a current Player to roll the dice!", "Game ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

			if (game.rolled && game.moveMade) {
				JOptionPane.showMessageDialog(null, "You have already rolled! Let the next player go.", "Game ERROR",
						JOptionPane.ERROR_MESSAGE);
			}

			if (game.isMoveSelection && !game.rolled) {
				dicecanvas.setDiceOne(game.diceRoll());
				dicecanvas.setDiceTwo(game.diceRoll());
				game.currentPlayer().coordinatesTaken().clear();
				game.currentPlayer().setNumberofMoves(game.currentRoll);
				leftPanel.repaint();
				game.rolled = true;
			}
		});

		btnMakeMove.addActionListener(e -> {
			if (game.moveMade) {
				game.reset();
				return;
			}
			game.isMoveSelection = true;
			game.btnPressed = true;
		});
		/*********************
		 * END OF ACTION/MOUSE LISTENER STUFF
		 ***************************/
	}
}
