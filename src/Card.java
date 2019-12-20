import java.util.ArrayList;

import javax.swing.ImageIcon;
/**
 * This class creates the card object that will be distributed
 * 
 * @author Diabul Haque
 * @version 1.0
 */
public class Card {
	private ImageIcon icon;
	private int value;
	/**
	 * This is the only constructor method. It takes in a unique card ID number and initiates an instance of a card with an icon and value.
	 * @param cardId
	 */
	public Card(String cardId){
		if(cardId.length()>2) {
			if (Integer.valueOf(cardId.substring(2,3))<1) {
				this.value=10;
			}else {
				this.value=100;
			}
		}else {
			this.value=Integer.valueOf(cardId.substring(1,2));
		}
		String url="Images/card_"+cardId+".gif";
		icon= new ImageIcon(url);
	}
	
	/**
	 * This is a getter method that returns the card icon
	 * @return ImageIcon
	 */
	ImageIcon getIcon() {
		return icon;
	}
	
	/**
	 * This is a getter method that returns the card value
	 * @return value Value of the card
	 */
	int getValue() {
		return value;
	}
	
	/**
	 * The getAllCards method returns 52 unique card IDs for the deck
	 * @return ArrayList<String> ArrayList that stores the card IDs in string format
	 */
	public static ArrayList<String> getAllCards(){
		ArrayList<String> allCards= new ArrayList<String>();
		for (int i=1; i<5; i++) {
			for (int x=1; x<14; x++) {
				String id="";
				id+=Integer.toString(i);
				id+=Integer.toString(x);
				allCards.add(id);
			}
		}
		return allCards;
	}
	
	/**
	 * The pickCard generates a method to return a random number to simulate a shuffled deck
	 * @param cardList
	 * @return CardPosition The position to pick a card from
	 */
	public static int pickCard(ArrayList<String> cardList) {
		int cardPos=(int)(Math.random()*((cardList.size()-1)+1))+1;
		return cardPos-1;
	}
}

