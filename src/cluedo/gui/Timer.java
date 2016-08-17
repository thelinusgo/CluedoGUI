package cluedo.gui;
import javax.swing.*;

public class Timer extends Thread {
	
	private CluedoJFrame cluedoJFrame;
	
	public Timer(){
		cluedoJFrame = new CluedoJFrame();
	}
	
	public void run() {
		while(true) {
			try{
				Thread.sleep(100);
				cluedoJFrame.canvas.repaint(); // repaint the canvas
			}catch(InterruptedException e) {
				
			}
		}
	}
}