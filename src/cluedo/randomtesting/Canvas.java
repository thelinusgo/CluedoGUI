package cluedo.randomtesting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener{
	
	private Color color = new Color(255,22,55);
	private int r = color.getRed();
	private int g = color.getGreen();
	private int b = color.getBlue();
	private Rectangle rect = null;
	
	public Canvas(){
		this.addMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	public void paint(Graphics g){
		g.setColor(color);
		rect = new Rectangle(0, 0, 20, 20);
		g.fillRect(0, 0, 20, 20);
		g.setColor(new Color(0,0,0));
		g.fillOval(0, 0, 10, 10);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("hi");
		if(rect.contains(e.getPoint())){
			System.out.println("changed colour");
			color = new Color(r, g, b, 50);
		}
		startTimer();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void startTimer() {
		while(true) {
			try{
				Thread.sleep(100);
				this.repaint(); // repaint the canvas
			}catch(InterruptedException e) {
				
			}
		}
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
