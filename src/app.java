import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This program implements a simple, card game between a dealer and a player.
 * 
 * @author Diabul Haque
 * @version 1.0
 */

public class app {
	
	//creating the image labels
	private static JLabel label_Image1 = new JLabel();
	private static JLabel label_Image2 = new JLabel();
	private static JLabel label_Image3 = new JLabel();
	private static JLabel label_Image4 = new JLabel();
	private static JLabel label_Image5 = new JLabel();
	private static JLabel label_Image6 = new JLabel();
	
	private static boolean rp_enabled1=false;
	private static boolean rp_enabled2=false;
	private static boolean rp_enabled3=false;
	private static boolean start_enabled=true;
	private static boolean result_enabled=false;
	
	private static ArrayList<String> remainingCards=Card.getAllCards();
	private static Card[] initialCards=new Card[6];
	
	private static int cardsRp=0;
	private static int intbet=0;
	private static int remainingMoney=100;
	private static String initialInfo="Please place your bet!";
	
	/**
	 * This method checks if the player won
	 * 
	 * @param cards The cards on the table
	 * @return true is player wins and false if player loses
	 */
	public static boolean didWin(Card[] cards) {
		int dealerValue=cards[0].getValue()+cards[1].getValue()+cards[2].getValue();
		int playerValue=cards[3].getValue()+cards[4].getValue()+cards[5].getValue();;
		int diff=playerValue-dealerValue;
		
		if (diff>39) {
			return true;
		}else if (diff<-39) {
			return false;
		}
		
		playerValue=playerValue%10;
		dealerValue=dealerValue%10;
		
		if(playerValue>dealerValue) {
			return true;
		}else if(playerValue<dealerValue) {
			return false;
		}
		return false;
	}
	
	/**
	 * This is the main method. It generates the GUI and adds functionality through actionListeners.
	 * @param args Unused
	 * @return Nothing
	 */
	
