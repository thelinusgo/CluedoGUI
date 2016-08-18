package cluedo.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cluedo.assets.Player;
import cluedo.assets.Position;
import cluedo.assets.Character;
import cluedo.main.CluedoGame;
import cluedo.main.Initializer;
import cluedo.randomtesting.CardsCanvas;
import cluedo.randomtesting.CardsFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
public class CluedoJFrame extends JFrame {
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
	private static CluedoGame game;
	/* JButtons */
	private JButton btnStartTurn;
	private JButton btnEndTurn;
	private JButton btnMakeMove;
	private JButton btnRollDice;
	private JButton btnDisplayHand;
	private JButton btnMakeArgument;
	/* ComboBoxes used for Suggestion/Accusation. */
	private JComboBox suspectsComboBox;
	private JComboBox weaponsComboBox;
	private JComboBox roomsComboBox;
	/* RadioButtons */
	private JRadioButton suggestionRadioButton;
	private JRadioButton accusationRadioButton;
	/* Various text panes and text Fields. */
	private JTextPane current_players_pane;
	public JTextField currentPlayerText; // this is where one would set the
											// current Player's name.
	private DiceCanvas dicecanvas = new DiceCanvas();

	/*
	 * The canvas representing the pop up window, for drawing the players hand.
	 */
	private CardsCanvas cardcanvas = new CardsCanvas();
	/* The JFrame for the cardcanvas. */
	private CardsFrame cardsframe;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CluedoJFrame frame = new CluedoJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CluedoJFrame() {
		game = new CluedoGame(this);// create a new instance of the game.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 942, 700);
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		/* Stuff that goes under the File Tab */
		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmStartGame = new JMenuItem("Start Game");
		mnFile.add(mntmStartGame);

		mntmExitGame = new JMenuItem("Exit Program");
		mnFile.add(mntmExitGame);

		/* Stuff that goes into the HELP tab */
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		mntmGameInstructions = new JMenuItem("Game Instructions");
		mnHelp.add(mntmGameInstructions);

		mntmAboutCluedogui = new JMenuItem("About CluedoGUI");
		mnHelp.add(mntmAboutCluedogui);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(
				new MigLayout("", "[88px,grow]", "[14px][][][][][][][][][][][][][][][][][][][][][][][][][][][][]"));

		// create JPanel
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new MigLayout("", "[113px,grow]", "[23px][][][][][][][45px][][][][grow]"));

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		btnMakeArgument = new JButton("MAKE ARGUMENT");
		panel.add(btnMakeArgument, "cell 0 1,alignx center");

		suggestionRadioButton = new JRadioButton("Do Suggestion");
		panel.add(suggestionRadioButton, "cell 0 2");
		
		
		accusationRadioButton = new JRadioButton("Do Accusation");
		panel.add(accusationRadioButton, "cell 0 3");

		JLabel label = new JLabel("1. Choose your Suspects");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(label, "cell 0 4");

		suspectsComboBox = new JComboBox();
		panel.add(suspectsComboBox, "cell 0 5,growx");
		suspectsComboBox.addItem("Miss Scarlett");
		suspectsComboBox.addItem("Colonel Mustard");
		suspectsComboBox.addItem("Mrs. White");
		suspectsComboBox.addItem("The Reverend Green");
		suspectsComboBox.addItem("Mrs. Peacock");
		suspectsComboBox.addItem("Professor Plum");
		suspectsComboBox.setEnabled(false);
		

		JLabel label_1 = new JLabel("2.	Choose your Weapons");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(label_1, "cell 0 6");

		weaponsComboBox = new JComboBox();
		panel.add(weaponsComboBox, "cell 0 7,growx");
		weaponsComboBox.addItem("Knife");
		weaponsComboBox.addItem("Rope");
		weaponsComboBox.addItem("Revolver");
		weaponsComboBox.addItem("Wrench");
		weaponsComboBox.addItem("Pipe");
		weaponsComboBox.addItem("Candlestick");
		weaponsComboBox.setEnabled(false);

		JLabel label_2 = new JLabel("3.	Choose your Rooms");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(label_2, "cell 0 8");

		roomsComboBox = new JComboBox();
		panel.add(roomsComboBox, "cell 0 9,growx");
		roomsComboBox.addItem("Kitchen");
		roomsComboBox.addItem("Ball Room");
		roomsComboBox.addItem("Conservatory");
		roomsComboBox.addItem("Billard Room");
		roomsComboBox.addItem("Library");
		roomsComboBox.addItem("Study");
		roomsComboBox.addItem("Hall");
		roomsComboBox.addItem("Lounge");
		roomsComboBox.addItem("Dining Room");
		roomsComboBox.setEnabled(false);

