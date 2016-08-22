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
import javax.swing.JOptionPane;
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
import cluedo.main.CluedoGameController;
import cluedo.main.CluedoGameController.InvalidMove;
/**
 * Class that represents the Cluedo Canvas (Board)
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

	private Graphics g;
	/**
	 * Represents the board in 2d array form.
	 */
	private Tile[][] board = new Tile[24][25];

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

	/**
	 * x and y coordinates of where the board starts drawing on the canvas
	 */
	private final int xCanvas = 38;
	private final int yCanvas = 25;

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

	/**
	 * Paints the board.
	 */
	public void paint(Graphics g){
		this.g = g;
		g.setColor(new Color(128, 189, 147));
		g.fillRect(30, 20, 24*Tile.TILESIZE+25, 25*Tile.TILESIZE+20);
		//Draw images of rooms
		g.drawImage(kitchenIm, xCanvas+kitchen.x*Tile.TILESIZE, yCanvas+kitchen.y*Tile.TILESIZE, kitchen.width*Tile.TILESIZE, kitchen.height*Tile.TILESIZE, null);
		g.drawImage(ballroomIm, xCanvas+ballRm.x*Tile.TILESIZE, yCanvas+ballRm.y*Tile.TILESIZE, ballRm.width*Tile.TILESIZE, ballRm.height*Tile.TILESIZE, null);
		g.drawImage(diningRoomIm, xCanvas+diningRoom.x*Tile.TILESIZE, yCanvas+diningRoom.y*Tile.TILESIZE, diningRoom.width*Tile.TILESIZE, diningRoom.height*Tile.TILESIZE, null);
		g.drawImage(loungeIm, xCanvas+lounge.x*Tile.TILESIZE, yCanvas+lounge.y*Tile.TILESIZE, lounge.width*Tile.TILESIZE, lounge.height*Tile.TILESIZE, null);
		g.drawImage(hallIm, xCanvas+hall.x*Tile.TILESIZE, yCanvas+hall.y*Tile.TILESIZE, hall.width*Tile.TILESIZE, hall.height*Tile.TILESIZE+9, null);
		g.drawImage(studyIm, xCanvas+study.x*Tile.TILESIZE, yCanvas+study.y*Tile.TILESIZE, study.width*Tile.TILESIZE, study.height*Tile.TILESIZE, null);
		g.drawImage(libraryIm, xCanvas+library.x*Tile.TILESIZE, yCanvas+library.y*Tile.TILESIZE, library.width*Tile.TILESIZE+8, library.height*Tile.TILESIZE, null);
		g.drawImage(billiardRoomIm, xCanvas+billiardRm.x*Tile.TILESIZE, yCanvas+billiardRm.y*Tile.TILESIZE, billiardRm.width*Tile.TILESIZE+10, billiardRm.height*Tile.TILESIZE, null);
		g.drawImage(conservatoryIm, xCanvas+conservatory.x*Tile.TILESIZE, yCanvas+conservatory.y*Tile.TILESIZE, conservatory.width*Tile.TILESIZE+10, conservatory.height*Tile.TILESIZE, null);
		g.drawImage(cluedoIm, xCanvas+this.cluedo.x*Tile.TILESIZE, yCanvas+this.cluedo.y*Tile.TILESIZE, cluedo.width*Tile.TILESIZE, cluedo.height*Tile.TILESIZE, null);
		this.drawBoard();
	}

	/**
	 * Initialises the 2D array of Tiles.
	 */
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

	}

	/**
	 * Draws the path.
	 */
	private void drawPath(){
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board[0].length; y++){
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
			board[i][board[0].length-1] = new WallTile(i, board[0].length-1);
		}
		for(int i = 0; i < this.board[0].length; i++){
			board[0][i] = new WallTile(0, i);
			board[board.length-1][i] = new WallTile(board.length-1, i);
		}
		board[6][1] = new WallTile(6, 1);
		board[17][1] = new WallTile(17, 1);
	}

	/**
	 * Draws the kitchen.
	 * @return
	 */
	private Rectangle drawKitchen(){
		int size = 6;
		int x = 0;
		int y = 1;
		/*Boarder*/
		//top
		for(int i = 0; i < size; i++){
			board[i][y] = new RoomTile(i, y, "top", null, CluedoGameController.initializer.kitchen);
		}
		//bottom
		for(int i = 0; i < size; i++){
			board[i][y+size-1] = new RoomTile(i, y+size-1, "bottom", null, CluedoGameController.initializer.kitchen);
		}
		//left
		for(int i = y; i < size+y; i++){
			board[x][i] = new RoomTile(x, i, "left", null, CluedoGameController.initializer.kitchen);
		}
		//right
		for(int i = y; i < size; i++){
			board[x+size-1][i] = new RoomTile(x+size-1, i, "right", null, CluedoGameController.initializer.kitchen);
		}
		//area
		for(int i = x+1; i < size-1; i++){
			for(int j = y+1; j < size; j++){
				board[i][j] = new RoomTile(i, j, null, null, CluedoGameController.initializer.kitchen);
			}
		}

		board[x+size-1][y] = new StairsTile(x+size-1, y, "right", "top");
		Door d = new Door(false, 4, 6, CluedoGameController.initializer.kitchen, "^");
		d.setInFront(new Position(4, 7));
		CluedoGameController.initializer.kitchen.addDoors(d);
		doors.add(d);

		board[x][y] = new RoomTile(x, y, "top", "left", CluedoGameController.initializer.kitchen);
		board[x][y+size-1] = new RoomTile(x, y+size-1, "bottom", "left", CluedoGameController.initializer.kitchen);
		board[x+size-1][y+size-1] = new RoomTile(x+size-1, y+size-1, "bottom", "right", CluedoGameController.initializer.kitchen);

		return new Rectangle(x, y, size, size);
	}

	/**
	 * Draws the dining room.
	 * @return
	 */

	private Rectangle drawDiningRoom(){
		int width = 8;
		int height = 7;
		int x = 0;
		int y = 9;

		/*Boarder*/
		//top
		for(int i = 0; i < 5; i++){
			board[i][y] = new RoomTile(i, y, "top", null, CluedoGameController.initializer.diningrm);
		}
		for(int i = 5; i < width; i++){
			board[i][y+1] = new RoomTile(i, y+1, "top", null, CluedoGameController.initializer.diningrm);
		}

		//bottom
		for(int i = 0; i < width; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null, CluedoGameController.initializer.diningrm);
		}
		//left
		for(int i = y; i < height + y; i++){
			board[x][i] = new RoomTile(x, i, "left", null, CluedoGameController.initializer.diningrm);
		}
		//right
		for(int i = y+2; i < height + y; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null, CluedoGameController.initializer.diningrm);
		}

		//area
		for(int i = x+1; i < 4; i++){
			for(int j = y+1; j < y+height-1; j++){
				board[i][j] = new RoomTile(i, j, null, null, CluedoGameController.initializer.diningrm);
			}
		}

		for(int i = 4; i < width-1; i++){
			for(int j = y+2; j < y+height-1; j++){
				board[i][j] = new RoomTile(i, j, null, null, CluedoGameController.initializer.diningrm);
			}
		}
		board[4][y+1] = new RoomTile(4, y+1, null, null, CluedoGameController.initializer.diningrm);

		Door d1 = new Door(true, width-1, 12, CluedoGameController.initializer.diningrm, "<");
		Door d2 = new Door(false, width-2, y+height-1, CluedoGameController.initializer.diningrm, "^");
		d1.setInFront(new Position(width, 12));
		d2.setInFront(new Position(width-2, y+height));
		CluedoGameController.initializer.diningrm.addDoors(d1);
		CluedoGameController.initializer.diningrm.addDoors(d2);
		doors.add(d1);
		doors.add(d2);

		board[x][y] = new RoomTile(x, y, "top", "left", CluedoGameController.initializer.diningrm);
		board[x][y+height-1] = new RoomTile(x, y+height-1, "bottom", "left", CluedoGameController.initializer.diningrm);
		board[4][y] = new RoomTile(4, y, "top", "right", CluedoGameController.initializer.diningrm);
		board[x+width-1][y+1] = new RoomTile(x+width-1, y+1, "top", "right", CluedoGameController.initializer.diningrm);
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "bottom", "right", CluedoGameController.initializer.diningrm);

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the lounge.
	 * @return
	 */
	private Rectangle drawLounge(){
		int x = 0;
		int y = 19;
		int width = 7;
		int height = 6;
		/*Boarder*/
		//top
		for(int i = 0; i < width; i++){
			board[i][y] = new RoomTile(i, y, "top", null, CluedoGameController.initializer.lounge);
		}

		//bottom
		for(int i = 0; i < width; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null, CluedoGameController.initializer.lounge);
		}

		//left
		for(int i = y; i < y+height; i++){
			board[x][i] = new RoomTile(x, i, "left", null, CluedoGameController.initializer.lounge);
		}

		//right
		for(int i = y+1; i < y+height; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null, CluedoGameController.initializer.lounge);
		}

		//area
		for(int i = x+1; i < width-1; i++){
			for(int j = y+1; j < y+height-1; j++){
				board[i][j] = new RoomTile(i, j, null, null, CluedoGameController.initializer.lounge);
			}
		}

		board[x][y] = new StairsTile(x, y, "top", "left");

		Door d = new Door(false, x+width-1, y, CluedoGameController.initializer.lounge, "v");
		d.setInFront(new Position(x+width-1, y-1));
		CluedoGameController.initializer.lounge.addDoors(d);
		board[x+width-1][y] = new DoorTile(x+width-1, y, d, "right");

		board[x][y+height-1] = new RoomTile(x, y+height-1, "bottom", "left", CluedoGameController.initializer.lounge);
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "bottom", "right", CluedoGameController.initializer.lounge);

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the hall.
	 * @return
	 */
	private Rectangle drawHall() {
		int x = 9;
		int y = 18;
		int width = 6;
		int height = 7;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new RoomTile(i, y, "top", null, CluedoGameController.initializer.hall);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null, CluedoGameController.initializer.hall);
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = new RoomTile(x, i, "left", null, CluedoGameController.initializer.hall);
		}

		//right
		for(int i = y+1; i < height+y-1; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null, CluedoGameController.initializer.hall);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null, CluedoGameController.initializer.hall);
			}
		}

		Door d1 = new Door(true, x+width-1, y+2, CluedoGameController.initializer.hall, "<");
		Door d2 = new Door(true, x+3, y , CluedoGameController.initializer.hall, "v");
		Door d3 = new Door(true, x+2, y, CluedoGameController.initializer.hall, "v");
		d1.setInFront(new Position(x+width, y+2));
		d2.setInFront(new Position(x+3, y-1));
		d3.setInFront(new Position(x+2, y-1));
		CluedoGameController.initializer.hall.addDoors(d1);
		CluedoGameController.initializer.hall.addDoors(d2);
		CluedoGameController.initializer.hall.addDoors(d3);
		doors.add(d1);
		doors.add(d2);
		doors.add(d3);

		board[x][y] = new RoomTile(x, y, "top", "left", CluedoGameController.initializer.hall);
		board[x+width-1][y] = new RoomTile(x+width-1, y, "top", "right", CluedoGameController.initializer.hall);
		board[x][y+height-1] = new RoomTile(x, y+height-1, "left", "bottom", CluedoGameController.initializer.hall);
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "right", "bottom", CluedoGameController.initializer.hall);

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the study room.
	 * @return
	 */
	private Rectangle drawStudy() {
		int x = 17;
		int y = 21;
		int width = 7;
		int height = 4;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new RoomTile(i, y, "top", null, CluedoGameController.initializer.study);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null, CluedoGameController.initializer.study);
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = new RoomTile(x, i, "left", null, CluedoGameController.initializer.study);
		}

		//right
		for(int i = y; i < height+y; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null, CluedoGameController.initializer.study);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null, CluedoGameController.initializer.study);
			}
		}

		board[x+width-1][y] = new StairsTile(x+width-1, y, "top", "right");

		Door d = new Door(false, x, y, CluedoGameController.initializer.study, "v");
		d.setInFront(new Position(x, y-1));
		CluedoGameController.initializer.study.addDoors(d);
		board[x][y] = new DoorTile(x, y, d, "left");

		board[x][y+height-1] = new RoomTile(x, y+height-1, "left", "bottom", CluedoGameController.initializer.study);
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "bottom", "right", CluedoGameController.initializer.study);

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the library.
	 * @return
	 */
	private Rectangle drawLibrary() {
		int x = 17;
		int y = 14;
		int width = 7;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = x+1; i < width+x-1; i++){
			board[i][y] = new RoomTile(i, y, "top", null, CluedoGameController.initializer.lib);
		}

		//bottom
		for(int i = x+1; i < width+x-1; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null, CluedoGameController.initializer.lib);
		}

		//left
		for(int i = y+1; i < height+y-1; i++){
			board[x][i] = new RoomTile(x, i, "left", null, CluedoGameController.initializer.lib);
		}

		//right
		for(int i = y+1; i < height+y-1; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null, CluedoGameController.initializer.lib);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null, CluedoGameController.initializer.lib);
			}
		}

		Door d1 = new Door(true, x, y+2, CluedoGameController.initializer.lib,  ">");
		Door d2 = new Door(true, x+3, y, CluedoGameController.initializer.lib, "v");
		d1.setInFront(new Position(x-1, y+2));
		d2.setInFront(new Position(x+3, y-1));
		CluedoGameController.initializer.lib.addDoors(d1);
		CluedoGameController.initializer.lib.addDoors(d2);
		doors.add(d1);
		doors.add(d2);

		board[x][y+1] = new RoomTile(x, y+1, "top", "left", CluedoGameController.initializer.lib);
		board[x+1][y] = new RoomTile(x+1, y, "top", "left", CluedoGameController.initializer.lib);
		board[x][y+height-2] = new RoomTile(x, y+height-2, "left", "bottom", CluedoGameController.initializer.lib);
		board[x+1][y+height-1] = new RoomTile(x+1, y+height-1, "left", "bottom", CluedoGameController.initializer.lib);
		board[x+width-2][y] = new RoomTile(x+width-2, y, "top", "right", CluedoGameController.initializer.lib);
		board[x+width-1][y+1] = new RoomTile(x+width-1, y+1, "top", "right", CluedoGameController.initializer.lib);
		board[x+width-1][y+height-2] = new RoomTile(x+width-1, y+height-2, "bottom", "right", CluedoGameController.initializer.lib);
		board[x+width-2][y+height-1] = new RoomTile(x+width-2, y+height-1, "bottom", "right", CluedoGameController.initializer.lib);

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the billiard room.
	 * @return
	 */
	private Rectangle drawBilliard() {
		int x = 18;
		int y = 8;
		int width = 6;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new RoomTile(i, y, "top", null, CluedoGameController.initializer.billRm);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null, CluedoGameController.initializer.billRm);
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = new RoomTile(x, i, "left", null, CluedoGameController.initializer.billRm);
		}

		//right
		for(int i = y; i < height+y; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null, CluedoGameController.initializer.billRm);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null, CluedoGameController.initializer.billRm);
			}
		}

		Door d1 = new Door(true, x, y+1, CluedoGameController.initializer.billRm, ">");
		Door d2 = new Door(false, x+width-2, y+height-1, CluedoGameController.initializer.billRm, "^");
		d1.setInFront(new Position(x+width-2, y+height));
		d2.setInFront(new Position(x-1, y+1));
		CluedoGameController.initializer.billRm.addDoors(d1);
		CluedoGameController.initializer.billRm.addDoors(d2);
		doors.add(d1);
		doors.add(d2);

		board[x][y] = new RoomTile(x, y, "top", "left", CluedoGameController.initializer.billRm);
		board[x][y+height-1] = new RoomTile(x, y+height-1, "bottom", "left", CluedoGameController.initializer.billRm);
		board[x+width-1][y] = new RoomTile(x+width-1, y, "top", "right", CluedoGameController.initializer.billRm);
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "bottom", "right", CluedoGameController.initializer.billRm);

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the conservatory.
	 * @return
	 */
	private Rectangle drawConservatory() {
		int x = 18;
		int y = 1;
		int width = 6;
		int height = 5;

		/*Boarder*/
		//top
		for(int i = x; i < width+x; i++){
			board[i][y] = new RoomTile(i, y, "top", null, CluedoGameController.initializer.conservatory);
		}

		//bottom
		for(int i = x+1; i < width+x-1; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1, "bottom", null, CluedoGameController.initializer.conservatory);
		}

		//left
		for(int i = y; i < height+y-1; i++){
			board[x][i] = new RoomTile(x, i, "left", null, CluedoGameController.initializer.conservatory);
		}

		//right
		for(int i = y; i < height+y-1; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null, CluedoGameController.initializer.conservatory);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null, CluedoGameController.initializer.conservatory);
			}
		}

		board[x+width-2][y+height-1] = new StairsTile(x+width-2, y+height-1, "bottom", "right");

		Door d = new Door(true, x+1, y+height-1, CluedoGameController.initializer.conservatory, "^");
		d.setInFront(new Position(x+1, y+height));
		CluedoGameController.initializer.conservatory.addDoors(d);
		board[x+1][y+height-1] = new DoorTile(x+1, y+height-1, d, "bottom");

		board[x][y] = new RoomTile(x, y, "top", "left", CluedoGameController.initializer.conservatory);
		board[x+width-1][y] = new RoomTile(x+width-1, y, "top", "right", CluedoGameController.initializer.conservatory);
		board[x+width-1][y+height-2] = new RoomTile(x+width-1, y+height-2, "bottom", "right", CluedoGameController.initializer.conservatory);
		board[x][y+height-2] = new RoomTile(x, y+height-2, "bottom", "left", CluedoGameController.initializer.conservatory);

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the ball room.
	 * @return
	 */
	private Rectangle drawBallRoom() {
		int x = 8;
		int y = 0;
		int width = 8;
		int height = 8;

		/*Boarder*/
		//top
		for(int i = x+2; i < width+x-2; i++){
			board[i][y] = new RoomTile(i, y, "top", null, CluedoGameController.initializer.ballRm);
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = new RoomTile(i, y+height-1,"bottom", null, CluedoGameController.initializer.ballRm);
		}

		//left
		for(int i = y+2; i < height+y; i++){
			board[x][i] = new RoomTile(x, i, "left", null, CluedoGameController.initializer.ballRm);
		}

		//right
		for(int i = y+2; i < height+y; i++){
			board[x+width-1][i] = new RoomTile(x+width-1, i, "right", null, CluedoGameController.initializer.ballRm);
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+2; j < height+y-1; j++){
				board[i][j] = new RoomTile(i, j, null, null, CluedoGameController.initializer.ballRm);
			}
		}

		Door d1 = new Door(true, x, 5, CluedoGameController.initializer.ballRm, ">");
		Door d2 = new Door(true, x+width-1, 5, CluedoGameController.initializer.ballRm, "<");
		Door d3 = new Door(false, x+1, y+height-1, CluedoGameController.initializer.ballRm, "^");
		Door d4 = new Door(false, x+width-2, y+height-1, CluedoGameController.initializer.ballRm, "^");
		d1.setInFront(new Position(x-1, 5));
		d2.setInFront(new Position(x+width, 5));
		d3.setInFront(new Position(x+1, y+height));
		d4.setInFront(new Position(x+width-2, y+height)); 
		CluedoGameController.initializer.ballRm.addDoors(d1);
		CluedoGameController.initializer.ballRm.addDoors(d2);
		CluedoGameController.initializer.ballRm.addDoors(d3);
		CluedoGameController.initializer.ballRm.addDoors(d4);
		doors.add(d1);
		doors.add(d2);
		doors.add(d3);
		doors.add(d4);

		board[x+2][y+1] = new RoomTile(x+2, y+1, "left", null, CluedoGameController.initializer.ballRm);
		board[x+3][y+1] = new RoomTile(x+3, y+1, null, null, CluedoGameController.initializer.ballRm);
		board[x+4][y+1] = new RoomTile(x+4, y+1, null, null, CluedoGameController.initializer.ballRm);
		board[x+5][y+1] = new RoomTile(x+5, y+1, "right", null, CluedoGameController.initializer.ballRm);
		board[x+1][y+2] = new RoomTile(x+1, y+2, "top", null, CluedoGameController.initializer.ballRm);
		board[x+width-2][y+2] = new RoomTile(x+width-2, y+2, "top", null, CluedoGameController.initializer.ballRm);
		board[x][y+2] = new RoomTile(x, y+2, "top", "left", CluedoGameController.initializer.ballRm);
		board[x+width-1][y+2] = new RoomTile(x+width-1, y+2, "top", "right", CluedoGameController.initializer.ballRm);
		board[x][y+height-1] = new RoomTile(x, y+height-1, "bottom", "left", CluedoGameController.initializer.ballRm);
		board[x+width-1][y+height-1] = new RoomTile(x+width-1, y+height-1, "bottom", "right", CluedoGameController.initializer.ballRm);
		board[x+2][y] = new RoomTile(x+2, y, "top", "left", CluedoGameController.initializer.ballRm);
		board[x+width-3][y] = new RoomTile(x+width-3, y, "top", "right", CluedoGameController.initializer.ballRm);

		return new Rectangle(x, y, width, height);
	}

	/**
	 * Draws the solution room.
	 * @return
	 */
	private Rectangle drawCluedo() {
		int x = 10;
		int y = 10;
		int width = 5;
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
		board[9][0] = new StartTile(9, 0, CluedoGameController.initializer.getCharacters()[2]);
		board[14][0] = new StartTile(14, 0, CluedoGameController.initializer.getCharacters()[3]);
		board[0][17] = new StartTile(0, 17, CluedoGameController.initializer.getCharacters()[1]);
		board[7][24] = new StartTile(7, 24, CluedoGameController.initializer.getCharacters()[0]);
		board[23][19] = new StartTile(23, 19, CluedoGameController.initializer.getCharacters()[5]);
		board[23][6] = new StartTile(23, 6, CluedoGameController.initializer.getCharacters()[4]);

	}

	/**
	 * Draws the board.
	 */
	public void drawBoard(){
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board[0].length; y++){
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
			((StartTile)board[x][y]).setPlayer(p);
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
	public void move(int directionX, int directionY, Player p, List<Player> currentPlayers) throws InvalidMove{
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
	}

	/**
	 * Moves player to another room.
	 * @param p - current player
	 * @param rm - room player wants to move to
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
		for(Player player : currentPlayers){
			if(!player.equals(p)){
				if(x == player.position().getX() && y == player.position().getY()){
					JOptionPane.showMessageDialog(null, "Cannot exit room.", "GAME WARNING" ,JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	/**
	 * Checks if player is doing a valid move. If they are not, then it returns false, else it returns true.
	 * 
	 * @param Position - position of player after being moved
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
			if(p.numberofMoves() < Math.abs(directionX - directionY)){
				throw new CluedoGameController.InvalidMove("You cannot move into this tile because you do not have enough moves.");
			}if(x > 24 || x < 0 || y > 24 || y < 0){
				throw new CluedoGameController.InvalidMove("Cannot go out of bounds!");
			}else if(board[x][y] instanceof WallTile){
				throw new CluedoGameController.InvalidMove("Cannot move into wall.");
			}else if(board[x][y] instanceof RoomTile){
				RoomTile r = (RoomTile) board[x][y];
				if(r.getDir() != null){
					if(r.getDir().equals(direction) && !p.isInRoom()){
						throw new CluedoGameController.InvalidMove("Cannot move into wall.");
					}
				}
				if(r.getDir2() != null){
					if(r.getDir2().equals(direction) && !p.isInRoom()){
						throw new CluedoGameController.InvalidMove("Cannot move into wall.");
					}
				}
			}else if((board[x][y] instanceof StairsTile) && !p.isInRoom()){
				throw new CluedoGameController.InvalidMove("Player is not in room to take the stairs.");
			}else if(board[x][y] instanceof SolutionTile){
				throw new CluedoGameController.InvalidMove("Cannot move into solution space");
			}else{
				for(Position pos : p.coordinatesTaken()){
					if(pos.getX() == x && pos.getY() == y){
						throw new CluedoGameController.InvalidMove("You cannot move into the same square within this move.");
					}
				}

				for(Player player : currentPlayers){
					if(!player.getName().equals(p.getName())){
						if(position.equals(player.position())){
							throw new CluedoGameController.InvalidMove("Cannot move into existing player's square!");
						}

					}
				}
			}
		} catch (InvalidMove e) {
			System.err.println(e.getMessage());
			return false;
		}

		if(board[x][y] instanceof RoomTile){
			Room r = ((RoomTile)board[x][y]).getRoom();
			Door d = findClosestDoor(r, p);
			p.setDoor(d);
		}else if(board[x][y] instanceof DoorTile){
			Door d = ((DoorTile)board[x][y]).getDoor();
			p.setDoor(d);
		}
		return true;
	}

	public Door findClosestDoor(Room r, Player p){
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		Door minDoor = null;
		for(Door d : r.getDoors()){
			if(minX > Math.abs(d.getPosition().getX() - p.position().getX()) && minY > Math.abs(d.getPosition().getY() - p.position().getY())){
				minX = d.getPosition().getX();
				minY = d.getPosition().getY();
				minDoor = d;
			}
		}
		return minDoor;
	}

	public Tile[][] board(){
		return board;
	}
}
