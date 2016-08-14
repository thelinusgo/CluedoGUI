package cluedo.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
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

/**
 * This class represents the JFrame for the Cluedo Game.
 * This contains all of the menu contexts, the buttons and the layout for the GUI of the game.
 * @author linus + casey
 *
 */
public class CluedoJFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	
	//initialize the buttons that have logic in them.
	private JButton btnSuggestion;
	private JButton btnAccusation;
	private JButton btnEndTurn;
	private JButton btnRollDice;
	//the combo boxes.
	private JComboBox combo1;
	private JComboBox combo2;
	private JComboBox combo3;
	//the text pane.
	private JTextPane textPane;
	
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
		textPane = new JTextPane();

		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 942, 700);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmStartGame = new JMenuItem("Start Game");
		mnFile.add(mntmStartGame);
		
		JMenuItem mntmExitGame = new JMenuItem("Exit Game");
		mnFile.add(mntmExitGame);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmGameInstructions = new JMenuItem("Game Instructions");
		mnHelp.add(mntmGameInstructions);
		
		JMenuItem mntmAboutCluedogui = new JMenuItem("About CluedoGUI");
		mnHelp.add(mntmAboutCluedogui);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(new MigLayout("", "[88px,grow]", "[14px][][][][][][][][][][][][][][][][][][][][][][][][][][]"));
		
		//create JPanel here.
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new MigLayout("", "[113px,grow]", "[23px][][][][][grow]"));
				
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JLabel label = new JLabel("1. Choose your Suspects");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(label, "cell 0 1");
		
		combo1 = new JComboBox();
		panel.add(combo1, "cell 0 2,growx");
		combo1.addItem("Miss Scarlett");
		combo1.addItem("Colonel Mustard");
		combo1.addItem("Mrs. White");
		combo1.addItem("The Reverend Green");
		combo1.addItem("Mrs. Peacock");
		combo1.addItem("Professor Plum");
		

		
		JLabel label_1 = new JLabel("2.	Choose your Weapons");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(label_1, "cell 0 8");
		
		combo2 = new JComboBox();
		panel.add(combo2, "cell 0 9,growx");
		combo2.addItem("Knife");
		combo2.addItem("Rope");
		combo2.addItem("Revolver");
		combo2.addItem("Wrench");
		combo2.addItem("Pipe");
		combo2.addItem("Candlestick");

		JLabel label_2 = new JLabel("3.	Choose your Rooms");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(label_2, "cell 0 15");
		
		combo3 = new JComboBox();
		panel.add(combo3, "cell 0 16,growx");
		combo3.addItem("Kitchen");
		combo3.addItem("Ball Room");
		combo3.addItem("Conservatory");
		combo3.addItem("Billard Room");
		combo3.addItem("Library");
		combo3.addItem("Study");
		combo3.addItem("Hall");
		combo3.addItem("Lounge");
		combo3.addItem("Dining Room");

		JLabel label_3 = new JLabel("Current Player");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_1.add(label_3, "cell 0 0");
		
		btnSuggestion = new JButton("Suggestion");
		panel_1.add(btnSuggestion, "cell 0 1,alignx center");
		btnSuggestion.addActionListener(new ActionListener(){
			//TODO: implement button logic here.
			public void actionPerformed(ActionEvent e){
				
			}
		});
		
		btnAccusation = new JButton("Accusation");
		panel_1.add(btnAccusation, "cell 0 2,alignx center");
		btnAccusation.addActionListener(new ActionListener(){
			//TODO: implement button logic here.
			public void actionPerformed(ActionEvent e){
				
			}
		});
		
		btnEndTurn = new JButton("End Turn");
		panel_1.add(btnEndTurn, "cell 0 3,alignx center");
		btnSuggestion.addActionListener(new ActionListener(){
			//TODO: implement button logic here
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		btnRollDice = new JButton("Roll Dice");
		btnRollDice.addActionListener(new ActionListener() {
			//TODO: implement button logic here.
			public void actionPerformed(ActionEvent e) {
			textPane.setText(Double.toString((int)(10*Math.random())));
			}
		});
		
		JLabel lblDice = new JLabel("Dice Value: ");
		panel_2.add(lblDice);
		panel_2.add(textPane);
		panel_2.add(btnRollDice);
		
		CluedoCanvas canvas = new CluedoCanvas();
		contentPane.add(canvas, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

}
