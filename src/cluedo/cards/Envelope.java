package cluedo.cards;

/**
 * Class that holds three Cards. It is backed by an array.
 * This data structure is special because it is limited to 3 elements.
 * 
 * @author Casey & Linus
 */
public class Envelope{
	/**
	 * The underlying data structure of the envelope
	 */
	private Card[] envelope;
	
	/**
	 * The size of the envelope.
	 */
	private int count = 0;
	
	/**
	 * Creates a new envelope
	 */
	public Envelope(){
		this.envelope = new Card[3];	
	}
	
	/**
	 * Gets a specified card at that index.
	 * @param i
	 * @return
	 */
	public Card get(int i){
		if(i > 0 || i < 3){
			return envelope[i]; 
		}else throw new ArrayIndexOutOfBoundsException("envelope is out of bounds.");
	}
	
	/**
	 * Adds a card in. If the count is greater than 3, don't do anything. fail silently.
	 * @param card
	 */
	public void add(Card card){
		if(count >= 3){
			return;
		}
		envelope[count] = card;
		count++;	
	}
	
	/**
	 * Returns a copy of the envelope array. Modification of this array does not affect the real envelope.
	 * @return
	 */
	public Card[] getCards(){
		Card[] temp = this.envelope;
		return temp;
	}
	
}


