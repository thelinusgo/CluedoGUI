package RaandomTesting;
import java.awt.event.*;
import sun.audio.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import java.io.*;

public class Sound {

	public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(100, 100);
        JButton button = new JButton("Play");
        frame.add(button);
        button.addActionListener(new AL());
        frame.show(true);
    }

    public static class AL implements ActionListener {

        public final void actionPerformed(ActionEvent e) {
            music();
        }
    }

    public static void music() {
    	try {
    	    File yourFile;
    	    AudioInputStream stream;
    	    AudioFormat format;
    	    DataLine.Info info;
    	    Clip clip;

    	    stream = AudioSystem.getAudioInputStream(new File("Spy_Glass.wav"));
    	    format = stream.getFormat();
    	    info = new DataLine.Info(Clip.class, format);
    	    clip = (Clip) AudioSystem.getLine(info);
    	    clip.open(stream);
    	    clip.start();
    	}
    	catch (Exception e) {
    	    //whatevers
    	}
    }
}