		JLabel currentPlyrLabel = new JLabel("Current Player:");
		currentPlyrLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_1.add(currentPlyrLabel, "cell 0 0");

		currentPlayerText = new JTextField();
		currentPlayerText.setText("null\r\n");
		currentPlayerText.setEditable(false);
		panel_1.add(currentPlayerText, "cell 0 1,growx");
		currentPlayerText.setColumns(10);
		btnStartTurn = new JButton("Start Turn");
		panel_1.add(btnStartTurn, "cell 0 2,alignx left");

		btnMakeMove = new JButton("Make Move");
		panel_1.add(btnMakeMove, "cell 0 3");
		btnMakeMove.addActionListener(e ->{
			game.isMoveSelection = true;
		});

		btnEndTurn = new JButton("End Turn");
		panel_1.add(btnEndTurn, "cell 0 4,alignx left");
		btnEndTurn.addActionListener(e->{
			game.isMoveSelection = false;
			game.isSuggestionSelection = false;
		});
		btnDisplayHand = new JButton("Display Hand");
		btnDisplayHand.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(btnDisplayHand, "cell 0 5");

		btnRollDice = new JButton("Roll Dice");
		panel_1.add(btnRollDice, "cell 0 6");

		panel_1.add(dicecanvas, "cell 0 7,aligny top");

		JLabel lblListOfPlayers = new JLabel("List of available players: ");
		panel_1.add(lblListOfPlayers, "cell 0 8");
		current_players_pane = new JTextPane();
		current_players_pane.setEditable(false);
		panel_1.add(current_players_pane, "cell 0 9,grow");

		contentPane.add(game.cluedoCanvas, BorderLayout.CENTER);
		
		this.enableRadioButtons(false);
		/***************************
		 * START OF ACTION/MOUSE LISTENER STUFF
		 ***************************/
		game.cluedoCanvas.addMouseListener(game);
		btnMakeArgument.addActionListener(e ->{
			game.argsButtonPressed = true;
			enableRadioButtons(true);
			if(game.isSuggestionSelection){
				enableComboBoxes(true);
			}else{
				enableComboBoxes(false);
			}
		});
		
		suggestionRadioButton.addActionListener(e->{
			game.isSuggestionSelection = true;
		});
		
		accusationRadioButton.addActionListener(e->{
			game.isSuggestionSelection = false;
		});
		
		
		
		
		mntmStartGame.addActionListener(e -> {
			current_players_pane.setText(game.askPlayers());
			try {
				game.runGame();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		mntmExitGame.addActionListener(e -> {
			int value = JOptionPane.showConfirmDialog(null, "Do you want to exit this Game?", "Confirmation",
					JOptionPane.YES_NO_OPTION);
			if (value == 0)
				System.exit(0);
		});
		mntmAboutCluedogui.addActionListener(e -> JOptionPane.showMessageDialog(null,
				"This game was created by Casey Huang and Linus Go for their SWEN 222 Project. \n (c) 2016 All rights reserved."));

		btnDisplayHand.addActionListener(e -> cardsframe = new CardsFrame());

		btnRollDice.addActionListener(e -> {
			if(game.isMoveSelection){
				dicecanvas.setDiceOne(game.diceRoll());
				dicecanvas.setDiceTwo(game.diceRoll());
				panel_1.repaint();
			}
		});
		/*********************
		 * END OF ACTION/MOUSE LISTENER STUFF
		 ***************************/
	}
	
	/**
	 * This enables the radioButtons, once the Argument button has been pressed.
	 * @param b
	 */
	private void enableRadioButtons(boolean b){
		suggestionRadioButton.setEnabled(b);
		accusationRadioButton.setEnabled(b);
//		suspectsComboBox.setEnabled(b);
//		weaponsComboBox.setEnabled(b);
//		roomsComboBox.setEnabled(b);
	}
	
	/**
	 * This enables the appropriate combo boxes, once a radio button has been pressed.
	 * @param isSuggestion
	 */
	private void enableComboBoxes(boolean isSuggestion){
		if(isSuggestion){
			accusationRadioButton.setEnabled(false);
			suspectsComboBox.setEnabled(true);
			weaponsComboBox.setEnabled(true);
			roomsComboBox.setEnabled(false);
		}else{
			suggestionRadioButton.setEnabled(false);
			suspectsComboBox.setEnabled(true);
			weaponsComboBox.setEnabled(true);
			roomsComboBox.setEnabled(true);
		}
	}
	
	
	
	
	
}
