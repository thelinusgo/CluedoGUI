package cluedo.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import cluedo.assets.Player;
import cluedo.cards.Card;

/**
 * Canvas used to display the cards that a current Player has.
 * @author linus
 *
 */
public class CardsCanvas extends JPanel{
	/**
	 * This cardCanvas player.
	 */
	private Player player;

	public CardsCanvas(Player p){
		this.player = p;
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(640,552);
	}

	@Override
	public void paint(Graphics g){
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		g.drawString("Cards in hand:", 5, 15);
		g.drawString("Weapons:", 5, 27);
		g.drawString("Rooms:", 5, 52+Card.CARD_HT);
		g.drawString("Characters: " , 5, (3*Card.CARD_HT)-35);
		this.drawCards();
	}

	public void drawCards(){
		for(int i = 0; i < player.getCards().size(); i++){
			Card c = player.getCards().get(i);
			c.draw(this.getGraphics(), i);
		}
	}




}
