package Pieces;
import java.util.ArrayList;

import Pieces.King;
import Pieces.Piece;


/*
 * represents the Rook piece and its movements
 */
public class Rook extends Piece{
	public boolean isKingSide;
	public King myKing;
	
	/*
	 * Constructor for Rook Piece
	 */
	public Rook(boolean isWhite, int[] currLocation, King king, Piece[][] board) {
		super.isWhite = isWhite;
		super.name = "R";
		super.value = 5;
		super.location = currLocation;
		super.board = board;
		this.myKing = king;
		if (this.location[1]==7) {
			this.isKingSide = false;
		}
		else {
			this.isKingSide = true;
		}
	}

	/*
	 * Override method to set what movements are possible for the Rook. Only looks at movement
	 * and not circumstance (for example if pieces block movement)
	 */
	public boolean legalMove(int[] prevLoc, int[] nextLoc) {
		boolean isLegal = false;
		if (board[prevLoc[0]][prevLoc[1]]==null) {
			isLegal = false;
		}
		else if (nextLoc[0]>7 || nextLoc[1]>7 || nextLoc[0]<0 || nextLoc[1]<0) {
			isLegal = false;
		}
		else if (board[nextLoc[0]][nextLoc[1]]!= null && board[prevLoc[0]][prevLoc[1]].isWhite==board[nextLoc[0]][nextLoc[1]].isWhite) {
			isLegal = false;
		}
		else if ( (( nextLoc[0]-prevLoc[0]!=0 && nextLoc[1]-prevLoc[1]==0 ) || ( nextLoc[0]-prevLoc[0]==0 && nextLoc[1]-prevLoc[1]!=0 )) && !isBlocked(prevLoc,nextLoc) ) {
			isLegal = true;
		}
		else {
			isLegal = false;
		}
		return isLegal;
	}
	
	/*
	 * Checks to see if a piece exists between original position and where it moved to.
	 * Making it a illegal move (boolean - true = illegal move)
	 */
	public boolean isBlocked(int[] prevLoc, int[] nextLoc) {
		int[] tempLoc = nextLoc.clone();
		if ( nextLoc[0]-prevLoc[0]>0 ) {
			tempLoc[0]-=1;
		}
		else if ( nextLoc[0]-prevLoc[0]<0 ) {
			tempLoc[0]+=1;
		}
		else if ( nextLoc[1]-prevLoc[1]>0 ) {
			tempLoc[1]-=1;
		}
		else {
			tempLoc[1]+=1;
		}
		while (tempLoc[0]!=prevLoc[0] || tempLoc[1]!= prevLoc[1]) {
			if ( board[tempLoc[0]][tempLoc[1]]!=null ) {
				return true;
			}
			if ( nextLoc[0]-prevLoc[0]>0 ) {
				tempLoc[0]-=1;
			}
			else if ( nextLoc[0]-prevLoc[0]<0 ) {
				tempLoc[0]+=1;
			}
			else if ( nextLoc[1]-prevLoc[1]>0 ) {
				tempLoc[1]-=1;
			}
			else {
				tempLoc[1]+=1;
			}
		}
		return false;
	}
	
	/*
	 * moves the Rook (but not on the board)
	 */
	public void move(int[] newPosition) {
		if (!this.isKingSide) {
			this.myKing.canCastleQueenSide = false;
		}
		else if (this.isKingSide) {
			this.myKing.canCastleKingSide = false;
		}
		this.location = newPosition;
	}
	
	/*
	 * Finds all possible legal moves
	 */
	public ArrayList<int[][]> possibleMoveLocations() {
		ArrayList<int[][]> possMoves = new ArrayList<int[][]>();
		int currRow = this.location[0]+1;
		int currColumn = this.location[1];
		while (currRow<8) {
			int[] possibleLocation = new int[] {currRow,currColumn};
			if (legalMove(this.location,possibleLocation)) {
				possMoves.add(new int[][] {this.location,possibleLocation});
			}
			currRow++;
		}
		currRow = this.location[0];
		currColumn = this.location[1]+1;
		while (currColumn<8) {
			int[] possibleLocation = new int[] {currRow,currColumn};
			if (legalMove(this.location,possibleLocation)) {
				possMoves.add(new int[][] {this.location,possibleLocation});
			}
			currColumn++;
		}
		currRow = this.location[0]-1;
		currColumn = this.location[1];
		while (currRow>=0) {
			int[] possibleLocation = new int[] {currRow,currColumn};
			if (legalMove(this.location,possibleLocation)) {
				possMoves.add(new int[][] {this.location,possibleLocation});
			}
			currRow--;
		}
		currColumn = this.location[1]-1;
		currRow = this.location[0];
		while (currColumn>=0) {
			int[] possibleLocation = new int[] {currRow,currColumn};
			if (legalMove(this.location,possibleLocation)) {
				possMoves.add(new int[][] {this.location,possibleLocation});
			}
			currColumn--;
		}
		return possMoves;
	}

	
}
