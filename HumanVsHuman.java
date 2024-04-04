

public class HumanVsHuman {

	public static void main(String[] args) {
		Board testBoard  = new Board();
		Player testPlayerWhite = new HumanPlayer(testBoard.getWhitePieces(),testBoard,"white",true);
		Player testPlayerBlack = new HumanPlayer(testBoard.getBlackPieces(),testBoard,"black",false);
		Chess testChess = new Chess(testPlayerWhite,testPlayerBlack,testBoard);
		testChess.playGame();
		
	}
	
}
