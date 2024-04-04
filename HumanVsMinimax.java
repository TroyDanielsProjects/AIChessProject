

public class HumanVsMinimax {
	
	public static void main(String[] args) {
		Board testBoard  = new Board();
		Player testPlayerWhite = new HumanPlayer(testBoard.getWhitePieces(),testBoard,"white",true);
		Player testPlayerBlack = new MiniMaxPlayer(testBoard.getBlackPieces(),testBoard,"black",false, testPlayerWhite);
		Chess testChess = new Chess(testPlayerWhite,testPlayerBlack,testBoard);
		testChess.playGame();
		
	}

}
