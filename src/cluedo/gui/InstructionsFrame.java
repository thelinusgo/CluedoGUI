package cluedo.gui;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
/**
 * The Instructions frame that reprents a popup dialog box.
 * @author linus
 *
 */
public class InstructionsFrame extends JFrame{
	
	private InstructionsCanvas canvas;
	
	public InstructionsFrame(){
			super("Game Instructions");
			canvas = new InstructionsCanvas(); // create canvas
			setLayout(new BorderLayout()); // use border layour
			add(canvas, BorderLayout.CENTER); // add canvas
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			pack(); // pack components tightly together
			setResizable(false); // prevent us from being resizeable
			setVisible(true); // make sure we are visible!
	}
	
	public static void main(String... args){
		new InstructionsFrame();
	}
	
	
	
	
}
