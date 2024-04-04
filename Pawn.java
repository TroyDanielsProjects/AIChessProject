

import java.util.ArrayList;

public class Pawn extends Piece{
	
	 boolean notMoved = true;
	
	public Pawn(boolean isWhite, int[] currLocation,Piece[][] board) {
		super.isWhite = isWhite;
		super.name = "P";
		super.value = 1;
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
		else if ( (nextLoc[0]-prevLoc[0]==1) && board[nextLoc[0]][nextLoc[1]]==null && (nextLoc[1]-prevLoc[1]==0) && this.isWhite && board[nextLoc[0]][nextLoc[1]]==null) {
			isLegal = true;
		}
		else if ( (nextLoc[0]-prevLoc[0]==-1) && board[nextLoc[0]][nextLoc[1]]==null && (nextLoc[1]-prevLoc[1]==0) && !this.isWhite && board[nextLoc[0]][nextLoc[1]]==null) {
			isLegal = true;
		}
		else if ( this.notMoved && board[nextLoc[0]][nextLoc[1]]==null && (((nextLoc[0]-prevLoc[0]==-2) && (nextLoc[1]-prevLoc[1]==0) && !this.isWhite) || ( (nextLoc[0]-prevLoc[0]==2) && (nextLoc[1]-prevLoc[1]==0) && this.isWhite)) ) {
			isLegal = true;
		}
		else if (board[nextLoc[0]][nextLoc[1]]!= null && board[prevLoc[0]][prevLoc[1]].isWhite && !board[nextLoc[0]][nextLoc[1]].isWhite && nextLoc[0]-prevLoc[0]==1 && Math.abs(nextLoc[1]-prevLoc[1])==1) {
			isLegal = true;
		}
		else if (board[nextLoc[0]][nextLoc[1]]!= null && !board[prevLoc[0]][prevLoc[1]].isWhite && board[nextLoc[0]][nextLoc[1]].isWhite && nextLoc[0]-prevLoc[0]==-1 && Math.abs(nextLoc[1]-prevLoc[1])==1) {
			isLegal = true;
		}
		else {
			isLegal = false;
		}
		return isLegal;
	}
	
	public void move(int[] newPosition) {
		this.location = newPosition;
		this.notMoved = false;
	}
	
	public boolean legalAttack(int[] nextLoc) {
		int[] prevLoc = this.location;
		boolean isLegal = false;
		if ( this.isWhite && nextLoc[0]-prevLoc[0]==1 && Math.abs(nextLoc[1]-prevLoc[1])==1) {
			isLegal = true;
		}
		else if (!this.isWhite && nextLoc[0]-prevLoc[0]==-1 && Math.abs(nextLoc[1]-prevLoc[1])==1) {
			isLegal = true;
		}
		return isLegal;
	}


	@Override
	public boolean isBlocked(int[] prevLoc, int[] nextLoc) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public ArrayList<int[][]> possibleMoveLocations() {
		ArrayList<int[][]> possMoves = new ArrayList<int[][]>();
		int[][] currentMove;
		if (this.isWhite) {
			if (this.location[0]==1) {
			currentMove = new int[][] {this.location,{this.location[0]+2,this.location[1]}};
				if (legalMove(currentMove[1])) {
					possMoves.add(currentMove);
				}
			}
			currentMove = new int[][] {this.location,{this.location[0]+1,this.location[1]}};
			if (legalMove(currentMove[1])) {
				possMoves.add(currentMove);
			}
			currentMove = new int[][] {this.location,{this.location[0]+1,this.location[1]+1}};
			if (legalMove(currentMove[1])) {
				possMoves.add(currentMove);
			}
			currentMove = new int[][] {this.location,{this.location[0]+1,this.location[1]-1}};
			if (legalMove(currentMove[1])) {
				possMoves.add(currentMove);
			}
		}
		else {
			if (this.location[0]==6) {
				currentMove = new int[][] {this.location,{this.location[0]-2,this.location[1]}};
				if (legalMove(currentMove[1])) {
					possMoves.add(currentMove);
				}
			}
			currentMove = new int[][] {this.location,{this.location[0]-1,this.location[1]}};
			if (legalMove(currentMove[1])) {
				possMoves.add(currentMove);
			}
			currentMove = new int[][] {this.location,{this.location[0]-1,this.location[1]+1}};
			if (legalMove(currentMove[1])) {
				possMoves.add(currentMove);
			}
			currentMove = new int[][] {this.location,{this.location[0]-1,this.location[1]-1}};
			if (legalMove(currentMove[1])) {
				possMoves.add(currentMove);
			}
		}
		return possMoves;
	}

}
