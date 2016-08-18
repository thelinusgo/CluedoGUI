package cluedo.gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import cluedo.assets.Player;
import cluedo.assets.Position;
import cluedo.assets.Room;
import cluedo.assets.tiles.DoorTile;
import cluedo.assets.tiles.RoomTile;
import cluedo.assets.tiles.SolutionTile;
import cluedo.assets.tiles.StairsTile;
import cluedo.assets.tiles.StartTile;
import cluedo.assets.tiles.Tile;
import cluedo.assets.tiles.WallTile;
import cluedo.main.CluedoGame;
import cluedo.main.CluedoGame.InvalidMove;
/**
 * Class that represents the Cluedo Canvas
 * @author Casey and Linus
 *
 */
public class CluedoCanvas extends JPanel{
	private Rectangle kitchen;
	private Rectangle diningRoom;
	private Rectangle lounge;
	private Rectangle hall;
	private Rectangle study;
	private Rectangle library;
	private Rectangle billiardRm;
	private Rectangle conservatory;
	private Rectangle ballRm;
	private Rectangle cluedo;
	/**
	 * Represents the board in 2d array form.
	 */
	public Tile[][] board = new Tile[25][25];

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

	/**
	 * Pattern for determining the direction of the wall
	 */
	private static String direction = "top|bottom|right|left";
	
	//Setup images
	private BufferedImage cluedoIm = null;
	private BufferedImage kitchenIm = null;
	private BufferedImage ballroomIm = null;
	private BufferedImage billiardRoomIm = null;
	private BufferedImage hallIm = null;
	private BufferedImage loungeIm = null;
	private BufferedImage studyIm = null;
	private BufferedImage conservatoryIm = null;
	private BufferedImage libraryIm = null;
	private BufferedImage diningRoomIm = null;

