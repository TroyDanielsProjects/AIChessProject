package Game;
import Players.Player;

/*
 * Creates the game of chess itself - the functionality of turns until the game ends
 */
public class Chess {
	Player player1;
	Player player2;
	Board board;
	
	/*
	 * constructor for to create a game. Needs both players and a board
	 */
	public Chess(Player player1,Player player2, Board board) {
		this.player1 = player1;
		this.player2 = player2;
		this.board = board;
	}
	
	/*
	 * Starts game, iterate turns until game ends.
	 */
	public void playGame() {
		boolean isCheckmate = false;
		boolean isWhitesTurn = true;
		while (!isCheckmate) {
			if (isWhitesTurn) {
				this.player1.turn();
			}
			else {
				this.player2.turn();
			}
			if ( (this.player1.isInCheck() && this.player1.checkmate()) || (this.player2.isInCheck() && this.player2.checkmate() )) {
				isCheckmate = true;
			}
			isWhitesTurn = !isWhitesTurn;
		}
		System.out.println(this.board);
		System.out.println("game over!");
	}
}
