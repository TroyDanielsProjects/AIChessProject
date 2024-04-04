

import java.util.ArrayList;

public abstract class Piece {
	protected int value;
	protected String name;
	public boolean isWhite;
	protected int[] location;
	protected Piece[][] board;
	
	public abstract boolean legalMove(int[] prevLoc, int[] nextLoc);
		
	
	public abstract boolean isBlocked(int[] prevLoc, int[] nextLoc);
	
	public boolean legalMove(int[] nextLoc) {
		return legalMove(this.location,nextLoc);
	}
	
	public boolean legalAttack(int[] nextLoc) {
		return legalMove(this.location,nextLoc);
	}
	
	public void move(int[] newPosition) {
		this.location = newPosition;
	}
	
	public void unregisteredMove(int[] newPosition) {
		this.location = newPosition;
	}
	
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
