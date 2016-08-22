package cluedo.gui;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import sun.audio.*;

public class Sound {
	public static void music(){
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

    	}
	}
}