	public CluedoCanvas(){
		this.initialise();
		
		try {
			cluedoIm = ImageIO.read(new File("cluedo.jpg"));
			kitchenIm = ImageIO.read(new File("kitchenRoom.jpg"));
			ballroomIm = ImageIO.read(new File("ballroomRoom.jpg"));
			billiardRoomIm = ImageIO.read(new File("billiardRoomRoom.jpg"));
			hallIm = ImageIO.read(new File("hallRoom.jpg"));
			loungeIm = ImageIO.read(new File("loungeRoom.jpg"));
			studyIm = ImageIO.read(new File("studyRoom.jpg"));
			conservatoryIm = ImageIO.read(new File("conservatoryRoom.jpg"));
			libraryIm = ImageIO.read(new File("libraryRoom.jpg"));
			diningRoomIm = ImageIO.read(new File("diningRoomRoom.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		int x = 38;
		int y = 25;
		g.setColor(new Color(128, 189, 147));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(15));
		g.drawRect(32, 20, 25*Tile.TILESIZE+10, 25*Tile.TILESIZE+10);
		g2.setStroke(new BasicStroke(0));
		//Draw images of rooms
		g.drawImage(kitchenIm, x+kitchen.x*Tile.TILESIZE, y+kitchen.y*Tile.TILESIZE, kitchen.width*Tile.TILESIZE, kitchen.height*Tile.TILESIZE, null);
		g.drawImage(ballroomIm, x+ballRm.x*Tile.TILESIZE, y+ballRm.y*Tile.TILESIZE, ballRm.width*Tile.TILESIZE, ballRm.height*Tile.TILESIZE, null);
		g.drawImage(diningRoomIm, x+diningRoom.x*Tile.TILESIZE, y+diningRoom.y*Tile.TILESIZE, diningRoom.width*Tile.TILESIZE, diningRoom.height*Tile.TILESIZE, null);
		g.drawImage(loungeIm, x+lounge.x*Tile.TILESIZE, y+lounge.y*Tile.TILESIZE, lounge.width*Tile.TILESIZE, lounge.height*Tile.TILESIZE, null);
		g.drawImage(hallIm, x+hall.x*Tile.TILESIZE, y+hall.y*Tile.TILESIZE, hall.width*Tile.TILESIZE, hall.height*Tile.TILESIZE+9, null);
		g.drawImage(studyIm, x+study.x*Tile.TILESIZE, y+study.y*Tile.TILESIZE, study.width*Tile.TILESIZE, study.height*Tile.TILESIZE, null);
		g.drawImage(libraryIm, x+library.x*Tile.TILESIZE, y+library.y*Tile.TILESIZE, library.width*Tile.TILESIZE+8, library.height*Tile.TILESIZE, null);
		g.drawImage(billiardRoomIm, x+billiardRm.x*Tile.TILESIZE, y+billiardRm.y*Tile.TILESIZE, billiardRm.width*Tile.TILESIZE+10, billiardRm.height*Tile.TILESIZE, null);
		g.drawImage(conservatoryIm, x+conservatory.x*Tile.TILESIZE, y+conservatory.y*Tile.TILESIZE, conservatory.width*Tile.TILESIZE, conservatory.height*Tile.TILESIZE, null);
		g.drawImage(cluedoIm, x+this.cluedo.x*Tile.TILESIZE, y+this.cluedo.y*Tile.TILESIZE, cluedo.width*Tile.TILESIZE, cluedo.height*Tile.TILESIZE, null);
		this.drawBoard(g);
	}

	private void initialise(){
		this.drawPath();
		this.drawBorder();
		this.kitchen = this.drawKitchen();
		this.diningRoom = this.drawDiningRoom();
		this.lounge = this.drawLounge();
		this.hall = this.drawHall();
		this.study = this.drawStudy();
		this.library = this.drawLibrary();
		this.billiardRm = this.drawBilliard();
		this.conservatory = this.drawConservatory();
		this.ballRm = this.drawBallRoom();
		this.drawDoors();
		this.drawStart();
		this.cluedo = this.drawCluedo();
		board[0][7] = new Tile(0, 7);
		board[16][24] = new Tile(16, 24);
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
	private Rectangle drawKitchen(){
		int size = 6;
		int x = 0;
		int y = 1;
		/*Boarder*/
		//top
		for(int i = 0; i < size; i++){
			board[i][y] = new RoomTile(i, y, "top", null);
		}
		//bottom
		for(int i = 0; i < size; i++){
			board[i][y+size-1] = new RoomTile(i, y+size-1, "bottom", null);
		}
		//left
		for(int i = y; i < size+y; i++){
			board[x][i] = new RoomTile(x, i, "left", null);
		}
		//right
		for(int i = y; i < size; i++){
			board[x+size-1][i] = new RoomTile(x+size-1, i, "right", null);
		}
		//area
		for(int i = x+1; i < size-1; i++){
			for(int j = y+1; j < size; j++){
				board[i][j] = new RoomTile(i, j, null, null);
			}
		}

		board[x+size-1][y] = new StairsTile(x+size-1, y, "right", "top");

		Door d = new Door(false, 4, 6, CluedoGame.initializer.kitchen, "^");
		d.setInFront(new Position(4, 7));
		doors.add(d);

		board[x][y] = new RoomTile(x, y, "top", "left");
		board[x][y+size-1] = new RoomTile(x, y+size-1, "bottom", "left");
		board[x+size-1][y+size-1] = new RoomTile(x+size-1, y+size-1, "bottom", "right");

		return new Rectangle(x, y, size, size);
	}

	/**
	 * Draws the dining room.
	 */
	private Rectangle drawDiningRoom(){
		int width = 8;
		int height = 7;
		int x = 0;
		int y = 9;

		/*Boarder*/
		//top
		for(int i = 0; i < 5; i++){
			board[i][y] = new RoomTile(i, y, "top", null);
		}
		for(int i = 5; i < width; i++){
			board[i][y+1] = new RoomTile(i, y+1, "top", null);
		}

		//bottom
		for(int i = 0; i < width; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null);
		}
		//left
		for(int i = y; i < height + y; i++){
			board[x][i] = new RoomTile(x, i, "left", null);
		}
		//right
		for(int i = y+2; i < height + y; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null);
		}

		//area
		for(int i = x+1; i < 4; i++){
			for(int j = y+1; j < y+height-1; j++){
				board[i][j] = new RoomTile(i, j, null, null);
			}
		}

		for(int i = 4; i < width-1; i++){
			for(int j = y+2; j < y+height-1; j++){
				board[i][j] = new RoomTile(i, j, null, null);
			}
		}
		board[4][y+1] = new RoomTile(4, y+1, null, null);

		Door d1 = new Door(true, width-1, 12, CluedoGame.initializer.diningrm, "<");
		Door d2 = new Door(false, width-2, y+height-1, CluedoGame.initializer.diningrm, "^");
		d1.setInFront(new Position(width, 12));
		d2.setInFront(new Position(width-2, y+height));
		doors.add(d1);
		doors.add(d2);

		board[x][y] = new RoomTile(x, y, "top", "left");
		board[x][y+height-1] = new RoomTile(x, y+height-1, "bottom", "left");
		board[4][y] = new RoomTile(4, y, "top", "right");
		board[x+width-1][y+1] = new RoomTile(x+width-1, y+1, "top", "right");
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "bottom", "right");

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the lounge.
	 */
	private Rectangle drawLounge(){
		int x = 0;
		int y = 19;
		int width = 7;
		int height = 6;
		/*Boarder*/
		//top
		for(int i = 0; i < width; i++){
			board[i][y] = new RoomTile(i, y, "top", null);
		}

		//bottom
		for(int i = 0; i < width; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null);
		}

		//left
		for(int i = y; i < y+height; i++){
			board[x][i] = new RoomTile(x, i, "left", null);
		}

		//right
		for(int i = y+1; i < y+height; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null);
		}

