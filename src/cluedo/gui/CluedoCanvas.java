package cluedo.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import cluedo.assets.Door;
import cluedo.assets.DoorTile;
import cluedo.assets.Position;
import cluedo.assets.RoomTile;
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
		this.drawBoard(g);
		//TODO get rid of this!
		g.setColor(Color.red);
		g.drawRect(0, 0, 25, 25);
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
		int x = 1;
		int y = 0;
		/*Boarder*/
		//top
		for(int i = y; i < size+y; i++){
			board[x][i] = new WallTile(x, i);
		}
		//bottom
		for(int i = y; i < size+y; i++){
			board[x+size-1][i] = new WallTile(x+size-1, i);
		}
		//left
		for(int i = x; i < size+x; i++){
			board[i][y] = new WallTile(i, y);
		}
		//right
		for(int i = x; i < size; i++){
			board[i][y+size-1] = new WallTile(i, y+size-1);
		}
		//area
		for(int i = x+1; i < size-1; i++){
			for(int j = y+1; j < size-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		board[2][4] = new StairsTile(2, 4);

		Door d = new Door(false, 6, 3, CluedoGame.initializer.kitchen, "^");
		d.setInFront(new Position(7, 3));
		doors.add(d);
	}

	/**
	 * Draws the dining room.
	 */
	private void drawDiningRoom(){
		int width = 8;
		int height = 7;
		int x = 9;
		int y = 0;

		/*Boarder*/
		//top
		for(int i = y; i < 5; i++){
			board[x][i] = new WallTile(x, i);
		}
		for(int i = 4; i < width; i++){
			board[x+1][i] = new WallTile(x+1, i);
		}

		//bottom
		for(int i = y; i < width; i++){
			board[x+height-1][i] = new WallTile(x+height-1, i);
		}
		//left
		for(int i = x; i < height + x; i++){
			board[i][y] = new WallTile(i, y);
		}
		//right
		for(int i = x+1; i < height + x; i++){
			board[i][y+width-1] = new WallTile(i, y+width-1);
		}

		//area
		for(int i = x+1; i < x+height-1; i++){
			for(int j = y+1; j < 4; j++){
				board[i][j] = new WallTile(i, j);
			}
		}

		for(int i = 4; i < x+height-1; i++){
			for(int j = y+2; j < width-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		Door d1 = new Door(true, 12, 7, CluedoGame.initializer.diningrm, "<");
		Door d2 = new Door(false, 15, 5, CluedoGame.initializer.diningrm, "^");
		d1.setInFront(new Position(12, 8));
		d2.setInFront(new Position(16, 5));
		doors.add(d1);
		doors.add(d2);
	}


	/**
	 * Draws the lounge.
	 */
	private void drawLounge(){
		int x = 19;
		int y = 0;
		int width = 7;
		int height = 6;
		/*Boarder*/
		//top
		for(int i = y; i < width; i++){
			board[x][i] = new WallTile(x, i);
		}

		//bottom
		for(int i = y; i < width; i++){
			board[x+height-1][i] = new WallTile(x+height-1, i);
		}

		//left
		for(int i = x; i < x+height; i++){
			board[i][y] = new WallTile(i, y);
		}

		//right
		for(int i = x+1; i < x+height; i++){
			board[i][y+width-1] = new WallTile(i, y+width-1);
		}

		//area
		for(int i = x+1; i < x+height-1; i++){
			for(int j = y+1; j < width-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}
		
		board[x+1][y+1] = new StairsTile(x+1, y+1);

		Door d = new Door(false, x, 5, CluedoGame.initializer.lounge, "v");
		d.setInFront(new Position(x-1, 5));
		doors.add(d);
	}


	/**
	 * Draws the hall.
	 */
	private void drawHall() {
		int x = 18;
		int y = 9;
		int width = 6;
		int height = 7;

		/*Boarder*/
		//top
		for(int i = y; i < width+y; i++){
			board[x][i] = new WallTile(x, i);
		}

		//bottom
		for(int i = y; i < width+y; i++){
			board[x+height-1][i] = new WallTile(x+height-1, i);
		}

		//left
		for(int i = x; i < height+x; i++){
			board[i][y] = new WallTile(i, y);
		}

		//right
		for(int i = x; i < height+x; i++){
			board[i][y+width-1] = new WallTile(i, y+width-1);
		}

		//area
		for(int i = x+1; i < height+x-1; i++){
			for(int j = y+1; j < width+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		Door d1 = new Door(true, 20, 14, CluedoGame.initializer.hall, "<");
		Door d2 = new Door(true, x, 11, CluedoGame.initializer.hall, "v");
		Door d3 = new Door(true, x, 12, CluedoGame.initializer.hall, "v");
		d1.setInFront(new Position(20, 15));
		d2.setInFront(new Position(x-1, 11));
		d3.setInFront(new Position(x-1, 12));
		doors.add(d1);
		doors.add(d2);
		doors.add(d3);
	}

	/**
	 * Draws the study room.
	 */
	private void drawStudy() {
		int x = 21;
		int y = 17;
		int width = 8;
		int height = 4;

		/*Boarder*/
		//top
		for(int i = y; i < width+y; i++){
			board[x][i] = new WallTile(x, i);
		}

		//bottom
		for(int i = y; i < width+y; i++){
			board[x+height-1][i] = new WallTile(x+height-1, i);
		}

		//left
		for(int i = x; i < height+x; i++){
			board[i][y] = new WallTile(i, y);
		}

		//right
		for(int i = x; i < height+x; i++){
			board[i][y+width-1] = new WallTile(i, y+width-1);
		}

		//area
		for(int i = x+1; i < height+x-1; i++){
			for(int j = y+1; j < width+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		board[22][23] = new StairsTile(22, 23);

		Door d = new Door(false, x, y+1, CluedoGame.initializer.study, "v");
		d.setInFront(new Position(x-1, y+1));
		doors.add(d);
	}

	/**
	 * Draws the library.
	 */
	private void drawLibrary() {
		int x = 14;
		int y = 18;
		int width = 7;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = y+1; i < width+y-1; i++){
			board[x][i] = new WallTile(x, i);
		}

		//bottom
		for(int i = y+1; i < width+y-1; i++){
			board[x+height-1][i] = new WallTile(x+height-1, i);
		}

		//left
		for(int i = x+1; i < height+x-1; i++){
			board[i][y] = new WallTile(i, y);
		}

		//right
		for(int i = x+1; i < height+x-1; i++){
			board[i][y+width-1] = new WallTile(i, y+width-1);
		}

		//area
		for(int i = x+1; i < height+x-1; i++){
			for(int j = y+1; j < width+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		Door d1 = new Door(true, x, y+2, CluedoGame.initializer.lib,  ">");
		Door d2 = new Door(true, 16, 17, CluedoGame.initializer.lib, "v");
		d1.setInFront(new Position(x-1, y+2));
		d2.setInFront(new Position(x+2, y));
		doors.add(d1);
		doors.add(d2);
	}

	/**
	 * Draws the billiard room.
	 */
	private void drawBilliard() {
		int x = 8;
		int y = 19;
		int width = 6;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = y; i < width+y; i++){
			board[x][i] = new WallTile(x, i);
		}

		//bottom
		for(int i = y; i < width+y; i++){
			board[x+height-1][i] = new WallTile(x+height-1, i);
		}

		//left
		for(int i = x; i < height+x; i++){
			board[i][y] = new WallTile(i, y);
		}

		//right
		for(int i = x; i < height+x; i++){
			board[i][y+width-1] = new WallTile(i, y+width-1);
		}

		//area
		for(int i = x+1; i < height+x-1; i++){
			for(int j = y+1; j < width+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		Door d1 = new Door(true, x+1, y, CluedoGame.initializer.billRm, ">");
		Door d2 = new Door(false, x+height-1, y+width-2, CluedoGame.initializer.billRm, "^");
		d1.setInFront(new Position(13, 23));
		d2.setInFront(new Position(x+1, y-1));
		doors.add(d1);
		doors.add(d2);
	}

	/**
	 * Draws the conservatory.
	 */
	private void drawConservatory() {
		int x = 1;
		int y = 18;
		int width = 7;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = y; i < width+y; i++){
			board[x][i] = new WallTile(x, i);
		}

		//bottom
		for(int i = y+1; i < width+y-1; i++){
			board[x+height-1][i] = new WallTile(x+height-1, i);
		}

		//left
		for(int i = x; i < height+x-1; i++){
			board[i][y] = new WallTile(i, y);
		}

		//right
		for(int i = x; i < height+x-1; i++){
			board[i][y+width-1] = new WallTile(i, y+width-1);
		}

		//area
		for(int i = x+1; i < height+x-1; i++){
			for(int j = y+1; j < width+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		board[5][23] = new StairsTile(5, 23);

		Door d = new Door(false, x+height-1, y+1, CluedoGame.initializer.conservatory, "^");
		d.setInFront(new Position(x+height, y+1));
		doors.add(d);
	}

	/**
	 * Draws the ball room.
	 */
	private void drawBallRoom(){
		int x = 0;
		int y = 8;
		int width = 8;
		int height = 8;

		/*Boarder*/
		//top
		for(int i = y+2; i < width+y-2; i++){
			board[x][i] = new WallTile(x, i);
		}

		//bottom
		for(int i = y; i < width+y; i++){
			board[x+height-1][i] = new WallTile(x+height-1, i);
		}

		//left
		for(int i = x+1; i < height+x; i++){
			board[i][y] = new WallTile(i, y);
		}

		//right
		for(int i = x+1; i < height+x; i++){
			board[i][y+width-1] = new WallTile(i, y+width-1);
		}

		//area
		for(int i = x+3; i < height+x-1; i++){
			for(int j = y+1; j < width+y-1; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}
		for(int i = x+1; i <= x+2; i++){
			for(int j = y+3; j <= y+4; j++){
				board[i][j] = new RoomTile(i, j);
			}
		}

		Door d1 = new Door(true, x+5, y, CluedoGame.initializer.ballRm, ">");
		Door d2 = new Door(true, x+5, y+width-1, CluedoGame.initializer.ballRm, "<");
		Door d3 = new Door(false, x+height-1, y+1, CluedoGame.initializer.ballRm, "^");
		Door d4 = new Door(false, x+height-1, y+width-2, CluedoGame.initializer.ballRm, "^");
		d1.setInFront(new Position(x+5, y-1));
		d2.setInFront(new Position(x+5, y+width));
		d3.setInFront(new Position(x+height, y+1));
		d4.setInFront(new Position(x+height, y+width-2)); 
		doors.add(d1);
		doors.add(d2);
		doors.add(d3);
		doors.add(d4);

		board[10][2] = new WallTile(10, 2);
		board[11][2] = new WallTile(11, 2);
		board[14][2] = new WallTile(14, 2);
		board[15][2] = new WallTile(15, 2);
		board[11][1] = new WallTile(11, 1);
		board[14][1] = new WallTile(14, 1);
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
				board[i][j] = new WallTile(i, j);
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
		board[0][9] = new StartTile(0, 9, CluedoGame.initializer.getCharacters().get(2));
		board[0][14] = new StartTile(0, 14, CluedoGame.initializer.getCharacters().get(1));
		board[17][0] = new StartTile(17, 0, CluedoGame.initializer.getCharacters().get(3));
		board[board.length-1][7] = new StartTile(board.length-1, 7, CluedoGame.initializer.getCharacters().get(0));
		board[board.length-6][board.length-1] = new StartTile(board.length-6, board.length-1, CluedoGame.initializer.getCharacters().get(5));
		board[6][board.length-1] = new StartTile(6, board.length-1, CluedoGame.initializer.getCharacters().get(4));
	}
	
	public void drawBoard(Graphics g){
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board.length; y++){
				board[x][y].draw(g);
			}
		}
	}
	
}
