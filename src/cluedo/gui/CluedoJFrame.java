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
 * the menu contexts, the buttons and the layout for the GUI of the game.
 * 
 * @author linus + casey
 *
 */
public class CluedoJFrame extends JFrame {

	private JPanel contentPane;
	private static CluedoGame game;
	private JButton btnEndTurn;
	private JButton btnRollDice;
	// the combo boxes.
	private JComboBox combo1;
	private JComboBox combo2;
	private JComboBox combo3;
	private JTextPane textPane_1;
	private JTextField txtNull;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CluedoJFrame frame = new CluedoJFrame();
					frame.setVisible(true);
					game = new CluedoGame();
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
		game = new CluedoGame();//create a new instance of the game.

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 942, 700);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		/* Stuff that goes under the File Tab */
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmStartGame = new JMenuItem("Start Game");
		mnFile.add(mntmStartGame);

		JMenuItem mntmExitGame = new JMenuItem("Exit Program");
		mnFile.add(mntmExitGame);

		/* Stuff that goes into the HELP tab */
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmGameInstructions = new JMenuItem("Game Instructions");
		mnHelp.add(mntmGameInstructions);

		JMenuItem mntmAboutCluedogui = new JMenuItem("About CluedoGUI");
		mnHelp.add(mntmAboutCluedogui);

		/* ACTION LISTENER STUFF */
		mntmStartGame.addActionListener(new ActionListener() {
			// TODO need to implement this.
			@Override
			public void actionPerformed(ActionEvent e) {
				textPane_1.setText(game.askPlayers());
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

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(
				new MigLayout("", "[88px,grow]", "[14px][][][][][][][][][][][][][][][][][][][][][][][][][][][]"));

		// create JPanel
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new MigLayout("", "[113px,grow]", "[23px][][][][][][][][][][grow]"));

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JLabel label = new JLabel("1. Choose your Suspects");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(label, "cell 0 2");

		combo1 = new JComboBox();
		panel.add(combo1, "cell 0 3,growx");
		combo1.addItem("Miss Scarlett");
		combo1.addItem("Colonel Mustard");
		combo1.addItem("Mrs. White");
		combo1.addItem("The Reverend Green");
		combo1.addItem("Mrs. Peacock");
		combo1.addItem("Professor Plum");

		JLabel label_1 = new JLabel("2.	Choose your Weapons");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(label_1, "cell 0 4");

		combo2 = new JComboBox();
		panel.add(combo2, "cell 0 5,growx");
		combo2.addItem("Knife");
		combo2.addItem("Rope");
		combo2.addItem("Revolver");
		combo2.addItem("Wrench");
		combo2.addItem("Pipe");
		combo2.addItem("Candlestick");

		JLabel label_2 = new JLabel("3.	Choose your Rooms");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(label_2, "cell 0 6");

		combo3 = new JComboBox();
		panel.add(combo3, "cell 0 7,growx");
		combo3.addItem("Kitchen");
		combo3.addItem("Ball Room");
		combo3.addItem("Conservatory");
		combo3.addItem("Billard Room");
		combo3.addItem("Library");
		combo3.addItem("Study");
		combo3.addItem("Hall");
		combo3.addItem("Lounge");
		combo3.addItem("Dining Room");

		JLabel lblCurrentPlayer = new JLabel("Current Player:");
		lblCurrentPlayer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_1.add(lblCurrentPlayer, "cell 0 0");
		

		txtNull = new JTextField();
		txtNull.setText("null\r\n");
		txtNull.setEditable(false);
		panel_1.add(txtNull, "cell 0 1,growx");
		txtNull.setColumns(10);	
				JButton btnStartTurn = new JButton("Start Turn");
				panel_1.add(btnStartTurn, "cell 0 2,alignx center");
		
				btnEndTurn = new JButton("End Turn");
				panel_1.add(btnEndTurn, "cell 0 3,alignx center");
		
		JRadioButton rdbtnDoSuggestion = new JRadioButton("Do Suggestion");
		panel_1.add(rdbtnDoSuggestion, "cell 0 4");
		
		JRadioButton rdbtnDoAccusation = new JRadioButton("Do Accusation");
		panel_1.add(rdbtnDoAccusation, "cell 0 5");
		
				btnRollDice = new JButton("Roll Dice");
				
				JButton btnDisplayHand = new JButton("Display Hand");
				btnDisplayHand.setHorizontalAlignment(SwingConstants.RIGHT);
				panel_1.add(btnDisplayHand, "cell 0 6");
				DiceCanvas dicecanvas = new DiceCanvas();
				panel_1.add(dicecanvas, "cell 0 10");
				panel_1.add(btnRollDice, "cell 0 7");
				btnRollDice.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dicecanvas.setDiceOne(game.diceRoll());
						dicecanvas.setDiceTwo(game.diceRoll());
						panel_1.repaint();
					}
				});
		textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		panel_1.add(textPane_1, "cell 0 9,grow");
		
		JLabel lblListOfPlayers = new JLabel("List of available players: ");
		panel_1.add(lblListOfPlayers, "cell 0 8");
		
		CluedoCanvas canvas = new CluedoCanvas();
		contentPane.add(canvas, BorderLayout.CENTER);
		canvas.addMouseListener(game);
	}
}
