package cluedo.randomtesting;

import java.awt.EventQueue;

import javax.swing.*;


public class TestOptionPane03 {

	public static void main(String[] args) {

		TestOptionPane03 obj = new TestOptionPane03();
		

	}

	public TestOptionPane03() {
		run();
	}
	/**
	 * This code allows us to select from a list of characters, given a player.
	 */
	public void run() {
		/*This is our array of choices. Used for the drop down menu */
		String[] characters = {
				"Miss Scarlett",
				"Colonel Mustard",
				"Mrs. White",
				"The Reverend Green",
				"Mrs. Peacock",
				"Professor Plum"
		};
		for(int j = 0; j < 3; ++j){

			JPanel panel = new JPanel();
			panel.add(new JLabel("Please make a selection:"));
			@SuppressWarnings("rawtypes")
			DefaultComboBoxModel model = new DefaultComboBoxModel();
			for(int i = 0; i != characters.length; ++i){
				if(!characters[i].equals("...")){
					model.addElement(characters[i]);
				}
			}
			JComboBox comboBox = new JComboBox(model);
			panel.add(comboBox);

			int result = JOptionPane.showConfirmDialog(null, panel, "Choose a Character", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			switch (result) {
			case JOptionPane.OK_OPTION:
				System.out.println("You selected " + comboBox.getSelectedItem());
				for(int i = 0 ; i != characters.length; ++i){
					if(characters[i].equals(comboBox.getSelectedItem())){
						characters[i] = "...";
					}else{
						continue;
					}
				}
				break;
			}
		}  
	} 
	
	public 

}