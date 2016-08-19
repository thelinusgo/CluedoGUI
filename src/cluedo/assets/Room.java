package cluedo.assets;

import java.util.*;

/**
 * Room Class. Stores the name of the room, and the weapon it is currently holding.
 * @author Casey & Linus
 *
 */
public class Room {

	/**
	 * The name of the Room.
	 */
	private String name;

	/**
	 * The weapon that the room is in.
	 */
	private Weapon weapon = null;
	
	/**
	 * The Character that is in the room.
	 */
	private Character character = null;

	/**
	 * The x position of this room.
	 */
	private int x;

	/**
	 * The y position of this room.
	 */
	private int y;

	/**
	 * The width of this room.
	 */
	private int width;

	/**
	 * The height of this room.
	 */
	private int height;

	/**
	 * Stores the doors that belong to this room.
	 */
	private List<Door> doors;

	/**
	 * Coordinates that players will be placed in if they have entered the room
	 */
	private Position[] playerCoords;
	
	/**
	 * Stores the position of the player in this room.
	 */
	private Map<Position, Player> playerMap;

	/**
	 * Determines whether the room has stairs
	 */
	private boolean hasStairs;

	/**
	 * Connects rooms with stairs.
	 */
	private Room other;
	
	private int i;
	
	/**
	 * Construct a Room.
	 */
	public Room(String n){
		this.name = n;
	}

	/**
	 * Construct a Room with x, y coordinates, its width and height and whether it has stairs or not.
	 * @param n
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param hS
	 */
	public Room(String n, int x, int y, int width, int height, boolean hS){
		this.name = n;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		playerCoords = new Position[6];
		doors = new ArrayList<Door>();
		playerMap = new HashMap<>();
		addPlayerCoords();
		this.hasStairs = hS;
	}

	/**
	 * Determines where the player would be placed in room.
	 */
	private void addPlayerCoords(){
		int j = 0;
		int k = 0;
		for(int i = 0; i < playerCoords.length; i++){
			playerCoords[i] = new Position(this.x+1+j, this.y+2+k);
			j++;
			if(j >= 3){
				j = 0;
				k++;
			}
		}
		
		for(Position pos : playerCoords){
			playerMap.put(pos, null);
		}
	}

	/**
	 * Add player and position to Map.
	 * @param p
	 */
	public void addPlayer(Player p){
		Position pos = playerCoords[i];
		loop: for(Map.Entry<Position, Player> e : playerMap.entrySet()){
			if(e.getValue() == null){
				playerMap.put(pos, p);
				p.setPos(pos.getX(), pos.getY());
				break loop;
			}
		}
		i++;
		if(i >= playerCoords.length){
			i = 0;
		}
	}
	
	/**
	 * Removes player from the room.
	 * @param p
	 */
	public void removePlayer(Player p){
		for(int i = 0; i < playerCoords.length; i++){
			if(p.position().equals(playerCoords[i])){
				playerMap.put(p.position(), null);
			}
		}
	}
	
	/**
	 * Returns the map of players who are currently in this room.
	 * @return
	 */
	public Map<Position, Player> getMap(){
		return playerMap;
	}

	/**
	 * Add weapon to room
	 * @param w
	 */
	public void addWeapon(Weapon w){
		this.weapon = w;
	}
	
	/**
	 * Add Character to room.
	 * @param c
	 */
	public void addCharacter(Character c){
		this.character = c;
	}

	/**
	 * Adds a door object to the doors ArrayList.
	 * @param d
	 */
	public void addDoors(Door d){
		this.doors.add(d);
	}

	/**
	 * Returns the doors ArrayList.
	 * @return
	 */
	public List<Door> getDoors(){
		return this.doors;
	}

	/**
	 * Returns whether a co-ordinate in the room is contained in this room.
	 * Returns false otherwise. 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(int x, int y){
		if(this.x <= x && this.x + width > x && this.y <= y && this.y + height > y){
			return true;
		}
		return false;
	}

	/**
	 * Gets the weapon being held in this current room.
	 * @return
	 */
	public Weapon getWeapon(){
		return this.weapon;
	}
	
	/**
	 * Returns the character in this room.
	 * @return
	 */
	public Character getCharacter() {
		return this.character;
	}

	/**
	 * Returns whether the room has stairs.
	 * @return
	 */
	public boolean hasStairs(){
		return this.hasStairs;
	}

	/**
	 * Returns the room connected.
	 * @return
	 */
	public Room getOtherRoom(){
		return this.other;
	}

	/**
	 * Set room this is connected to this room.
	 * @param rm
	 */
	public void setRoom(Room rm){
		this.other = rm;
	}

	/**
	 * Returns the name of this room formatted nicely.
	 * @return
	 */
	public String stringName(){
		return "[Room: " + name + "]";
	}
	
	/**
	 * Returns a toString representation of this Room.
	 */
	@Override
	public String toString(){
		if(this.weapon != null){
			return "[Room: " + name + " | Weapon: " + weapon.weaponName() + "]";
		}
		return "[Room: " + name + " | Weapon: null]";
	}
	
}
