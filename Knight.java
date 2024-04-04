

import java.util.ArrayList;

public class Knight extends Piece{
	
	public Knight(boolean isWhite, int[] currLocation, Piece[][] board) {
		super.isWhite = isWhite;
		super.name = "N";
		super.value = 3;
		super.location = currLocation;
		super.board = board;
	}

	@Override
	public boolean legalMove(int[] prevLoc, int[] nextLoc) {
		boolean isLegal = false;
		if (board[prevLoc[0]][prevLoc[1]]==null) {
			isLegal = false;
		}
		else if (nextLoc[0]>7 || nextLoc[1]>7 || nextLoc[0]<0 || nextLoc[1]<0 || isBlocked(prevLoc,nextLoc)) {
			isLegal = false;
		}
		else if (board[nextLoc[0]][nextLoc[1]]!= null && board[prevLoc[0]][prevLoc[1]].isWhite==board[nextLoc[0]][nextLoc[1]].isWhite) {
			isLegal = false;
		}
		else if ( ( Math.abs(nextLoc[0]-prevLoc[0])==2 && Math.abs(nextLoc[1]-prevLoc[1])==1 ) || ( Math.abs(nextLoc[0]-prevLoc[0])==1 && Math.abs(nextLoc[1]-prevLoc[1])==2) ){
			isLegal = true;
		}
		else {
			isLegal = false;
		}
		return isLegal;
	}


	@Override
	public boolean isBlocked(int[] prevLoc, int[] nextLoc) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<int[][]> possibleMoveLocations() {
		ArrayList<int[][]> possMoves = new ArrayList<int[][]>();
		int[][] currentMove = new int[][] {this.location,{this.location[0]+2,this.location[1]+1}};
		if (legalMove(currentMove[1])) {
			possMoves.add(currentMove);
		}
		currentMove = new int[][] {this.location,{this.location[0]+2,this.location[1]-1}};
		if (legalMove(currentMove[1])) {
			possMoves.add(currentMove);
		}
		currentMove = new int[][] {this.location,{this.location[0]+1,this.location[1]+2}};
		if (legalMove(currentMove[1])) {
			possMoves.add(currentMove);
		}
		currentMove = new int[][] {this.location,{this.location[0]+1,this.location[1]-2}};
		if (legalMove(currentMove[1])) {
			possMoves.add(currentMove);
		}
		currentMove = new int[][] {this.location,{this.location[0]-1,this.location[1]+2}};
		if (legalMove(currentMove[1])) {
			possMoves.add(currentMove);
		}
		currentMove = new int[][] {this.location,{this.location[0]-1,this.location[1]-2}};
		if (legalMove(currentMove[1])) {
			possMoves.add(currentMove);
		}
		currentMove = new int[][] {this.location,{this.location[0]-2,this.location[1]+1}};
		if (legalMove(currentMove[1])) {
			possMoves.add(currentMove);
		}
		currentMove = new int[][] {this.location,{this.location[0]-2,this.location[1]-1}};
		if (legalMove(currentMove[1])) {
			possMoves.add(currentMove);
		}
		
		return possMoves;
	}


}
