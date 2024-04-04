
import java.util.ArrayList;

public class Bishop extends Piece{
	
	public Bishop(boolean isWhite, int[] currLocation,Piece[][] board) {
		super.isWhite = isWhite;
		super.name = "B";
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
		else if (nextLoc[0]>7 || nextLoc[1]>7 || nextLoc[0]<0 || nextLoc[1]<0 || nextLoc[0]-prevLoc[0]==0 || nextLoc[1]-prevLoc[1]==0) {
			isLegal = false;
		}
		else if (this.board[nextLoc[0]][nextLoc[1]]!= null && board[prevLoc[0]][prevLoc[1]].isWhite==board[nextLoc[0]][nextLoc[1]].isWhite) {
			isLegal = false;
		}
		else if ( (nextLoc[0]-prevLoc[0]!=0 && nextLoc[1]-prevLoc[1]!=0 &&  Math.abs(nextLoc[0]-prevLoc[0]) == Math.abs(nextLoc[1]-prevLoc[1])) && !isBlocked(prevLoc,nextLoc)) {
			isLegal = true;
		}
		else {
			isLegal = false;;
		}
		return isLegal;
	}
	
	
	public boolean isBlocked(int[] prevLoc, int[] nextLoc) {
		int[] tempLoc = nextLoc.clone();
		if ( nextLoc[0]-prevLoc[0]>0 && nextLoc[1]-prevLoc[1]>0 ) {
			tempLoc[0]-=1;
			tempLoc[1]-=1;
		}
		else if ( nextLoc[0]-prevLoc[0]>0 && nextLoc[1]-prevLoc[1]<0 ) {
			tempLoc[0]-=1;
			tempLoc[1]+=1;
		}
		else if ( nextLoc[0]-prevLoc[0]<0 && nextLoc[1]-prevLoc[1]>0 ) {
			tempLoc[0]+=1;
			tempLoc[1]-=1;
		}
		else if ( nextLoc[0]-prevLoc[0]<0 && nextLoc[1]-prevLoc[1]<0 ) {
			tempLoc[0]+=1;
			tempLoc[1]+=1;
		}
		while (tempLoc[0]!=prevLoc[0] || tempLoc[1]!= prevLoc[1]) {
			if ( board[tempLoc[0]][tempLoc[1]]!=null ) {
				return true;
			}
			if ( nextLoc[0]-prevLoc[0]>0 && nextLoc[1]-prevLoc[1]>0 ) {
				tempLoc[0]-=1;
				tempLoc[1]-=1;
			}
			else if ( nextLoc[0]-prevLoc[0]>0 && nextLoc[1]-prevLoc[1]<0 ) {
				tempLoc[0]-=1;
				tempLoc[1]+=1;
			}
			else if ( nextLoc[0]-prevLoc[0]<0 && nextLoc[1]-prevLoc[1]>0 ) {
				tempLoc[0]+=1;
				tempLoc[1]-=1;
			}
			else if ( nextLoc[0]-prevLoc[0]<0 && nextLoc[1]-prevLoc[1]<0 ) {
				tempLoc[0]+=1;
				tempLoc[1]+=1;
			}
		}
		return false;
	}

	public ArrayList<int[][]> possibleMoveLocations() {
		ArrayList<int[][]> possMoves = new ArrayList<int[][]>();
		int currRow = this.location[0]+1;
		int currColumn = this.location[1]+1;
		while (currRow<8 && currColumn<8) {
			int[] possibleLocation = new int[] {currRow,currColumn};
			if (legalMove(this.location,possibleLocation)) {
				possMoves.add(new int[][] {this.location,possibleLocation});
			}
			currRow++;
			currColumn++;
		}
		currRow = this.location[0]+1;
		currColumn = this.location[1]-1;
		while (currRow<8 && currColumn>=0) {
			int[] possibleLocation = new int[] {currRow,currColumn};
			if (legalMove(this.location,possibleLocation)) {
				possMoves.add(new int[][] {this.location,possibleLocation});
			}
			currRow++;
			currColumn--;
		}
		currRow = this.location[0]-1;
		currColumn = this.location[1]-1;
		while (currRow>=0 && currColumn>=0) {
			int[] possibleLocation = new int[] {currRow,currColumn};
			if (legalMove(this.location,possibleLocation)) {
				possMoves.add(new int[][] {this.location,possibleLocation});
			}
			currRow--;
			currColumn--;
		}
		currRow = this.location[0]-1;
		currColumn = this.location[1]+1;
		while (currRow>=0 && currColumn<8) {
			int[] possibleLocation = new int[] {currRow,currColumn};
			if (legalMove(this.location,possibleLocation)) {
				possMoves.add(new int[][] {this.location,possibleLocation});
			}
			currRow--;
			currColumn++;
		}
		return possMoves;
	}
	
}
