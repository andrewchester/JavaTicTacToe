import javax.swing.JFrame;
import javax.swing.SwingUtilities;
//Player class, hold their wins, letter, and name
public class Player {
	GameBoard board;
	private String letter;
	private String pName;
	private int wins;
	Player(GameBoard board, int playerNum){
		this.board = board;
		wins = 0;
		if(playerNum == 1){
			letter = board.getX();
			pName = "Player 1";
		}else{
			letter = board.getO();
			pName = "Player 2";
		}
	}
	//Getters and setters, fun fact if you right click, go to source, and then click on generate getters and setters, eclipse will create these for you
	public int getWins(){ return wins; }
	public void addWins(int num){wins += num;}
	public String getName(){ return pName; }
	public void setName(String name){this.pName = name;}
}
