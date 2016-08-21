package cluedo.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;
import cluedo.cards.Card;
import cluedo.main.CluedoGame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
/**
 * This class represents the pop up window that comes up, when a player views his hand.
 * @author linus
 *
 */
public class CardsFrame extends JFrame{

	private CardsCanvas canvas;
	public CardsFrame(List<Card> hand){
		super("Cards In Hand");
		canvas = new CardsCanvas(hand);
		getContentPane().setLayout(new BorderLayout()); // use border layout
		getContentPane().add(canvas, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!	
	}
	
	public CardsCanvas canvas(){
		return this.canvas;
	}

}
