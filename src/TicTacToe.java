import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
//Main Class for the game, handles the gui mostly 
public class TicTacToe {
	JFrame frame;
	GameBoard board;
	Player p1;
	Player p2;
	boolean updating;
	
	JLabel turn;
	JLabel p1Wins;
	JLabel p2Wins;
	
	TicTacToe(){
		// Initialization //
		
		frame = new JFrame("TicTacToe");
		board = new GameBoard();
		p1 = new Player(board,1);
		p2 = new Player(board,2);
		board.setPlayers(p1, p2);
		
		// JFrame Settings //
		
	    frame.setSize(900, 660);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	   	frame.setLayout(null);
	   	
		// Label Section //
	    p1Wins = new JLabel(p1.getName() + "'s wins: " + p1.getWins());
	    p1Wins.setBounds(15,590,100,50);
		
	    p2Wins = new JLabel(p2.getName() + "'s wins: " + p2.getWins());
	    p2Wins.setBounds(770,590, 100,50);
	    
	    turn = new JLabel(board.getStrState());
	    turn.setBounds(395, 590, 100,50);
	    
	   	// Action Listeners //
	   	ActionListener changeName = new ActionListener(){
	   		public void actionPerformed(ActionEvent e){
	   			//Set up the new window for taking the new name 
	   			String cButton = e.getActionCommand();
	   			JFrame f = new JFrame("New Name");
	   			f.setSize(300, 100);
	   			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	   			f.setLocationRelativeTo(null);
	   			f.setLayout(null);
	   			//Sets up all the labels and text fields
	   			JTextField nameEntry = new JTextField(20);
	   			nameEntry.setBounds(100,20,125,25);
	   			JLabel nameLabel = new JLabel("New Name Here: ");
	   			nameLabel.setBounds(2,20,100,25);
	   			Button ok = new Button("Ok");
	   			ok.setBounds(225,20,50,25);
	   			ok.addActionListener(new ActionListener(){
	   				//If you click the ok button it checks your input then closes the window
	   				public void actionPerformed(ActionEvent e){
	   					if(cButton == "p1Change"){
	   						if(nameEntry.getText().length() > 1)
	   							p1.setName(nameEntry.getText());
	   						p1Wins.setText(p1.getName() + "'s wins: " + p1.getWins());
	   					}else{
	   						if(nameEntry.getText().length() > 1)
	   							p2.setName(nameEntry.getText());
	   						p2Wins.setText(p2.getName() + "'s wins: " + p2.getWins());
	   					}
	   					f.dispose();
	   				}
	   			});
	   			f.add(nameEntry);
	   			f.add(nameLabel);
	   			f.add(ok);
	   			f.setVisible(true);
	   		}
	   	};
	    
	    // Button Section //
	    Button changeNameP1 = new Button("Change P1's name");
	    changeNameP1.setActionCommand("p1Change");
	    changeNameP1.setBounds(125,590,120,50);
	    changeNameP1.addActionListener(changeName);
	    
	    Button changeNameP2 = new Button("Change P2's name");
	    changeNameP2.setActionCommand("p2Change");
	    changeNameP2.setBounds(630,590,120,50);
	    changeNameP2.addActionListener(changeName);
	    
	    // Adding Components //
	    board.addBoard(frame);
		frame.add(p1Wins);
		frame.add(p2Wins);
		frame.add(turn);
		frame.add(changeNameP1);
		frame.add(changeNameP2);
	    
		frame.setVisible(true);
		updateComponents();
	}
	//Method for updating all the components cause all the variables are in other objects which can't access the components they need to update so we need to make convoluted run on sentences explaining it
	public void updateComponents(){
		updating = true;
		while(updating){//This loop doesn't really stop it just continuously updates until the program is terminated, the important part is that it can be terminated if necessary
			turn.setText(board.getStrState());
			p1Wins.setText(p1.getName() + "'s wins: " + p1.getWins());
			p2Wins.setText(p2.getName() + "'s wins: " + p2.getWins());
		}
	}
	public static void main(String[] args){ new TicTacToe(); }
}
