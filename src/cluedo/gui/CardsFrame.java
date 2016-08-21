package cluedo.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;
import cluedo.cards.Card;
/**
 * This class represents the pop up window that comes up, when a player views his hand.
 * @author linus
 *
 */
public class CardsFrame extends JFrame{
	//TODO: casey - need to put some logic into here.
	private CardsCanvas canvas;
	public CardsFrame(List<Card> hand){
		super("Cards In Hand");
		canvas = new CardsCanvas(hand);
		setLayout(new BorderLayout()); // use border layout
		add(canvas, BorderLayout.CENTER); // add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
	}
}
