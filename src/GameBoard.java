import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
//This class handles the buttons on the board, checking if the game is over, and handling the gamestate & end of the game
public class GameBoard {
	private Button board[][];
	
	//Characters(which are strings) for the markers
	private final String BLANK = " ";
	private final String X = "X";
	private final String O = "O";
	
	
	private Outcomes result;
	private States gameState = States.P1;
	
	private Player p1;
	private Player p2;
	
	//Initializing all the buttons, adding them to the 2d array, and adding an action listener to each
	GameBoard(){
		board = new Button[3][3];
		
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board[0].length; y++){
				Button tempButton = new Button(BLANK);
				tempButton.setSize(300,200);
				tempButton.setLabel(BLANK);
				tempButton.setLocation(x * 300, y * 200);
				tempButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						buttonPressed(tempButton, e);
					}
				});
				board[x][y] = tempButton;
			}
		}
	}
	//ActionListener for each button, updates the button depending on the gamestate. Also switches the gamestate and is the go between from playing the game to the game being over
	@SuppressWarnings("deprecation")
	public void buttonPressed(Button button, ActionEvent e){
		if(button.getLabel() == BLANK){
			if(gameState == States.P1){
				button.setLabel(X);
				button.disable();
				if(testEnd()){
					gameOver(result);
				}else{
					gameState = States.P2;
				}
			}else if(gameState == States.P2){
				button.setLabel(O);
				button.disable();
				if(testEnd()){
					gameOver(result);
				}else{
					gameState = States.P1;
				}
			}else{
				return;
			}
		}
	}
	//All the win conditions in one place
	public boolean testEnd(){
		if(checkDiags()){
			return true;
		}
		for(int i = 0; i < board.length; i++)
			if(threeInARow(i, false))
				return true;
		
		for(int i = 0; i < board[0].length; i++)
			if(threeInARow(i, true))
				return true;
		
		
		if(isFull()){
			return true;
		}
		return false;
	}
	//Returns true if the board is full(since each button is disabled on click it checks how many are disabled)
	public boolean isFull(){
		int numFilled = 9;
		for(int i = 0; i < board.length; i++)
			for(int y = 0; y < board[0].length; y++)
				if(board[i][y].isEnabled())
					numFilled--;
		if(numFilled == 9){
			result = Outcomes.TIE;
			return true;
		}
		System.out.println(numFilled);
		
		return false;
	}
	//Manually checks each diagonal
	public boolean checkDiags(){
		String topLeft = board[0][0].getLabel();
		String topRight = board[2][0].getLabel();
		String middle = board[1][1].getLabel();
		String bottomLeft = board[0][2].getLabel();
		String bottomRight = board[2][2].getLabel();
		if(topLeft == middle && middle == bottomRight && bottomRight != BLANK){
			if(bottomRight == X)
				result = Outcomes.X_WIN;
			else if(bottomRight == O)
				result = Outcomes.O_WIN;
			return true;
		}
		else if(topRight == middle && middle == bottomLeft && bottomLeft != BLANK){
			if(bottomLeft == X)
				result = Outcomes.X_WIN;
			else if(bottomLeft == O)
				result = Outcomes.O_WIN;
			return true;
		}else {
			return false;
		}
	}
	//Checks a specific row, meant to be used in a for loop to check a large number of rows/columns at once
	public boolean threeInARow(int num, boolean isRow){
		if(isRow){
			String first = board[num][0].getLabel();
			String second = board[num][1].getLabel();
			String third = board[num][2].getLabel();
			if(first == second && second == third && third != BLANK){
				if(third == X){
					result = Outcomes.X_WIN;
				}else{
					result = Outcomes.O_WIN;
				}
				return true;
			}else{
				return false;
			}
		}else{
			String first = board[0][num].getLabel();
			String second = board[1][num].getLabel();
			String third = board[2][num].getLabel();
			if(first == second && second == third && third != BLANK){
				if(third == X){
					result = Outcomes.X_WIN;
				}else{
					result = Outcomes.O_WIN;
				}
				return true;
			}else{ 
				return false;
			}
		}
	}
	//Game over method, creates a window with text changing based on who won and the win condition(Tie or a player won)
	public void gameOver(Outcomes r){
		JFrame f = new JFrame("Game Over!");
		f.setSize(300, 100);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setLayout(null);
		Button ok = new Button("Ok");
		ok.setBounds(90,35,50,25);
		Button close = new Button("Close");
		close.setBounds(190,35,50,25);
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clear();
				f.dispose();
			}
		});
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		JLabel prompt = new JLabel();
		if(r == Outcomes.X_WIN){
			p1.addWins(1);
			prompt.setText(p1.getName() + " has won! Play Again?");
		}else if(r == Outcomes.O_WIN){
			p2.addWins(1);
			prompt.setText(p2.getName() + " has won! Play Again?");
		}else{
			prompt.setText("It's a tie! Play Again?");
		}
		prompt.setBounds(60, 10, 200, 25);
		f.add(ok);
		f.add(close);
		f.add(prompt);
		f.setVisible(true);
	}
	public void clear(){
		for(int i = 0; i < board.length; i++){
			for(int I = 0; I < board[0].length; I++){
				board[i][I].setLabel(BLANK);
				board[i][I].setEnabled(true);
			}
		}
	}
	public void addBoard(JFrame frame){
		for(int x = 0; x < board.length; x++)
			for(int y = 0; y < board[0].length; y++)
				frame.add(board[x][y]);
	}

	public Button[][] getBoard() {return board;}
	public void setBoard(Button[][] board) {this.board = board;}
	public String getBLANK() {return BLANK;}
	public String getX() {return X;}
	public String getO() {return O;}
	public String getStrState(){
		if(gameState == States.P1){
			return "Player 1's turn";
		}else if (gameState == States.P2){
			return "Player 2's turn";
		}else{
			return "Game Over";
		}
	}
	public States getState(){return gameState;}
	public void setState(States gameState){this.gameState = gameState;}
	public void setPlayers(Player p1, Player p2){
		this.p1 = p1;
		this.p2 = p2;
	}
}
