package cluedo.randomtesting;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import cluedo.gui.CardsCanvas;
import cluedo.gui.DiceCanvas;
/**
 * This class represents the pop up window that comes up, when a player views his hand.
 * @author linus
 *
 */
public class CardsFrame extends JFrame{
	//TODO: casey - need to put some logic into here.
	private CardsCanvas canvas;
	public CardsFrame(){
		super("Cards In Hand");
		canvas = new CardsCanvas();
		setLayout(new BorderLayout()); // use border layout
		add(canvas, BorderLayout.CENTER); // add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
	}
	
	public void showCards(){
		
	}
	
//	public static void main(String... args){
//		new CardsFrame();
//	}
	
	
}
