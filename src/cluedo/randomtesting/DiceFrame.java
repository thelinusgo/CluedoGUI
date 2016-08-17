package cluedo.randomtesting;
import java.awt.BorderLayout;

import javax.swing.JFrame;
public class DiceFrame extends JFrame{
	
	private DiceCanvas canvas;
	public DiceFrame(){
		super("test");
		canvas = new DiceCanvas(3,3);
		
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