		//area
		for(int i = x+1; i < width-1; i++){
			for(int j = y+1; j < y+height-1; j++){
				board[i][j] = new RoomTile(i, j, null, null);
			}
		}

		board[x][y] = new StairsTile(x, y, "top", "left");

		Door d = new Door(false, x+width-1, y, CluedoGame.initializer.lounge, "v");
		d.setInFront(new Position(x+width-1, y-1));
		board[x+width-1][y] = new DoorTile(x+width-1, y, d, "right");

		board[x][y+height-1] = new RoomTile(x, y+height-1, "bottom", "left");
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "bottom", "right");

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the hall.
	 */
	private Rectangle drawHall() {
		int x = 9;
		int y = 18;
		int width = 6;
		int height = 7;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new RoomTile(i, y, "top", null);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null);
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = new RoomTile(x, i, "left", null);
		}

		//right
		for(int i = y+1; i < height+y-1; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null);
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

		board[x][y] = new RoomTile(x, y, "top", "left");
		board[x+width-1][y] = new RoomTile(x+width-1, y, "top", "right");
		board[x][y+height-1] = new RoomTile(x, y+height-1, "left", "bottom");
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "right", "bottom");

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the study room.
	 */
	private Rectangle drawStudy() {
		int x = 17;
		int y = 21;
		int width = 8;
		int height = 4;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new RoomTile(i, y, "top", null);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null);
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = new RoomTile(x, i, "left", null);
		}

		//right
		for(int i = y; i < height+y; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null);
			}
		}

		board[x+width-1][y] = new StairsTile(x+width-1, y, "top", "right");

		Door d = new Door(false, x, y, CluedoGame.initializer.study, "v");
		d.setInFront(new Position(x, y-1));
		board[x][y] = new DoorTile(x, y, d, "left");

		board[x][y+height-1] = new RoomTile(x, y+height-1, "left", "bottom");
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "bottom", "right");

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the library.
	 */
	private Rectangle drawLibrary() {
		int x = 18;
		int y = 14;
		int width = 7;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = x+1; i < width+x-1; i++){
			board[i][y] = new RoomTile(i, y, "top", null);
		}

		//bottom
		for(int i = x+1; i < width+x-1; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null);
		}

		//left
		for(int i = y+1; i < height+y-1; i++){
			board[x][i] = new RoomTile(x, i, "left", null);
		}

		//right
		for(int i = y+1; i < height+y-1; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null);
			}
		}

		Door d1 = new Door(true, x, y+2, CluedoGame.initializer.lib,  ">");
		Door d2 = new Door(true, x+3, y, CluedoGame.initializer.lib, "v");
		d1.setInFront(new Position(x-1, y+2));
		d2.setInFront(new Position(x+3, y-1));
		doors.add(d1);
		doors.add(d2);

		board[x][y+1] = new RoomTile(x, y+1, "top", "left");
		board[x+1][y] = new RoomTile(x+1, y, "top", "left");
		board[x][y+height-2] = new RoomTile(x, y+height-2, "left", "bottom");
		board[x+1][y+height-1] = new RoomTile(x+1, y+height-1, "left", "bottom");
		board[x+width-2][y] = new RoomTile(x+width-2, y, "top", "right");
		board[x+width-1][y+1] = new RoomTile(x+width-1, y+1, "top", "right");
		board[x+width-1][y+height-2] = new RoomTile(x+width-1, y+height-2, "bottom", "right");
		board[x+width-2][y+height-1] = new RoomTile(x+width-2, y+height-1, "bottom", "right");

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the billiard room.
	 */
	private Rectangle drawBilliard() {
		int x = 19;
		int y = 8;
		int width = 6;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new RoomTile(i, y, "top", null);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null);
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = new RoomTile(x, i, "left", null);
		}

		//right
		for(int i = y; i < height+y; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null);
			}
		}

		Door d1 = new Door(true, x, y+1, CluedoGame.initializer.billRm, ">");
		Door d2 = new Door(false, x+width-2, y+height-1, CluedoGame.initializer.billRm, "^");
		d1.setInFront(new Position(x+width-2, y+height));
		d2.setInFront(new Position(x-1, y+1));
		doors.add(d1);
		doors.add(d2);

		board[x][y] = new RoomTile(x, y, "top", "left");
		board[x][y+height-1] = new RoomTile(x, y+height-1, "bottom", "left");
		board[x+width-1][y] = new RoomTile(x+width-1, y, "top", "right");
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "bottom", "right");

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the conservatory.
	 */
	private Rectangle drawConservatory() {
		int x = 18;
		int y = 1;
		int width = 7;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new RoomTile(i, y, "top", null);
		}

		//bottom
		for(int i = x+1; i < width+x-1; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null);
		}

		//left
		for(int i = y; i < height+y-1; i++){
			board[x][i] = new RoomTile(x, i, "left", null);
		}

		//right
		for(int i = y; i < height+y-1; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null);
			}
		}

		board[x+width-2][y+height-1] = new StairsTile(x+width-2, y+height-1, "bottom", "right");

		Door d = new Door(true, x+1, y+height-1, CluedoGame.initializer.conservatory, "^");
		d.setInFront(new Position(x+1, y+height));
		board[x+1][y+height-1] = new DoorTile(x+1, y+height-1, d, "bottom");

		board[x][y] = new RoomTile(x, y, "top", "left");
		board[x+width-1][y] = new RoomTile(x+width-1, y, "top", "right");
		board[x+width-1][y+height-2] = new RoomTile(x+width-1, y+height-2, "bottom", "right");
		board[x][y+height-2] = new RoomTile(x, y+height-2, "bottom", "left");

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the ball room.
	 */
	private Rectangle drawBallRoom() {
		int x = 8;
		int y = 0;
		int width = 8;
		int height = 8;

		/*Boarder*/
		//top
		for(int i = x+2; i < width+x-2; i++){
			board[i][y] = new RoomTile(i, y, "top", null);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1,"bottom", null);
		}

		//left
		for(int i = y+2; i < height+y; i++){
			board[x][i] = new RoomTile(x, i, "left", null);
		}

		//right
		for(int i = y+2; i < height+y; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+2; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null);
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

		board[x+2][y+1] = new RoomTile(x+2, y+1, "left", null);
		board[x+3][y+1] = new RoomTile(x+3, y+1, null, null);
		board[x+4][y+1] = new RoomTile(x+4, y+1, null, null);
		board[x+5][y+1] = new RoomTile(x+5, y+1, "right", null);
		board[x+1][y+2] = new RoomTile(x+1, y+2, "top", null);
		board[x+width-2][y+2] = new RoomTile(x+width-2, y+2, "top", null);
		board[x][y+2] = new RoomTile(x, y+2, "top", "left");
		board[x+width-1][y+2] = new RoomTile(x+width-1, y+2, "top", "right");
		board[x][y+height-1] = new RoomTile(x, y+height-1, "bottom", "left");
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "bottom", "right");
		board[x+2][y] = new RoomTile(x+2, y, "top", "left");
		board[x+width-3][y] = new RoomTile(x+width-3, y, "top", "right");

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the solution room.
	 */
	private Rectangle drawCluedo() {
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

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the doors.
	 */
	private void drawDoors(){
		for(int i = 0; i < doors.size(); i++){
			Door d = doors.get(i);
			int x = d.getPosition().getX();
			int y = d.getPosition().getY();
			board[x][y] = new DoorTile(x, y, d, null);
		}
	}

	/**
	 * Draws the start spaces.
	 */
	private void drawStart(){
		board[9][0] = new StartTile(9, 0, CluedoGame.initializer.getCharacters().get(2));
		board[14][0] = new StartTile(14, 0, CluedoGame.initializer.getCharacters().get(3));
		board[0][17] = new StartTile(0, 17, CluedoGame.initializer.getCharacters().get(1));
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

	/**
	 * Initializes each player's position.
	 * @param list of currentPlayers
	 */
	public void setPlayerPosition(List<Player> currentPlayers){
		for(Player p : currentPlayers){
			int x = p.getCharacter().getStartPos().getX();
			int y = p.getCharacter().getStartPos().getY();
			board[x][y].setPlayer(p);
			p.setPos(x, y);
		}
	}

	/**
	 * Moves Player.
	 * @param directionX
	 * @param directionY
	 * @param p
	 * @param currentPlayers
	 * @throws InvalidMove
	 */
	public void move(int directionX, int directionY, Player p, List<Player> currentPlayers, Graphics g) throws InvalidMove{
		List<Room> rooms = CluedoGame.initializer.getRooms();
		int x = p.position().getX() + directionX;
		int y = p.position().getY() + directionY;
		if(isValidMove(new Position(x, y), directionX, directionY, p, currentPlayers)){
			isValidMove = true;
			board[p.position().getX()][p.position().getY()].setPlayer(null);
			if(p.door() != null){
				Room r = p.door().getRoom();
				r.addPlayer(p);
				p.setRoom(r);
				p.setIsInRoom(true);
			}else{
				p.setPos(x, y);
			}
			p.moveNStep(directionX, directionY);
			board[p.position().getX()][p.position().getY()].setPlayer(p);
		}else{
			isValidMove = false;
		}
		this.paint(g);
	}

	/**
	 * Moves player to another room.
	 * @param p
	 * @param rm
	 */
	public void moveToRoom(Player p, Room rm){
		if(p.position() != null){
			board[p.position().getX()][p.position().getY()].setPlayer(null);
			p.getRoom().removePlayer(p);
		}
		p.setRoom(rm);
		p.getRoom().addPlayer(p);
		board[p.position().getX()][p.position().getY()].setPlayer(p);
	}

	/**
	 * Make player exit room.
	 * @param p
	 * @param currentPlayers
	 */
	public void exitRoom(Player p, List<Player> currentPlayers){
		int x = p.position().getX();
		int y = p.position().getY();
		board[p.position().getX()][p.position().getY()].setPlayer(null);
		for(Player player : currentPlayers){
			if(player != p){
				if(player.position().getX() != p.door().getInFront().getX() && player.position().getY() != p.door().getInFront().getY()){
					x = p.door().getInFront().getX();
					y = p.door().getInFront().getY();
				}
			}
		}
		p.setPos(x, y);
		board[p.position().getX()][p.position().getY()].setPlayer(p);
		p.getRoom().removePlayer(p);
		p.setIsInRoom(false);
		p.setRoom(null);
		p.setDoor(null);
		if(x == p.position().getX() && y == p.position().getY()){
			System.out.println("Cannot exit room.");
		}
	}

	/**
	 * Checks if player is doing a valid move. If they are not, then it returns false, else it returns true.
	 * 
	 * @param x - position of player after being moved
	 * @param y - position of player after being moved
	 * @param directionX - how much the player is moving by in the x direction
	 * @param directionY - how much the player is moving by in the y direction
	 * @param p - current player
	 * @param currentPlayers - list of current players
	 * @return
	 */
	public boolean isValidMove(Position position, int directionX, int directionY, Player p, List<Player> currentPlayers){
		int x = position.getX();
		int y = position.getY();
		try {
			if(x > 24 || x < 0 || y > 24 || y < 0){
				throw new CluedoGame.InvalidMove("Cannot go out of bounds!");
			}else if(board[x][y] instanceof WallTile){
				throw new CluedoGame.InvalidMove("Cannot move into wall.");
			}else if(board[x][y] instanceof RoomTile){
				if(((RoomTile) board[x][y]).getDir().equals(direction)){
					throw new CluedoGame.InvalidMove("Cannot move into wall.");
				}
				if(((RoomTile) board[x][y]).getDir2().equals(direction)){
					throw new CluedoGame.InvalidMove("Cannot move into wall.");
				}
			}else if((board[x][y] instanceof StairsTile) && !p.isInRoom()){
				throw new CluedoGame.InvalidMove("Player is not in room to take the stairs.");
			}else if(board[x][y] instanceof SolutionTile){
				throw new CluedoGame.InvalidMove("Cannot move into solution space");
			}else{
				for(Door d : doors){
					if(!d.isHorizontal() && x == d.getPosition().getX() && y == d.getPosition().getY() && (directionX > 0 || directionX < 0) && directionY == 0){
						throw new CluedoGame.InvalidMove("Going through door the wrong way!");
					}else if(d.isHorizontal() && x == d.getPosition().getX() && y == d.getPosition().getY() && directionX == 0 && (directionY > 0 || directionY < 0)){
						throw new CluedoGame.InvalidMove("Going through door the wrong way!");
					}else if(x == d.getPosition().getX() && y == d.getPosition().getY()){
						p.setDoor(d);
						break;
					}
				}

				for(Position pos : p.coordinatesTaken()){
					if(pos.getX() == x && pos.getY() == y){
						throw new CluedoGame.InvalidMove("You cannot move into the same square within this move.");
					}
				}

				for(Player player : currentPlayers){
					if(!player.getName().equals(p.getName())){
						if(position.equals(player.position())){
							throw new CluedoGame.InvalidMove("Cannot move into existing player's square!");
						}

					}
				}
			}
		} catch (InvalidMove e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Determines if player can still move or not.
	 * @param p
	 * @return
	 *//*
	public boolean canMove(Player p){
		for(Position position : p.getPossibleCoords()){
			if(position != null){
				if(!p.coordinatesTaken().contains(position)){
					return true;
				}
			}
		}
		return false;
	}

	*//**
	 * Returns true if player has valid possible moves.
	 * @param pos
	 * @param p
	 * @return
	 *//*
	public boolean validPos(Position pos, Player p){
		int x = pos.getX();
		int y = pos.getY();
		if(x > 24 || x < 0 || y > 24 || y < 0){
			return false;
		}else if(board[x][y].equals("|#|") || board[x][y].equals("#|") || board[x][y].equals("|X|") || board[x][y].equals("X|")){
			return false;
		}
		return true;
	}*/
}