	public static void main(String[] args) {
		for (int i=0; i<6; i++) {
			int num=Card.pickCard(remainingCards);
			String cardId=remainingCards.get(num);
			remainingCards.remove(num);
			initialCards[i]=new Card(cardId);
		}
		
		//creating the menu bar
		JMenuBar menubar=new JMenuBar();
		
		JMenu control= new JMenu("Control");
		JMenuItem controlExit= new JMenuItem("Exit");
		control.add(controlExit);
		
		JMenu help= new JMenu("Help");
		JMenuItem rules= new JMenuItem("Rules");
		help.add(rules);
		
		menubar.add(control);
		menubar.add(help);
		
		
		//setting the image labels
		ImageIcon cardback= new ImageIcon("Images/card_back.gif");
		label_Image1.setIcon(cardback);
		label_Image2.setIcon(cardback);
		label_Image3.setIcon(cardback);
		label_Image4.setIcon(cardback);
		label_Image5.setIcon(cardback);
		label_Image6.setIcon(cardback);
		
		//creating the buttons for replacing player cards
		JButton btn_rpcard1= new JButton("Replace Card 1");
		JButton btn_rpcard2= new JButton("Replace Card 2");
		JButton btn_rpcard3= new JButton("Replace Card 3");
		
		
		
		//creating the components for the betting panel
		JLabel label_bet= new JLabel("Bet: $");
		JTextField txt_inputbet = new JTextField(10);
		JButton btn_start= new JButton("Start");
		JButton btn_result= new JButton("Result");
		
		//creating the components for the info panel
		JLabel label_info=new JLabel(initialInfo);
		JLabel label_money=new JLabel("Amount of money you have: $"+Integer.toString(remainingMoney));
		
		JPanel MainPanel = new JPanel();
		
		//creating panel for the dealer
		JPanel DealerPanel = new JPanel();
		DealerPanel.add(label_Image1);
		DealerPanel.add(label_Image2);
		DealerPanel.add(label_Image3);
		
		//creating panel for the player
		JPanel PlayerPanel = new JPanel(); 
		PlayerPanel.add(label_Image4);
		PlayerPanel.add(label_Image5);
		PlayerPanel.add(label_Image6);
		
		//creating panel that replaces cards
		JPanel RpCardBtnPanel = new JPanel();
		RpCardBtnPanel.add(btn_rpcard1);
		RpCardBtnPanel.add(btn_rpcard2);
		RpCardBtnPanel.add(btn_rpcard3);
		
		//creating panel for the betting
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.add(label_bet);
		ButtonPanel.add(txt_inputbet);
		ButtonPanel.add(btn_start);
		ButtonPanel.add(btn_result);
		
		//creating panel that shows the info
		JPanel InfoPanel = new JPanel();
		InfoPanel.add(label_info);
		InfoPanel.add(label_money);
		
		MainPanel.setLayout(new GridLayout(5,1));
		MainPanel.add(DealerPanel);
		MainPanel.add(PlayerPanel);
		MainPanel.add(RpCardBtnPanel);
		MainPanel.add(ButtonPanel);
		MainPanel.add(InfoPanel);
		
		DealerPanel.setBackground(Color.green);
		PlayerPanel.setBackground(Color.green);
		RpCardBtnPanel.setBackground(Color.green);
		
		JFrame frame= new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(MainPanel);
		frame.setTitle("A Simple Card Game");
		frame.setJMenuBar(menubar);
		frame.setSize(400, 700);
		frame.setVisible(true);
		
		//adding action listeners
		btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(start_enabled) {
					String bet=txt_inputbet.getText();
					if (bet.length()>0) {
						try {
							int number=Integer.parseInt(bet);
							if (number>0) {
								if(number<=remainingMoney) {
									intbet=number;
									rp_enabled1=true;
									rp_enabled2=true;
									rp_enabled3=true;
									result_enabled=true;
									start_enabled=false;
									label_Image4.setIcon(initialCards[3].getIcon());
									label_Image5.setIcon(initialCards[4].getIcon());
									label_Image6.setIcon(initialCards[5].getIcon());
									String info="Your current bet is: $"+bet;
									label_info.setText(info);
								}else {
									JFrame DialogFrame= new JFrame("Message");
									JOptionPane.showMessageDialog(DialogFrame, "WARNING: You can't bet more than the amount of money you have!");
								}
							}else {
								JFrame DialogFrame= new JFrame("Message");
								JOptionPane.showMessageDialog(DialogFrame, "WARNING: The bet you place must be a positive integer!");
							}
						}catch(Exception event){
							JFrame DialogFrame= new JFrame("Message");
							JOptionPane.showMessageDialog(DialogFrame, "WARNING: The bet you place must be a positive integer!");
						}
					}else {
						JFrame DialogFrame= new JFrame("Message");
						JOptionPane.showMessageDialog(DialogFrame, "WARNING: You have to place a bet");
					}
				}
			}	
		});
		
		btn_result.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(result_enabled) {
					label_Image1.setIcon(initialCards[0].getIcon());
					label_Image2.setIcon(initialCards[1].getIcon());
					label_Image3.setIcon(initialCards[2].getIcon());
					
					boolean win=didWin(initialCards);
					if (win) {
						remainingMoney+=intbet;
						JFrame DialogFrame= new JFrame("Message");
						JOptionPane.showMessageDialog(DialogFrame, "Congratulations! You win this round!");
						label_info.setText(initialInfo);
						label_money.setText("Amount of money you have: $"+Integer.toString(remainingMoney));
						start_enabled=true;
					}else {
						remainingMoney-=intbet;
						JFrame DialogFrame= new JFrame("Message");
						if(remainingMoney<=0) {
							label_info.setText("You have no more money!");
							label_money.setText("Please start a new game!");
							JOptionPane.showMessageDialog(DialogFrame, "Game Over!\nYou have no more money!\nPlease start a new game!");
							start_enabled=false;
						}else {
							JOptionPane.showMessageDialog(DialogFrame, "Sorry! The Dealer wins this round!");
							label_info.setText(initialInfo);
							label_money.setText("Amount of money you have: $"+Integer.toString(remainingMoney));
							start_enabled=true;
						}
						
					}
					
					rp_enabled1=false;
					rp_enabled2=false;
					rp_enabled3=false;
					result_enabled=false;
					cardsRp=0;
					
					
					remainingCards=Card.getAllCards();
					initialCards=new Card[6];
					for (int i=0; i<6; i++) {
						int num=Card.pickCard(remainingCards);
						String cardId=remainingCards.get(num);
						remainingCards.remove(num);
						initialCards[i]=new Card(cardId);
					}
					
					label_Image1.setIcon(cardback);
					label_Image2.setIcon(cardback);
					label_Image3.setIcon(cardback);
					label_Image4.setIcon(cardback);
					label_Image5.setIcon(cardback);
					label_Image6.setIcon(cardback);
				}
			}
		});
		
		controlExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		btn_rpcard1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rp_enabled1 && cardsRp<2) {
					cardsRp+=1;
					int num=Card.pickCard(remainingCards);
					String cardId=remainingCards.get(num);
					remainingCards.remove(num);
					initialCards[3]=new Card(cardId);
					label_Image4.setIcon(initialCards[3].getIcon());
					rp_enabled1=false;
				}
			}
		});
		
		btn_rpcard2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rp_enabled2 && cardsRp<2) {
					cardsRp+=1;
					int num=Card.pickCard(remainingCards);
					String cardId=remainingCards.get(num);
					remainingCards.remove(num);
					initialCards[4]=new Card(cardId);
					label_Image5.setIcon(initialCards[4].getIcon());
					rp_enabled2=false;
				}
				
			}
		});
		
		btn_rpcard3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rp_enabled3 && cardsRp<2) {
					cardsRp+=1;
					int num=Card.pickCard(remainingCards);
					String cardId=remainingCards.get(num);
					remainingCards.remove(num);
					initialCards[5]=new Card(cardId);
					label_Image6.setIcon(initialCards[5].getIcon());
					rp_enabled3=false;
				}
			}
		});
		
		rules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String helpMessage="Rules to detemine who has better cards:\nJ, Q, K are regarded as special cards.\nRule 1: The one with more special cards wins.\nRule 2: If both have the same number of special cards, add the face values of the other card(s) and take the remainder after dividing the sum by 10. The one with the bigger remainder wins. (Note: Ace is 1).\nRule 3: The dealer wins if both rule 1 and rule 2 cannot distinguish the winner.";
				JFrame DialogFrame= new JFrame("Message");
				JOptionPane.showMessageDialog(DialogFrame, helpMessage);
			}
		});
		
	}

}

