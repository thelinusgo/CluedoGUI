package cluedo.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import cluedo.assets.Player;
import cluedo.cards.Card;

/**
 * Canvas used to display the cards that a current Player has.
 * This is drawn on an attached JFrame.
 * @author linus
 *
 */
public class CardsCanvas extends JPanel{
	/**
	 * List of cards to display on the canvas.
	 */
	private List<Card> cards;
	public boolean hasbeenPainted = false;

	public CardsCanvas(List<Card> cards){
		this.cards = cards;
		hasbeenPainted = false;
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(1200,900);
	}

	@Override
	public void paint(Graphics g){
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		g.drawString("Cards in hand:", 5, 15);
		g.drawString("Weapons:", 5, 27);
		g.drawString("Rooms:", 5, 52+Card.CARD_HT);
		g.drawString("Characters: " , 5, (3*Card.CARD_HT)-35);
		this.drawCards();
		hasbeenPainted = true;

	}

	public void drawCards(){
		System.out.println("draw cards is being called.");
		System.out.println("cards size: " + cards.size());
		for(int i = 0; i < cards.size(); i++){
			Card c = cards.get(i);
			c.draw(this.getGraphics(), i);
		}
		

	}
}
