package cluedo.gui;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
/**
 * The Game over frame that shows up when the game has been lost..
 * @author linus
 *
 */
public class GameOverFrame extends JFrame{
	
	private GameOverCanvas canvas;
	
	public GameOverFrame(){
			super("Game Over. You lost.");
			canvas = new GameOverCanvas(); // create canvas
			setLayout(new BorderLayout()); // use border layour
			add(canvas, BorderLayout.CENTER); // add canvas
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //dont kill the whole thingy.
			pack(); // pack components tightly together
			setResizable(false); // prevent us from being resizeable
			setVisible(true); // make sure we are visible!
	}
	
	public static void main(String... args){
		new GameOverFrame();
	}
	
	
	
	
}
