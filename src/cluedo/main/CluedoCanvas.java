package cluedo.main;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
/**
 * Class that represents the Cluedo Canvas
 * @author linus
 *
 */
public class CluedoCanvas extends JPanel {

	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	/**
	 * This sets the dimension of the Cluedo Canvas.
	 */
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(600,600);
	}
	
	
	
}
