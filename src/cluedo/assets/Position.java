package cluedo.assets;
/**
 * Class that represents the position. It holds an x and a y tuple of data.
 * @author Casey
 *
 */
public class Position {
	
	private int x;
	private int y;
	
	/**
	 * Construct a new Position object with x and y.
	 * @param x
	 * @param y
	 */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Retrieves the X value.
	 * @return
	 */
	public int getX(){
		return this.x;
	}
	
	/**
	 * Retrieves the Y value.
	 * @return
	 */
	public int getY(){
		return this.y;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Position){
			if(this.x == ((Position) obj).getX() && this.y == ((Position) obj).getY()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a toString representation of this Position.
	 */
	public String toString(){
		return this.x + " " + this.y;
	}
	
}
