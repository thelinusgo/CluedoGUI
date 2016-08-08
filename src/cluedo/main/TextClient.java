package cluedo.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import cluedo.arguments.Accusation;
import cluedo.arguments.Argument;
import cluedo.arguments.Suggestion;
import cluedo.assets.Player;
import cluedo.cards.*;
import cluedo.main.CluedoGame.InvalidMove;

/**
 * Reads user input and feeds the data back to the main game.
 * 
 * @author Casey
 *
 */
public class TextClient {
	private static Board board = CluedoGame.board;

	private static String MOVES = "w|a|s|d"; //pattern describing the possible moves.
	private static String OPTIONS = "m|s|a|c|d"; //patterns describing the possible options.
	private static String CHOICE = "y|n"; //pattern for the user's choice

	private static boolean correctInput = false; //set to true if the correct input has been provided.

	private static Scanner sc = new Scanner(System.in); //field containing the scanner.
	
	/**
	 * Get string from System.in
	 * @return
	 */
	public static String inputString() {
		String choice = "z";
		while(!choice.matches(CHOICE)){
			choice = sc.next();
			if(!choice.matches(CHOICE)){
				System.out.println("Not a choice. Use lower case letters.");
			}
		}
		return choice;
	}

	/**
	 * Listen for direction movement. W, S, A, D are valid directions.
	 * 
	 * @param p
	 * @param currentPlayers
	 * @throws InvalidMove
	 */
	public static void movementListener(Player p, List<Player> currentPlayers) throws InvalidMove{
		String dir = "z";
		System.out.println("Please enter the letters W, A, S or D to move.");
		System.out.println("W - Up");
		System.out.println("A - Left");
		System.out.println("S - Down");
		System.out.println("D - Right\n");
		boolean isValidMove = false;
		correctInput = false;

		while(!correctInput && !isValidMove){
			dir = sc.next();
			if(dir.matches(MOVES)){
				switch(dir){
				case "w":
					board.move(0, -1, p, currentPlayers);
					break;
				case "s": 
					board.move(0, 1, p, currentPlayers);
					break;
				case "a": 
					board.move(-1, 0, p, currentPlayers);
					break;
				case "d": 
					board.move(1, 0, p, currentPlayers);
					break;
				}
				if(board.isValidMove()){
					isValidMove = true;
					correctInput = true;
				}else{
					System.out.println("Please try again.");
				}
			}else{
				System.out.println("That is not a valid direction!");
			}
		}
	}

	/**
	 * Asks the player for which index of a list they want to choose.
	 * @param list
	 * @return
	 */
	public static int askIndex(List<? extends Card> list){
		String indexChoice = "z";
		boolean isInteger = false;
		while(!isInteger){
			indexChoice = sc.next();
			if(isInteger(indexChoice)){
				if(Integer.parseInt(indexChoice) < 0 || Integer.parseInt(indexChoice) > list.size()-1){
					System.out.println("Incorrect index. Please try again.");
				}else{
					isInteger = true;
				}
			}
		}
		return Integer.parseInt(indexChoice);
	}

	/**
	 * Ask for initial players, and their names.
	 */
	public static void askPlayers(){
		/*Local variables */
		String amount = "z";
		String singleName = "";

		boolean correctInput = false;
		boolean isInteger = false;
		/*Now, ask for user input. */
		while(!correctInput && !isInteger){
			System.out.println("How many players would you like?");
			amount = sc.next();
			if(isInteger(amount)){
				if(Integer.parseInt(amount) > 6 || Integer.parseInt(amount) < 3){
					System.out.println("Cluedo needs 3-6 players.");
				}else{
					correctInput = true;
					isInteger = true;
				}
			}
		}
		for(int i = 0 ; i != Integer.parseInt(amount); ++i){
			System.out.println("Please enter Player "  + String.valueOf(i+1) + "'s name");
			singleName = sc.next();
			CluedoGame.addPlayer(new Player(singleName));
		}
		System.out.println("Please note that every player will be assigned a random character.");
		CluedoGame.askSuccess = true;
	}

	/**
	 * Ask player what they want to do, i.e. show cards, move, make a suggestion or make an accusation.
	 * @return
	 */
	public static String askOption(){
		String option = "z";
		System.out.println("What do you want to do?");
		System.out.println("Press M to make a move.");
		System.out.println("Press S to make a suggestion.");
		System.out.println("Press A to make an accusation.");
		System.out.println("Press C to show current cards.");
		System.out.println("Press D to display previous players' cards.");
		while(!option.matches(OPTIONS)){
			option = sc.next();
			if(!option.matches(OPTIONS)){
				System.out.println("Not an option. Use lower case letters.");
			}
		}
		return option;
	}

	/**
	 * Determines whether the user has inputed an integer.
	 * @param input
	 * @return
	 */
	public static boolean isInteger(String input){
		try{
			Integer.parseInt( input );
			return true;
		}catch(Exception e){
			return false;
		}
	}

}