package cluedo.main;
import java.io.*;
import java.util.*;
import cluedo.assets.*;
import cluedo.main.CluedoGame.InvalidMove;

/**
 * Fills up the board of the game and is used to move a character piece around the board.
 * 
 * @author Casey
 *
 */
public class Board {
	/**
	 * Represents the board in 2d array form.
	 */
	private String[][] board = new String[25][25];

	/**
	 * Stores the list of doors on the board.
	 */
	private List<Door> doors = new ArrayList<Door>();

	/**
	 * Stores the starting positions of each player.
	 */
	private List<Position> startPos = new ArrayList<Position>();

	/** This helps generating a random shuffle for the lists */
	private long seed = System.nanoTime();

	/**
	 * For checking if player did a valid move or not.
	 */
	private boolean isValidMove = false;

	/**
	 * Creates a new board for the game.
	 */
	public Board(){
		//filling up the board so it does not contain any null values
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board.length; y++){
				board[x][y] = "_|";
			}
		}

		//fills up the side of the board
		for(int i = 0; i < board.length; i++){
			board[0][i] = "|_|";
		}
		initialiseBoard();
		drawBoard();
	}

	/**
	 * Returns the isValidMove field.
	 * @return
	 */
	public boolean isValidMove() {
		return isValidMove;
	}

	/**
	 * Fill board with rooms
	 */
	public void initialiseBoard(){
		drawBorder();
		drawKitchen();
		drawDiningRoom();
		drawLounge();
		drawHall();
		drawStudy();
		drawLibrary();
		drawBilliard();
		drawConservatory();
		drawBallroom();
		drawCluedo();
		drawDoors();
		drawStart();
	}

	/**
	 * Draws the border.
	 */
	private void drawBorder(){
		for(int i = 0; i < this.board.length; i++){
			board[i][0] = "#|";
			board[i][board.length-1] = "#|";
		}
		for(int i = 0; i < this.board.length; i++){
			board[0][i] = "|#|";
			board[board.length-1][i] = "#|";
		}
		board[6][1] = "#|";
		board[17][1] = "#|";
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
			board[i][y] = "X|";
		}
		//bottom
		for(int i = 0; i < size; i++){
			board[i][y+size-1] = "X|";
		}
		//left
		for(int i = y; i < size+y; i++){
			board[x][i] = "|X|";
		}
		//right
		for(int i = y; i < size; i++){
			board[x+size-1][i] = "X|";
		}
		//area
		for(int i = x+1; i < size-1; i++){
			for(int j = y+1; j < size; j++){
				board[i][j] = "  ";
			}
		}

		for(int i = y+1; i < size; i++){
			board[size-2][i] = " |";
		}

		board[1][2] = "1 ";
		board[5][1] = "S|";

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
			board[i][y] = "X|";
		}
		for(int i = 4; i < width; i++){
			board[i][y+1] = "X|";
		}
		board[x+4][y+1] = " |";

		//bottom
		for(int i = 0; i < width; i++){
			board[i][y+height-1] = "X|";
		}
		//left
		for(int i = y; i < height + y; i++){
			board[x][i] = "|X|";
		}
		//right
		for(int i = y+2; i < height + y; i++){
			board[x+width-1][i] = "X|";
		}

		//area
		for(int i = x+1; i < 4; i++){
			for(int j = y+1; j < y+height-1; j++){
				board[i][j] = "  ";

			}
		}

		for(int i = 4; i < width-1; i++){
			for(int j = y+2; j < y+height-1; j++){
				board[i][j] = "  ";

			}
		}

		for(int i = y+2; i < height + y; i++){
			board[x+width-2][i] = " |";
		}

		board[1][10] = "2 ";

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
			board[i][y] = "X|";
		}

		//bottom
		for(int i = 0; i < width; i++){
			board[i][y+height-1] = "X|";
		}

		//left
		for(int i = y; i < y+height; i++){
			board[x][i] = "|X|";
		}

		//right
		for(int i = y+1; i < y+height; i++){
			board[x+width-1][i] = "X|";
		}

		//area
		for(int i = x+1; i < width-1; i++){
			for(int j = y+1; j < y+height-1; j++){
				board[i][j] = "  ";
			}
		}

		for(int i = y+1; i < y+height-1; i++){
			board[x+width-2][i] = " |";
		}

		board[x][y] = "|S|";
		board[x+1][y+1] = "3 ";

		Door d = new Door(false, x+width-1, y, CluedoGame.initializer.lounge, "v");
		d.setInFront(new Position(x+width-1, y-1));
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
			board[i][y] = "X|";
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = "X|";
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = "X|";
		}

		//right
		for(int i = y+1; i < height+y-1; i++){
			board[x+width-1][i] = "X|";
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = "  ";
			}
		}

		for(int i = y+1; i < y+height-1; i++){
			board[x+width-2][i] = " |";
		}

		board[x+1][y+1] = "4 ";

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
			board[i][y] = "X|";
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = "X|";
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = "X|";
		}

		//right
		for(int i = y; i < height+y; i++){
			board[x+width-1][i] = "X|";
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = "  ";
			}
		}

		for(int i = y+1; i < y+height-1; i++){
			board[x+width-2][i] = " |";
		}

		board[x+width-1][y] = "S|";
		board[x+1][y+1] = "5 ";

		Door d = new Door(false, x, y, CluedoGame.initializer.study, "v");
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
			board[i][y] = "X|";
		}

		//bottom
		for(int i = x+1; i < width+x-1; i++){
			board[i][y+height-1] = "X|";
		}

		//left
		for(int i = y+1; i < height+y-1; i++){
			board[x][i] = "X|";
		}

		//right
		for(int i = y+1; i < height+y-1; i++){
			board[x+width-1][i] = "X|";
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = "  ";
			}
		}

		for(int i = y+1; i < y+height-1; i++){
			board[x+width-2][i] = " |";
		}
		board[x+1][y+1] = "6 ";

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
			board[i][y] = "X|";
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = "X|";
		}

		//left
		for(int i = y; i < height+y; i++){
			board[x][i] = "X|";
		}

		//right
		for(int i = y; i < height+y; i++){
			board[x+width-1][i] = "X|";
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = "  ";
			}
		}

		for(int i = y+1; i < y+height-1; i++){
			board[x+width-2][i] = " |";
		}
		board[x+1][y+1] = "7 ";

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
			board[i][y] = "X|";
		}

		//bottom
		for(int i = x+1; i < width+x-1; i++){
			board[i][y+height-1] = "X|";
		}

		//left
		for(int i = y; i < height+y-1; i++){
			board[x][i] = "X|";
		}

		//right
		for(int i = y; i < height+y-1; i++){
			board[x+width-1][i] = "X|";
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = "  ";
			}
		}

		for(int i = y+1; i < y+height-1; i++){
			board[x+width-2][i] = " |";
		}
		board[x+width-2][y+height-1] = "S|";
		board[x+1][y+1] = "8 ";

		Door d = new Door(false, x, y+height-2, CluedoGame.initializer.conservatory, "^");
		d.setInFront(new Position(x, y+height-1));
		doors.add(d);
	}

	/**
	 * Draws the ball room.
	 */
	private void drawBallroom() {
		int x = 8;
		int y = 1;
		int width = 8;
		int height = 7;

		/*Boarder*/
		//top
		for(int i = x+2; i < width+x-2; i++){
			board[i][y] = "X|";
		}

		//bottom
		for(int i = x; i < width+x; i++){
			board[i][y+height-1] = "X|";
		}

		//left
		for(int i = y+1; i < height+y; i++){
			board[x][i] = "X|";
		}

		//right
		for(int i = y+1; i < height+y; i++){
			board[x+width-1][i] = "X|";
		}

		//area
		for(int i = x+1; i < width+x-1; i++){
			for(int j = y+1; j < height+y-1; j++){
				board[i][j] = "  ";
			}
		}

		for(int i = y+1; i < y+height-1; i++){
			board[x+width-2][i] = " |";
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

		board[x+1][y+1] = "X|";
		board[x+width-2][y+1] = "X|";
		board[x+width-3][y+1] = " |";
		board[x+2][y+1] = "9 ";
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
				board[i][j] = "*|";
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
			board[x][y] = d.getString() + "|";
		}
	}

	/**
	 * Draws the start spaces.
	 */
	private void drawStart(){
		startPos.add(new Position(9, 0));
		startPos.add(new Position(14, 0));
		startPos.add(new Position(0, 17));
		startPos.add(new Position(7, board.length-1));
		startPos.add(new Position(board.length-1, board.length-6));
		startPos.add(new Position(board.length-1, 6));

		for(int i = 0; i < startPos.size(); i++){
			int x = startPos.get(i).getX();
			int y = startPos.get(i).getY();
			board[x][y] = "/|";
			if(x == 0 && y == 17){
				board[x][y] = "|/|";
			}
		}
		Collections.shuffle(startPos, new Random(seed)); 
	}

	/**
	 * Initializes each player's position.
	 * @param list of currentPlayers
	 */
	public void setPlayerPosition(List<Player> currentPlayers){
		for(int i = 0; i < currentPlayers.size(); i++){
			int x = startPos.get(i).getX();
			int y = startPos.get(i).getY();
			board[x][y] = currentPlayers.get(i).getCharacterName() + "|";
			currentPlayers.get(i).setPos(x, y);
			if(x == 0 && y == 17){
				board[x][y] = "|" +currentPlayers.get(i).getCharacterName() + "|";
			}
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
		List<Room> rooms = CluedoGame.initializer.getRooms();
		int x = p.position().getX() + directionX;
		int y = p.position().getY() + directionY;
		if(isValidMove(new Position(x, y), directionX, directionY, p, currentPlayers)){
			isValidMove = true;
			board[p.position().getX()][p.position().getY()] = p.getLookBack();
			if(p.door() != null){
				Room r = p.door().getRoom();
				r.addPlayer(p);
				p.setRoom(r);
				p.setIsInRoom(true);
			}else{
				p.setPos(x, y);
			}
			p.setLookBack(board[p.position().getX()][p.position().getY()]);
			p.moveAStep();
			if(p.isInRoom()){
				board[p.position().getX()][p.position().getY()] = p.getCharacterName() + " ";
			}else{
				board[p.position().getX()][p.position().getY()] = p.getCharacterName() + "|";
			}
		}else{
			isValidMove = false;
		}
		drawBoard();
	}

	/**
	 * Moves player to another room.
	 * @param p
	 * @param rm
	 */
	public void moveToRoom(Player p, Room rm){
		if(p.position() != null){
			board[p.position().getX()][p.position().getY()] = p.getLookBack();
			p.setLookBack(board[p.position().getX()][p.position().getY()]);
			p.getRoom().removePlayer(p);
		}
		p.setRoom(rm);
		p.getRoom().addPlayer(p);
		board[p.position().getX()][p.position().getY()] = p.getCharacterName() + " ";
	}

	/**
	 * Make player exit room.
	 * @param p
	 * @param currentPlayers
	 */
	public void exitRoom(Player p, List<Player> currentPlayers){
		int x = p.position().getX();
		int y = p.position().getY();
		board[p.position().getX()][p.position().getY()] = p.getLookBack();
		p.setLookBack(board[p.position().getX()][p.position().getY()]);
		for(Player player : currentPlayers){
			if(player != p){
				if(player.position().getX() != p.door().getInFront().getX() && player.position().getY() != p.door().getInFront().getY()){
					x = p.door().getInFront().getX();
					y = p.door().getInFront().getY();
				}
			}
		}
		p.setPos(x, y);
		board[p.position().getX()][p.position().getY()] = p.getCharacterName() + "|";
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
			}else if(board[x][y].equals("|#|") || board[x][y].equals("#|")){
				throw new CluedoGame.InvalidMove("Cannot move into wall.");
			}else if(board[x][y].equals("|X|") || board[x][y].equals("X|")){
				throw new CluedoGame.InvalidMove("Cannot move into wall.");
			}else if((board[x][y].equals("S|") || board[x][y].equals("|S|")) && !p.isInRoom()){
				throw new CluedoGame.InvalidMove("Player is not in room to take the stairs.");
			}else if(board[x][y].equals("*|")){
				throw new CluedoGame.InvalidMove("Cannot move into envelope space.");
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
	 */
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

	/**
	 * Returns true if player has valid possible moves.
	 * @param pos
	 * @param p
	 * @return
	 */
	public boolean validPos(Position pos, Player p){
		int x = pos.getX();
		int y = pos.getY();
		if(x > 24 || x < 0 || y > 24 || y < 0){
			return false;
		}else if(board[x][y].equals("|#|") || board[x][y].equals("#|") || board[x][y].equals("|X|") || board[x][y].equals("X|")){
			return false;
		}
		return true;
	}

	/**
	 * Prints out the board.
	 */
	public void drawBoard(){
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board.length; y++){
				System.out.print(board[y][x]);
			}
			System.out.println();
		}
	}

	/**
	 * For testing purposes only
	 * @return
	 */
	public String[][] getBoard(){
		return board;
	}
}
