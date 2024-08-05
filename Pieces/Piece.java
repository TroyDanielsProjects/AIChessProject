package Pieces;


import java.util.ArrayList;

/*
 * Abstract class for all chess pieces to inherit.
 */
public abstract class Piece {
	
	// value of what the piece is worth
	public int value;
	// Name of the piece
	protected String name;
	// sets the player the piece belongs to
	public boolean isWhite;
	// The location of the piece on the board
	public int[] location;
	// reference to the board itself
	protected Piece[][] board;
	
	/*
	 * determines if a move is legal based upon the pieces movement style
	 */
	public abstract boolean legalMove(int[] prevLoc, int[] nextLoc);
		
	/*
	 * checks to see if there is a piece in between a move, preventing the move.
	 */
	public abstract boolean isBlocked(int[] prevLoc, int[] nextLoc);
	
	public boolean legalMove(int[] nextLoc) {
		return legalMove(this.location,nextLoc);
	}
	
	public boolean legalAttack(int[] nextLoc) {
		return legalMove(this.location,nextLoc);
	}
	
	/*
	 * updates the pieces location on the board after a move
	 */
	public void move(int[] newPosition) {
		this.location = newPosition;
	}
	
	/*
	 * Moves a piece on the condition that it will be moved back
	 * needed for checking permutations of alpha-beta tree
	 * only for program use. Do Not use for player input
	 */
	public void unregisteredMove(int[] newPosition) {
		this.location = newPosition;
	}
	
	/*
	 * finds all of the possible moves for the piece
	 */
	public abstract ArrayList<int[][]> possibleMoveLocations();
	
	public String toString() {
		String tempString;
		if (this.isWhite) {
			tempString = "(W)";
		}
		else {
			tempString = "(B)";
		}
		return this.name + tempString;
	}
}
