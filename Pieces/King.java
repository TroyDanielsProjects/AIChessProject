package Pieces;
import Game.Board;

import java.util.ArrayList;

/*
 * Class that represents the King Piece and its movements
 */
public class King extends Piece {
	
	public boolean canCastleKingSide;
	public boolean canCastleQueenSide;
	public Board currBoard;
	
	public King(boolean isWhite, int[] currLocation,Piece[][] board,Board currBoard) {
		super.isWhite = isWhite;
		super.name = "K";
		super.value = 0;
		super.location = currLocation;
		super.board = board;
		this.canCastleKingSide = true;
		this.canCastleQueenSide = true;
		this.currBoard = currBoard;
	}
	
	/*
	 * Checks to see if move is legal, also checks for Castle maneuver 
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
		else if ((canCastleKingSide && !currBoard.isSquareBeingAttacked(this.isWhite, this.location) && prevLoc[1]==3 && (prevLoc[0] == 0 && nextLoc[0]==0 && this.isWhite) && nextLoc[1]==1) && kingCastle(prevLoc,nextLoc)) {
			isLegal = true;
		}
		else if ((canCastleKingSide && !currBoard.isSquareBeingAttacked(this.isWhite, this.location) && prevLoc[1]==3 && (prevLoc[0] ==7 && nextLoc[0]==7 && !this.isWhite) && nextLoc[1]==1) && kingCastle(prevLoc,nextLoc)) {
			isLegal = true;
		}
		else if ((canCastleQueenSide && !currBoard.isSquareBeingAttacked(this.isWhite, this.location) && prevLoc[1]==3 && (prevLoc[0] == 0 && nextLoc[0]==0 && this.isWhite) && nextLoc[1]==5 && kingCastle(prevLoc,nextLoc))) {
			isLegal = true;
		}
		else if ((canCastleQueenSide && !currBoard.isSquareBeingAttacked(this.isWhite, this.location) && prevLoc[1]==3 && (prevLoc[0] == 7 && nextLoc[0]==7 && !this.isWhite) && nextLoc[1]==5 && kingCastle(prevLoc,nextLoc))) {
			isLegal = true;
		}
		else if ( Math.abs(nextLoc[0]-prevLoc[0])>1 || Math.abs(nextLoc[1]-prevLoc[1])>1) {
			isLegal = false;
		}
		else {
			isLegal = true;
		}
		return isLegal;
	}
	
	/*
	 * Helper method to determine if Castle maneuver is legal
	 */
	public boolean kingCastle(int[] prevLoc, int[] nextLoc) {
		boolean isLegal = false;
		if (canCastleKingSide && board[prevLoc[0]][prevLoc[1]-1]==null && board[prevLoc[0]][prevLoc[1]-2]==null 
				&& !currBoard.isSquareBeingAttacked(this.isWhite, new int[] {prevLoc[0],prevLoc[1]-1}) 
				&& !currBoard.isSquareBeingAttacked(this.isWhite, new int[] {prevLoc[0],prevLoc[1]-2})){
			isLegal = true;
		}
		else if (nextLoc[1]==5 && canCastleQueenSide && board[prevLoc[0]][prevLoc[1]+1]==null && board[prevLoc[0]][prevLoc[1]+2]==null 
				&& board[prevLoc[0]][prevLoc[1]+3]==null && !currBoard.isSquareBeingAttacked(this.isWhite, new int[] {prevLoc[0],prevLoc[1]+1}) 
				&& !currBoard.isSquareBeingAttacked(this.isWhite, new int[] {prevLoc[0],prevLoc[1]+2}) 
				&& !currBoard.isSquareBeingAttacked(this.isWhite, new int[] {prevLoc[0],prevLoc[1]+3})){
			isLegal = true;
		}
		return isLegal;

	}
	
	/*
	 * Checks to see if a capture move is legal
	 */
	public boolean legalAttack(int[] nextLoc) {
		boolean isLegal = false;
		int[] prevLoc = this.location;
		if (board[prevLoc[0]][prevLoc[1]]==null) {
			isLegal = false;
		}
		else if (nextLoc[0]>7 || nextLoc[1]>7 || nextLoc[0]<0 || nextLoc[1]<0) {
			isLegal = false;
		}
		else if (board[nextLoc[0]][nextLoc[1]]!= null && board[prevLoc[0]][prevLoc[1]].isWhite==board[nextLoc[0]][nextLoc[1]].isWhite) {
			isLegal = false;
		}
		else if ( Math.abs(nextLoc[0]-prevLoc[0])>1 || Math.abs(nextLoc[1]-prevLoc[1])>1) {
			isLegal = false;
		}
		else {
			isLegal = true;
		}
		return isLegal;
	}
	

	/*
	 * Checks to see if the location it is trying to move to is blocked by another piece
	 */
	public boolean isBlocked(int[] prevLoc, int[] nextLoc) {
		if ( (nextLoc[1]==1 && prevLoc[1]==3) || (nextLoc[1]==5 && prevLoc[1]==3) ) {
			return kingCastle(prevLoc, nextLoc);
		}
		return false;
	}
	
	/*
	 * moves the King's location
	 */
	public void move(int[] newPosition) {
		this.location = newPosition;
		this.canCastleKingSide = false;
		this.canCastleQueenSide = false;
	}
	
	
	/*
	 * Finds all possible legal moves
	 */
	public ArrayList<int[][]> possibleMoveLocations() {
		ArrayList<int[][]> possMoves = new ArrayList<int[][]>();
		int[][] kingSideCastle = {this.location,{this.location[0],1}}; 
		if (legalMove(kingSideCastle[0],kingSideCastle[1])){
			possMoves.add(kingSideCastle);
		}
		int[][] QueenSideCastle = {this.location,{this.location[0],5}}; 
		if (legalMove(QueenSideCastle[0],QueenSideCastle[1])){
			possMoves.add(QueenSideCastle);
		}
		for (int i = -1; i<2;i++) {
			for (int j = -1; j<2;j++) {
				if (i!=0 || j!=0) {
					int[][] currentMove = new int[][] {this.location,{this.location[0]+i,this.location[1]+j}};
					if (legalMove(currentMove[1])) {
						possMoves.add(currentMove);
					}
				}
			}
		}
		return possMoves;
	}

	
}
