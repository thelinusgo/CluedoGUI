package cluedo.randomtesting;
import javax.swing.*;

public class Timer extends Thread {
	
	private Frame cluedoJFrame;
	
	public Timer(){
		cluedoJFrame = new Frame();
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