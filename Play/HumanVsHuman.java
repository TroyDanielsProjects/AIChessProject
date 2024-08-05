package Play;
import Game.Board;
import Players.Player;
import Game.Chess;
import Players.HumanPlayer;

/*
 * run class to start a game between two human players
 */
public class HumanVsHuman {

	public static void main(String[] args) {
		Board testBoard  = new Board();
		Player testPlayerWhite = new HumanPlayer(testBoard.getWhitePieces(),testBoard,"white",true);
		Player testPlayerBlack = new HumanPlayer(testBoard.getBlackPieces(),testBoard,"black",false);
		Chess testChess = new Chess(testPlayerWhite,testPlayerBlack,testBoard);
		testChess.playGame();
		
	}
	
}
