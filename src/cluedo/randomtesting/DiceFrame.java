package cluedo.randomtesting;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import cluedo.gui.DiceCanvas;
public class DiceFrame extends JFrame{
	
	private CardsCanvas canvas;
	public DiceFrame(){
		super("Cards In Hand");
		canvas = new CardsCanvas();
		setLayout(new BorderLayout()); // use border layour
		add(canvas, BorderLayout.CENTER); // add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
	}
	
	public static void main(String... args){
		new DiceFrame();
	}
	
	
}
