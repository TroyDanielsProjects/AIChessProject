package Players;

/*
 * Saves the details of a move so that it can be undone later or used by the player.
 */
public class MiniMaxReturn {
	public int[][] move;
	public double value;
	
	public MiniMaxReturn() {
		this.value = 0;
	}
}
