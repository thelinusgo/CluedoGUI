package cluedo.gui;
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class CluedoFrame extends JFrame {
	
	private CluedoCanvas canvas;

	public CluedoFrame(){
		super("Cluedo GUI Game");
		canvas = new CluedoCanvas(); // create canvas
		setLayout(new BorderLayout()); // use border layour
		add(canvas, BorderLayout.CENTER); // add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
	}
	
	
}
