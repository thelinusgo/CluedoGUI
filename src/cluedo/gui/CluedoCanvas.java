package cluedo.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import cluedo.assets.Door;
import cluedo.assets.DoorTile;
import cluedo.assets.Position;
import cluedo.assets.RoomTile;
import cluedo.assets.SolutionTile;
import cluedo.assets.StairsTile;
import cluedo.assets.StartTile;
import cluedo.assets.Tile;
import cluedo.assets.WallTile;
import cluedo.main.CluedoGame;
/**
 * Class that represents the Cluedo Canvas
 * @author Casey and Linus
 *
 */
public class CluedoCanvas extends JPanel {
	/**
	 * Represents the board in 2d array form.
	 */
	private Tile[][] board = new Tile[25][25];

	/**
	 * Stores the list of doors on the board.
	 */
	private List<Door> doors = new ArrayList<Door>();

	/** This helps generating a random shuffle for the lists */
	private long seed = System.nanoTime();

	/**
	 * For checking if player did a valid move or not.
	 */
	private boolean isValidMove = false;

	public CluedoCanvas(){
		CluedoGame game = new CluedoGame();
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	/**
	 * This sets the dimension of the Cluedo Canvas.
	 */
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(800,800);
	}

	public void paint(Graphics g){
		this.initialise();
		
		BufferedImage kitchen = null;
		BufferedImage ballroom = null;
		BufferedImage billiardRoom = null;
		BufferedImage hall = null;
		BufferedImage lounge = null;
		BufferedImage study = null;
		BufferedImage conservatory = null;
		BufferedImage library = null;
		BufferedImage diningRoom = null;
		BufferedImage cluedo = null;
		
		try {
			kitchen = ImageIO.read(new File("kitchenRoom.jpg"));
			ballroom = ImageIO.read(new File("ballroomRoom.jpg"));
			billiardRoom = ImageIO.read(new File("billiardRoomRoom.jpg"));
			hall = ImageIO.read(new File("hallRoom.jpg"));
			lounge = ImageIO.read(new File("loungeRoom.jpg"));
			study = ImageIO.read(new File("studyRoom.jpg"));
			conservatory = ImageIO.read(new File("conservatoryRoom.jpg"));
			library = ImageIO.read(new File("libraryRoom.jpg"));
			diningRoom = ImageIO.read(new File("diningRoomRoom.jpg"));
			cluedo = ImageIO.read(new File("cluedo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(kitchen, 0, Tile.TILESIZE, 590, 590, null);
		this.drawBoard(g);
	}

	private void initialise(){
		this.drawPath();
		this.drawBorder();
		this.drawKitchen();
		this.drawDiningRoom();
		this.drawLounge();
		this.drawHall();
		this.drawStudy();
		this.drawLibrary();
		this.drawBilliard();
		this.drawConservatory();
		this.drawBallRoom();
		this.drawDoors();
		this.drawStart();
		this.drawCluedo();
	}
	
	private void drawPath(){
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board.length; y++){
				board[x][y] = new Tile(x, y);
			}
		}
	}

	/**
	 * Draws the border.
	 */
	private void drawBorder(){
		for(int i = 0; i < this.board.length; i++){
			board[i][0] = new WallTile(i, 0);
			board[i][board.length-1] = new WallTile(i, board.length-1);
		}
		for(int i = 0; i < this.board.length; i++){
			board[0][i] = new WallTile(0, i);
			board[board.length-1][i] = new WallTile(board.length-1, i);
		}
		board[6][1] = new WallTile(6, 1);
		board[17][1] = new WallTile(17, 1);
	}

	/**
	 * Draws the kitchen.
	 */
	private void drawKitchen(){
		int size = 6;
		int x = 0;
		int y = 1;
		/*Boarder*/
		//top
		for(int i = 0; i < size; i++){
			board[i][y] = new WallTile(i, y);
		}
		//bottom
		for(int i = 0; i < size; i++){
			board[i][y+size-1] = new WallTile(i, y+size-1);
		}
		//left
		for(int i = y; i < size+y; i++){
			board[x][i] = new WallTile(x, i);
		}
		//right
		for(int i = y; i < size; i++){
			board[x+size-1][i] = new WallTile(x+size-1, i);
		}
		//area
		for(int i = x+1; i < size-1; i++){
			for(int j = y+1; j < size; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}
		
		board[4][2] = new StairsTile(4, 2);

		Door d = new Door(false, 4, 6, CluedoGame.initializer.kitchen, "^");
		d.setInFront(new Position(4, 7));
		doors.add(d);
	}

	/**
	 * Draws the dining room.
	 */
	private void drawDiningRoom(){
		int width = 8;
		int height = 7;
		int x = 0;
		int y = 9;

		/*Boarder*/
		//top
		for(int i = 0; i < 5; i++){
			board[i][y] = new WallTile(i, y);
		}
		for(int i = 4; i < width; i++){
			board[i][y+1] = new WallTile(i, y+1);
		}

		//bottom
		for(int i = 0; i < width; i++){
			board[i][y+height-1] = new WallTile(i, y+height-1);
		}
		//left
		for(int i = y; i < height + y; i++){
			board[x][i] = new WallTile(x, i);
		}
		//right
		for(int i = y+2; i < height + y; i++){
			board[x+width-1][i] = new WallTile(x+width-1, i);
		}

		//area
		for(int i = x+1; i < 4; i++){
			for(int j = y+1; j < y+height-1; j++){
				board[i][j] = new RoomTile(i, j);

			}
		}

		for(int i = 4; i < width-1; i++){
			for(int j = y+2; j < y+height-1; j++){
				board[i][j] = new RoomTile(i, j);

			}
		}

		Door d1 = new Door(true, width-1, 12, CluedoGame.initializer.diningrm, "<");
		Door d2 = new Door(false, width-2, y+height-1, CluedoGame.initializer.diningrm, "^");
		d1.setInFront(new Position(width, 12));
		d2.setInFront(new Position(width-2, y+height));
		doors.add(d1);
		doors.add(d2);
	}

	/**
	 * Draws the lounge.
	 */
	private void drawLounge(){
		int x = 0;
		int y = 19;
		int width = 7;
		int height = 6;
		/*Boarder*/
		//top
		for(int i = 0; i < width; i++){
			board[i][y] = new WallTile(i, y);
		}

		//bottom
		for(int i = 0; i < width; i++){
			board[i][y+height-1] = new WallTile(i, y+height-1);
		}

		//left
		for(int i = y; i < y+height; i++){
			board[x][i] = new WallTile(x, i);
		}

		//right
		for(int i = y+1; i < y+height; i++){
			board[x+width-1][i] = new WallTile(x+width-1, i);
		}

		//area
		for(int i = x+1; i < width-1; i++){
			for(int j = y+1; j < y+height-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		board[x+1][y+1] = new StairsTile(x+1, y+1);
		
		Door d = new Door(false, x+width-2, y, CluedoGame.initializer.lounge, "v");
		d.setInFront(new Position(x+width-2, y-1));
		doors.add(d);
	}

	/**
	 * Draws the hall.
	 */
	private void drawHall() {
		int x = 9;
		int y = 18;
		int width = 6;
		int height = 7;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new WallTile(i, y);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new WallTile(i, y+height-1);
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = new WallTile(x, i);
		}

		//right
		for(int i = y+1; i < height+y-1; i++){
			board[x+width-1][i] = new WallTile(x+width-1, i);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		Door d1 = new Door(true, x+width-1, y+2, CluedoGame.initializer.hall, "<");
		Door d2 = new Door(true, x+3, y , CluedoGame.initializer.hall, "v");
		Door d3 = new Door(true, x+2, y, CluedoGame.initializer.hall, "v");
		d1.setInFront(new Position(x+width, y+2));
		d2.setInFront(new Position(x+3, y-1));
		d3.setInFront(new Position(x+2, y-1));
		doors.add(d1);
		doors.add(d2);
		doors.add(d3);
	}

	/**
	 * Draws the study room.
	 */
	private void drawStudy() {
		int x = 17;
		int y = 21;
		int width = 8;
		int height = 4;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new WallTile(i, y);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new WallTile(i, y+height-1);
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = new WallTile(x, i);
		}

		//right
		for(int i = y; i < height+y; i++){
			board[x+width-1][i] = new WallTile(x+width-1, i);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		board[x+width-2][y+1] = new StairsTile(x+width-2, y+1);

		Door d = new Door(false, x+1, y, CluedoGame.initializer.study, "v");
		d.setInFront(new Position(x, y-1));
		doors.add(d);
	}

	/**
	 * Draws the library.
	 */
	private void drawLibrary() {
		int x = 18;
		int y = 14;
		int width = 7;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = x+1; i < width+x-1; i++){
			board[i][y] = new WallTile(i, y);
		}

		//bottom
		for(int i = x+1; i < width+x-1; i++){
			board[i][y+height-1] = new WallTile(i, y+height-1);
		}

		//left
		for(int i = y+1; i < height+y-1; i++){
			board[x][i] = new WallTile(x, i);
		}

		//right
		for(int i = y+1; i < height+y-1; i++){
			board[x+width-1][i] = new WallTile(x+width-1, i);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		Door d1 = new Door(true, x, y+2, CluedoGame.initializer.lib,  ">");
		Door d2 = new Door(true, x+3, y, CluedoGame.initializer.lib, "v");
		d1.setInFront(new Position(x-1, y+2));
		d2.setInFront(new Position(x+3, y-1));
		doors.add(d1);
		doors.add(d2);
	}

	/**
	 * Draws the billiard room.
	 */
	private void drawBilliard() {
		int x = 19;
		int y = 8;
		int width = 6;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new WallTile(i, y);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new WallTile(i, y+height-1);
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = new WallTile(x, i);
		}

		//right
		for(int i = y; i < height+y; i++){
			board[x+width-1][i] = new WallTile(x+width-1, i);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		Door d1 = new Door(true, x, y+1, CluedoGame.initializer.billRm, ">");
		Door d2 = new Door(false, x+width-2, y+height-1, CluedoGame.initializer.billRm, "^");
		d1.setInFront(new Position(x+width-2, y+height));
		d2.setInFront(new Position(x-1, y+1));
		doors.add(d1);
		doors.add(d2);
	}

	/**
	 * Draws the conservatory.
	 */
	private void drawConservatory() {
		int x = 18;
		int y = 1;
		int width = 7;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new WallTile(i, y);
		}

		//bottom
		for(int i = x+1; i < width+x-1; i++){
			board[i][y+height-1] = new WallTile(i, y+height-1);
		}

		//left
		for(int i = y; i < height+y-1; i++){
			board[x][i] = new WallTile(x, i);
		}

		//right
		for(int i = y; i < height+y-1; i++){
			board[x+width-1][i] = new WallTile(x+width-1, i);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		board[x+width-2][y+height-1] = new StairsTile(x+width-2, y+height-1);

		Door d = new Door(false, x+1, y+height-1, CluedoGame.initializer.conservatory, "^");
		d.setInFront(new Position(x+1, y+height));
		doors.add(d);
	}

	/**
	 * Draws the ball room.
	 */
	private void drawBallRoom() {
		int x = 8;
		int y = 1;
		int width = 8;
		int height = 7;

		/*Boarder*/
		//top
		for(int i = x+2; i < width+x-2; i++){
			board[i][y] = new WallTile(i, y);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new WallTile(i, y+height-1);
		}

		//left
		for(int i = y+1; i < height+y; i++){
			board[x][i] = new WallTile(x, i);
		}

		//right
		for(int i = y+1; i < height+y; i++){
			board[x+width-1][i] = new WallTile(x+width-1, i);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		Door d1 = new Door(true, x, 5, CluedoGame.initializer.ballRm, ">");
		Door d2 = new Door(true, x+width-1, 5, CluedoGame.initializer.ballRm, "<");
		Door d3 = new Door(false, x+1, y+height-1, CluedoGame.initializer.ballRm, "^");
		Door d4 = new Door(false, x+width-2, y+height-1, CluedoGame.initializer.ballRm, "^");
		d1.setInFront(new Position(x-1, 5));
		d2.setInFront(new Position(x+width, 5));
		d3.setInFront(new Position(x+1, y+height));
		d4.setInFront(new Position(x+width-2, y+height)); 
		doors.add(d1);
		doors.add(d2);
		doors.add(d3);
		doors.add(d4);

		board[x+1][y+1] = new WallTile(x+1, y+1);
		board[x+width-2][y+1] = new WallTile(x+width-2, y+1);
	}

	/**
	 * Draws the solution room.
	 */
	private void drawCluedo() {
		int x = 10;
		int y = 10;
		int width = 6;
		int height = 7;

		//area
		for(int i = x; i < width+x; i++){
			for(int j = y; j < height+y; j++){
				board[i][j] = new SolutionTile(i, j);
			}
		}
	}

	/**
	 * Draws the doors.
	 */
	private void drawDoors(){
		for(int i = 0; i < doors.size(); i++){
			Door d = doors.get(i);
			int x = d.getPosition().getX();
			int y = d.getPosition().getY();
			board[x][y] = new DoorTile(x, y, d);
		}
	}
	
	/**
	 * Draws the start spaces.
	 */
	private void drawStart(){
		board[9][0] = new StartTile(9, 0, CluedoGame.initializer.getCharacters().get(2));
		board[14][0] = new StartTile(14, 0, CluedoGame.initializer.getCharacters().get(1));
		board[0][17] = new StartTile(0, 17, CluedoGame.initializer.getCharacters().get(3));
		board[7][board.length-1] = new StartTile(7, board.length-1, CluedoGame.initializer.getCharacters().get(0));
		board[board.length-1][board.length-6] = new StartTile(board.length-1, board.length-6, CluedoGame.initializer.getCharacters().get(5));
		board[board.length-1][6] = new StartTile(board.length-1, 6, CluedoGame.initializer.getCharacters().get(4));
	}
	
	public void drawBoard(Graphics g){
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board.length; y++){
				board[x][y].draw(g);
			}
		}
	}
	
}
